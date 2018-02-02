package io.excel.validation;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tapan Banker
 * Author @Tapan Banker
 */
public class ApplicationExcel {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(ExcelReader.class);

    public static void main(String[] args) throws Exception {

        ExcelReader excelReader = new ExcelReader();
        String excelFilePath = "/Users/taxcup/intellij-workspace/excel-validation/src/main/resources/Books1.xlsx";
        String jsonFilePath = "/Users/taxcup/intellij-workspace/excel-validation/src/main/resources/outputbooks.json";
        String outputCsvFilePath = "/Users/taxcup/intellij-workspace/excel-validation/src/main/resources/outputbooks.csv";
        String sheetName = "CMSTCPI";

        ExcelReader reader = new ExcelReader();
        List<Book> listBooks = reader.readBooksFromExcelFile(excelFilePath, sheetName);

        LOGGER.info("ArrayList<Book> = " + listBooks);
        String stringJsonBook = excelReader.convertToJson(listBooks);
        reader.outputJson(jsonFilePath, stringJsonBook);
        LOGGER.info("jsonStringListBook = " + stringJsonBook);

        // Convert to a CSV File
        reader.convertToCsv(outputCsvFilePath, listBooks);
    }
}
