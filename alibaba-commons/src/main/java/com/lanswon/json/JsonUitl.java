package com.lanswon.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * FSATJSON ---和object 互转
 * @author GU-YW
 *
 */
public class JsonUitl {
	/**
	 * 将对象转为JSON
	 * @param object
	 * @return
	 */
	
	public static String object2Json(Object object) {
		
		return JSONObject.toJSONString(object);
	}
	
	/**
	 * 将json转为指定对象
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> T json2object(String content,Class<T> clazz){
		
		Object object=JSONObject.parseObject(content, clazz);
		
		return clazz.cast(object);
	}
	
	/**
	 * 将json转为指定对象集合
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T> List<T>  json2Collection(String content,Class<T> clazz) {
		List<T> list=JSONArray.parseArray(content, clazz);
		return list;
		
	}
	/**
	 * 将json转为指定对象集合
	 * @param content
	 * @param clazz
	 * @return
	 */
	public static <T, K>Map<K,T> json2map(String content) {
		JSONObject jsonObject=JSONObject.parseObject(content);
		@SuppressWarnings("unchecked")
		Map<K,T> map=JSONObject.toJavaObject(jsonObject, Map.class);
		return map;
		
	}

}
