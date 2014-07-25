package com.zxt.table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DrawForlumn implements DrawTable {
	private Workbook workbook;
	private String sheetName;
	
	public DrawForlumn(Workbook workbook,String sheetName){
		this.workbook = workbook;
		this.sheetName = sheetName;
	}
	@Override
	public void onDraw() {
		Sheet sheet = workbook.getSheet(sheetName);
		CellStyle style = CellUtil.drawBroder(workbook);
		style.setDataFormat(CellUtil.cellFormat(workbook, "0.00%"));
		int lastRow = sheet.getLastRowNum();
		int lastCell = sheet.getRow(lastRow-1).getLastCellNum();
		for(int i=2; i<lastCell; i++){
			Cell cell = sheet.getRow(lastRow).createCell(i);
			drawRatioFormula(cell);
			cell.setCellStyle(style);
		}
	}
	
	private void drawRatioFormula(Cell cell){
		int cIndex = cell.getColumnIndex();
		int rIndex = cell.getRowIndex();
		String cTag = CellUtil.getCellTag(cIndex);
		
		StringBuilder formula = new StringBuilder();
		formula.append(cTag).append(rIndex-1).append("/").append(cTag).append(rIndex);
		cell.setCellFormula(formula.toString());
	}

}
