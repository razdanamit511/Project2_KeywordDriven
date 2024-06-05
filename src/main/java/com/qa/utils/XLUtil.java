package com.qa.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qa.base.BaseTest;


public class XLUtil extends BaseTest{

	public static FileOutputStream fo;
	public static FileInputStream fi;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;

	/* Method1: Returns RowCount for a Sheet */
	public static int getRowCount(String xlfile, String xlsheet) throws Exception {

		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		int row = ws.getLastRowNum();
		wb.close();
		fi.close();
		return row;
	}
	
	/* Method2: Returns GetCellCount for a Sheet */
	public static int getCellCount(String xlfile, String xlsheet, int rownum) throws Exception {

		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlsheet);
		row = ws.getRow(rownum);
		int cellCount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellCount;
	}

	/* Method3: Returns CellData for a Sheet */
	public static String getCellData(String xlFile, String xlSheet, int rowNum, int columnNum) throws Exception {
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.getCell(columnNum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}
	
	
	/* Method4: Set Cell Data for a Sheet */
	public static void setCellData(String xlFile, String xlSheet, int rowNum, int columnNum, String data)
			throws IOException {
		fi = new FileInputStream(xlFile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(xlSheet);
		row = ws.getRow(rowNum);
		cell = row.createCell(columnNum);
		cell.setCellValue(data);
		fo = new FileOutputStream(xlFile);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}

	/* Method 5: Customized method dev by Amit : Get entire row of data for entire test step */
		public static Map<String, String> getDataForStep(String TestStepName, String sheetName) throws Exception {

			String filePath = prop.getTestSuiteFilePath();


			int rowCount = XLUtil.getRowCount(filePath, sheetName);

			int colCount = XLUtil.getCellCount(filePath, sheetName, 0);
		
			String data;

			int RowForUse = -1;

			String[] colNames = new String[colCount];

			for (int i = 0; i <= rowCount; i++) {

				for (int j = 0; j < colCount; j++) {

					data = XLUtil.getCellData(filePath, sheetName, i, j);

					if (i == 0) {
						colNames[j] = data;
					}

					if (data.equalsIgnoreCase(TestStepName)) {
						RowForUse = i;
			
					}

				}

			}


			// Step4: Create one map
		     Map<String, String> FetchedValueMap = new HashMap<String, String>(10);
			
			if (RowForUse == -1) {
				
				//If we can't find the testMethod requested by user .... then return Blank Map. 
				
	             return FetchedValueMap;
			} else {

				for (int j = 0; j < colCount; j++) {

					data = XLUtil.getCellData(filePath, sheetName, RowForUse, j);

						FetchedValueMap.put(colNames[j], data);

				}

			}
	
			return FetchedValueMap;
		}
	
}
