/**
 * 
 */
package com.ylb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Version implements Comparable<Version> {
	private String code = "";

	@Override
	public int compareTo(Version version) {
		return compare(this.code, version.code);
	}

	public Version(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static int compare(String version1, String version2) {
		//System.out.println(version1 + "|" + version2);
		if (version1.equals(version2)) {
			return 0;
		}
		String[] version1Array = version1.split("\\.");
		String[] version2Array = version2.split("\\.");
		// System.out.println(version1Array.length + "|" + version2Array.length);
		int index = 0;
		// 获取最小长度值
		int minLen = Math.min(version1Array.length, version2Array.length);
		// System.out.println(minLen);
		int diff = 0;
		// 循环判断每位的大小
		while (index < minLen && (diff == 0)) {
			int code1 = 0;
			try {
				code1 = Integer.parseInt(version1Array[index]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			int code2 = 0;
			try {
				code2 = Integer.parseInt(version2Array[index]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			diff = code1 - code2;
			index++;
		}
		// System.out.println(diff);
		if (diff == 0) {
			// 如果位数不一致，比较多余位数;
			return version1Array.length > version2Array.length ? 1 : -1;
		} else {
			return diff > 0 ? 1 : -1;
		}
	}

	public static void readFile(String path,List <Version>list) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(path);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			//System.out.println(line);
			Version version = new Version(line);
			list.add(version);
		}

		fileInputStream.close();
	}


    public static void writeFile(String path,String content) throws Exception{
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }


	public static void main(String[] args) {
		//System.out.println(compare("1.0.0.0", "1.1.0"));
		List<Version> list1 = new ArrayList<Version>();
		StringBuffer sb=new StringBuffer();
		try {
			readFile("version.txt",list1);
			Collections.sort(list1);
			for (Version v : list1) {
				System.out.println(v.code);
				sb.append(v.code+"\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writeFile("version_bak.txt",sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
