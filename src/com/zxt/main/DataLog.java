package com.zxt.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zxt.data.SheetTitle;
import com.zxt.table.TimeBucket;

public class DataLog {

	private StringBuilder log;
	private TimeBucket timtBucket;

	private Sheet ac2;
	private Sheet ac3;
	private Sheet ac4;

	private int ac2Row;
	private int ac3Row;
	private int ac4Row;

	private int ac2Column;
	private int ac3Column;
	private int ac4Column;

	public DataLog(TimeBucket timtBucket) {
		this.log = new StringBuilder();
		this.timtBucket = timtBucket;
	}

	public void createLog(Workbook workbook) {
		this.ac2 = workbook.getSheet(SheetTitle.AP_2);
		this.ac3 = workbook.getSheet(SheetTitle.AP_3);
		this.ac4 = workbook.getSheet(SheetTitle.AP_4);

		this.ac2Row = this.ac2.getLastRowNum();
		this.ac3Row = this.ac3.getLastRowNum();
		this.ac4Row = this.ac4.getLastRowNum();

		this.ac2Column = this.ac2.getRow(this.ac2Row).getLastCellNum();
		this.ac3Column = this.ac3.getRow(this.ac3Row).getLastCellNum();
		this.ac4Column = this.ac4.getRow(this.ac4Row).getLastCellNum();

		analyzeData(ac2, ac2Row, ac2Column);
		analyzeData(ac3, ac3Row, ac3Column);
		analyzeData(ac4, ac4Row, ac4Column);
	}

	private void analyzeData(Sheet sheet, int rowNum, int cellNum) {
		int oldRow;
		if(timtBucket.equals(TimeBucket.AM))
			oldRow = rowNum - 3;
		else
			oldRow = rowNum - 1;
		
		double oldNumInt = 0;
		double newNumInt = 0;
		
		for (int i = 1; i < cellNum; i++) {
			Cell oldCell = sheet.getRow(oldRow).getCell(i);
			Cell newCell = sheet.getRow(rowNum).getCell(i);
			if(oldCell != null)
				oldNumInt = readData(oldCell.getCellType(), oldCell);
			if(newCell != null)
				newNumInt = readData(newCell.getCellType(), newCell);
			if (oldNumInt - newNumInt > 30 || oldNumInt - newNumInt < -30) {
				writeLogText(sheet, i, oldNumInt - newNumInt);
			}
		}
	}

	private void writeLogText(Sheet sheet, int cellNum, double number) {
		String tag = sheet.getRow(0).getCell(cellNum).getStringCellValue();
		log.append(tag).append(":").append((int)number).append("  ");
	}

	private double readData(int type, Cell cell) {
		double number = 0;
		switch (type) {
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_BOOLEAN:
		case Cell.CELL_TYPE_ERROR:
		case Cell.CELL_TYPE_FORMULA:
			number = 0;
		case Cell.CELL_TYPE_NUMERIC:
			number = cell.getNumericCellValue();
			break;
		case Cell.CELL_TYPE_STRING:
			number = Double.valueOf(cell.getStringCellValue());
			break;

		default:
			break;
		}
		return number;
	}

	public void writeLog() {
		FileOutputStream fos = null;
		File logPath = new File("D:/Excel", "log.txt");
		try {
			fos = new FileOutputStream(logPath);
			fos.write(log.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
