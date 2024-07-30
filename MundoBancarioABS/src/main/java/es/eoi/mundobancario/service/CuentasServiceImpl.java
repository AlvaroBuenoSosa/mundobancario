package es.eoi.mundobancario.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.MovimientosEnum;
import es.eoi.mundobancario.repository.CuentasRepository;
import es.eoi.mundobancario.repository.MovimientoRepository;
import es.eoi.mundobancario.repository.PrestamoRepository;

@Service
public class CuentasServiceImpl implements CuentasService{
	
	@Autowired	
	CuentasRepository cuentaRepository;
	
	@Autowired
    MovimientoRepository movimientoRepository;
	
	@Autowired	
	PrestamoRepository prestamoRepository;

	@Override
	public List<Cuenta> encontrarCuentas() {				
		return cuentaRepository.findAll();
				
	}
	
	@Override
	public List<Cuenta> obtenerCuentasDeudoras() {
		return cuentaRepository.findAllBySaldoLessThan(0.0);
	}
	
	@Override
	 public Cuenta obtenerCuentaPorId(Integer id) {
	        return cuentaRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
	        

}
	@Override
	public Cuenta addCuenta(Cuenta nuevaCuenta) {
        return cuentaRepository.save(nuevaCuenta);
    }
	
	
	@Override
	public Cuenta updateAlias(Integer id, String nuevoAlias) {
        Cuenta cuenta = cuentaRepository.findById(id).get();
        cuenta.setAlias(nuevoAlias);
        return cuentaRepository.save(cuenta);
    }

	public MovimientoRepository getMovimientoRepository() {
		return movimientoRepository;
	}

	public void setMovimientoRepository(MovimientoRepository movimientoRepository) {
		this.movimientoRepository = movimientoRepository;
	}

	@Transactional public Cuenta realizarMovimiento(Integer id,
	 MovimientosEnum tipo, double importe, String description, LocalDateTime fecha) throws Exception { Cuenta cuenta =
	 cuentaRepository.findById(id).orElseThrow(() -> new
	 Exception("Cuenta no encontrada")); if (tipo == MovimientosEnum.PAGO &&
	 cuenta.getSaldo() <= 0) { throw new
	 Exception("No se puede realizar el pago, saldo insuficiente"); } Movimiento
	 movimiento = new Movimiento(); 
	 movimiento.setTipo(tipo);
	 movimiento.setImporte(importe); 
	 movimiento.setCuenta(cuenta);
	 movimiento.setDescripcion(description);
	 movimiento.setFecha(fecha);
	 movimientoRepository.save(movimiento); return actualizarSaldo(cuenta, tipo,
	 importe, description, fecha); }
	  
	 @Override public Cuenta actualizarSaldo(Cuenta cuenta, MovimientosEnum tipo,
	 double importe, String description, LocalDateTime fecha) { switch (tipo) { case INGRESO: case PRESTAMO:
	 cuenta.setSaldo(cuenta.getSaldo() + importe); break; case PAGO: case
	 AMORTIZACIÓN: case INTERES: cuenta.setSaldo(cuenta.getSaldo() - importe);
	 break; } return cuentaRepository.save(cuenta); }
	 
	   public List<Movimiento> getMovimientosByCuentaId(Integer num_cuenta) {

	        return movimientoRepository.findByCuenta(num_cuenta);
	 
	   }	
	   
	   @Override
	    public List<Prestamo> getPrestamosByCuenta(Integer num_cuenta) {
	        return prestamoRepository.findByCuenta(num_cuenta);
	    }
	   
	   @Override
	    @Transactional
	    public void solicitarPrestamo(Integer num_cuenta, Double importe, Integer plazos) {
	        Cuenta cuenta = cuentaRepository.findById(num_cuenta).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

	        if (prestamoRepository.existsByCuentaAndAmortizacionIsNull(cuenta)) {
	            throw new IllegalArgumentException("Ya hay un préstamo en curso");
	        }

	        Prestamo prestamo = new Prestamo();
	        prestamo.setCuenta(cuenta);
	        prestamo.setImporte(importe);
	        prestamo.setPlazos(plazos);
	        prestamo.setFechaSolicitud(LocalDate.now());

	        cuenta.setSaldo(cuenta.getSaldo() + importe);

	        cuenta.getPrestamo().add(prestamo);

	        for (int i = 1; i <= plazos; i++) {
	            Amortizacion amortizacion = new Amortizacion();
	            amortizacion.setImporte(importe / plazos);
	            amortizacion.setFecha(LocalDate.now().plusMonths(i));
	            amortizacion.setPrestamo(prestamo);
	            prestamo.getAmortizacion().add(amortizacion);
	        }

	        Movimiento movimiento = new Movimiento();
	        movimiento.setCuenta(cuenta);
	        movimiento.setFecha(LocalDateTime.now());
	        movimiento.setImporte(importe);
	        movimiento.setTipo(MovimientosEnum.PRESTAMO);
	        cuenta.getMovimiento().add(movimiento);

	        cuentaRepository.save(cuenta);
	    }

	    @Override
	    @Transactional
	    public void revisarAmortizaciones() {
	        List<Prestamo> prestamos = prestamoRepository.findAll();
	        for (Prestamo prestamo : prestamos) {
	            for (Amortizacion amortizacion : prestamo.getAmortizacion()) {
	                if (amortizacion.getFecha().equals(LocalDate.now()) && !amortizacion.getPrestamo().getCuenta().getMovimiento().stream()
	                        .anyMatch(mov -> mov.getFecha().toLocalDate().equals(LocalDate.now()) && mov.getTipo() == MovimientosEnum.AMORTIZACIÓN)) {
	                    
	                    Cuenta cuenta = prestamo.getCuenta();
	                    Movimiento movAmortizacion = new Movimiento();
	                    movAmortizacion.setCuenta(cuenta);
	                    movAmortizacion.setFecha(LocalDateTime.now());
	                    movAmortizacion.setImporte(-amortizacion.getImporte());
	                    movAmortizacion.setTipo(MovimientosEnum.AMORTIZACIÓN);
	                    cuenta.getMovimiento().add(movAmortizacion);
	                    cuenta.setSaldo(cuenta.getSaldo() - amortizacion.getImporte());

	                    Movimiento movInteres = new Movimiento();
	                    movInteres.setCuenta(cuenta);
	                    movInteres.setFecha(LocalDateTime.now());
	                    movInteres.setImporte(-amortizacion.getImporte() * 0.02);
	                    movInteres.setTipo(MovimientosEnum.INTERES);
	                    cuenta.getMovimiento().add(movInteres);
	                    cuenta.setSaldo(cuenta.getSaldo() - (amortizacion.getImporte() * 0.02));

	                    cuentaRepository.save(cuenta);
	                }
	            }
	        }
	    }
	   
	
}