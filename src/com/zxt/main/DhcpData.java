package com.zxt.main;

import java.io.BufferedReader;
import java.io.IOException;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DhcpData {
	private static int lie_0 = 0;
	private static int lie_1 = 0;
	private static int lie_2 = 0;
	private static int lie_3 = 4;
	private WritableSheet sheet;

	DhcpData(WritableSheet sheet) {
		this.sheet = sheet;
	}

	public void writeString(String string, BufferedReader br) {
		if (string.contains("����2��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_0, 6, br, "AT");
			lie_0++;
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1, 9, br, "AT");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readAt4Data(br);
		} else if (string.contains("����3��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_1, 9, br, "ZX");
			lie_1++;
		} else if (string.contains("����4��")) {
			AppUI.addText(string);
			System.out.println(string);
			readData(lie_2, 12, br, "HS");
			lie_2++;
		} else {
			// System.out.println("ZXT");
		}
	}

	public void readData(int lie, int hang, BufferedReader br, String name) {
		String number = null;
		try {
			String str = br.readLine();
			String[] stringData = str.split(" ");
			if (name.equals("AT")) {
				number = AtData(stringData);
				// ����ȡ����ֵ��0ʱ���ٶ�ȡһ�����ݡ�
				while (number.equals("0")) {
					str = br.readLine();
					stringData = str.split(" ");
					number = AtData(stringData);
				}
			} else if (name.equals("ZX") || name.equals("HS")) {
				number = stringData[3];
			}
			AppUI.addText(number);
			System.out.println(number);
		} catch (IOException e) {
			AppUI.addText("����DHCP����ʱ,��ȡ��ֵ����");
			e.printStackTrace();
		}
		jxl.write.Number addNumber = new jxl.write.Number(lie, hang, Integer.valueOf(number));
		// Label label = new Label(lie, hang, number);
		try {
			// sheet.addCell(label);
			sheet.addCell(addNumber);
		} catch (RowsExceededException e) {
			AppUI.addText("����DHCP����ʱ,������");
			e.printStackTrace();
		} catch (WriteException e) {
			AppUI.addText("����DHCP����ʱ,����д�����");
			e.printStackTrace();
		}
	}

	public void readAt4Data(BufferedReader br) {
		String str = null;
		String number = null;
		try {
			while ((str = br.readLine()) != null) {
				String[] stringData = str.split(" ");
				number = AtData(stringData);
				if(number==null||number.isEmpty()||number.equals(" "))
					break;
				AppUI.addText(number);
				System.out.println(number);
				jxl.write.Number addNumber = new jxl.write.Number(lie_3, 12, Integer.valueOf(number));
				// Label label = new Label(lie_3, 12, number);
				try {
					// sheet.addCell(label);
					sheet.addCell(addNumber);
				} catch (RowsExceededException e) {
					AppUI.addText("����DHCP����ʱ,������");
					e.printStackTrace();
				} catch (WriteException e) {
					AppUI.addText("����DHCP����ʱ,����д�����");
					e.printStackTrace();
				}
				lie_3++;
			}
		} catch (NumberFormatException e) {
			AppUI.addText("����DHCP����ʱ,ת��Integer����");
			e.printStackTrace();
		} catch (IOException e) {
			AppUI.addText("����DHCP����ʱ,IO����");
			e.printStackTrace();
		}
	}

	public String AtData(String[] stringData) {
		String number = null;
		int num = 0;
		for (String data : stringData) {
			// ��ȥ�ո�
			if (data.equals(" ") || data.isEmpty()) {
			} else {
				num++;
				if (num == 4) {
					number = data;
				}
			}
		}
		return number;
	}
}
