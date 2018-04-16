package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.CancelApplyDao;
import com.sys.entity.CancelApply;
import com.sys.entity.Task;
import com.sys.entity.User;


@Component(value="cancelApplyDaoImpl")
public class CancelApplyDaoImpl implements CancelApplyDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public CancelApply getById(Integer id) {
		return hibernateTemplate.get(CancelApply.class, id);
	}
	
	@Override
	public CancelApply add(CancelApply cancelApply) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(cancelApply);
		return cancelApply;
	}
	
	@Override
	public void update(CancelApply cancelApply) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(cancelApply);
	}
	
	@Override
	public void delete(CancelApply cancelApply) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(cancelApply);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<CancelApply> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<CancelApply> newCancelApplys = new ArrayList<CancelApply>();
		if(page != null && pageSize != null){
			newCancelApplys = (List<CancelApply>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newCancelApplys = (List<CancelApply>) hibernateTemplate.findByCriteria(criteria);
		}
		return newCancelApplys;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CancelApply.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("cancelApplyId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CancelApply.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTaskAndType(CancelApply cancelApply) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CancelApply.class);
		if(cancelApply.getTask() != null){
			criteria.add(Property.forName("task").eq( cancelApply.getTask() ) );
		}
		
		if(cancelApply.getType() != null && cancelApply.getType() != -1){
			criteria.add(Property.forName("type").eq( cancelApply.getType() ) );
		}
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTaskAndTypeForReleaseUser(
			CancelApply cancelApply, User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CancelApply.class);
		DetachedCriteria taskCriteria = DetachedCriteria.forClass(Task.class);
		if(cancelApply.getTask() != null){
			taskCriteria.add(Property.forName("taskId").eq(cancelApply.getTask().getTaskId()));
		}
		taskCriteria.add(Property.forName("releaseUser").eq(user));
		
		taskCriteria.setProjection(Property.forName("taskId")); 
		
		if(cancelApply.getType() != null && cancelApply.getType() != -1){
			criteria.add(Property.forName("type").eq( cancelApply.getType() ) );
		}
		criteria.add(Property.forName("task").in( taskCriteria ) );
		
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaByTaskAndTypeForReceiveUser(
			CancelApply cancelApply, User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CancelApply.class);
		DetachedCriteria taskCriteria = DetachedCriteria.forClass(Task.class);
		if(cancelApply.getTask() != null){
			taskCriteria.add(Property.forName("taskId").eq(cancelApply.getTask().getTaskId()));
		}
		taskCriteria.add(Property.forName("receiveUser").eq(user));
		
		taskCriteria.setProjection(Property.forName("taskId")); 
		
		if(cancelApply.getType() != null && cancelApply.getType() != -1){
			criteria.add(Property.forName("type").eq( cancelApply.getType() ) );
		}
		criteria.add(Property.forName("task").in( taskCriteria ) );
		
		return criteria;
	}
	
	
	
	
}

