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

public class UserData {
	private int atAc2Column = 2;
	private int atZx3Column = 2;
	private int atAc4Column = 6;
	private int hsAc4Column = 3;
	private int dsHsAc4Column = 2;
	
	private Sheet ac2;
	private Sheet ac3;
	private Sheet ac4;
	private int ac2Row;
	private int ac3Row;
	private int ac4Row;
	
	private CellStyle style;
	

	public UserData(Workbook workbook) {
		// 获取各个表格
		this.ac2 = workbook.getSheet("二期");
		this.ac3 = workbook.getSheet("三期");
		this.ac4 = workbook.getSheet("四期");
		// 获取要填的表格的最大行数，然后减去2就是要填的用户数的行。
		this.ac2Row = this.ac2.getLastRowNum()-2;
		this.ac3Row = this.ac3.getLastRowNum()-2;
		this.ac4Row = this.ac4.getLastRowNum()-2;
		
		this.style = CellUtil.drawBroder(workbook);
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("傲天2期")) {
			System.out.println(string);
			readData(atAc2Column++, ac2Row, br, ac2);
		} else if (string.contains("傲天3期")) {
			System.out.println(string);
			readData(atZx3Column++, ac3Row, br, ac3);
		} else if (string.contains("傲天4期")) {
			System.out.println(string);
			readAt4Data(ac4Row, br, ac4);
		} else if (string.contains("中兴3期")) {
			System.out.println(string);
			readData(atZx3Column++, ac3Row, br, ac3);
		} else if (string.contains("华三4期")) {
			System.out.println(string);
			if (string.contains("德胜")) {
				readData(dsHsAc4Column, ac4Row, br, ac4);
			} else {
				readData(hsAc4Column++, ac4Row, br, ac4);
			}
		}
	}

	public void readData(int lie, int hang, BufferedReader br, Sheet sheet) {
		int number = 0;
		try {
			number = StringUtil.getNumber(br.readLine());
			System.out.println(number);
		} catch (IOException e) {
			System.out.println("解析用户在线数据时,读取数值错误");
			e.printStackTrace();
		}
		Row row = sheet.getRow(hang);
		if (row == null)
			row = sheet.createRow(hang);
		Cell cell = row.createCell(lie);
		cell.setCellStyle(style);
		cell.setCellValue(number);
	}

	public void readAt4Data(int hang, BufferedReader br, Sheet sheet) {
		String str = null;
		int oldNumber = 0;
		int number = 0;
		int num = 0;
		try {
			while ((str = br.readLine()) != null && !str.isEmpty()) {
				oldNumber = number;
				number = StringUtil.getNumber(str);
				System.out.println(number);
				// 检查数值是否相同，因为有可能有一次重复的数据。
				if (number == oldNumber && num < 1) {
					// 当计数器大于0时，说明已经遇到过一次相同的了，同时不再检查数值是否相同
					num++;
					continue;
				}
				Row row = sheet.getRow(hang);
				if (row == null)
					row = sheet.createRow(hang);
				Cell cell = row.createCell(atAc4Column++);
				cell.setCellStyle(style);
				cell.setCellValue(number);
				// 计数器清零
				num = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
