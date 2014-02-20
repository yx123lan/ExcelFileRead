package com.zxt.main;

import java.io.BufferedReader;
import java.io.IOException;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ApData {
	private static int lie_0 = 0;
	private static int lie_1 = 0;
	private static int lie_2 = 0;
	private static int lie_3 = 8;
	private WritableSheet sheet;

	ApData(WritableSheet sheet) {
		this.sheet = sheet;
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("����2��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_0, 0, br, "AT");
			lie_0++;
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1, 1, br, "AT");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readAt4Data(br);
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1, 1, br, "ZX");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_2, 2, br, "HS");
			lie_2++;
		}
	}

	public void readData(int lie, int hang, BufferedReader br, String name) {
		String number = "";
		try {
			String str = br.readLine();
			String[] stringData = str.split(" ");
			if (name.equals("AT")) {
				number = stringData[0];
			} else if (name.equals("ZX") || name.equals("HS")) {
				number = stringData[stringData.length - 1];
			}
			AppUI.addText(number);
			System.out.println(number);
		} catch (IOException e) {
			AppUI.addText("����AP��������ʱ,��ȡ��ֵ����");
			e.printStackTrace();
		}
		jxl.write.Number addNumber = new jxl.write.Number(lie, hang, Integer.valueOf(number));
		// Label label = new Label(lie, hang, number);
		try {
			// sheet.addCell(label);
			sheet.addCell(addNumber);
		} catch (RowsExceededException e) {
			AppUI.addText("����AP��������ʱ,������");
			e.printStackTrace();
		} catch (WriteException e) {
			AppUI.addText("����AP��������ʱ,����д�����");
			e.printStackTrace();
		}
	}

	public void readAt4Data(BufferedReader br) {
		String str = null;
		String number =null;
		try {
			while ((str = br.readLine()) != null) {
				String[] stringData = str.split(" ");
				number = stringData[0];
				if(number==null||number.isEmpty()||number.equals(" "))
					break;
				jxl.write.Number addNumber = new jxl.write.Number(lie_3, 2, Integer.valueOf(number));
				AppUI.addText(number);
				System.out.println(number);
				// Label label = new Label(lie_3, 2, number);
				try {
					// sheet.addCell(label);
					sheet.addCell(addNumber);
				} catch (RowsExceededException e) {
					AppUI.addText("����AP��������ʱ,������");
					e.printStackTrace();
				} catch (WriteException e) {
					AppUI.addText("����AP��������ʱ,����д�����");
					e.printStackTrace();
				}
				lie_3++;
			}
		} catch (NumberFormatException e) {
			AppUI.addText("����AP����ʱ,ת��Integer����");
			e.printStackTrace();
		} catch (IOException e) {
			AppUI.addText("����AP����ʱ,IO����");
			e.printStackTrace();
		}
	}
}
