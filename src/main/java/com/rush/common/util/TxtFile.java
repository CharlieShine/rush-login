package com.rush.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFile {
	public static String read(String filePath) {
		return read(filePath, "UTF-8");
	}

	public static String read(String filePath, String encoding) {
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String allTxt = "";
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					allTxt += lineTxt;
				}
				read.close();
				return allTxt;
			} else {
				System.out.println("找不到指定的文件");
				return "";
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			return "";
		}
	}

	public static List<String> readLine(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				List<String> listStr = new ArrayList<String>();
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					listStr.add(lineTxt);
				}
				read.close();
				return listStr;
			} else {
				System.out.println("找不到指定的文件");
				return new ArrayList<String>();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			return new ArrayList<String>();
		}
	}

	public static boolean writeLine(List<String> listStr, String writePath) {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(writePath), "UTF-8"), true);
			for (String s : listStr)
				pw.println(s);

			pw.close();

			return true;
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			return false;
		}
	}

}
