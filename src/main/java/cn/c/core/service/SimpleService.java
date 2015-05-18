package cn.c.core.service;

import cn.c.core.domain.IdEntity;

/**
 * 
 * @author hz453@126.com
 */
public interface SimpleService<T extends IdEntity> {
	public String sayHello();
	
}
