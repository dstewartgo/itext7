package com.itextpdf.canvas;

import com.itextpdf.core.exceptions.PdfException;
import com.itextpdf.core.fonts.PdfStandardFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.testutils.CompareTool;
import com.itextpdf.text.DocWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class PdfCanvasTest {

    static final public String sourceFolder = "./src/test/resources/com/itextpdf/canvas/PdfCanvasTest/";
    static final public String destinationFolder = "./target/test/com/itextpdf/canvas/PdfCanvasTest/";

    @BeforeClass
    static public void beforeClass() {
        new File(destinationFolder).mkdirs();
    }

    @Test
    public void createSimpleCanvas() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "simpleCanvas.pdf");
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas.rectangle(100, 100, 100, 100).fill();
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "simpleCanvas.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void createSimpleCanvasWithDrawing() throws IOException, PdfException {

        final String fileName = "simpleCanvasWithDrawing.pdf";

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + fileName);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas
                .saveState()
                .setLineWidth(30)
                .moveTo(36, 700)
                .lineTo(300, 300)
                .stroke()
                .restoreState();
        canvas
                .saveState()
                .rectangle(250, 500, 100, 100)
                .fill()
                .restoreState();

        canvas
                .saveState()
                .circle(100, 400, 25)
                .fill()
                .restoreState();

        canvas
                .saveState()
                .roundRectangle(100, 650, 100, 100, 10)
                .fill()
                .restoreState();

        canvas
                .saveState()
                .setLineWidth(10)
                .roundRectangle(250, 650, 100, 100, 10)
                .stroke()
                .restoreState();

        canvas
                .saveState()
                .setLineWidth(5)
                .arc(400, 650, 550, 750, 0, 180)
                .stroke()
                .restoreState();

        canvas
                .saveState()
                .setLineWidth(5)
                .moveTo(400, 550)
                .curveTo(500, 570, 450, 450, 550, 550)
                .stroke()
                .restoreState();

        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + fileName);
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void createSimpleCanvasWithText() throws IOException, PdfException {

        final String fileName = "simpleCanvasWithText.pdf";

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + fileName);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        //Initialize canvas and write text to it
        canvas
                .saveState()
                .beginText()
                .moveText(36, 750)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.Helvetica), 16)
                .showText("Hello Helvetica!")
                .endText()
                .restoreState();

        canvas
                .saveState()
                .beginText()
                .moveText(36, 700)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.HelveticaBoldOblique), 16)
                .showText("Hello Helvetica Bold Oblique!")
                .endText()
                .restoreState();

        canvas
                .saveState()
                .beginText()
                .moveText(36, 650)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.Courier), 16)
                .showText("Hello Courier!")
                .endText()
                .restoreState();

        canvas
                .saveState()
                .beginText()
                .moveText(36, 600)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.TimesItalic), 16)
                .showText("Hello Times Italic!")
                .endText()
                .restoreState();

        canvas
                .saveState()
                .beginText()
                .moveText(36, 550)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.Symbol), 16)
                .showText("Hello Ellada!")
                .endText()
                .restoreState();

        canvas
                .saveState()
                .beginText()
                .moveText(36, 500)
                .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.ZapfDingbats), 16)
                .showText("Hello ZapfDingbats!")
                .endText()
                .restoreState();

        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + fileName);
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void createSimpleCanvasWithPageFlush() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "simpleCanvasWithPageFlush.pdf");
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas.rectangle(100, 100, 100, 100).fill();
        page1.flush();
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "simpleCanvasWithPageFlush.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void createSimpleCanvasWithFullCompression() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "simpleCanvasWithFullCompression.pdf");
        PdfWriter writer = new PdfWriter(fos);
        writer.setFullCompression(true);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas.rectangle(100, 100, 100, 100).fill();
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "simpleCanvasWithFullCompression.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void createSimpleCanvasWithPageFlushAndFullCompression() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "simpleCanvasWithPageFlushAndFullCompression.pdf");
        PdfWriter writer = new PdfWriter(fos);
        writer.setFullCompression(true);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        PdfPage page1 = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas.rectangle(100, 100, 100, 100).fill();
        page1.flush();
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "simpleCanvasWithPageFlushAndFullCompression.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        PdfDictionary page = reader.getPageN(1);
        Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        reader.close();
    }

    @Test
    public void create1000PagesDocument() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "1000PagesDocument.pdf");
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        int pageCount = 1000;
        for (int i = 0; i < pageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.rectangle(100, 100, 100, 100).fill();
            page.flush();
        }
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "1000PagesDocument.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        for (int i = 1; i <= pageCount; i++) {
            PdfDictionary page = reader.getPageN(i);
            Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        }
        reader.close();
    }

    @Test
    public void create1000PagesDocumentWithText() throws IOException, PdfException {

        final String file = "1000PagesDocumentWithText.pdf";

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + file);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        int pageCount = 1000;
        for (int i = 0; i < pageCount; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.saveState()
                    .beginText()
                    .moveText(36, 650)
                    .setFontAndSize(new PdfStandardFont(pdfDoc, PdfStandardFont.Courier), 16)
                    .showText("Page " + (i + 1))
                    .endText();

            canvas.rectangle(100, 100, 100, 100).fill();

            page.flush();
        }
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + file);
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        for (int i = 1; i <= pageCount; i++) {
            PdfDictionary page = reader.getPageN(i);
            Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        }
        reader.close();
    }

    @Test
    public void create1000PagesDocumentWithFullCompression() throws IOException, PdfException {

        final String author = "Alexander Chingarev";
        final String creator = "iText 6";
        final String title = "Empty iText 6 Document";

        FileOutputStream fos = new FileOutputStream(destinationFolder + "1000PagesDocumentWithFullCompression.pdf");
        PdfWriter writer = new PdfWriter(fos);
        writer.setFullCompression(true);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.getInfo().setAuthor(author).
                setCreator(creator).
                setTitle(title);
        for (int i = 0; i < 1000; i++) {
            PdfPage page = pdfDoc.addNewPage();
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.rectangle(100, 100, 100, 100).fill();
            page.flush();
        }
        pdfDoc.close();

        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(destinationFolder + "1000PagesDocumentWithFullCompression.pdf");
        HashMap<String, String> info = reader.getInfo();
        Assert.assertEquals(author, info.get("Author"));
        Assert.assertEquals(creator, info.get("Creator"));
        Assert.assertEquals(title, info.get("Title"));
        for (int i = 1; i <= 1000; i++) {
            PdfDictionary page = reader.getPageN(i);
            Assert.assertEquals(com.itextpdf.text.pdf.PdfName.PAGE, page.get(com.itextpdf.text.pdf.PdfName.TYPE));
        }
        reader.close();
    }

    @Test
    public void comparePerformanceTest() throws IOException, PdfException, DocumentException {
        comparePerformance(false);
    }

    @Test
    public void comparePerformanceTestFullCompression() throws IOException, PdfException, DocumentException {
        comparePerformance(true);
    }

    @Test
    public void copyPagesTest() throws IOException, PdfException, DocumentException, InterruptedException {
        FileOutputStream fos1 = new FileOutputStream(destinationFolder + "copyPages1.pdf");
        PdfWriter writer1 = new PdfWriter(fos1);
        PdfDocument pdfDoc1 = new PdfDocument(writer1);

        PdfPage page1 = pdfDoc1.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page1);
        canvas.rectangle(100, 600, 100, 100);
        canvas.fill();
        canvas.beginText();
        canvas.setFontAndSize(new PdfStandardFont(pdfDoc1, PdfStandardFont.Courier), 12);
        canvas.setTextMatrix(1, 0, 0, 1, 100, 500);
        canvas.showText("Hello World!");
        canvas.endText();

        FileOutputStream fos2 = new FileOutputStream(destinationFolder + "copyPages2.pdf");
        PdfWriter writer2 = new PdfWriter(fos2);
        PdfDocument pdfDoc2 = new PdfDocument(writer2);
        PdfPage page2 = page1.copy(pdfDoc2);
        pdfDoc2.addPage(page2);

        page1.flush();
        page2.flush();

        pdfDoc1.close();
        pdfDoc2.close();

        PdfReader reader = new PdfReader(destinationFolder + "copyPages2.pdf");
        com.itextpdf.text.pdf.PdfDictionary page = reader.getPageN(1);
        Assert.assertNotNull(page.get(PdfName.PARENT));
        reader.close();
        Assert.assertNull(new CompareTool(destinationFolder + "copyPages1.pdf", destinationFolder + "copyPages2.pdf").compareByContent(destinationFolder, "diff_"));
    }

    private void comparePerformance(boolean fullCompression) throws IOException, PdfException, DocumentException {
        int pageCount = 100000;
        int runCount = 10;

        final String author = "Alexander Chingarev";
        final String creator = "iText";
        final String title = "Empty iText Document";

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            Document.compress = false;
            Document document = new Document();
            FileOutputStream fos = new FileOutputStream(destinationFolder + "comparePerformanceTest_iText5.pdf");
            com.itextpdf.text.pdf.PdfWriter writer = com.itextpdf.text.pdf.PdfWriter.getInstance(document, fos);
            if (fullCompression)
                writer.setFullCompression();
            document.addAuthor(author);
            document.addCreator(creator);
            document.addTitle(title);
            document.open();
            for (int k = 0; k < pageCount; k++) {
                document.newPage();
                PdfContentByte cb = writer.getDirectContent();
                cb.rectangle(100, 100, 100, 100);
                cb.fill();
            }
            document.close();
        }
        long t2 = System.currentTimeMillis();
        long iText5Time = t2 - t1;
        System.out.println(String.format("iText5 time: %dms", iText5Time));

        t1 = System.currentTimeMillis();
        for (int i = 0; i < runCount; i++) {
            FileOutputStream fos = new FileOutputStream(destinationFolder + "comparePerformanceTest_iText6.pdf");
            PdfWriter writer = new PdfWriter(fos);
            writer.setFullCompression(fullCompression);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.getInfo().setAuthor(author).
                    setCreator(creator).
                    setTitle(title);
            for (int k = 0; k < pageCount; k++) {
                PdfPage page = pdfDoc.addNewPage();
                PdfCanvas canvas = new PdfCanvas(page);
                canvas.rectangle(100, 100, 100, 100).fill();
                page.flush();
            }
            pdfDoc.close();
        }
        t2 = System.currentTimeMillis();
        long iText6Time = t2 - t1;
        System.out.println(String.format("iText6 time: %dms", iText6Time));
        Assert.assertTrue(iText5Time > iText6Time);

    }


}
