package rs.ac.uns.ftn.nistagram.campaign.report.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDF {

    public static void createAndSave(String location, String text) {
        PDDocument pdf = PDF.create("Campaign report");
        PDF.write(pdf, text);
        PDF.save(pdf, location);
    }

    public static void test(String location) {
        PDDocument pdf = PDF.create("Hello world");
        String text = "Hello world. \nI am feeling extremely grateful today because\n I got my first dose of vaxx. \nNow since you all might be wondering I \ndid in fact get it in the hospital.";
        PDF.write(pdf, text);
        try {
            pdf.save(new File(location));
            pdf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PDDocument create(String title) {
        PDDocument pdf = new PDDocument();
        pdf.addPage(new PDPage());
        PDDocumentInformation info = pdf.getDocumentInformation();
        info.setAuthor("Nistagram");
        info.setTitle(title);
        return pdf;
    }

    public static void save(PDDocument pdf, String location) {
        try {
            pdf.save(location);
            pdf.close();
        }
        catch (IOException e) {
            System.out.println("Failed to save PDF.");
        }
    }

    public static void write(PDDocument pdf, String multiLineText) {
        PDPage page = pdf.getPage(0);
        try {
            PDPageContentStream stream = new PDPageContentStream(pdf, page);
            stream.beginText();

            stream.newLineAtOffset(25, 700);
            stream.setFont(PDType1Font.TIMES_ROMAN, 12);
            stream.setLeading(14.5f);

            String[] tokens = multiLineText.split("\n");
            for (String token : tokens) {
                stream.showText(token);
                stream.newLine();
            }

            stream.endText();
            stream.close();
        }
        catch (IOException e) {
            System.out.println("Failed to open a stream for PDF file.");
        }
    }
}
