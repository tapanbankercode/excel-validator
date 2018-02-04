import com.amazonaws.auth.BasicAWSCredentials;
import io.aws.s3.utilility.AWSCredentials;
import io.aws.s3.utilility.AWSS3Utility;
import io.database.postgresql.PostgresDbUtility;
import io.excel.validation.Measures.Measures;
import io.excel.validation.Measures.MeasuresExcelReader;
import io.json.utility.JsonSchemaValidationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Tapan N. Banker on 2/4/18.
 *
 * @author Tapan N. Banker
 */
public class MeasuresDataIngestApplication {
    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(MeasuresDataIngestApplication.class);

    public static void main(String[] args) {
        try {
            // Measure Excel File Reader
            MeasuresExcelReader measuresReader = new MeasuresExcelReader();

            // File Path on local Machine to Measures 1. Input Excel File 2. Output Json and 3. Output CSV and 4. Measure Schema
            String jsonMeasuresSchemaFilePath = System.getProperty("user.dir") + "/src/main/resources/schema/measuresSchema.json";
            String excelMeasureFilePath = System.getProperty("user.dir") + "/src/main/resources/MeasuresVerificationTestData.xlsx";
            String jsonMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/measuresJsonOutput.json";
            String csvMeasuresFilePath = System.getProperty("user.dir") + "/src/main/resources/measuresCsvOuput.csv";
            //Sheet Name Inside the Excel of the Input Measure Excel Sheet
            String measureSheetName = "Sheet1";
            // S3 Bucket containing the Input Measure Excel Sheet
            String s3bucketName = "aaa-cmstcpi-tnb-test";
            // Directory and File Name of the Input measure Excel Sheet
            String objectPathInBucket = "input/MeasuresVerificationTestData.xlsx";
            String csvMeasuresFilePathInBucket = "output/measuresCsvOuput.csv";
            String jsonMeasuresFilePathInBucket = "output/measuresJsonOuput.json";
            File schemaFile = new File(jsonMeasuresSchemaFilePath);
            File jsonFile = new File(jsonMeasuresFilePath);
            int rowCount = -1;

            // Login to AWS using the secret Key and Access Key (Encrypted Cipher Logic)
            BasicAWSCredentials credentials = AWSS3Utility.loginToAws(AWSCredentials.ACCESS_KEY_ENCRYPTED, AWSCredentials.SECRET_KEY_ENCRYPTED);

            // Download the Object from S3 to Local Directory
            AWSS3Utility.downloadTheObject(credentials, s3bucketName, objectPathInBucket, excelMeasureFilePath);

            // 1. Read the Excel - Get Array list by reading the Measure Excel File
            List<Measures> measuresList = measuresReader.readMeasuresFromExcelFile(excelMeasureFilePath, measureSheetName);
             // LOGGER.info("ArrayList<Measure> = \n\n" + measuresList);

            // 2. Convert to JSON Object for Measure - Convert the Excel into JSON object
            String stringJsonMeasure = measuresReader.convertToJson(measuresList);
            LOGGER.info("\n\n  JSON object Measure = " + stringJsonMeasure);

            // 3. Output the Measures JSON object - Output JSON to File Path
            measuresReader.outputJson(jsonMeasuresFilePath, stringJsonMeasure);

            // 4. Perform JSON Schema Validation on the output Object
            Boolean valueb = JsonSchemaValidationUtility.isJsonValid(schemaFile, jsonFile);
            LOGGER.info(" ###########  Measures Schema Validation ===== " + valueb + " =====  ##############");
            AWSS3Utility.uploadObjectToS3BucketAndDelete(credentials, s3bucketName, jsonMeasuresFilePathInBucket, jsonMeasuresFilePath);

            // 5. Output the CSV File for Measures - Convert to a CSV File for Measures
            measuresReader.outputToCsvFile(csvMeasuresFilePath, measuresList);
            AWSS3Utility.uploadObjectToS3BucketAndDelete(credentials, s3bucketName, csvMeasuresFilePathInBucket, csvMeasuresFilePath);

            // 6. Insert Measure data in the Database - Write the Data in the Database in Measures Tables
            // Database Postgres SQL Connection Object
            Connection connectionDB = PostgresDbUtility.getConnection();
            // Insert the Measure ArrayList Data in the Postgres SQL Table
            measuresReader.insertData(measuresList, connectionDB);
            // Check the total Records Count in Measures Table
            rowCount = measuresReader.recordCountMeasureTable(connectionDB);
            LOGGER.info("Measure Table Row Count " + rowCount);

        } catch (Exception e) {
            LOGGER.info(e.toString());
            e.printStackTrace();
        }
    }
}