package who.reconsystem.app.io.type;

import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.io.File;
import who.reconsystem.app.io.FileGenerator;
import who.reconsystem.app.io.FileReader;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.config.DateFormat;
import who.reconsystem.app.root.config.Functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XLSXFile extends FileGenerator {

    private final XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private XSSFRow row;

    private XSSFCell cell;

    @Setter
    List<String> headings;

    public XLSXFile(String fileName, String path, String ext) {
        super(fileName, path, ext);
        workbook = theWorkbook();
    }

    public XLSXFile(String fileName, String path) {
        super(fileName, path);
        workbook = theWorkbook();
    }

    public XLSXFile(Path filePath) {
        super(filePath);
        workbook = theWorkbook();
    }

    @Override
    public File create() {
        try {
            OutputStream output = Files.newOutputStream(filePath, StandardOpenOption.SPARSE);
            sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("Export"));
            row = workbook.getSheetAt(0).createRow(0);
            setRowValue(workbook, row, headings);
            workbook.write(output);
            workbook.close();
        }catch (IOException io) {
            Log.set(XLSXFile.class).trace(io);
            DialogMessage.exceptionDialog(io);
        }
        return this;
    }

    @Override
    public void addContent(String content) {
        sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();
        row = sheet.createRow(totalRows);
        List<String[]> rowValues = Functions.stringToJaggedArray(content);
        settableValue(workbook, sheet, rowValues);
    }

    @Override
    public FileReader getContent() {
        List<String[]> sheetContent = new ArrayList<>();
        sheet = workbook.getSheetAt(0);
        int columns = sheet.getRow(0).getPhysicalNumberOfCells();
        int lines = sheet.getPhysicalNumberOfRows();
        if (lines - 1 != 0) {
            String[] content = new String[columns];
            for (int i = 1; i < lines; i++) {
                row = sheet.getRow(i);
                for(int j = 0; j < columns; j++) {
                    if (row.getCell(j) != null) {
                        cell = row.getCell(j);
                        content[j] = cell.getRichStringCellValue().getString();
                    }else {
                        content[j] = "";
                    }
                    sheetContent.add(i, content);
                }
            }
        }
        return new FileReader(sheetContent, FileType.XLSX_FILE);
    }

    private XSSFWorkbook theWorkbook() {
        XSSFWorkbook xlsxFile = new XSSFWorkbook();
        try {
            if (!fileDontExists(filePath)) {
                InputStream in = Files.newInputStream(filePath);
                xlsxFile = new XSSFWorkbook(in);
            }
        }catch (IOException io) {
            Log.set(XLSXFile.class).trace(io);
            DialogMessage.exceptionDialog(io);
        }
        return xlsxFile;
    }

    private void setRowValue(XSSFWorkbook workbook, XSSFRow row, List<String> rowValue) {
        for (int i = 0; i < rowValue.size(); i++) {
            XSSFCell cell = row.createCell(i);
            setCellsDatatype(workbook, cell, rowValue.get(i));
        }
    }

    private void settableValue(XSSFWorkbook workbook, XSSFSheet sheet, List<String[]> rowsValues) {
        for (int i = 0; i < rowsValues.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            setRowValue(workbook, row, Arrays.asList(rowsValues.get(i)));
        }
    }

    private void setCellsDatatype(XSSFWorkbook workbook, XSSFCell cell, String value) {
        cell.setCellValue(value);
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        DataFormat format = workbook.createDataFormat();
        if (Functions.isValidLocaleDate(value)) {
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DateFormat.DATE_BY_SLASH.getIso()));
            cell.setCellStyle(cellStyle);
        }else if (Functions.isValidNumber(value)) {
            cellStyle.setDataFormat(format.getFormat("#0"));
            cell.setCellStyle(cellStyle);
        } else if (Functions.isValidDecimalNumber(value)) {
            cellStyle.setDataFormat(format.getFormat("#0.00"));
            cell.setCellStyle(cellStyle);
        }
    }
}
