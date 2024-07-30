package es.eoi.mundobancario.service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.MovimientosEnum;

import es.eoi.mundobancario.repository.CuentasRepository;

import es.eoi.mundobancario.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService{

	@Autowired
    private CuentasRepository cuentaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    
    @Override
    @Transactional
    public Prestamo solicitarPrestamo(Integer num_cuenta, Double importe, Integer plazos) {
        Cuenta cuenta = cuentaRepository.findById(num_cuenta).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if (prestamoRepository.existsByCuentaAndAmortizacionIsNull(cuenta)) {
            throw new IllegalArgumentException("Ya hay un préstamo en curso");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setCuenta(cuenta);
        prestamo.setImporte(importe);
        prestamo.setPlazos(plazos);
        prestamo.setFecha(LocalDateTime.now());
        prestamo.setDescripcion("Préstamo solicitado");

        cuenta.setSaldo(cuenta.getSaldo() + importe);

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

        cuenta.getPrestamo().add(prestamo);
        cuentaRepository.save(cuenta);

        return prestamo;
    }
    
    @Override
    public List<Prestamo> obtenerPrestamosPorCuenta(Integer num_cuenta) {
        Cuenta cuenta = cuentaRepository.findById(num_cuenta).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        return cuenta.getPrestamo();
    }

    @Override
    @Transactional
    public void revisarAmortizaciones(Integer num_cuenta) {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        for (Prestamo prestamo : prestamos) {
            for (Amortizacion amortizacion : prestamo.getAmortizacion()) {
                if (amortizacion.getFecha().equals(LocalDate.now())) {
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
