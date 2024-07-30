package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.entity.Prestamo;

public interface PrestamoService {
	
	Prestamo solicitarPrestamo(Integer num_cuenta, Double importe, Integer plazos);
	List<Prestamo> obtenerPrestamosPorCuenta(Integer num_cuenta);
    void revisarAmortizaciones(Integer num_cuenta);
}
