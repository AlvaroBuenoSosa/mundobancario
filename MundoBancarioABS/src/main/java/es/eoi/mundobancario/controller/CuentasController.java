package es.eoi.mundobancario.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.MovimientosEnum;
import es.eoi.mundobancario.service.CuentasService;
import es.eoi.mundobancario.service.MovimientoService;

@RestController
@RequestMapping("/Cuentas")
public class CuentasController {

	@Autowired
	CuentasService cuentasService;

	@Autowired
	MovimientoService movimientoService;

	@GetMapping
	public ResponseEntity<List<Cuenta>> obtenerCuentas() {

		List<Cuenta> cuentas = cuentasService.encontrarCuentas();
		return ResponseEntity.ok(cuentas);

	}

	@GetMapping("/deudoras")
	public List<Cuenta> obtenerCuentasDeudoras() {
		return cuentasService.obtenerCuentasDeudoras();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Integer id) {
		Cuenta cuenta = cuentasService.obtenerCuentaPorId(id);
		return ResponseEntity.ok(cuenta);
	}

	@PostMapping
	public ResponseEntity<Cuenta> agregarCuenta(@RequestBody Cuenta nuevaCuenta) {
		Cuenta cuenta = cuentasService.addCuenta(nuevaCuenta);
		return ResponseEntity.ok(cuenta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> actualizarAlias(@PathVariable Integer id, @RequestBody String nuevoAlias) {
		cuentasService.updateAlias(id, nuevoAlias);
		return ResponseEntity.ok("Alias actualizado correctamente");
	}

	@GetMapping("/{id}/movimientos")
	public Cuenta obtenerMovimientosPorCuenta(@PathVariable Integer id) {
		return cuentasService.obtenerCuentaPorId(id);
	}

	@PostMapping("/{id}/movimientos")
	public ResponseEntity<String> realizarMovimiento(@PathVariable Integer id, @RequestParam MovimientosEnum tipo,
			@RequestParam double importe, @RequestParam String Description) {
		try {
			LocalDateTime fecha1 = LocalDateTime.now();
			cuentasService.realizarMovimiento(id, tipo, importe, Description, fecha1);
			return ResponseEntity.ok("Movimiento realizado con éxito");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}/prestamos")
	public ResponseEntity<List<Prestamo>> getPrestamosByCuenta(@PathVariable Integer id) {
		List<Prestamo> prestamos = cuentasService.getPrestamosByCuenta(id);
		if (prestamos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(prestamos);
	}

	@PostMapping("/{id}/ingresos")
	public ResponseEntity<String> crearIngreso(@PathVariable Integer id, @RequestParam double importe,
			@RequestParam String description) {
		try {
			movimientoService.crearIngreso(id, importe, description);
			return ResponseEntity.ok("Ingreso creado con éxito");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/{id}/pagos")
	public ResponseEntity<String> crearPago(@PathVariable Integer id, @RequestParam double importe,
			@RequestParam String description) {
		try {
			movimientoService.crearPago(id, importe, description);
			return ResponseEntity.ok("Pago creado con éxito");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/{id}/prestamos")
	public ResponseEntity<String> solicitarPrestamo(@PathVariable Integer id, @RequestParam Double importe,
			@RequestParam Integer plazos) {
		try {
			cuentasService.solicitarPrestamo(id, importe, plazos);
			return ResponseEntity.ok("Préstamo solicitado con éxito");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/amortizaciones/revisar")
	public ResponseEntity<String> revisarAmortizaciones() {
		cuentasService.revisarAmortizaciones();
		return ResponseEntity.ok("Revisión de amortizaciones completada");
	}

}


	 	 	 
