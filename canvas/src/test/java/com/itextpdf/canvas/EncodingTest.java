package com.itextpdf.canvas;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontEncoding;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.CompareTool;
import com.itextpdf.core.testutils.annotations.type.IntegrationTest;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Category(IntegrationTest.class)
public class EncodingTest {
    static final public String sourceFolder = "./src/test/resources/com/itextpdf/canvas/EncodingTest/";
    static final public String outputFolder = "./target/test/com/itextpdf/canvas/EncodingTest/";

    @BeforeClass
    static public void beforeClass() {
        new File(outputFolder).mkdirs();
    }

    @Test
    public void surrogatePairTest() throws IOException, InterruptedException {
        String fileName = "surrogatePairTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, sourceFolder + "DejaVuSans.ttf", PdfEncodings.IDENTITY_H);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 750).
                setFontAndSize(font, 72).
                showText("\uD835\uDD59\uD835\uDD56\uD835\uDD5D\uD835\uDD5D\uD835\uDD60\uD83D\uDE09\uD835\uDD68" +
                "\uD835\uDD60\uD835\uDD63\uD835\uDD5D\uD835\uDD55").
                endText().
                restoreState();
        canvas.release();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void Cp855EncodingTimesRomanTest() throws IOException, DocumentException, InterruptedException {
        String filename = "Cp855EncodingTimesRomanTest.pdf";
        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp855", true);
        Document doc = new Document();

        com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new FileOutputStream(outputFolder + filename));
        doc.open();
        doc.newPage();
        Font f = new Font(bf);
        doc.add(new Paragraph("М", f));
        doc.add(new Paragraph("и", f));
        doc.add(new Paragraph("р", f));
        doc.close();

        new com.itextpdf.testutils.CompareTool().compareByContent(outputFolder + filename, sourceFolder + "cmp_" + filename, outputFolder, "diff");
    }

    @Test
    public void customSimpleEncodingTimesRomanTest() throws IOException, InterruptedException {
        String fileName = "customSimpleEncodingTimesRomanTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, sourceFolder + "FreeSans.ttf",
                "# simple 1 0020 041c 0456 0440 044a 0050 0065 0061 0063", true);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 806).
                setFontAndSize(font, 12).
                showText("Міръ Peace").
                endText().
                restoreState();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void customFullEncodingTimesRomanTest() throws IOException, InterruptedException {
        String fileName = "customFullEncodingTimesRomanTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, FontConstants.TIMES_ROMAN,
                "# full 'A' Aring 0041 'E' Egrave 0045 32 space 0020");
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 806).
                setFontAndSize(font, 12).
                showText("A E").
                endText().
                restoreState();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void notdefInStandardFontTest() throws IOException, InterruptedException {
        String fileName = "notdefInStandardFontTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, FontConstants.HELVETICA,
                "# full 'A' Aring 0041 'E' abc11 0045 32 space 0020");
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 36).
                showText("A E").
                endText().
                restoreState();

        font = PdfFont.createFont(doc, FontConstants.HELVETICA, PdfEncodings.WINANSI);
        canvas.
                saveState().
                beginText().
                moveText(36, 756).
                setFontAndSize(font, 36).
                showText("\u0188").
                endText().
                restoreState();

        doc.close();


        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void notdefInTrueTypeFontTest() throws IOException, InterruptedException {
        String fileName = "notdefInTrueTypeFontTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);
        PdfFont font = PdfFont.createFont(doc, sourceFolder + "FreeSans.ttf", "# simple 32 0020 00C5 1987", true);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 36).
                showText("Å \u1987").
                endText().
                restoreState();
        font = PdfFont.createFont(doc, sourceFolder + "FreeSans.ttf", PdfEncodings.WINANSI, true);
        canvas.
                saveState().
                beginText().
                moveText(36, 756).
                setFontAndSize(font, 36).
                showText("\u1987").
                endText().
                restoreState();
        doc.close();


        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void notdefInType0Test() throws IOException, InterruptedException {
        String fileName = "notdefInType0Test.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, sourceFolder + "FreeSans.ttf", PdfEncodings.IDENTITY_H);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 36).
                showText("Å \u1987").
                endText().
                restoreState();

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test @Ignore("Should we update built-in font's descriptor in case not standard font encoding?")
    public void symbolDefaultFontTest() throws IOException, InterruptedException {
        String fileName = "symbolDefaultFontTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, FontConstants.SYMBOL, PdfEncodings.WINANSI);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        String str = new String();
        for (int i = 32; i <= 100; i++) {
            str += (char)i;
        }
        canvas.
                saveState().
                beginText().
                moveText(36, 806).
                setFontAndSize(font, 12).
                showText(str).
                endText();

        str = "";
        for (int i = 101; i <= 190; i++) {
            str += (char)i;
        }
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 12).
                showText(str).
                endText();
        str = "";
        for (int i = 191; i <= 254; i++) {
            str += (char)i;
        }
        canvas.
                beginText().
                moveText(36, 766).
                showText(str).
                endText().
                restoreState();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void symbolTrueTypeFontWinAnsiTest() throws IOException, InterruptedException {
        String fileName = "symbolTrueTypeFontWinAnsiTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, sourceFolder + "Symbols1.ttf", true);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        String str = new String();
        for (int i = 32; i <= 65; i++) {
            str+= (char)i;
        }
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 36).
                showText(str).
                endText();

        str = "";
        for (int i = 65; i <= 190; i++) {
            str+= (char)i;
        }
        canvas.
                saveState().
                beginText().
                moveText(36, 756).
                setFontAndSize(font, 36).
                showText(str).
                endText();
        str = "";
        for (int i = 191; i <= 254; i++) {
            str+= (char)i;
        }
        canvas.
                beginText().
                moveText(36, 726).
                setFontAndSize(font, 36).
                showText(str).
                endText().
                restoreState();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }

    @Test
    public void symbolTrueTypeFontIdentityTest() throws IOException, InterruptedException {
        String fileName = "symbolTrueTypeFontIdentityTest.pdf";
        PdfWriter writer = new PdfWriter(outputFolder + fileName);
        PdfDocument doc = new PdfDocument(writer);

        PdfFont font = PdfFont.createFont(doc, sourceFolder + "Symbols1.ttf", PdfEncodings.IDENTITY_H);
        PdfCanvas canvas = new PdfCanvas(doc.addNewPage());
        StringBuilder builder = new StringBuilder();
        for (int i = 32; i <= 100; i++) {
            builder.append((char)i);
        }
        String str = builder.toString();
        canvas.
                saveState().
                beginText().
                moveText(36, 786).
                setFontAndSize(font, 36).
                showText(str).
                endText();

        str = "";
        for (int i = 101; i <= 190; i++) {
            str+= (char)i;
        }
        canvas.
                saveState().
                beginText().
                moveText(36, 746).
                setFontAndSize(font, 36).
                showText(str).
                endText();
        str = "";
        for (int i = 191; i <= 254; i++) {
            str+= (char)i;
        }
        canvas.
                beginText().
                moveText(36, 766).
                showText(str).
                endText().
                restoreState();
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outputFolder + fileName, sourceFolder + "cmp_" + fileName, outputFolder, "diff_"));
    }
}