package es.eoi.mundobancario.service;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.ClienteReport;
import es.eoi.mundobancario.entity.Cliente;

public interface ClienteService {
	
	public List<ClienteDto> findAll();
	
	public Cliente findClientes(Integer id);
	
	public ClienteDto findClientesDto(Integer id);
	
	public Cliente updateCliente(Cliente cliente);
	
	public Cliente updateEmail(Integer id, String nuevoEmail);
	
	public Cliente addCliente(Cliente nuevoCliente);
		
	public void deleteAll();

	public void delete(Integer id);
	
	ClienteReport getClienteReport(Integer id);
	
	void generateSimplePdf(Integer id, String filePath) throws IOException, DocumentException;

}
