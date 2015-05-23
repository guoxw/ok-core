package cn.c.core.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 请求 工具类 
 * @memo: 请使用HttpClientHelper类替代此类的功能.
 *        此类短时间内,大量并发操作可能导致运行主机socket耗尽,因为此时使用短连接,一个请求对应一个socket
 * @creator yuyoo(zhandulin@aspirehld.com)
 * @author www.aspirehld.com
 * @date 2011-5-10 下午04:54:17
 * @version 
 * @since
 */
//@Deprecated
public final class HttpClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class); 
	 
	/**
	 * 发送http post请求 
	 * @param url -  http url
	 * @param content - post内容
	 * @return -  响应字符串
	 */
	public static String post(String url, Map<String, String> pairs) {

		int timeout = 5000; //default timeout: 5 seconds
		if (null == pairs) {
			return post(url, null, timeout, true);
		}
		
		StringBuilder sb = new StringBuilder();
		int size = pairs.size();
		int curr = 1; 
		for (String k : pairs.keySet()) {
			String v = pairs.get(k);
			sb.append(k).append("=").append(v);
			if (curr < size) {
				sb.append("&");
			}
			curr++;
		}
		String req = sb.toString();
		return post(url, req, timeout, true); 
	}
	
	/**
	 * 发送http post
	 * @param url -  http url
	 * @param req -  post内容 
	 * @param response - true,处理响应内容
	 * @return - 如果处理响应内容,则返回响应内容,否则返回空;
	 */
	public static String post(String url, String req, boolean response) {
		
		 int timeout =  5000; // default timeout: 5 seconds
		 return post(url, req, timeout, response);
	}
	
	/**
     * 发送http post
     * @param url -  http url
     * @param req -  post内容
     * @param readTimeout - post请求响应超时设置
     * @param connectTimeout - post连接超时时间设置
     * @param response - true,处理响应内容
     * @return - 如果处理响应内容,则返回响应内容,否则返回空;
     */
    public static String post(String url, String req, int readTimeout, int connectTimeout, boolean response) {
        
        if (null == url) {
            throw new IllegalArgumentException("url不能为空");
        }
         
        BufferedOutputStream out = null;
        InputStreamReader in = null;
        try {
        	URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Encoding", "UTF-8");
            //FIXME:等用户平台修改获取content-type代码后, 需要增加Content-Type头
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8,*");
            conn.setRequestProperty("Pragma", "no-cachet");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Comp-Control", "dsmp/sms-mt");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = new BufferedOutputStream(conn.getOutputStream());
            if (null != req) {
                byte[] outBuffer = req.getBytes("utf-8");
                out.write(outBuffer);
            }
            out.flush();
            if (response) {
                in = new InputStreamReader(conn.getInputStream(), "utf-8");
                StringBuilder sb = new StringBuilder(conn.getContentLength() + 100);
                int readed = 0;
                char[] buffer = new char[1024];
                while ((readed = in.read(buffer)) > 0) {
                    for (int i = 0; i < readed; i++) {
                        sb.append(buffer[i]);
                    }
                } 
                String res = sb.toString();
                logger.debug("post ok with request:{}, response:{}", req, res);
                return res;  
            } else {
                conn.getInputStream();
            }
            return null;
        } catch (Exception ex) {
            logger.warn("post error with url:{}, request:{}", url, req);
            throw new RuntimeException(ex);
        } finally {
        	try {
        		if(in != null) {
        			in.close();
        		}
        		if(out != null) {
        			out.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        }
    }
	
	/**
	 * 发送http post
	 * @param url -  http url
	 * @param req -  post内容
	 * @param timeout - post请求响应超时设置
	 * @param response - true,处理响应内容
	 * @return - 如果处理响应内容,则返回响应内容,否则返回空;
	 */
	public static String post(String url, String req, int timeout, boolean response) {
		return post(url, req, timeout, 3000, response);
	}
	/**
	 * 发送http get请求
	 * @param url - http URL
	 * @return - 响应字符串
	 */
	public static String get(String url) {
		
		if (null == url) {
			throw new IllegalArgumentException("url不能为空");
		}
		 
		logger.debug("http get url:{}", url);
		InputStreamReader in = null;
		try { 
			URL readUrl = new URL(url); 
			// 打开和URL之间的连接
			URLConnection conn = readUrl.openConnection();  
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Accept-Language", "en-us,en");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
			conn.setRequestProperty("Accept-Charset", "UTF-8,*");
			conn.setRequestProperty("Keep-Alive", "60");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			// 建立实际的连接
			conn.connect(); 
			// 定义BufferedReader输入流来读取URL的响应
			in = new InputStreamReader(conn.getInputStream(), "utf-8");
			StringBuilder sb = new StringBuilder(conn.getContentLength() + 100);
			int readed = 0;
			char[] buffer = new char[1024];
			while ((readed = in.read(buffer)) > 0) {
				for (int i = 0; i < readed; i++) {
					sb.append(buffer[i]);
				}
			} 
			return sb.toString();
		} catch (Exception ex) {
			logger.warn("get error with url:{}", url);
			throw new RuntimeException(ex);
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	/**
	 * 防止非法实例化
	 */
	private HttpClientUtil() {}
}
