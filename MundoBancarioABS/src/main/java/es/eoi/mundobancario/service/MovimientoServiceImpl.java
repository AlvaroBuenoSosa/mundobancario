package es.eoi.mundobancario.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.enums.MovimientosEnum;
import es.eoi.mundobancario.repository.CuentasRepository;
import es.eoi.mundobancario.repository.MovimientoRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService{
	
	@Autowired
    private MovimientoRepository movimientoRepository;
	
	@Autowired
    private CuentasRepository cuentasRepository;

	
    public List<Movimiento> obtenerMovimientosPorCuenta(Integer id) {
        return movimientoRepository.findByCuenta(id);
    }


	@Override
	public void crearIngreso(Integer num_cuenta, double importe, String description) {
		Cuenta cuenta = cuentasRepository.findById(num_cuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipo(MovimientosEnum.INGRESO);
        movimiento.setImporte(importe);
        movimiento.setDescripcion(description);
        movimiento.setFecha(LocalDateTime.now());

        movimientoRepository.save(movimiento);

        // Update account balance
        cuenta.setSaldo(cuenta.getSaldo() + importe);
        cuentasRepository.save(cuenta);
		
	}
	
	@Override
    public void crearPago(Integer num_cuenta, double importe, String description) {
        Cuenta cuenta = cuentasRepository.findById(num_cuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipo(MovimientosEnum.PAGO);
        movimiento.setImporte(importe);
        movimiento.setDescripcion(description);
        movimiento.setFecha(LocalDateTime.now());

        movimientoRepository.save(movimiento);

        // Update account balance
        cuenta.setSaldo(cuenta.getSaldo() - importe);
        cuentasRepository.save(cuenta);
    }
    

}
