package com.zxt.main;

import java.io.BufferedReader;
import java.io.IOException;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class UserData {

	private static int lie_0 = 0;
	private static int lie_1 = 0;
	private static int lie_2 = 0;
	private static int lie_3 = 4;
	private WritableSheet sheet;
	UserData(WritableSheet sheet) {
		this.sheet = sheet;
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("����2��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_0, 5, br, "AT");
			lie_0++;
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1,8, br, "AT");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readAt4Data(br);
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1, 8, br, "ZX");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_2, 11, br, "HS");
			lie_2++;
		}
	}

	public void readData(int lie, int hang, BufferedReader br, String name) {
		String number = "";
		try {
			String str = br.readLine();
			String[] stringData = str.split(" ");
			if (name.equals("AT")) {
				number = stringData[stringData.length-1];
			} else if (name.equals("ZX") || name.equals("HS")) {
				number = stringData[2];
			}
			AppUI.addText(number);
			System.out.println(number);
		} catch (IOException e) {
			AppUI.addText("�����û���������ʱ,��ȡ��ֵ����");
			e.printStackTrace();
		}
		jxl.write.Number addNumber = new jxl.write.Number(lie, hang, Integer.valueOf(number));
		//Label label = new Label(lie, hang, number);
		try {
			//sheet.addCell(label);
			sheet.addCell(addNumber);
		} catch (RowsExceededException e) {
			AppUI.addText("�����û���������ʱ,������");
			e.printStackTrace();
		} catch (WriteException e) {
			AppUI.addText("�����û���������ʱ,����д�����");
			e.printStackTrace();
		}
	}

	public void readAt4Data(BufferedReader br) {
		for (int i = 0; i < 23; i++) {
			String str = null;
			try {
				// ���˵�10��21��22���⣬���������ݶ����ظ���Ҫ����1�в��ܶ�����һ����������
				if (i == 10 || i == 21 || i == 22) {
					str = br.readLine();
				} else {
					str = br.readLine();
					br.readLine();
					i++;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] stringData = str.split(" ");
			// �û����������ڷֽ�������ַ�����������λ���ϣ���Ҫ��ȡ�ַ�����������һ���ַ���
			String number = stringData[stringData.length-1];
			AppUI.addText(number);
			System.out.println(number);
			jxl.write.Number addNumber = new jxl.write.Number(lie_3, 11, Integer.valueOf(number));
			//Label label = new Label(lie_3, 11, number);
			try {
				//sheet.addCell(label);
				sheet.addCell(addNumber);
			} catch (RowsExceededException e) {
				AppUI.addText("�����û���������ʱ,������");
				e.printStackTrace();
			} catch (WriteException e) {
				AppUI.addText("�����û���������ʱ,����д�����");
				e.printStackTrace();
			}
			// ����ȡ����12��ʱ��Ҫ����2�в��ܶ�������AC25������
			if (i == 12) {
				i = i + 2;
				try {
					br.readLine();
					br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("" + i);
			}
			lie_3++;
		}
	}
}
