package cn.c.core.util;

/**
 * 
 * @author hz453@126.com
 */
public class StringUtils extends org.springframework.util.StringUtils {
	public static boolean isNullOrEmpty(String string) {
		if(string == null) {
			return true;
		} else if (string.equals("")) {
			return true;
		} else {
			return false;
		}
	}

}
