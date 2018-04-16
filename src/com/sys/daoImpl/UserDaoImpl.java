package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sys.dao.UserDao;
import com.sys.entity.User;


@Component(value="userDaoImpl")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public User getById(Integer id) {
		return hibernateTemplate.get(User.class, id);
	}
	
	@Override
	public User add(User user) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(user);
		return user;
	}
	
	@Override
	public void update(User user) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(user);
	}
	
	@Override
	public void delete(User user) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(user);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<User> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<User> newUsers = new ArrayList<User>();
		if(page != null && pageSize != null){
			newUsers = (List<User>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newUsers = (List<User>) hibernateTemplate.findByCriteria(criteria);
		}
		return newUsers;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("userId").in( ids ) );
		}
		return criteria;
	}


	@Override
	public DetachedCriteria getCriteriaByUserName(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Property.forName("userName").eq(userName));
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getByNameAndUserName(User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		if(user.getName() != null){
			criteria.add(Property.forName("name").like("%"+user.getName()+"%"));
		}
		if(user.getUserName() != null){
			criteria.add(Property.forName("userName").like("%"+user.getUserName()+"%"));
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaForSuspened() {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Property.forName("suspenedDeadline").gt(new Date()));
		return criteria;
	}
	
	
	
	
}

