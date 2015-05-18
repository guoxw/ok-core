package cn.c.core.domain;

import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;

import cn.c.core.search.Search;

/**
 * 
 * @author hz453@126.com
 */
@MappedSuperclass
public abstract class IdEntity extends AbstractPersistable<Long> implements Search{

	private static final long serialVersionUID = -7443719330553954877L;

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	@Override
	public boolean isNew() {
		return null == getId() || 1 > getId();
	}
	
	@Override
	public String[] getSearchFields(){
		return new String[]{};
	}
}
