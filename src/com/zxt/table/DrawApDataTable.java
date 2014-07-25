package com.zxt.table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DrawApDataTable implements DrawTable {
	public static final int TIME_TITLE = 0;
	private Sheet sheet;
	private CellStyle style;
	private TimeBucket time;
	private String day;

	public DrawApDataTable(Workbook workbook, String sheetName, TimeBucket time, String day) {
		this.sheet = workbook.getSheet(sheetName);
		this.style = CellUtil.drawBroder(workbook);
		this.time = time;
		this.day = day;
	}

	@Override
	public void onDraw() {
		int firstRow = sheet.getLastRowNum() + 2;
		//Row title = sheet.createRow(firstRow);
		int amRow = firstRow + 1;
		int pmRow = firstRow - 1;
		// 填入标题。
		switch (time) {
		case AM:
			drawFristTitle(firstRow);
			sheet.getRow(firstRow).getCell(TIME_TITLE).setCellValue(day);
			drawContentTitle(amRow);
			sheet.getRow(amRow).getCell(TIME_TITLE).setCellValue(day+"  9:30");
			break;
		case PM:
			drawContentTitle(pmRow);
			sheet.getRow(pmRow).getCell(TIME_TITLE).setCellValue(day+"  15:30");
			break;

		default:
			break;
		}
	}
	
	private void drawFristTitle(int titleRow) {
		// 生成一行表格，设置边框。
		Cell cell = sheet.createRow(titleRow).createCell(TIME_TITLE);
		cell.setCellStyle(style);
	}

	private void drawContentTitle(int contentRow) {
		// 生成两行表格，设置边框。
		Row content = sheet.createRow(contentRow);
		Cell contentCell = content.createCell(TIME_TITLE);
		contentCell.setCellStyle(style);
	}

}
