package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.service.ClienteService;

@RestController
@RequestMapping("/Clientes")
public class ClientesController {

	@Autowired
	ClienteService clienteService;
	
	
	
	//Todas los clientes
	@GetMapping
	public ResponseEntity<List<ClienteDto>> findAll() {
		return ResponseEntity.ok(clienteService.findAll());
	}
	
	
	//Cliente por id
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> find(@PathVariable Integer id) {
		try {
			ClienteDto clienteDto = clienteService.findClientesDto(id);
			return new ResponseEntity<>(clienteDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Login (Crear el login)
	@PostMapping("/login") // para el front
	public ResponseEntity<ClienteDto> create(@PathVariable Integer id) {
		try {
			ClienteDto clienteDto = clienteService.findClientesDto(id);
			return new ResponseEntity<>(clienteDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Cuenta por id del cliente
	
	@GetMapping("/clientes/{id}/cuentas")
	public ResponseEntity<List<Cuenta>> obtenerCuentasCliente(@PathVariable Integer id) {
		try {
			Cliente cliente = clienteService.findClientes(id);
			List<Cuenta> cuentas = cliente.getCuentas();	
			return new ResponseEntity<>(cuentas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	//Cambiar el email en el cliente
	
	 @PutMapping("/{id}/email")
	    public ResponseEntity<String> actualizarEmail(@PathVariable Integer id, @RequestBody String nuevoEmail) {
	        clienteService.updateEmail(id, nuevoEmail);
	        return ResponseEntity.ok("Email actualizado correctamente");
	    }
	 
	 //Creacion de cliente nuevo
	@PostMapping
		public ResponseEntity<String> agregarCliente(@RequestBody Cliente nuevoCliente) {		
	    clienteService.addCliente(nuevoCliente);
	    return ResponseEntity.ok("Cliente agregado correctamente");
		}
	}

