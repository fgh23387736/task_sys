package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.PrivateMessageDao;
import com.sys.entity.PrivateMessage;


@Component(value="privateMessageDaoImpl")
public class PrivateMessageDaoImpl implements PrivateMessageDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public PrivateMessage getById(Integer id) {
		return hibernateTemplate.get(PrivateMessage.class, id);
	}
	
	@Override
	public PrivateMessage add(PrivateMessage privateMessage) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(privateMessage);
		return privateMessage;
	}
	
	@Override
	public void update(PrivateMessage privateMessage) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(privateMessage);
	}
	
	@Override
	public void delete(PrivateMessage privateMessage) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(privateMessage);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<PrivateMessage> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<PrivateMessage> newPrivateMessages = new ArrayList<PrivateMessage>();
		if(page != null && pageSize != null){
			newPrivateMessages = (List<PrivateMessage>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newPrivateMessages = (List<PrivateMessage>) hibernateTemplate.findByCriteria(criteria);
		}
		return newPrivateMessages;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrivateMessage.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("privateMessageId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrivateMessage.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTitleAndUse(
			PrivateMessage privateMessage) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrivateMessage.class);
		if(privateMessage.getTitle() != null){
			criteria.add(Property.forName("title").like("%"+ privateMessage.getTitle()+"%") );
		}
		if(privateMessage.getUser() != null){
			criteria.add(Property.forName("user").eq( privateMessage.getUser() ) );
		}
		return criteria;
	}
	
	
	
	
}

