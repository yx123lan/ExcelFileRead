package com.zxt.data;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zxt.main.StringUtil;
import com.zxt.table.CellUtil;


public class ApData {
	private int atAc2Column = 1;
	private int atAc3Column = 1;
	private int hsAc4Column = 1;
	private int atAc4Column = 9;
	private Sheet ac2;
	private Sheet ac3;
	private Sheet ac4;
	private int ac2Row;
	private int ac3Row;
	private int ac4Row;
	
	private CellStyle style;

	public ApData(Workbook workbook) {
		this.ac2 = workbook.getSheet(SheetTitle.AP_2);
		this.ac3 = workbook.getSheet(SheetTitle.AP_3);
		this.ac4 = workbook.getSheet(SheetTitle.AP_4);
		
		this.ac2Row = this.ac2.getLastRowNum();
		this.ac3Row = this.ac3.getLastRowNum();
		this.ac4Row = this.ac4.getLastRowNum();
		
		this.style = CellUtil.drawBroder(workbook);
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("傲天2期")) {
			System.out.println(string);
			readData(atAc2Column++, ac2Row, br, ac2);
			
		} else if (string.contains("傲天3期")) {
			System.out.println(string);
			readData(atAc3Column++, ac3Row, br, ac3);
			
		} else if (string.contains("傲天4期")) {
			System.out.println(string);
			readAt4Data(ac4Row, br, ac4);
			
		} else if (string.contains("中兴3期")) {
			System.out.println(string);
			readData(atAc3Column++, ac3Row, br, ac3);
			
		} else if (string.contains("华三4期")) {
			System.out.println(string);
			readData(hsAc4Column++, ac4Row, br, ac4);
			
		}
	}

	public void readData(int lie, int hang, BufferedReader br, Sheet sheet) {
		int number = 0;
		try {
			number = StringUtil.getNumber(br.readLine());
		} catch (IOException e) {
			System.out.println("解析AP在线数据时,读取数值错误");
			e.printStackTrace();
		}
		Row row = sheet.getRow(hang);
		if(row == null)
			row = sheet.createRow(hang);
		Cell cell = row.createCell(lie);
		cell.setCellStyle(style);
		cell.setCellValue(number);
		System.out.println(number);
	}

	public void readAt4Data(int hang, BufferedReader br, Sheet sheet) {
		String str = null;
		int number = 0;
		try {
			while ((str = br.readLine()) != null && !str.isEmpty()) {
				number = StringUtil.getNumber(str);
				Row row = sheet.getRow(hang);
				if(row == null)
					row = sheet.createRow(hang);
				Cell cell = row.createCell(atAc4Column++);
				cell.setCellStyle(style);
				cell.setCellValue(number);
				System.out.println(number);
			}
		}catch (IOException e) {
			System.out.println("解析AP数据时,IO错误");
			e.printStackTrace();
		}
	}
}
