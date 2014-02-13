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
			AppUI.addText("EXCEL文件创建错误");
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
			// 把要解析的数据的路径都放进一个String数组
			String[] paths = { "E:/ExcelData/", "E:/ZX3/" };
			// 读取每一个路径下的内容
			for (String path : paths) {
				File files = new File(path);
				// 把文件夹内所有的文件的名称放进一个String数组
				String[] fileNames = files.list();
				// 读取文件夹内的每一个文件
				for (String fileName : fileNames) {
					fr = new FileReader(path + fileName);
					br = new BufferedReader(fr);
					String strLine = null;
					while ((strLine = br.readLine()) != null) {
						//System.out.println(strLine);
						if (fileName.contains("AP")) {
							ap.writeString(strLine, br);
						} else if (fileName.contains("用户")) {
							user.writeString(strLine, br);
						} else if (fileName.contains("DHCP")) {
							dhcp.writeString(strLine, br);
						} else if(fileName.contains("中兴3期AC01")){
							zxac.writeFirstString(strLine, br);
						} else{
							zxac.writeString(strLine, br);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			AppUI.addText("文本打开错误或无法打开");
			e.printStackTrace();
		} catch (IOException e) {
			AppUI.addText("读取文本行错误");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e1) {
				AppUI.addText("流关闭时发生错误");
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
			AppUI.addText("文件解析和写入完成！");
		}
	}
}
