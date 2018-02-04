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
public class ApplicationExcel {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(ApplicationExcel.class);

    public static void main(String[] args) {
        try {
        // Measure Excel File Reader
        MeasuresExcelReader measuresReader = new MeasuresExcelReader();

        // Book Excel File Reader
        BookExcelReader bookReader = new BookExcelReader();

        // File Path on local Machine to Measures 1. Input Excel File 2. Output Json and 3. Output CSV
        String excelMeasureFilePath = System.getProperty("user.dir") + "/src/main/resources/MeasuresVerificationTestData.xlsx";
        String jsonMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/measuresJsonOutput.json";
        String jsonMeasuresSchemaFilePath = System.getProperty("user.dir") + "/src/main/resources/measuresSchema.json";
        String csvMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/measuresCsvOuput.csv";
        String s3bucketName = "aaa-cmstcpi-tnb-test";
        String objectPathInBucket = "input/MeasuresVerificationTestData.xlsx";
        File schemaFile = new File(jsonMeasuresSchemaFilePath);
        File jsonFile = new File(jsonMeasuresFilePath);
        String measureSheetName = "Sheet1";
        int rowCount = -1;

        // File Path on local machine to Books 1. Input Excel File 2. Output Json and 3. Output CSV
        String excelBookFilePath = System.getProperty("user.dir") + "/src/main/resources/Books1.xlsx";
        String csvBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.csv";
        String jsonBookFilePath = System.getProperty("user.dir") + "/src/main/resources/outputbooks.json";
        String booksSheetName = "CMSTCPI";

        // Login to AWS using the secret Key and Access Key
        BasicAWSCredentials credentials = AWSS3Utility.loginToAws(AWSCredentials.ACCESS_KEY_ENCRYPTED, AWSCredentials.SECRET_KEY_ENCRYPTED);

        // List all the Buckets in AWS S3
        List<String> s3BucketList = AWSS3Utility.listS3Buckets(credentials);
        LOGGER.info("\n" +s3BucketList.toString() + "\n");

        // List Object in Bucket
        List<String> objectList = AWSS3Utility.listObjectsInBucket(credentials, s3bucketName);
        LOGGER.info("\n" + objectList.toString() + "\n");

        // Download the Object from S3 to Local Directory
        AWSS3Utility.downloadTheObject( credentials,  s3bucketName,  objectPathInBucket, excelMeasureFilePath);

        // Get Array list by reading the Measure Excel File
        List<Measures> measuresList = measuresReader.readMeasuresFromExcelFile(excelMeasureFilePath, measureSheetName);
        LOGGER.info("ArrayList<Measure> = \n\n" + measuresList);
        // Convert to JSON Object
        String stringJsonMeasure = measuresReader.convertToJson(measuresList);
        LOGGER.info("\n\n  JSON object Measure = " + stringJsonMeasure);
        // Output JSON to File Path
        measuresReader.outputJson(jsonMeasuresFilePath, stringJsonMeasure);
        // Convert to a CSV File for Measures
        measuresReader.convertToCsv(csvMeasuresFilePath, measuresList);

        Boolean valueb =  JsonSchemaValidationUtility.isJsonValid(schemaFile, jsonFile);
        LOGGER.info(" ###################################  Schema Validation " + valueb + "##############################################");

        // Database Postgres SQL Connection Object
        Connection connectionDB = PostgresDbUtility.getConnection();
        // Create the Table
        measuresReader.createMeasuresTable(connectionDB);
        // Insert the Measure ArrayList Data in the Postgres SQL Table
        measuresReader.insertData(measuresList, connectionDB);
        // Check the total Records Count in Measures Table
        rowCount = measuresReader.recordCountMeasureTable(connectionDB);
        LOGGER.info("Measure Table Row Count " + rowCount);


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
