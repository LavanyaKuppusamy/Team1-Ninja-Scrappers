package Scrapingdata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.ss.usermodel.*;


public class Excelcopypaste {
	
	    public static void main(String[] args) throws Exception {
	        // Open the Excel file
	        FileInputStream inputWorkbook = new FileInputStream(new File(""));
	        Workbook workbook = WorkbookFactory.create(inputWorkbook);
	        
	        // Get the source sheet and range of cells to copy
	        Sheet sourceSheet = workbook.getSheet("Sheet1");
	        CellRangeAddress sourceRange = new CellRangeAddress(0, 5, 0, 2); // rows 1-6, columns A-C
	        
	        // Get the target sheet and starting cell to paste
	        Sheet targetSheet = workbook.getSheet("Sheet2");
	        int targetRowNum = 0;
	        int targetColNum = 0;
	        
	        // Copy the source range to the target sheet
	        for (Row sourceRow : sourceSheet) {
	            if (sourceRange.isInRange(sourceRow.getRowNum(), 0)) {
	                Row targetRow = targetSheet.getRow(targetRowNum);
	                if (targetRow == null) {
	                    targetRow = targetSheet.createRow(targetRowNum);
	                }
	                for (int sourceColNum = sourceRange.getFirstColumn(); 
	                     sourceColNum <= sourceRange.getLastColumn(); 
	                     sourceColNum++) {
	                    int targetColIndex = targetColNum + (sourceColNum - sourceRange.getFirstColumn());
	                    Cell sourceCell = sourceRow.getCell(sourceColNum);
	                    Cell targetCell = targetRow.getCell(targetColIndex);
	                    if (targetCell == null) {
	                        targetCell = targetRow.createCell(targetColIndex);
	                    }
	                    targetCell.setCellValue(sourceCell.getStringCellValue());
	                }
	                targetRowNum++;
	            }
	        }
	        
	        // Save the workbook
	        FileOutputStream outputWorkbook = new FileOutputStream(new File("output.xlsx"));
	        workbook.write(outputWorkbook);
	        outputWorkbook.close();
	        workbook.close();
	    }
	}
	
	

