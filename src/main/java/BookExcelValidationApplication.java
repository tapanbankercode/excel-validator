import com.amazonaws.auth.BasicAWSCredentials;
import io.aws.s3.utilility.AWSCredentials;
import io.aws.s3.utilility.AWSS3Utility;
import io.database.postgresql.PostgresDbUtility;
import io.excel.validation.Book.Book;
import io.excel.validation.Book.BookExcelReader;
import io.excel.validation.Measures.Measures;
import io.excel.validation.Measures.MeasuresExcelReader;
import io.json.utility.JsonSchemaValidationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Tapan Banker
 *
 * @Author Tapan Banker
 * The main Application Call that call Measures and Book functionality
 */
public class BookExcelValidationApplication {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(BookExcelValidationApplication.class);

    public static void main(String[] args) {
        try {

        // Book Excel File Reader
        BookExcelReader bookReader = new BookExcelReader();


        // File Path on local machine to Books 1. Input Excel File 2. Output Json and 3. Output CSV
        String excelBookFilePath = System.getProperty("user.dir") + "/src/main/resources/Books1.xlsx";
        String csvBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.csv";
        String jsonBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.json";
        String booksSheetName = "CMSTCPI";

        // Get Array list by reading the Book Excel File
        List<Book> listBooks = bookReader.readBooksFromExcelFile(excelBookFilePath, booksSheetName);
        LOGGER.info("ArrayList<Book> = " + listBooks);
        // Convert Book to JSON Object
        String stringJsonBook = bookReader.convertToJson(listBooks);
        LOGGER.info("jsonStringListBook = " + stringJsonBook);
        // Output Book JSON to File Path
        bookReader.outputJson(jsonBookFilePath, stringJsonBook);
        // Convert Book to a CSV File for Book
        bookReader.convertToCsv(csvBookFilePath, listBooks);

        } catch (Exception e) {
            LOGGER.info(e.toString());
            e.printStackTrace();
        }
    }
}
