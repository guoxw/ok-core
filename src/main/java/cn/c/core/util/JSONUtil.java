package cn.c.core.util;

import java.io.InputStream;
import java.io.Reader;

import org.codehaus.jackson.map.ObjectMapper;

@SuppressWarnings("unchecked")
public class JSONUtil {
	 
	private static ObjectMapper mapper = null;
	
	static {
		mapper = new ObjectMapper();
	}	
 
	/**
	 * 将json转成java bean
	 * @param <T> - 多态类型
	 * @param json - json字符串
	 * @param clazz - java bean类型(Class)
	 * @return - java bean对象
	 */
	public static <T> T toBean(String json, Class<?> clazz) {
		
		T t = null;
		try { 
			t = (T) mapper.readValue(json, clazz); 
		} catch (Exception ex) {   
			String cause = String.format("json to bean error:[%s],json:[%s]", ex.getMessage(), json); 
			throw new IllegalArgumentException(cause, ex);
		}
		return (T)t;		
	}	
	 
		/**
		 * 将输入流转成java bean
		 * @param <T> - 多态类型
		 * @param reader - 读取流
		 * @param clazz - java bean类型(Class)
		 * @return - java bean对象
		 */
		public static <T> T toBean(Reader reader, Class<?> clazz) {
			
			T rtv = null;
			try {  
				rtv = (T)mapper.readValue(reader, clazz); 
			} catch (Exception ex) {  
				String cause = String.format("json to bean error:[%s]", ex.getMessage()); 
				throw new IllegalArgumentException(cause, ex);
			}
			return (T)rtv;		
		}
 
	/**
	 * 将输入流转成java bean
	 * @param <T> - 多态类型
	 * @param in - 输入流
	 * @param clazz - java bean类型(Class)
	 * @return - java bean对象
	 */
	public static <T> T toBean(InputStream in, Class<?> clazz) {
		
		T rtv = null;
		try {  
			rtv = (T)mapper.readValue(in, clazz); 
		} catch (Exception ex) {  
			throw new IllegalArgumentException("json-stream to bean error", ex);
		}
		return (T)rtv;		
	}
	
	/**
	 * 将java bean转成json
	 * @param bean - java bean
	 * @return - json 字符串
	 */
	public static String toJson(Object bean) {
		
		String json = null;
		try { 
			json = mapper.writeValueAsString(bean);
		} catch (Exception ex) {  
			String cause = String.format("bean to json error:[%s],bean-class:[%s]", ex.getMessage(), bean.getClass()); 
			throw new IllegalArgumentException(cause, ex);
		}
		return json;		
	}
	
	
	/**
	 * 防止实例化
	 */
	private JSONUtil(){}
}
