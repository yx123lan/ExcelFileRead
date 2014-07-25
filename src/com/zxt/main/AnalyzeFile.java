package com.zxt.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zxt.data.ApData;
import com.zxt.data.DhcpData;
import com.zxt.data.UserData;
import com.zxt.table.DrawApDataTable;
import com.zxt.table.DrawForlumn;
import com.zxt.table.DrawUserDataTable;
import com.zxt.table.TimeBucket;

public class AnalyzeFile {
	public AnalyzeFile() {

	}

	public void execut() {

	}
	
	public void drawTable(String userPath, String apPath, String day, TimeBucket timeBucket){
		try {
			FileInputStream inUserFile = new FileInputStream(userPath);
			FileInputStream inApFile = new FileInputStream(apPath);
			
			Workbook user = WorkbookFactory.create(inUserFile);
			new DrawUserDataTable(user, "二期", timeBucket, day).onDraw();
			new DrawUserDataTable(user, "三期", timeBucket, day).onDraw();
			new DrawUserDataTable(user, "四期", timeBucket, day).onDraw();
			
			Workbook ap = WorkbookFactory.create(inApFile);
			new DrawApDataTable(ap, "一、二期", timeBucket, day).onDraw();
			new DrawApDataTable(ap, "三期", timeBucket, day).onDraw();
			new DrawApDataTable(ap, "四期", timeBucket, day).onDraw();
			
			writeReadFile(user, ap);
			
			new DrawForlumn(user, "二期").onDraw();
			new DrawForlumn(user, "三期").onDraw();
			new DrawForlumn(user, "四期").onDraw();
			
			DataLog log = new DataLog(timeBucket);
			log.createLog(ap);
			log.writeLog();
			
			FileOutputStream userFos = new FileOutputStream("D:/Excel/Text.xlsx");
			user.write(userFos);
			userFos.close();
			
			FileOutputStream apFos = new FileOutputStream("D:/Excel/Text2.xlsx");
			ap.write(apFos);
			apFos.close();
			
			inUserFile.close();
			inApFile.close();
			
			reName(userPath, apPath);
			System.out.println("完成");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void reName(String userName, String apName){
		File oldAp = new File(apName);
		File oldUser = new File(userName);
		oldAp.delete();
		oldUser.delete();
		
		File ap = new File("D:/Excel/Text2.xlsx");
		File user = new File("D:/Excel/Text.xlsx");
		System.out.println(ap.renameTo(oldAp));
		System.out.println(user.renameTo(oldUser));
	}

	private void writeReadFile(Workbook userBook, Workbook apBook) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			ApData ap = new ApData(apBook);
			UserData user = new UserData(userBook);
			DhcpData dhcp = new DhcpData(userBook);
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
				fis = new FileInputStream(readFile);
				isr = new InputStreamReader(fis, "GBK");
				br = new BufferedReader(isr);
				String strLine = null;
				while ((strLine = br.readLine()) != null) {
					// 根据读取的名称调用不同的类方法
					if (readFile.getName().contains("AP")) {
						ap.writeString(strLine, br);
					} else if (readFile.getName().contains("用户")) {
						user.writeString(strLine, br);
					} else if (readFile.getName().contains("DHCP")) {
						dhcp.writeString(strLine, br);
					} else if (readFile.getName().contains("中兴3期AC")) {
						System.out.println(readFile.getName());
						dhcp.writeString(readFile.getName(), br);
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
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("文件解析和写入完成！");
		}
	}

	/**
	 * 去掉"D:/宜通工作/数据采集/统计/"里重复的文件,然后把不重复的加入容器中
	 * 
	 * @param path
	 * @param list
	 */
	private void oneFile(String path, ArrayList<File> list) {
		File ap = null;
		File user = null;
		File dhcp = null;
		for (String name : new File(path).list()) {
			ap = name.contains("AP") ? new File(path, name) : ap;
			user = name.contains("用户") ? new File(path, name) : user;
			dhcp = name.contains("DHCP") ? new File(path, name) : dhcp;
		}
		System.out.println("main:" + ap.getName() + " " + user.getName() + " " + dhcp.getName());
		list.add(ap);
		list.add(user);
		list.add(dhcp);
	}

	/**
	 * 把路径下的所有文件加入到容器中
	 * 
	 * @param path
	 * @param list
	 */
	private void zxFile(String path, ArrayList<File> list) {
		for (String name : new File(path).list()) {
			list.add(new File(path, name));
		}
	}
}
