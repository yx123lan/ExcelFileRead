package com.zxt.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class SetFileName {
	public static final String TAG = SetFileName.class.getSimpleName();

	public void setName() {
		File file = new File("D:\\宜通工作\\数据采集\\log");
		File oneFile = null;
		String[] fileStr = file.list();
		String line = null;
		FileReader fr = null;
		BufferedReader br = null;
		String[] str = null;
		File newName = null;
		int num = 0;
		for (String name : fileStr) {
			oneFile = new File(file, name);
			if (!oneFile.isDirectory()) {
				try {
					fr = new FileReader(oneFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				br = new BufferedReader(fr);
				try {
					while ((line = br.readLine()) != null) {
						if (line.contains("WLAN-AC")) {
							str = line.split("-");
							num = Integer.valueOf(str[3]);
							if (line.contains("-AT")) {
								if (num > 0 && num < 7) {
									newName = new File(file, "傲天2期AC" + new DecimalFormat("00").format(num) + ".log");
								} else if (num > 32 && num < 73) {
									newName = new File(file, "傲天3期AC" + new DecimalFormat("00").format(num) + ".log");
								} else if (num > 8 && num < 27) {
									newName = new File(file, "傲天4期AC" + new DecimalFormat("00").format(num) + ".log");
								}
							} else if (line.contains("-ZX")) {
								newName = new File(file, "中兴3期AC" + new DecimalFormat("00").format(num) + ".log");
							} else if (line.contains("-HS")) {
								newName = new File(file, "华三4期AC" + new DecimalFormat("00").format(num) + ".log");
							}
							break;
						} else if (line.contains("WLAN-IB")) {
							str = line.split("-");
							num = Integer.valueOf(str[3]);
							if(line.contains("-HS6108-DS")){
								newName = new File(file + "/bras/" + "德胜华三4期AC" + new DecimalFormat("00").format(num) + ".log");
							}else{
								newName = new File(file + "/bras/" + "华三4期AC" + new DecimalFormat("00").format(num) + ".log");
							}
							break;
						}
					}
				} catch (IOException e) {
					System.out.println(TAG + "文件读取错误");
					e.printStackTrace();
				} finally {
					try {
						if (br != null) {
							br.close();
						}
						if (fr != null) {
							fr.close();
						}
					} catch (IOException e) {
						System.out.println(TAG + "流关闭错误");
						e.printStackTrace();
					}
					if (newName != null) {
						boolean isReName = oneFile.renameTo(newName);
						System.out.println(isReName ? newName.getName() + "重命名成功" : newName.getName() + "重命名失败，请检查文件是否已存在。");
					}
				}
			} else {
				String[] isD = oneFile.list();
				for (String fileName : isD) {
					File isFile = new File(oneFile, fileName);
					try {
						fr = new FileReader(isFile);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					br = new BufferedReader(fr);
					try {
						while ((line = br.readLine()) != null) {
							if (line.contains("WLAN-AC")) {
								line = line.replace("-", "");
								String nums = line.substring(11, 15);
								System.out.println(nums);
								num = Integer.valueOf(nums);
								if (line.contains("ZXW908")) {
									newName = new File(file, "/管理/中兴3期AC" + new DecimalFormat("00").format(num) + ".log");
									break;
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (br != null) {
								br.close();
							}
							if (fr != null) {
								fr.close();
							}
						} catch (IOException e) {
							System.out.println(TAG + "流关闭错误");
							e.printStackTrace();
						}
						if (newName != null) {
							//System.out.println(newName.getPath());
							boolean isReName = isFile.renameTo(newName);
							System.out.println(isReName ? newName.getName() + "重命名成功" : newName.getName() + "重命名失败，请检查文件是否已存在。");
						}
					}
				}
			}
		}
		System.out.println("重命名完成！");
	}
}
