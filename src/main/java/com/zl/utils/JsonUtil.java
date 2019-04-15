package com.zl.utils;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzxx
 */
public class JsonUtil {
	
	public static <T> T strToObj(Class<T> c,String jsonStr ){
		Gson gson = new Gson();
		T t = gson.fromJson(jsonStr, c);
		return t;		
	}

	public static <T> T strToObj(Class<T> c,String jsonStr,String pattern){
		Gson gson = new GsonBuilder()
				.setDateFormat(pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern)
				.create();
		T t = gson.fromJson(jsonStr, c);
		return t;
	}

	public static <T> List<T>  arrJsonStrToList(Class<T> c,String arrayJsonStr ){
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(arrayJsonStr).getAsJsonArray();
		ArrayList<T> lcs = new ArrayList<>();		 
		for(JsonElement obj : jsonArray ){
		    T cse = gson.fromJson( obj , c);
		    lcs.add(cse);  
		}
		return lcs;
	}

	public static String objToStr(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);	
	}

	/**构造json格式字符串
	 * @param k
	 * @param v
	 * @return
	 */
	public static String constructJsonStr(String k,Object v){
		return new StringBuilder().append("{\"").append(k).append("\":\"").append(v).append("\"}").toString();
	}

}
