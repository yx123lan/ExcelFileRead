package com.zxt.main;

import java.io.BufferedReader;
import java.io.IOException;

import jxl.write.Label;
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
		int num = 0;
		try {
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains("STA-POOL-01") || strLine.contains("STA-POOL-03") || strLine.contains("STA-POOL-04")) {
					AppUI.addText(strLine);
					System.out.println(strLine);
					for (int i = 0; i < 4; i++) {
						br.readLine();
					}
					String strLine2 = br.readLine();
					String[] datas = strLine2.split(" ");
					String data = datas[datas.length - 1];
					num = num + Integer.valueOf(data);
					AppUI.addText("num:" + num);
					System.out.println("num:" + num);
				}
			}
			jxl.write.Number addNumber = new jxl.write.Number(lie, 9, num);
			//Label label = new Label(lie, 9, "" + num);
			//sheet.addCell(label);
			sheet.addCell(addNumber);
			lie++;
		} catch (IOException e) {
			AppUI.addText("解析中兴3期AC01的DHCP数据时，读取文本行错误");
			e.printStackTrace();
		} catch (RowsExceededException e) {
			AppUI.addText("解析中兴3期AC的DHCP数据时，表格错误");
			e.printStackTrace();
		} catch (WriteException e) {
			AppUI.addText("解析中兴3期AC的DHCP数据时，数据写入错误");
			e.printStackTrace();
		}
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("STA-POOL-01")) {
			AppUI.addText(string);
			System.out.println(string);
			try {
				for (int i = 0; i < 4; i++) {
					br.readLine();
				}
				String strLine = br.readLine();
				String[] datas = strLine.split(" ");
				String data = datas[datas.length - 1];
				AppUI.addText(data);
				System.out.println(data);
				jxl.write.Number addNumber = new jxl.write.Number(lie, 9, Integer.valueOf(data));
				//Label label = new Label(lie, 9, data);
				//sheet.addCell(label);
				sheet.addCell(addNumber);
				lie++;
			} catch (IOException e) {
				AppUI.addText("解析中兴3期AC的DHCP数据时，读取文本行错误");
				e.printStackTrace();
			} catch (RowsExceededException e) {
				AppUI.addText("解析中兴3期AC的DHCP数据时，表格错误");
				e.printStackTrace();
			} catch (WriteException e) {
				AppUI.addText("解析中兴3期AC的DHCP数据时，数据写入错误");
				e.printStackTrace();
			}
		}
	}
}
