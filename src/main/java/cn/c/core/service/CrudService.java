package cn.c.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.c.core.domain.IdEntity;
import cn.c.core.repository.domain.SpecificationImpl;


/**
 * 
 * @author hz453@126.com
 */
public interface CrudService<T extends IdEntity> extends SimpleService<T> {
	
	public boolean exists(Long id);

	public long count();
	
	public T findOne(Long id);
	
	public Iterable<T> findAll();
	
	public Iterable<T> findAll(Sort sort);

    public Page<T> findAll(Pageable pageable);
    
    public Iterable<T> findAll(SpecificationImpl<T> specification);
	
	public Iterable<T> findAll(SpecificationImpl<T> specification, Sort sort);

    public Page<T> findAll(SpecificationImpl<T> specification, Pageable pageable);
    
    public Iterable<T> getItems(String keyword, int paginationType, Pageable pageable, Object... args);
	
	public T save(T entity);
	
	public Iterable<T> save(Iterable<T> entitys);
	
	public void delete(T entity);
	
	public void delete(Long id);
	
	public void delete(String ids);
	
	public <V extends IdEntity> Iterable<V> findAll(Class<V> dtoClass);
	
	public <V extends IdEntity> V findOne(Class<V> dtoClass, Long id);
	
	public <V extends IdEntity> T save(Class<V> dtoClass, Class<T> doMainClass, V dto);
	
	public <V extends IdEntity> Iterable<T> save(Class<V> dtoClass, Class<T> doMainClass, Iterable<V> dtos);
	
}
