package com.zxt.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MainFile {
	private static WritableWorkbook workbook;
	private static WritableSheet sheet;

	public static void main(String args[]) {
		new AppUI();
	}

	public static void start() {
		File file = new File("E:/Excel/MyExcel.xls");
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			AppUI.addText("EXCEL�ļ���������");
			e.printStackTrace();
		}
		sheet = workbook.createSheet("First Sheet", 0);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			ApData ap = new ApData(sheet);
			UserData user = new UserData(sheet);
			DhcpData dhcp = new DhcpData(sheet);
			ZxAcManage zxac = new ZxAcManage(sheet);
			// ��Ҫ���������ݵ�·�����Ž�һ��String����
			String[] paths = { "E:/ExcelData/", "E:/ZX3/" };
			// ��ȡÿһ��·���µ�����
			for (String path : paths) {
				File files = new File(path);
				// ���ļ��������е��ļ������ƷŽ�һ��String����
				String[] fileNames = files.list();
				// ��ȡ�ļ����ڵ�ÿһ���ļ�
				for (String fileName : fileNames) {
					fr = new FileReader(path + fileName);
					br = new BufferedReader(fr);
					String strLine = null;
					while ((strLine = br.readLine()) != null) {
						//System.out.println(strLine);
						if (fileName.contains("AP")) {
							ap.writeString(strLine, br);
						} else if (fileName.contains("�û�")) {
							user.writeString(strLine, br);
						} else if (fileName.contains("DHCP")) {
							dhcp.writeString(strLine, br);
						} else if(fileName.contains("����3��AC01")){
							zxac.writeFirstString(strLine, br);
						} else{
							zxac.writeString(strLine, br);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			AppUI.addText("�ı��򿪴�����޷���");
			e.printStackTrace();
		} catch (IOException e) {
			AppUI.addText("��ȡ�ı��д���");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e1) {
				AppUI.addText("���ر�ʱ��������");
				e1.printStackTrace();
			}
			try {
				workbook.write();
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			AppUI.addText("�ļ�������д����ɣ�");
		}
	}
}
