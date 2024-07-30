package es.eoi.mundobancario.service;

import java.time.LocalDateTime;
import java.util.List;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.MovimientosEnum;

public interface CuentasService  {
	
	public List<Cuenta> encontrarCuentas();
	public List<Cuenta> obtenerCuentasDeudoras();
	Cuenta obtenerCuentaPorId(Integer id);
	public Cuenta addCuenta(Cuenta nuevaCuenta);
	Cuenta updateAlias(Integer id, String nuevoAlias);
	public Cuenta realizarMovimiento(Integer num_cuenta, MovimientosEnum tipo, double importe, String description, LocalDateTime fecha) throws Exception;
	Cuenta actualizarSaldo(Cuenta cuenta, MovimientosEnum tipo, double importe, String description, LocalDateTime fecha);
	List<Prestamo> getPrestamosByCuenta(Integer num_cuenta);
	void solicitarPrestamo(Integer num_cuenta, Double importe, Integer plazos);
    void revisarAmortizaciones();

}
