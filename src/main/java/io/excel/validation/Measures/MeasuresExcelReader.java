package io.excel.validation.Measures;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tapan N. Banker
 *
 * @author Tapan N. Banker
 *         MeasuresExcel For Book Object
 */
public class MeasuresExcelReader {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    // Delimiter for the new line
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(MeasuresExcelReader.class);

    // Default Constructor
    public MeasuresExcelReader() {
    }


    /**
     * This method will read Excel Sheet and Convert rows in ArrayList
     *
     * @param excelFilePath Directory location of excel sheet
     * @return ArrayList containing the Object
     * @throws IOException
     */
    public List<Measures> readMeasuresFromExcelFile(String excelFilePath, String sheetName) throws IOException {
        // Object to store Measures
        List<Measures> listOfMeasures = new ArrayList<>();

        // Read the Excel sheet
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Read the Sheet
        Workbook workbook = new XSSFWorkbook(inputStream);
        // Read the specific Sheet by Sheet Name
        Sheet firstSheet = workbook.getSheet(sheetName);

        if (null != firstSheet) {
            // Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            // Iterate each Row, skip the first row
            iterator.next();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                // Iterate each Cell in the row
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Measures measure = new Measures();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    // Read all the column A=0, B=1, C=2, D=3, E=4, F=5, G=6, H=7
                    switch (columnIndex) {
                        case 0:
                            if (getCellValue(nextCell) instanceof String) {
                                // Trim the space, eliminate special case , " '
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                // Extract the value and set in the ArrayList
                                measure.setPtn(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 0 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 1:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setMeasure(csvPlainTitle);

                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 1 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 2:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setStandardizedMeasureName(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 2 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 3:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setMeasureType(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 3 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 4:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setImprovementAreaGoal(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 4 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 5:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setNationalStandardDefinitionUsed(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 6:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setAcronyms(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 6 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 7:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setIdentifiers(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 8:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setNqf(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 9:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setPqrs(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 10:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setCms(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 11:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setOther(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 12:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setNumeratorDefinition(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                        case 13:
                            if (getCellValue(nextCell) instanceof String) {
                                String plainString = getCellValue(nextCell).toString().trim();
                                String csvPlainTitle = plainString.replaceAll(",", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\'", "");
                                csvPlainTitle = csvPlainTitle.replaceAll("\"", "");
                                measure.setDenominatorDefinition(csvPlainTitle);
                                break;
                            } else {
                                LOGGER.error(" Instance Type Mismatch on Column 5 " + " Row = " + nextCell.getRowIndex());
                            }
                    }
                }
                // Add the excel row into the Array List
                listOfMeasures.add(measure);
            }
        }
        // Close the Workbook, close the input stream
        workbook.close();
        inputStream.close();
        // Return the ArrayList of all measures records
        return listOfMeasures;
    }


    /**
     * This method will convert the ArrayList into Json String Array
     *
     * @param listMeasures Arraylist containing Measures
     * @return
     */
    public String convertToJson(List<Measures> listMeasures) {
        // Convert to JSON Object and write a file
        Gson gson = new Gson();
        String jsonStringListBook = gson.toJson(listMeasures);
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
    public void convertToCsv(String csvFilePath, List<Measures> booksList) throws Exception {

        FileWriter fileWriter = new FileWriter(csvFilePath);
        try {
            //Write the CSV file header
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Measures b : booksList) {
                // Get the Title from ArrayList
                String getPtn = b.getPtn();
                if (null != getPtn) {
                    fileWriter.append(getPtn);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String measure = b.getMeasure();
                if (null != measure) {
                    fileWriter.append(measure);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String standardizedMeasureName = b.getStandardizedMeasureName();
                if (null != standardizedMeasureName) {
                    fileWriter.append(standardizedMeasureName);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String measureType = b.getMeasureType();
                if (null != measureType) {
                    fileWriter.append(measureType);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String improvementAreaGoal = b.getImprovementAreaGoal();
                if (null != improvementAreaGoal) {
                    fileWriter.append(improvementAreaGoal);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String nationalStandardDefinitionUsed = b.getNationalStandardDefinitionUsed();
                if (null != nationalStandardDefinitionUsed) {
                    fileWriter.append(nationalStandardDefinitionUsed);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String acronyms = b.getAcronyms();
                if (null != acronyms) {
                    fileWriter.append(acronyms);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String identifiers = b.getIdentifiers();
                if (null != identifiers) {
                    fileWriter.append(identifiers);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String nqf = b.getNqf();
                if (null != nqf) {
                    fileWriter.append(nqf);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String pqrs = b.getPqrs();
                if (null != pqrs) {
                    fileWriter.append(pqrs);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String cms = b.getCms();
                if (null != cms) {
                    fileWriter.append(cms);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String other = b.getOther();
                if (null != other) {
                    fileWriter.append(other);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String numeratorDefinition = b.getNumeratorDefinition();
                if (null != numeratorDefinition) {
                    fileWriter.append(numeratorDefinition);
                    fileWriter.append(COMMA_DELIMITER);
                }
                String denominatorDefinition = b.getDenominatorDefinition();
                if ((null != denominatorDefinition)) {
                    fileWriter.append(String.valueOf(b.getDenominatorDefinition()));
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


    /**
     * This method will create a table called Measures in the Database
     *
     * @param connectionDB
     * @throws SQLException
     */
    public void createMeasuresTable(Connection connectionDB) throws SQLException {
        // SQL Statement to create Table
        String sqlCreateTableStatement = "CREATE TABLE IF NOT EXISTS Measures ( \n" +
                "id SERIAL PRIMARY KEY, \n" +
                "ptn text, \n" +
                "measure text, \n" +
                "standardizedMeasureName text, \n" +
                "measureType text, \n" +
                "improvementAreaGoal text, \n" +
                "nationalStandardDefinitionUsed text, \n" +
                "acronyms text, \n" +
                "identifiers text, \n" +
                "nqf text, \n" +
                "pqrs text, \n" +
                "cms text, \n" +
                "other text, \n" +
                "numeratorDefinition text, \n" +
                "denominatorDefinition text \n" +
                " ); ";

        Statement statement = connectionDB.createStatement();
        statement.executeUpdate(sqlCreateTableStatement);

        LOGGER.info("Measure table has successfully been created ");
    }


    /**
     * This method returns number of records in the Measures Tables
     *
     * @param con
     */
    public int recordCountMeasureTable(Connection con) {
        Statement stmt = null;
        int returnCount = -1;
        try {
            try {
                stmt = con.createStatement();
                String query = "SELECT COUNT(*) AS total FROM measures";
                ResultSet rs = stmt.executeQuery(query);
                //Extact result from ResultSet rs
                while (rs.next()) {
                    LOGGER.info("Total Count " + rs.getInt("total)"));
                    returnCount = rs.getInt("total");
                }
                // close ResultSet rs

                rs.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
            // close Connection and Statement
            con.close();
            stmt.close();
            return returnCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCount;
    }

    /**
     * This method will insert the Measure ArrayList in the Database
     *
     * @param measureList
     * @param connectionDB
     * @throws SQLException
     */
    public void insertData(List<Measures> measureList, Connection connectionDB) throws SQLException {

        PreparedStatement preparedStatement = null;
        try {
            // Insert SQL Statement
            String insertTableSQL = "INSERT INTO Measures  (ptn, measure, standardizedMeasureName, measureType," +
                    " improvementAreaGoal, nationalStandardDefinitionUsed, acronyms, identifiers," +
                    " nqf, pqrs, cms, other, numeratorDefinition, denominatorDefinition) VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connectionDB.prepareStatement(insertTableSQL);

            // Interate the Array List
            for (Measures b : measureList) {
                preparedStatement.setString(1, b.getPtn());
                preparedStatement.setString(2, b.getMeasure());
                preparedStatement.setString(3, b.getStandardizedMeasureName());
                preparedStatement.setString(4, b.getMeasureType());
                preparedStatement.setString(5, b.getImprovementAreaGoal());
                preparedStatement.setString(6, b.getNationalStandardDefinitionUsed());
                preparedStatement.setString(7, b.getAcronyms());
                preparedStatement.setString(8, b.getIdentifiers());
                preparedStatement.setString(9, b.getNqf());
                preparedStatement.setString(10, b.getPqrs());
                preparedStatement.setString(11, b.getCms());
                preparedStatement.setString(12, b.getOther());
                preparedStatement.setString(13, b.getNumeratorDefinition());
                preparedStatement.setString(14, b.getDenominatorDefinition());

                // execute insert SQL statement
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
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
        // No match means Invalid Data type so return empty string
        return "";
    }
}
