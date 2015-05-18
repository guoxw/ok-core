package cn.c.core.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.c.core.excepion.BusinessException;


/**
 * 
 * @author hz453@126.com
 */
public class AjaxAspect {

	
	public void packageJson(Map<String, Object> jsonMap) {
		jsonMap.put("success", true);
		
		System.out.println(jsonMap);
	}
	
	public void packageJsonForThrowException(Exception e) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("success", false);
		jsonMap.put("message", e.getMessage());
		
		System.out.println(jsonMap);
		throw e;
	}
	
	/**
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public Object packageByMap(ProceedingJoinPoint pjp) throws Throwable{
		Map<String, Object> map = null;
		
		try {
			map = (Map<String, Object>)pjp.proceed();
			map.put("success", true);
		} catch (BusinessException be) {
			map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("message", be.getMessage());
			
			be.printStackTrace();
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("message", "Sorry！出错了！！！");
			
			e.printStackTrace();
		}
		
		//System.out.println(jsonMap);
		return map;
	    
	}
}
