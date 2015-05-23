package cn.c.core.repository.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.c.core.domain.IdEntity;
import cn.c.core.util.EntityUtil;

public class SpecificationImpl<T extends IdEntity> implements org.springframework.data.jpa.domain.Specification<T> {
	private String[] searchFields;
	private String[] keywords;
	private Object[] args;
	
	public SpecificationImpl(String[] keywords) {
		//this.searchFields = new String[]{};
		this.keywords = keywords;
	}

	public SpecificationImpl(String[] searchFields, String[] keywords) {
		this.searchFields = searchFields;
		this.keywords = keywords;
	}
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	
	@Override
	public Predicate toPredicate(Root<T> r, CriteriaQuery<?> q, CriteriaBuilder b) {
		Class<T> clazz = r.getModel().getJavaType();
		if(searchFields == null) {
			try {
				searchFields = clazz.newInstance().getSearchFields();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		Predicate[] likePredicates = new Predicate[keywords.length * searchFields.length];
		if((keywords.length * searchFields.length) > 0) {
			int index = 0;
			for(int i=0,len=searchFields.length; i<len; i++) {
				String[] field = searchFields[i].split("\\.");
				int lv = field.length;
				Join<T,Object> join = null;
				
				if(lv == 1){
				}else if(lv == 2) {
					join = r.join(field[0], JoinType.LEFT);
					join.as(EntityUtil.getGeterReturnType(clazz, field[0], true));
				}else{
					continue;
				}
				for(int j=0,len2=keywords.length; j<len2; j++) {
					if(lv == 1) {
						likePredicates[index++] = b.like(r.get(searchFields[i]).as(String.class), "%"+keywords[j]+"%");
					} else if(lv == 2) {
						likePredicates[index++] =  b.like(join.get(field[1]).as(String.class), "%"+keywords[j]+"%");
					}
				}
			}
			
		}
		
		Predicate customPredicate = this.customPredicate(r, q, b, this.args);
		if(likePredicates.length>0 && customPredicate != null) {
			q.where(b.and(customPredicate), b.or(likePredicates));
		} else if(likePredicates.length>0) {
			q.where(b.or(likePredicates));
		} else if(customPredicate != null) {
			q.where( b.and(customPredicate));
		}

		
		return q.getRestriction();
	}
	
	public Predicate customPredicate(Root<T> r, CriteriaQuery<?> q, CriteriaBuilder b, Object[] args) {
		return null;
	}

}
