package es.eoi.mundobancario.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.ClienteReport;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired	
	ClienteRepository clientesRepository;
	
	
	
	@Override
	public ClienteDto findClientesDto(Integer id) {
		
		Cliente entity = clientesRepository.findById(id).get();	
		ClienteDto dto=new ClienteDto();
		dto.setNombre(entity.getNombre());
		dto.setUsuario(entity.getUsuario());
		dto.setEmail(entity.getEmail());
		
		return dto;
	}
	
	@Override
	public Cliente findClientes(Integer id) {
		return clientesRepository.findById(id).get();
	}
	
	
	@Override
    public List<ClienteDto> findAll() {
		List<Cliente> entity = clientesRepository.findAll();
		List<ClienteDto> entity1 = new ArrayList<>();
		int i = 0;
		for (i=0; i<entity.size(); i++) {
			
			ClienteDto dto=new ClienteDto();
			dto.setNombre(entity.get(i).getNombre());
			dto.setUsuario(entity.get(i).getUsuario());
			dto.setEmail(entity.get(i).getEmail());
			
			entity1.add(dto);
		}
		
		return entity1;
	
    }
	
	@Override
	public Cliente updateEmail(Integer id, String nuevoEmail) {
        Cliente cliente = clientesRepository.findById(id).get();
        cliente.setEmail(nuevoEmail);
        return clientesRepository.save(cliente);
    }
	
	@Override
	public Cliente addCliente(Cliente nuevoCliente) {
		return clientesRepository.save(nuevoCliente);
		
	}
	
	
	@Override
	public void deleteAll() {
		clientesRepository.deleteAll();
		
	}
	
	@Override
	public Cliente updateCliente(Cliente cliente) {
        return clientesRepository.save(cliente);
    }



	@Override
	public void delete(Integer id) {
		clientesRepository.deleteById(id);
		
	}
	
	@Override
    public ClienteReport getClienteReport(Integer id) {
        Cliente cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        ClienteReport dto = new ClienteReport();
        dto.setClienteId(cliente.getId());
        dto.setNombre(cliente.getNombre());

        // Initialize the list for accounts
        List<ClienteReport.Cuenta> cuentaDtos = new ArrayList<>();
        
        // Convert each account to DTO
        for (Cuenta cuenta : cliente.getCuentas()) {
            ClienteReport.Cuenta cuentaDto = new ClienteReport.Cuenta();
            cuentaDto.setNumCuenta(cuenta.getNum_cuenta());
            cuentaDto.setAlias(cuenta.getAlias());
            cuentaDto.setSaldo(cuenta.getSaldo());

            // Initialize the list for movements
            List<ClienteReport.Movimiento> movimientoDtos = new ArrayList<>();
            
            // Convert each movement to DTO
            for (Movimiento movimiento : cuenta.getMovimiento()) {
                ClienteReport.Movimiento movimientoDto = new ClienteReport.Movimiento();
                movimientoDto.setId(movimiento.getId());
                movimientoDto.setTipo(movimiento.getTipo());
                movimientoDto.setImporte(movimiento.getImporte());
                movimientoDto.setDescription(movimiento.getDescripcion());
                movimientoDto.setFecha(movimiento.getFecha());
                
                movimientoDtos.add(movimientoDto);
            }
            
            cuentaDto.setMovimientos(movimientoDtos);
            cuentaDtos.add(cuentaDto);
        }
        
        dto.setCuentas(cuentaDtos);

        return dto;
    }
	
	public void generateSimplePdf(Integer id,String filePath) throws IOException, DocumentException {
	    Document document = new Document();
	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(filePath));
	        document.open();
	        document.add(new Paragraph("This is a simple test PDF."));
	        document.close();
	    } catch (Exception e) {
	        System.err.println("Error generating simple PDF: " + e.getMessage()); // Print the error message
	        e.printStackTrace(); // Print the stack trace
	        throw new IOException("Error generating simple PDF", e); // Throw IOException with detailed message
	    }
	}
}

	








