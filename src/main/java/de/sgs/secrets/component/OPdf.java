 package de.sgs.secrets.component;

 import com.lowagie.text.Font;
 import com.lowagie.text.Image;
 import com.lowagie.text.Rectangle;
 import com.lowagie.text.*;
 import com.lowagie.text.pdf.*;
 import com.lowagie.text.pdf.parser.PdfTextExtractor;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;

 import java.awt.*;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;

 @Component
 public class OPdf {

     private static final Logger LOG = LoggerFactory.getLogger(OPdf.class);

     public OPdf() {

     };


     private boolean inTest = false;
     private StringBuilder images;

     private Document document;
     private PdfWriter writer;
     private ByteArrayOutputStream baos;
     private PdfContentByte contentByte;
     private PdfOutline rootOutline;
     private Image logo1;
     private Image logo2;
     private Font fontNormal8;
     private Font fontNormal8i;
     private Font fontNormal10;
     private Font fontNormal12;
     private Font fontNormal14;
     private Font fontNormal16;
     private Font fontBold14;
     private Font fontBold16;
     private Font fontBold20;
     private Font fontBold30;
     private PdfPTable table;
     private PdfPage page;
     private int numberOfPages;
     private String fileName;


     private final static String VERSION = "1.1.7";

     private final static String AUTHOR = "OPdf";
     private final static String CREATOR = "OPdf";
     private final static String PRODUCER = "OPdf" + ", Library v" + VERSION + " by Stefan Gärtners";
     private final static String TITLE = "Liste aller Whiskeys";
     private final static String SUBJECT = "Liste aller Whiskeys";
     private final static String EXTRAHIERT = "";
     private final static String NAMECONTENT = "";
     private final static String MELDUNGAN = "";



     public boolean isInTest() {
         return inTest;
     }

     public void setInTest(boolean inTest) {
         this.inTest = inTest;
     }

     public PdfContentByte getContentByte() {
         return contentByte;
     }

     public Document getDocument() {
         return document;
     }

     public void setDocument(Document document) {
         this.document = document;
     }

     public int getNumberOfPages() {
         return numberOfPages;
     }

     public void setNumberOfPages(int numberOfPages) {
         this.numberOfPages = numberOfPages;
     }

 // =======================================================================================================================================

     public void setContentByte(PdfContentByte contentByte) {
         this.contentByte = contentByte;
     }

 // =======================================================================================================================================

     public PdfOutline getRootOutline() {
         return rootOutline;
     }

 // =======================================================================================================================================

     public void setRootOutline(PdfOutline rootOutline) {
         this.rootOutline = rootOutline;
     }

 // =======================================================================================================================================

     public void createDocument() {
         this.document = null;
         this.writer = null;
         this.numberOfPages = 0;
         this.fileName = fileName;

         if (this.isInTest()) {
             this.images = new StringBuilder();
         }

         try {
             this.document = new Document(PageSize.A4);
             this.baos = new ByteArrayOutputStream();
             this.writer = PdfWriter.getInstance(this.document, baos);
             this.writer.setPageEmpty(true);
             this.writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
             this.writer.setStrictImageSequence(true);
             this.writer.setPageEvent(new PdfPageEvent() {

                 @Override
                 public void onStartPage(PdfWriter writer, Document document) {
                     numberOfPages++;
                     LOG.debug("NumberOfPages: " + numberOfPages);
                 }

                 @Override
                 public void onSectionEnd(PdfWriter writer, Document document, float paragraphPosition) {

                 }

                 /**
                  * Called when a <CODE>Chunk</CODE> with a generic tag is written.
                  * <p>
                  * It is useful to pinpoint the <CODE>Chunk</CODE> location to generate
                  * bookmarks, for example.
                  *
                  * @param writer   the <CODE>PdfWriter</CODE> for this document
                  * @param document the document
                  * @param rect     the <CODE>Rectangle</CODE> containing the <CODE>Chunk</CODE>
                  * @param text     the text of the tag
                  */
                 @Override
                 public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {

                 }

                 @Override
                 public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title) {

                 }

                 @Override
                 public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {

                 }

                 @Override
                 public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {

                 }

                 @Override
                 public void onOpenDocument(PdfWriter writer, Document document) {
//                     ClassPathResource res = new ClassPathResource("images/bild1.png");
//                     ClassPathResource res2 = new ClassPathResource("images/bild2.png");
//                     InputStream in;
//                     InputStream in2;
//                     try {
//                         in = res.getInputStream();
//                         logo1 = Image.getInstance(in.readAllBytes());
//                         logo1.scalePercent(72);
//                         logo1.setAbsolutePosition(5, 725);
//                         in.close();
//
//                         in2 = res2.getInputStream();
//                         logo2 = Image.getInstance(in2.readAllBytes());
//                         logo2.setAbsolutePosition(490, 740);
//                         logo2.scalePercent(65);
//                         in2.close();
//
//                     } catch (IOException e) {
//                         LOG.error("OPDF-ERROR onOpenDocument-PageEvent: Resource not found!", e);
//                     }
                 }

                 @Override
                 public void onEndPage(PdfWriter writer, Document document) {
//                     writer.getDirectContent().addImage(logo1);
//                     writer.getDirectContent().addImage(logo2);
                 }

                 @Override
                 public void onCloseDocument(PdfWriter writer, Document document) {

                 }

                 @Override
                 public void onChapterEnd(PdfWriter writer, Document document, float paragraphPosition) {

                 }

                 @Override
                 public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {

                 }
             });
         } catch (Exception e) {
             e.printStackTrace();
             LOG.error("OPDF-ERROR CreateDocument crashed!", e);
         }

         fontNormal8 = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.BLACK);
         fontNormal8i = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, Color.BLACK);
         fontNormal10 = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.BLACK);
         fontNormal12 = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);
         fontNormal14 = new Font(Font.HELVETICA, 14, Font.NORMAL, Color.BLACK);
         fontNormal16 = new Font(Font.HELVETICA, 16, Font.NORMAL, Color.BLACK);
         fontBold14 = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLACK);
         fontBold16 = new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLACK);
         fontBold20 = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLACK);
         fontBold30 = new Font(Font.HELVETICA, 30, Font.BOLD, Color.BLACK);

         this.document.setMargins(20, 20, 20, 20);
         this.document.open();
         setMetaInformation();
         this.document.newPage();
         this.contentByte = writer.getDirectContent();
     }

 // =======================================================================================================================================

     public void setMetaInformation() {
         this.document.addAuthor(AUTHOR);
         this.document.addTitle(TITLE);
         this.document.addCreator(CREATOR);
         this.document.addProducer(PRODUCER);
         this.document.addSubject(SUBJECT);
         this.document.addCreationDate();
     }

 // =======================================================================================================================================

     public void newPage() {
         this.document.newPage();
         this.document.add(new Paragraph(" "));
     }

 // =======================================================================================================================================

     public byte[] getPdf() {
         document.close();
         return this.baos.toByteArray();
     }

 // =======================================================================================================================================

     public void saveDocument() {
         document.close();
     }

 // =======================================================================================================================================

     public void writeSmallEmptyLine() {
         Paragraph p = new Paragraph(" ", fontNormal8);
         p.setAlignment(Element.ALIGN_CENTER);
         p.setLeading(6);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeEmptyLine() {
         Paragraph p = new Paragraph(" ", fontNormal8);
         p.setAlignment(Element.ALIGN_CENTER);
         p.setLeading(8);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeXLEmptyLine() {
         Paragraph p = new Paragraph(" ", fontNormal8);
         p.setAlignment(Element.ALIGN_CENTER);
         p.setLeading(30);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeTitle(String title) {
         writeBold30Text(title);
     }

 // =======================================================================================================================================

     public void writeExtrahiert(String messageId) {
         writeBold16Text(EXTRAHIERT);
         writeNormal16Text(messageId);
         writeSmallEmptyLine();
     }

 // =======================================================================================================================================

     public void writeNameContent(String videoName) {
         writeBold16Text(NAMECONTENT);
         writeNormal16Text(videoName);
         writeSmallEmptyLine();
     }

 // =======================================================================================================================================

     public void writeText24(String meldungAn) {
         Paragraph p = new Paragraph();
         p.setLeading(24);
         p.setAlignment(Element.ALIGN_CENTER);
         p.add(new Chunk(MELDUNGAN, fontBold16));
         p.add(new Chunk(meldungAn, fontNormal16));
         this.document.add(p);
         writeSmallEmptyLine();
     }

 // =======================================================================================================================================

     public void writeBold14Text(String text) {
         Paragraph p = new Paragraph(text, fontBold14);
         p.setAlignment(Element.ALIGN_LEFT);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeBold16Text(String text) {
         Paragraph p = new Paragraph(text, fontBold16);
         p.setAlignment(Element.ALIGN_LEFT);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeBold30Text(String text) {
         Paragraph p = new Paragraph(text, fontBold30);
         p.setAlignment(Element.ALIGN_CENTER);
         p.setSpacingAfter(8);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeNormal14Text(String text) {
         Paragraph p = new Paragraph(text, fontNormal16);
         p.setAlignment(Element.ALIGN_LEFT);
         p.setLeading(16);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void writeNormal16Text(String text) {
         Paragraph p = new Paragraph(text, fontNormal16);
         p.setAlignment(Element.ALIGN_LEFT);
         p.setLeading(18);
         this.document.add(p);
     }

 // =======================================================================================================================================

     public void greyBar(String caption) {
         this.table = new PdfPTable(2);
         this.table.getDefaultCell().setBorder(0);
         this.table.setHorizontalAlignment(Element.ALIGN_LEFT);
         this.table.setKeepTogether(true);
         this.table.setWidthPercentage(99);
         this.table.setSplitLate(false);
         Paragraph p = new Paragraph(caption, fontBold16);
         p.setAlignment(Element.ALIGN_CENTER);
         PdfPCell cell = new PdfPCell(p);
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         cell.setBackgroundColor(Color.LIGHT_GRAY);
         cell.setColspan(2);
         cell.setBorder(0);
         cell.setFixedHeight(24);
         this.table.addCell(cell);
         this.document.add(this.table);
     }

 // =======================================================================================================================================

     public void image(byte[] picture) {
         Image img = null;
         try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
             Paragraph p = new Paragraph();
             p.setLeading(10f);
             p.setSpacingBefore(-5);
             p.setAlignment(Element.ALIGN_CENTER);
             img = Image.getInstance(picture);
             int identation = 0;
             float scalingFactor = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - identation) / img.getWidth()) * 50;
             img.scalePercent(Math.round(scalingFactor));
             p.add(img);
             this.document.add(p);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

 // =======================================================================================================================================

     public static void removeBlankPdfPages(PdfReader r) throws IOException{
         PdfTextExtractor extractor = new PdfTextExtractor(r);
         List<Integer> paginas = new ArrayList<Integer>();
         for (int i = 1; i <= r.getNumberOfPages(); i++) {
             PdfDictionary pageDict = r.getPageN(i);
             PdfDictionary resDict = (PdfDictionary) pageDict.get(PdfName.RESOURCES);
             boolean noFontsOrImages = true;
             if (resDict != null) {
                 noFontsOrImages = resDict.get(PdfName.FONT) == null && resDict.get(PdfName.XOBJECT) == null;
             }

             if (!noFontsOrImages) {
                 String textFromPage = extractor.getTextFromPage(i);
                 if(textFromPage.length() > 50){
                     paginas.add(i);
                 }
             }
         }
         r.selectPages(paginas);
     }

 }
