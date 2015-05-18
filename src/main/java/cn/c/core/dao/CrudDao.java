package cn.c.core.dao;

import cn.c.core.domain.IdEntity;

/**
 * 
 * @author hz453@126.com
 */
public interface CrudDao {
	public <T extends IdEntity> T get(Class<? extends IdEntity> clazz, Long id);
	public <T extends IdEntity> T save(T t);
}
