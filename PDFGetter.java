// requires latest version of tabula-java built from source with PDFBox 3.x
// and saved as /home/sullija/bin/tabula.jar

// jbang --cp "/home/sullija/bin/tabula.jar" PDFGetter.java
// or
// javac -cp "/home/sullija/bin/tabula.jar" PDFGetter.java
// java -cp "/home/sullija/bin/tabula.jar" PDFGetter.java

import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
public class PDFGetter {


    public void get() {
        //InputStream in = this.getClass().getResourceAsStream("/home/sullija/dev/java/tabula-java/src/test/resources/technology/tabula/table_report.pdf");
        try {
            PDDocument document = Loader.loadPDF(new File("/home/sullija/dev/java/tabula-java/src/test/resources/technology/tabula/table_report.pdf"));
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            int pageNo = 0;
            while (pi.hasNext()) {
                // iterate over the pages of the document
                Page page = pi.next();
                if (pageNo < 3) {  // Only do the first page for now
                    List<Table> table = sea.extract(page);
                    // iterate over the tables of the page
                    for(Table tables: table) {
                        List<List<RectangularTextContainer>> rows = tables.getRows();
                        // iterate over the rows of the table
                        for (List<RectangularTextContainer> cells : rows) {
                            // print all column-cells of the row plus linefeed
                            for (RectangularTextContainer content : cells) {
                                // Note: Cell.getText() uses \r to concat text chunks
                                String text = content.getText().replace("\r", " ");
                                System.out.print(text + "|");
                            }
                            System.out.println();
                        }
                    }
                }
                pageNo = pageNo + 1;
            }
            System.out.println("The PageNo: " + pageNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args)
    {
        PDFGetter pdf = new PDFGetter();
        pdf.get();

        System.out.println("My First Java Program.");
    }




}
