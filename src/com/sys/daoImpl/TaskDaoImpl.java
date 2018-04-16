package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.TaskDao;
import com.sys.entity.Task;


@Component(value="taskDaoImpl")
public class TaskDaoImpl implements TaskDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Task getById(Integer id) {
		return hibernateTemplate.get(Task.class, id);
	}
	
	@Override
	public Task add(Task task) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(task);
		return task;
	}
	
	@Override
	public void update(Task task) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(task);
	}
	
	@Override
	public void delete(Task task) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(task);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<Task> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<Task> newTasks = new ArrayList<Task>();
		if(page != null && pageSize != null){
			newTasks = (List<Task>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newTasks = (List<Task>) hibernateTemplate.findByCriteria(criteria);
		}
		return newTasks;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("taskId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByReleaseUserAndReceiveUserAndState(
			Task task) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Task.class);
		if(task.getReleaseUser() != null){
			criteria.add(Property.forName("releaseUser").eq( task.getReleaseUser() ) );
		}
		if(task.getReceiveUser() != null){
			criteria.add(Property.forName("receiveUser").eq( task.getReceiveUser() ) );
		}
		
		if(task.getState() != null && task.getState() != -1){
			criteria.add(Property.forName("state").eq( task.getState())  );
		}
		criteria.addOrder(Order.desc("releaseTime"));
		
		return criteria;
	}
	
	
	
	
}

