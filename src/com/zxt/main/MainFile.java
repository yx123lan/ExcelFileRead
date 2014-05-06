package com.zxt.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
		String path = "D:/Excel/MyExcel.xls";
		File file = new File(path);
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			System.out.println("EXCEL文件创建错误");
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
			ArrayList<File> list = new ArrayList<File>();
			File files = new File("D:/宜通工作/数据采集/会话日志/");
			String[] zxFiles = files.list();
			String zx = files + "/" + zxFiles[zxFiles.length - 1] + "/";
			oneFile("D:/宜通工作/数据采集/统计/", list);
			zxFile(zx, list);
			// 读取每一个路径下的内容
			// 读取文件夹内的每一个文件
			for (File readFile : list) {
				
				if (readFile.isDirectory()) {
					continue;
				}
				fr = new FileReader(readFile);
				br = new BufferedReader(fr);
				String strLine = null;
				while ((strLine = br.readLine()) != null) {
					// 根据读取的名称调用不同的类方法
					if (readFile.getName().contains("AP")) {
						ap.writeString(strLine, br);
					} else if (readFile.getName().contains("用户")) {
						user.writeString(strLine, br);
					} else if (readFile.getName().contains("DHCP")) {
						dhcp.writeString(strLine, br);
					} else if (readFile.getName().contains("中兴3期AC01")) {
						System.out.println(readFile.getName());
						zxac.writeFirstString(strLine, br);
						break;
					} else if (readFile.getName().contains("中兴3期AC")) {
						System.out.println(readFile.getName());
						zxac.writeString(strLine, br);
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("文本打开错误或无法打开");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("读取文本行错误");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e1) {
				System.out.println("流关闭时发生错误");
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
			System.out.println("文件解析和写入完成！");
			try {
				Runtime.getRuntime().exec("cmd.exe /c "+path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 去掉"D:/宜通工作/数据采集/统计/"里重复的文件,然后把不重复的加入容器中
	 * @param path
	 * @param list
	 */
	private static void oneFile(String path, ArrayList<File> list) {
		File ap = null;
		File user = null;
		File dhcp = null;
		for (String name : new File(path).list()) {
			ap = name.contains("AP") ? new File(path, name) : ap;
			user = name.contains("用户") ? new File(path, name) : user;
			dhcp = name.contains("DHCP") ? new File(path, name) : dhcp;
		}
		list.add(ap);
		list.add(user);
		list.add(dhcp);
	}
	/**
	 * 把路径下的所有文件加入到容器中
	 * @param path
	 * @param list
	 */
	private static void zxFile(String path, ArrayList<File> list) {
		for (String name : new File(path).list()) {
			list.add(new File(path, name));
		}
	}
}
