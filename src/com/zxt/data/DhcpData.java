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

public class DhcpData {
	private int atAc2Column = 2;
	private int atAc3Column = 2;
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

	public DhcpData(Workbook workbook) {
		this.ac2 = workbook.getSheet(SheetTitle.USER_2);
		this.ac3 = workbook.getSheet(SheetTitle.USER_3);
		this.ac4 = workbook.getSheet(SheetTitle.USER_4);

		this.ac2Row = this.ac2.getLastRowNum() - 1;
		this.ac3Row = this.ac3.getLastRowNum() - 1;
		this.ac4Row = this.ac4.getLastRowNum() - 1;

		this.style = CellUtil.drawBroder(workbook);
	}

	public void writeString(String string, BufferedReader br) throws IOException {
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
			readZxData(atAc3Column++, ac3Row, br, ac3);
		} else if (string.contains("华三4期")) {
			System.out.println(string);
			if (string.contains("德胜")) {
				readHsData(dsHsAc4Column, ac4Row, br, ac4);
			} else {
				readHsData(hsAc4Column++, ac4Row, br, ac4);
			}
		}
	}

	public void readData(int lie, int hang, BufferedReader br, Sheet sheet) {
		int number = 0;
		try {
			String str = br.readLine();
			while (str.contains("warn")) {
				str = br.readLine();
			}
			number = StringUtil.getAtDHCP(str);
			// 因为数据可能有两行，所以读下一行不为空时，再读取一次数据然后和原来的数值相加。
			if ((str = br.readLine()) == null || str.isEmpty() || str.equals(" ")) {
				writeSheet(hang, lie, number, sheet);
			} else {
				number += StringUtil.getAtDHCP(str);
				writeSheet(hang, lie, number, sheet);
			}
			System.out.println(number);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readAt4Data(int hang, BufferedReader br, Sheet sheet) throws IOException {
		String str = null;
		int number = 0;
		while ((str = br.readLine()) != null && !str.isEmpty()) {
			number = StringUtil.getAtDHCP(str);
			System.out.println(number);
			Row row = sheet.getRow(hang);
			if (row == null)
				row = sheet.createRow(hang);
			Cell cell = row.createCell(atAc4Column++);
			cell.setCellStyle(style);
			cell.setCellValue(number);
		}
	}

	public void readZxData(int lie, int hang, BufferedReader br, Sheet sheet) throws IOException {
		String str = null;
		int number = 0;
		while ((str = br.readLine()) != null) {
			if (str.contains("STA-POOL-")) {
				// 跳过前四行
				for (int i = 0; i < 4; i++) {
					br.readLine();
				}
				number += StringUtil.getNumber(br.readLine());
			}
		}
		System.out.println("num: " + number);
		Row row = sheet.getRow(hang);
		if (row == null)
			row = sheet.createRow(hang);
		Cell cell = row.createCell(lie);
		cell.setCellStyle(style);
		cell.setCellValue(number);
	}

	public void readHsData(int lie, int hang, BufferedReader br, Sheet sheet) {
		int number = 0;
		try {
			String str = br.readLine();
			if (str == null || str.isEmpty())
				return;
			number = StringUtil.getNumber(str);
			System.out.println(number);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Row row = sheet.getRow(hang);
		if (row == null)
			row = sheet.createRow(hang);
		Cell cell = row.createCell(lie);
		cell.setCellStyle(style);
		cell.setCellValue(number);
	}

	private void writeSheet(int hang, int lie, int number, Sheet sheet) {
		Row row = sheet.getRow(hang);
		if (row == null)
			row = sheet.createRow(hang);
		Cell cell = row.createCell(lie);
		cell.setCellStyle(style);
		cell.setCellValue(number);
	}
}
