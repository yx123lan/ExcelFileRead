package com.zxt.table;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class CellUtil {
	
	private static final String[] CELL_TAGS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static CellStyle drawBroder(Workbook workbook){
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	
	public static short cellFormat(Workbook workbook, String format){
		DataFormat cellFormat = workbook.createDataFormat();
		return cellFormat.getFormat(format);
	}
	
	public static String getCellTag(int column){
		if(column<0)
			throw new IllegalArgumentException("输入的列序列号不能小于0");
		StringBuilder tag = new StringBuilder();
		while (column > 25){
			column = (column%25)-1;
			tag.append(CELL_TAGS[column/25]);
		}
		tag.append(CELL_TAGS[column]);
		return tag.toString();
	}
}
