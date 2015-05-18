package cn.c.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.c.core.common.constant.PaginationType;
import cn.c.core.dao.CrudDao;
import cn.c.core.domain.IdEntity;
import cn.c.core.repository.CrudRepository;
import cn.c.core.repository.domain.SpecificationImpl;
import cn.c.core.util.EntityUtils;
import cn.c.core.util.StringUtils;

/**
 * 
 * @author hz453@126.com
 */
public abstract class CrudServiceImpl<T extends IdEntity, R extends CrudRepository<T>> extends SimpleServiceImpl<T> implements CrudService<T> {

	private CrudDao dao;
	private R repository;
	
	public CrudDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(CrudDao dao) {
		this.dao = dao;
	}

	public R getRepository() {
		return repository;
	}
	@Autowired
	public void setRepository(R repository) {
		this.repository = repository;
	}
	
	

	@Override
	public boolean exists(Long id) {
		return this.repository.exists(id);
	}

	@Override
	public long count() {
		return this.repository.count();
	}

	@Override
	public T findOne(Long id) {
		T entity = this.repository.findOne(id);
		this.afterLoad(entity);
		return entity;
	}
	
	@Override
	public Iterable<T> findAll() {
		Iterable<T> entitys = this.repository.findAll();
		return entitys;
	}
	
	@Override
	public Iterable<T> findAll(Sort sort) {
		Iterable<T> entitys = this.repository.findAll(sort);
		return entitys;
	}
	
	@Override
    public Page<T> findAll(Pageable pageable) {
//		AccurateSearch[] AccurateSearchs = new AccurateSearchRequest[]{new AccurateSearchRequest("flowStatus","equal", 20)};
//		Page<T> entitys = this.repository.findAll(new Specification<T>(this.getSearchField(), new String[]{}, AccurateSearchs), pageable);
		
		Page<T> entitys = this.repository.findAll(pageable);
		return entitys;
	}
	
	@Override
	public Iterable<T> findAll(SpecificationImpl<T> specification) {

		Iterable<T> entitys = this.repository.findAll(specification);
		return entitys;
	}
	
	@Override
	public Iterable<T> findAll(SpecificationImpl<T> specification, Sort sort) {
		Iterable<T> entitys = this.repository.findAll(specification, sort);
		return entitys;
	}
	
	@Override
	public Page<T> findAll(SpecificationImpl<T> specification, Pageable pageable) {
		Page<T> entitys = this.repository.findAll(specification, pageable);
		return entitys;
	}
	
	@Override
	public Iterable<T> getItems(String keyword, int paginationType, Pageable pageable, Object... args) {
		String[] keywords = null;
		SpecificationImpl<T> specification = null;

		if(StringUtils.hasText(keyword)) {
			keywords = keyword.split(" ");
		} else {
			keywords = new String[]{};
		}
		specification = this.beforeGetItems(paginationType, pageable, keywords, args);
		if(specification == null && StringUtils.hasText(keyword)) {
			specification = new SpecificationImpl<T>(keywords);
		}
		
		Iterable<T> items = null;
		if(PaginationType.BACKSTAGE_PAGINATION == paginationType) {
			if(specification != null) {
				items = this.findAll(specification, pageable);
			} else {
				items = this.findAll(pageable);
			}
			
		} else {
			if(pageable.getSort() != null) {
				if(specification != null) {
					items = this.findAll(specification, pageable.getSort());
				} else {
					items = this.findAll(pageable.getSort());
				}
			} else {
				if(specification != null) {
					items = this.findAll(specification);
				} else {
					items = this.findAll();
				}
			}
		}
		this.afterGetItems(items);
		return items;
	}
	
	@Override
	public T save(T entity) {
		this.beforeSave(entity);
		this.repository.save(entity);
		this.afterSave(entity);
		return entity;
	}

	@Override
	public Iterable<T> save(Iterable<T> entitys) {
		return this.repository.save(entitys);
	}
	
	@Override
	public void delete(T entity) {
		if (entity!=null && !entity.isNew()) {
			this.beforeDelete(entity);
			this.repository.delete(entity);
			this.afterDelete(entity);
		}
	}

	@Override
	public void delete(Long id) {
		T entity = this.findOne(id);
		this.delete(entity);
	}

	@Override
	public void delete(String ids) {
		if(ids == null || ids.equals("")) {
			return;
		}
		
		String[] idArray = ids.split(",");
		for(String id : idArray) {
			if(id.matches("\\d+")) {
				this.delete(Long.parseLong(id));
			}
		}

	}

	@Override
	public <V extends IdEntity> Iterable<V> findAll(Class<V> dtoClass) {
		List<V> dtos = new ArrayList<V>();
		List<T> entitys = (List<T>) this.findAll();
		for (IdEntity entity : entitys) {
			V dto = EntityUtils.copy(dtoClass, entity);
			if (dto != null) {
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public <V extends IdEntity> V findOne(Class<V> dtoClass, Long id) {
		return EntityUtils.copy(dtoClass, this.findOne(id));
	}

	@Override
	public <V extends IdEntity> T save(Class<V> dtoClass, Class<T> doMainClass, V dto) {
		T entity = EntityUtils.copy(doMainClass, dto);
		return this.save(entity);
	}

	@Override
	public <V extends IdEntity> Iterable<T> save(Class<V> dtoClass, Class<T> doMainClass, Iterable<V> dtos) {
		List<T> entitys = new ArrayList<T>();
		for (V dto : dtos) {
			T entity = EntityUtils.copy(doMainClass, dto);
			if (entity != null) {
				entitys.add(entity);
			}
		}

		return this.save(entitys);
	}
	
	public void afterLoad(T entity) {}
	public SpecificationImpl<T> beforeGetItems(int paginationType, Pageable pageable, String[] keywords, Object... args) { return null;}

	public void afterGetItems(Iterable<T> entitys) {}
	
	public void beforeSave(T entity) {}
	public void beforeSave(Iterable<T> entitys) {}
	public void afterSave(T entity) {}
	public void afterSave(Iterable<T> entitys) {}
	
	public void beforeDelete(T entity) {}
	public void afterDelete(T entity) {}
	
}
