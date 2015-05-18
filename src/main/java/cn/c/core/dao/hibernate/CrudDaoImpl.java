package cn.c.core.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.c.core.dao.CrudDao;
import cn.c.core.domain.IdEntity;

/**
 * 
 * @author hz453@126.com
 */
public class CrudDaoImpl implements CrudDao{

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired(required=false)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdEntity> T get(Class<? extends IdEntity> clazz, Long id) {
		return (T)this.getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdEntity> T save(T t) {
		return (T)this.getSession().save(t);
	}

}
