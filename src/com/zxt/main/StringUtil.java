package com.zxt.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static int getNumber(String string){
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(string);
		if(matcher.find())
			return Integer.valueOf(matcher.group());
		else
			return 0;
	}
	
	public static int getAtDHCP(String string){
		Pattern pattern = Pattern.compile("\\s\\d+\\s");
		Matcher matcher = pattern.matcher(string);
		// 取第二个匹配值。
		boolean isFind = false;
		for(int i=0; i<2; i++)
			isFind = matcher.find();
		// 如果匹配则返回转换后的值，不匹配返回0。
		if(isFind)
			return Integer.valueOf(matcher.group(0).trim());
		else
			return 0;
	}
}
