package com.zxt.main;

import java.io.BufferedReader;
import java.io.IOException;

import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ZxAcManage {
	private static int lie = 20;
	private WritableSheet sheet;

	ZxAcManage(WritableSheet sheet) {
		this.sheet = sheet;
	}

	public void writeFirstString(String string, BufferedReader br) {
		String strLine = null;
		String strLine2 = null;
		int num = 0;
		try {
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains("STA-POOL-01") || strLine.contains("STA-POOL-03") || strLine.contains("STA-POOL-04")) {
					for (int i = 0; i < 4; i++) {
						br.readLine();
					}
					strLine2 = br.readLine();
					String[] datas = strLine2.split(" ");
					String data = datas[datas.length - 1];
					num = num + Integer.valueOf(data);
				}
			}
			System.out.println("num: " + num);
			jxl.write.Number addNumber = new jxl.write.Number(lie, 9, num);
			sheet.addCell(addNumber);
			lie++;
		} catch (IOException e) {
			System.out.println("解析中兴3期AC01的DHCP数据时，读取文本行错误");
			e.printStackTrace();
		} catch (RowsExceededException e) {
			System.out.println("解析中兴3期AC的DHCP数据时，表格错误");
			e.printStackTrace();
		} catch (WriteException e) {
			System.out.println("解析中兴3期AC的DHCP数据时，数据写入错误");
			e.printStackTrace();
		}
	}

	public void writeString(String string, BufferedReader br) {
		String strLine = null;
		String strLine2 = null;
		try {
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains("STA-POOL-01")) {
					for (int i = 0; i < 4; i++) {
						br.readLine();
					}
					strLine2 = br.readLine();
					String[] datas = strLine2.split(" ");
					String data = datas[datas.length - 1];
					System.out.println("num: " + data);
					jxl.write.Number addNumber = new jxl.write.Number(lie, 9, Integer.valueOf(data));
					sheet.addCell(addNumber);
					lie++;

				}
			}
		} catch (IOException e) {
			System.out.println("解析中兴3期AC的DHCP数据时，读取文本行错误");
			e.printStackTrace();
		} catch (RowsExceededException e) {
			System.out.println("解析中兴3期AC的DHCP数据时，表格错误");
			e.printStackTrace();
		} catch (WriteException e) {
			System.out.println("解析中兴3期AC的DHCP数据时，数据写入错误");
			e.printStackTrace();
		}
	}
}
