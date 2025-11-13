package com.tallerwebi.dominio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.OutputStream;

@Service
public class ServicioPDFImpl implements ServicioPDF {

    @Override
    public void generarCredencial(Usuario usuario, OutputStream outputStream) throws Exception {

        // Crear el documento PDF
        Document document = new Document(PageSize.A5); // Tamaño de credencial
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // 1. FUENTES Y ESTILOS
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

        // 2. TÍTULO
        Paragraph titulo = new Paragraph("Credencial de Usuario VetConnect", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // 3. DATOS DEL DUEÑO
        document.add(new Paragraph("DATOS DEL PROPIETARIO", fontSubtitulo));
        document.add(new Paragraph("Nombre: " + usuario.getNombre() + " " + usuario.getApellido(), fontNormal));
        document.add(new Paragraph("ID de Usuario: " + usuario.getId(), fontNormal));
        document.add(Chunk.NEWLINE); // Salto de línea

        // 4. DATOS DE LAS MASCOTAS
        document.add(new Paragraph("MASCOTAS REGISTRADAS (" + usuario.getMascotas().size() + ")", fontSubtitulo));
        document.add(Chunk.NEWLINE);

        if (usuario.getMascotas().isEmpty()) {
            document.add(new Paragraph("No hay mascotas registradas asociadas a esta credencial.", fontNormal));
        } else {
            for (Mascota mascota : usuario.getMascotas()) {
                document.add(new Paragraph("- - - - - - - - - - - - - - - - - - - -", fontNormal));
                document.add(new Paragraph("Nombre: " + mascota.getNombre(), fontNormal));
                document.add(new Paragraph("Especie: " + mascota.getTipoDeMascota(), fontNormal));
                document.add(new Paragraph("Raza: " + mascota.getRaza(), fontNormal));
                // Opcional: Agregar ID de mascota si lo necesitas
                // document.add(new Paragraph("ID Mascota: " + mascota.getId(), fontNormal));
            }
        }

        document.close();
    }
}