package com.zl.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author tzxx
 */
public class FileUtil {

	private static  String[] docMimeTypes = {"doc","docx","xlsx","xls","ppt","pptx","txt"};

	public static  String getFileMimeType(String fileOriginalName){
		return fileOriginalName.substring(fileOriginalName.lastIndexOf(".")+1);
	}

	public static  String getFileUniqueIdentification(String fileName){
		return fileName.substring(0,fileName.lastIndexOf("."));
	}

	public static boolean isDocMimeType(String mimeType){
		for (String type : docMimeTypes) {
			if(type.equals(mimeType)){
				return true;
			}
		}
		return false;
	}


	public static String handelFileNameCoding(HttpServletRequest request , String fileName){
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0 && request.getHeader("User-Agent").toUpperCase().indexOf("EDGE") < 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return fileName;
	}
	public static String fileType(String name){
		return name.substring(name.lastIndexOf(".")+1);
	}
}