import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import io.database.postgresql.DbContract;
import io.database.postgresql.PostgresDB;
import io.excel.validation.Book.Book;
import io.excel.validation.Book.BookExcelReader;
import io.excel.validation.Measures.Measures;
import io.excel.validation.Measures.MeasuresExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tapan Banker
 * Author @Tapan Banker
 */
public class ApplicationExcel {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(BookExcelReader.class);

    public static void main(String[] args) throws Exception {

        // Measure Excel File
        MeasuresExcelReader measuresReader = new MeasuresExcelReader();

        // Book Excel File
        BookExcelReader bookReader = new BookExcelReader();

        // Database Postgres Object
        PostgresDB postgresDBClient = new PostgresDB(
                DbContract.HOST,
                DbContract.DB_NAME,
                DbContract.USERNAME,
                DbContract.PASSWORD);

        // File Path on local Machine to Measures 1. Input Excel File 2. Output Json and 3. Output CSV
        String excelMeasureFilePath = System.getProperty("user.dir") + "/src/main/resources/MeasuresVerificationTestData.xlsx";
        String jsonMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/outputMeasures.json";
        String csvMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/outputMeasures.csv";
        String measureSheetName = "Sheet1";

        // File Path on local machine to Books 1. Input Excel File 2. Output Json and 3. Output CSV
        String excelBookFilePath = System.getProperty("user.dir") + "/src/main/resources/Books1.xlsx";
        String csvBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.csv";
        String jsonBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.json";
        String booksSheetName = "CMSTCPI";


        // Get Array list by reading the Measure Excel File
        List<Measures> measuresList = measuresReader.readMeasuresFromExcelFile(excelMeasureFilePath, measureSheetName);
        //LOGGER.info("ArrayList<Measure> = \n\n" + measuresList);

        // Convert to JSON
        String stringJsonMeasure = measuresReader.convertToJson(measuresList);
        LOGGER.info("\n\n  JSON object Measure = " + stringJsonMeasure);
        //Output JSON to File Path
        measuresReader.outputJson(jsonMeasuresFilePath, stringJsonMeasure);

        // Convert to a CSV File for Measures
        measuresReader.convertToCsv(csvMeasuresFilePath, measuresList);

        try {
            Connection connectionDB = postgresDBClient.getConnection();
            // Create the Table
            measuresReader.createMeasuresTable(connectionDB);
            measuresReader.insertData(measuresList, connectionDB);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }


        /*
        List<Book> listBooks = bookReader.readBooksFromExcelFile(excelBookFilePath, booksSheetName);

        LOGGER.info("ArrayList<Book> = " + listBooks);
        String stringJsonBook = bookReader.convertToJson(listBooks);
        bookReader.outputJson(jsonBookFilePath, stringJsonBook);
        LOGGER.info("jsonStringListBook = " + stringJsonBook);

        // Convert to a CSV File for Book
        bookReader.convertToCsv(csvBookFilePath, listBooks);
        */

    }
}
