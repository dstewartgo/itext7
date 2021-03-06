package com.itextpdf.layout;

import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ListSymbolAlignment;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.ArrayList;

@Category(IntegrationTest.class)
public class ListTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/layout/ListTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/layout/ListTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void nestedListTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "nestedListTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_nestedListTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        List romanList2 = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add((ListItem) new ListItem("Three").add(romanList2));

        List list = new List(ListNumberingType.DECIMAL).setSymbolIndent(20).
                add("One").add("Two").add("Three").add("Four").add((ListItem) new ListItem("Roman List").add(romanList)).
                add("Five").add("Six").add((ListItem) new ListItem().add(romanList2));
        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listNumberingTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listNumberingTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listNumberingTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        java.util.List<List> lists = new ArrayList<>();
        lists.add(new List(ListNumberingType.DECIMAL));
        lists.add(new List(ListNumberingType.ROMAN_LOWER));
        lists.add(new List(ListNumberingType.ROMAN_UPPER));
        lists.add(new List(ListNumberingType.ENGLISH_LOWER));
        lists.add(new List(ListNumberingType.ENGLISH_UPPER));
        lists.add(new List(ListNumberingType.GREEK_LOWER));
        lists.add(new List(ListNumberingType.GREEK_UPPER));

        for (int i = 1; i <= 30; i++) {
            for (List list : lists) {
                list.add("Item #" + i);
            }
        }

        for (List list : lists) {
            document.add(list).add(new AreaBreak());
        }

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(count = 8, messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)
    })
    public void addListOnShortPage1() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "addListOnShortPage1.pdf";
        String cmpFileName = sourceFolder + "cmp_addListOnShortPage1.pdf";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, new PageSize(500, 60));

        ListItem item = new ListItem();
        ListItem nestedItem = new ListItem();
        List list = new List(ListNumberingType.DECIMAL);
        List nestedList = new List(ListNumberingType.ENGLISH_UPPER);
        List nestedNestedList = new List(ListNumberingType.GREEK_LOWER);

        nestedNestedList.add("Hello");
        nestedNestedList.add("World");
        nestedItem.add(nestedNestedList);

        nestedList.add(nestedItem);
        nestedList.add(nestedItem);

        item.add(nestedList);

        list.add(item);
        list.add(item);

        doc.add(list);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(count = 3, messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA)
    })
    public void addListOnShortPage2() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "addListOnShortPage2.pdf";
        String cmpFileName = sourceFolder + "cmp_addListOnShortPage2.pdf";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outFileName));
        Document doc = new Document(pdfDoc, new PageSize(500, 130));
        List list = new List(ListNumberingType.DECIMAL);

        ListItem item = new ListItem();
        item.add(new Paragraph("Red"));
        item.add(new Paragraph("Is"));
        item.add(new Paragraph("The"));
        item.add(new Paragraph("Color"));
        item.add(new Image(ImageDataFactory.create(sourceFolder + "red.png")));

        List nestedList = new List(ListNumberingType.ENGLISH_UPPER);
        nestedList.add("Hello");
        nestedList.add("World");

        item.add(nestedList);

        for (int i = 0; i < 3; i++) {
            list.add(item);
        }

        doc.add(list);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void divInListItemTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "divInListItemTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_divInListItemTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        ListItem item = new ListItem();
        item.add(new Div().add(new Paragraph("text")));
        document.add(new List().add(item));
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(ListNumberingType.DECIMAL).
                add("first string").
                add("second string").
                add("third string").
                add("fourth string");

        for (int i = 0; i < 28; i++) {
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest02() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest02.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest02.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(ListNumberingType.DECIMAL).
                add("first string");
        ListItem item = (ListItem) new ListItem("second string").add(new Paragraph("third string"));
        list.add(item).
                add("fourth item");

        for (int i = 0; i < 28; i++) {
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest03() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest03.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest03.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(ListNumberingType.DECIMAL).
                setItemStartIndex(10).
                add("first string").
                add("second string").
                add("third string").
                add("fourth string");

        for (int i = 0; i < 28; i++) {
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listEmptyItemTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listEmptyItemTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listEmptyItemTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        List list = new List(ListNumberingType.GREEK_LOWER);
        list.add(new ListItem()).add(new ListItem()).add(new ListItem()).add("123").add((ListItem) new ListItem().add(new Div()));

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void imageInListTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "imageInListTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_imageInListTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        List list = new List(ListNumberingType.GREEK_LOWER);
        PdfImageXObject xObject = new PdfImageXObject(ImageDataFactory.create(sourceFolder + "Desert.jpg"));
        Image image = new Image(xObject, 100);
        list.add(new ListItem()).add(new ListItem(image)).add(new ListItem()).add("123").add((ListItem) new ListItem().add(new Div().setHeight(70).setBackgroundColor(Color.RED)));

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listItemAlignmentTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listItemAlignmentTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listItemAlignmentTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        List list = new List(ListNumberingType.DECIMAL).setListSymbolAlignment(ListSymbolAlignment.LEFT);

        for (int i = 1; i <= 30; i++) {
            list.add("Item #" + i);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listItemTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listItemTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listItemTest01.pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(outFileName));
        Document document = new Document(pdf);
        List list = new List();
        list.add(new ListItem("The quick brown").setListSymbol(ListNumberingType.ZAPF_DINGBATS_1))
                .add(new ListItem("fox").setListSymbol(ListNumberingType.ZAPF_DINGBATS_2))
                .add(new ListItem("jumps over the lazy").setListSymbol(ListNumberingType.ZAPF_DINGBATS_3))
                .add(new ListItem("dog").setListSymbol(ListNumberingType.ZAPF_DINGBATS_4));
        document.add(list);
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

}
