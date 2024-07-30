package es.eoi.mundobancario.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import es.eoi.mundobancario.components.PdfGenerator;
import es.eoi.mundobancario.dto.ClienteReport;
import es.eoi.mundobancario.service.ClienteService;

@RestController
@RequestMapping("/reports/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private PdfGenerator pdfGenerator;

    @PostMapping("/{id}")
    public ResponseEntity<String> generateClientReport(@PathVariable("id") Integer id) {
        try {
            // Retrieve client data
            ClienteReport clienteReport = clienteService.getClienteReport(id);

            // Define the file path where the PDF will be saved
            String filePath = "static/reports/EOI_BANK_CLIENTE_" + String.format("%03d", id) + ".pdf";

            // Ensure the directory exists
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Generate the PDF
            pdfGenerator.generateClientePdf(clienteReport, filePath);

            return ResponseEntity.ok("PDF report generated successfully at: " + filePath);
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteReport> getClienteReport(@PathVariable Integer id) {
        try {
            ClienteReport report = clienteService.getClienteReport(id);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
  
}
