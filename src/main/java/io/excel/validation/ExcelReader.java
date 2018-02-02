package io.excel.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.google.gson.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tapan N. Banker
 * @author Tapan N. Banker
 * ExcelRead For Book Object
 */
public class ExcelReader {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(ExcelReader.class);

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    // Delimiter for the new line
    private static final String NEW_LINE_SEPARATOR = "\n";

    // Default Constructor
    public ExcelReader() {
    }

    /**
     * The method will return the cell data value
     *
     * @param cell Cell Object
     * @return Object containing value of the cell
     */
    private Object getCellValue(Cell cell) {
        // Check the data type of the cell
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();

            case BOOLEAN:
                return cell.getBooleanCellValue();

            case NUMERIC:
                return cell.getNumericCellValue();
        }
        // No match means Invalid Data type so return null
        return null;
    }


    /**
     * This method will read Excel Sheet and Convert rows in ArrayList
     *
     * @param excelFilePath Directory location of excel sheet
     * @return ArrayList containing the Object
     * @throws IOException
     */
    public List<Book> readBooksFromExcelFile(String excelFilePath, String sheetName) throws IOException {

        // Object to store Book
        List<Book> listBooks = new ArrayList<>();

        // Read the Excel sheet
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Read the Sheet
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheet(sheetName);

        if(null !=  firstSheet) {
            // Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            // Iterate each Row
            iterator.next();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                // Iterate each Cell in the row
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Book aBook = new Book();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        // Read Column 1
                        case 1:
                            if (getCellValue(nextCell) instanceof String) {
                                aBook.setTitle(getCellValue(nextCell).toString());
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 1 " + " Row = " + nextCell.getRowIndex());
                            }
                        // Read Column 2
                        case 2:
                            if (getCellValue(nextCell) instanceof String) {
                                aBook.setAuthor(getCellValue(nextCell).toString());
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 2 " + " Row = " + nextCell.getRowIndex());
                            }
                        // Read Column 3
                        case 3:
                            if (getCellValue(nextCell) instanceof java.lang.Double) {
                                aBook.setPrice((double) getCellValue(nextCell));
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 3  " + " Row = " + nextCell.getRowIndex());

                            }
                        // Read Column 4
                        case 4:
                            if (getCellValue(nextCell) instanceof java.lang.Double) {
                                aBook.setPublishDate((Date) nextCell.getDateCellValue());
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 4 " + " Row = " + nextCell.getRowIndex());
                            }
                    }
                }
                // Add the excel row into the Array List
                listBooks.add(aBook);
            }
        }
        workbook.close();
        inputStream.close();
        return listBooks;
    }


    /**
     * This method will convert the ArrayList into Json String Array
     *
     * @param listBooks Arraylist containing Books
     * @return
     */
    public String convertToJson(List<Book> listBooks)
    {
        // Convert to JSON Object and write a file
        Gson gson = new Gson();
        String jsonStringListBook = gson.toJson(listBooks);
        // Return the String Json Array
        return jsonStringListBook;
    }


    /**
     * This method will create a JSON File on the local drive
     *
     * @param filePath         Full path of the JSON file to be created
     * @param stringJsonObject String Object containing JSON
     */
    public void outputJson(String filePath, String stringJsonObject) {
        // write the complex object to a file
        try {
            FileWriter file = new FileWriter(filePath);
            file.write(stringJsonObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
    }


    /**
     * This method will convert the ArrayList object to a CSV File
     *
     * @param csvFilePath
     * @param booksList
     * @throws Exception
     */
    public void convertToCsv(String csvFilePath, List<Book> booksList) throws Exception {

        FileWriter fileWriter = new FileWriter(csvFilePath);
        try {
            //Write the CSV file header
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Book b : booksList) {

                // Get the Title from ArrayList
                String plainTitle = b.getTitle();
                if (null != plainTitle) {
                    String csvPlainTitle = plainTitle.replaceAll(",", " ");
                    fileWriter.append(csvPlainTitle);
                    fileWriter.append(COMMA_DELIMITER);
                }

                // Get the Author from Array List
                String plainAuthor = b.getAuthor();
                if (null != plainAuthor) {
                    String csvPlainAuthor = plainAuthor.replaceAll(",", " ");
                    fileWriter.append(csvPlainAuthor);
                    fileWriter.append(COMMA_DELIMITER);
                }
                if ((null != plainAuthor) && (null != plainTitle)) {
                    fileWriter.append(String.valueOf(b.getPrice()));
                    fileWriter.append(NEW_LINE_SEPARATOR);
                }
            }
            LOGGER.info("Location : " + csvFilePath + " CSV File Created Successfully");

        } catch (Exception e) {
            LOGGER.error("Location : " + csvFilePath + "Error in creating CSV File");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                LOGGER.error("Error in creating CSV File IO Expection");
                e.printStackTrace();
            }

        }
    }

}
