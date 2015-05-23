package cn.c.core.util;

/**
 * 
 * @author hz453@126.com
 */
public class StringUtil extends org.springframework.util.StringUtils {
	public static boolean isNullOrEmpty(String string) {
		if(string == null) {
			return true;
		} else if (string.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 防止实例化
	 */
	private StringUtil(){}

}
