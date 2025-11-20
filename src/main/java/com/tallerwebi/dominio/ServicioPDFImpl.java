package com.tallerwebi.dominio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;
import java.io.OutputStream;

@Service
public class ServicioPDFImpl implements ServicioPDF {

    @Override
    public void generarCredencial(Usuario usuario, OutputStream outputStream) throws Exception {

        Document document = new Document(PageSize.A5);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        BaseColor brandColor = new BaseColor(0x43, 0xA0, 0x47); // #43a047

        Font fontLogo = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.WHITE);
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, brandColor);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

        PdfContentByte canvas = writer.getDirectContent();

        canvas.setColorFill(brandColor);
        canvas.rectangle(0, document.getPageSize().getHeight() - 70,
                        document.getPageSize().getWidth(), 70);
        canvas.fill();

        Phrase phrase = new Phrase(0, "VetConnect", fontLogo); 

        ColumnText.showTextAligned(
                canvas,
                Element.ALIGN_CENTER,
                phrase,
                document.getPageSize().getWidth() / 2,
                document.getPageSize().getHeight() - 45,
                0
        );



        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Paragraph titulo = new Paragraph("Credencial de Usuario", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        Paragraph ownerTitle = new Paragraph("DATOS DEL PROPIETARIO", fontSubtitulo);
        ownerTitle.setSpacingAfter(5);
        document.add(ownerTitle);

        document.add(new Paragraph("Nombre: " + usuario.getNombre() + " " + usuario.getApellido(), fontNormal));
        document.add(new Paragraph("ID de Usuario: " + usuario.getId(), fontNormal));
        document.add(Chunk.NEWLINE);

        canvas.setLineWidth(1f);
        canvas.setColorStroke(brandColor);
        canvas.moveTo(40, writer.getVerticalPosition(true) - 5);
        canvas.lineTo(document.getPageSize().getWidth() - 40, writer.getVerticalPosition(true) - 5);
        canvas.stroke();
        document.add(Chunk.NEWLINE);

        Paragraph petsTitle = new Paragraph(
                "MASCOTAS REGISTRADAS (" + usuario.getMascotas().size() + ")",
                fontSubtitulo
        );
        petsTitle.setSpacingBefore(10);
        petsTitle.setSpacingAfter(10);
        document.add(petsTitle);

        if (usuario.getMascotas().isEmpty()) {

            document.add(new Paragraph("No hay mascotas registradas asociadas.", fontNormal));

        } else {

            for (Mascota mascota : usuario.getMascotas()) {

                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100);

                PdfPCell cell = new PdfPCell();
                cell.setPadding(8);
                cell.setBorderColor(brandColor);
                cell.setBorderWidth(1.5f);

                Paragraph info = new Paragraph(
                        "Nombre: " + mascota.getNombre() + "\n" +
                        "Especie: " + mascota.getTipoDeMascota() + "\n" +
                        "Raza: " + mascota.getRaza(),
                        fontNormal
                );

                cell.addElement(info);
                table.addCell(cell);
                document.add(table);

                document.add(Chunk.NEWLINE);
            }
        }

        canvas.setColorFill(brandColor);
        canvas.rectangle(0, 0, document.getPageSize().getWidth(), 25);
        canvas.fill();

        ColumnText.showTextAligned(
                canvas, Element.ALIGN_CENTER,
                new Phrase("VetConnect", 
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)),
                document.getPageSize().getWidth() / 2,
                10,
                0
        );

        document.close();
    }

}