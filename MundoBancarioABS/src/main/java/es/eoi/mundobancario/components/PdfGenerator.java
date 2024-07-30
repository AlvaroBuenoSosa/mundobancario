package es.eoi.mundobancario.components;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import es.eoi.mundobancario.dto.ClienteReport;
import es.eoi.mundobancario.enums.MovimientosEnum;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfGenerator {

    public void generateClientePdf(ClienteReport dto, String filePath) throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 12);
            new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GREEN);
            new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);

            // Title
            Paragraph title = new Paragraph("Cliente Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Client Details
            document.add(new Paragraph("Cliente ID: " + dto.getClienteId(), regularFont));
            document.add(new Paragraph("Nombre: " + dto.getNombre(), regularFont));
            document.add(Chunk.NEWLINE);

            // Accounts
            for (ClienteReport.Cuenta cuenta : dto.getCuentas()) {
                document.add(new Paragraph("Cuenta Número: " + cuenta.getNumCuenta(), headerFont));
                document.add(new Paragraph("Alias: " + cuenta.getAlias(), regularFont));
                document.add(new Paragraph("Saldo: " + formatImporte(cuenta.getSaldo()), regularFont));
                document.add(Chunk.NEWLINE);

                // Movements
                for (ClienteReport.Movimiento movimiento : cuenta.getMovimientos()) {
                    Paragraph movimientoPara = new Paragraph();
                    movimientoPara.add(new Chunk("Tipo: " + movimiento.getTipo() + ", ", regularFont));
                    movimientoPara.add(new Chunk("Importe: " + formatImporte(movimiento.getImporte()), getColorForMovimiento(movimiento.getTipo())));
                    movimientoPara.add(new Chunk(", Descripción: " + movimiento.getDescription() + ", Fecha: " + movimiento.getFecha(), regularFont));
                    document.add(movimientoPara);
                    document.add(Chunk.NEWLINE);
                }
                document.add(Chunk.NEWLINE);
            }

            document.close();
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage()); // Print the error message
            e.printStackTrace(); // Print the stack trace
            throw new IOException("Error generating PDF", e); // Throw IOException with detailed message
        }
    }

    private String formatImporte(double importe) {
        return String.format("%.2f", importe);
    }

    private Font getColorForMovimiento(MovimientosEnum tipo) {
        if (tipo == MovimientosEnum.INGRESO || tipo == MovimientosEnum.PRESTAMO) {
            return new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.GREEN);
        } else {
            return new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
        }
    }
}
