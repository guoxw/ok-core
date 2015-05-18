package cn.c.core.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.c.core.domain.IdEntity;

/**
 * 
 * @author hz453@126.com
 */
@NoRepositoryBean
public interface CrudRepository<T extends IdEntity> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {

}
