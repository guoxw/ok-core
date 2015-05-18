package cn.c.core.service;

import cn.c.core.domain.IdEntity;

/**
 * 
 * @author hz453@126.com
 */
public abstract class SimpleServiceImpl<T extends IdEntity> implements SimpleService<T> {
	
	public String sayHello(){
		String message = "Hello!";
		System.out.println(message);
		return message;
	}

}
