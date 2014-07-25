package com.zxt.table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class DrawUserDataTable implements DrawTable {
	public static final int TIME_TITLE = 0;
	public static final int TEXT_TITLE = 1;
	
	private CellStyle style;
	private Sheet sheet;
	private TimeBucket time;
	private String day;

	public DrawUserDataTable(Workbook workbook, String sheetName, TimeBucket time, String day) {
		this.sheet = workbook.getSheet(sheetName);
		this.style = CellUtil.drawBroder(workbook);
		this.time = time;
		this.day = day;
	}

	@Override
	public void onDraw() {
		int firstRow = sheet.getLastRowNum() + 2;
		
		int amRow = firstRow + 1;
		int pmRow = firstRow - 1;
		
		switch (time) {
		case AM:
			// 合并表格。
			sheet.addMergedRegion(new CellRangeAddress(amRow, amRow + 2, 0, 0));
			// 填入标题。
			drawFristTitle(firstRow);
			sheet.getRow(firstRow).getCell(TIME_TITLE).setCellValue(day);
			drawContentTitle(amRow);
			sheet.getRow(amRow).getCell(TIME_TITLE).setCellValue(day+"  9:30");
			sheet.getRow(amRow).getCell(TEXT_TITLE).setCellValue("用户在线数");
			sheet.getRow(amRow + 1).getCell(TEXT_TITLE).setCellValue("DHCP分配数");
			sheet.getRow(amRow + 2).getCell(TEXT_TITLE).setCellValue("百分比");
			break;
		case PM:
			// 合并表格。
			sheet.addMergedRegion(new CellRangeAddress(pmRow, pmRow + 2, 0, 0));
			// 填入标题。
			drawContentTitle(pmRow);
			sheet.getRow(pmRow).getCell(TIME_TITLE).setCellValue(day+"  15:30");
			sheet.getRow(pmRow).getCell(TEXT_TITLE).setCellValue("用户在线数");
			sheet.getRow(pmRow + 1).getCell(TEXT_TITLE).setCellValue("DHCP分配数");
			sheet.getRow(pmRow + 2).getCell(TEXT_TITLE).setCellValue("百分比");
			break;

		default:
			break;
		}
	}

	private void drawFristTitle(int titleRow) {
		// 生成一行表格，设置边框。
		Row title = sheet.createRow(titleRow);
		for (int i = 0; i < 2; i++) {
			Cell cell = title.createCell(i);
			cell.setCellStyle(style);
		}
	}

	private void drawContentTitle(int contentRow) {
		// 生成三行表格，设置边框。
		for (int j = contentRow; j < contentRow + 3; j++) {
			Row content = sheet.createRow(j);
			content.createCell(TIME_TITLE).setCellStyle(style);
			content.createCell(TEXT_TITLE).setCellStyle(style);
		}
	}
}
