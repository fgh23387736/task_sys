package com.sys.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sys.dao.ChatRecordDao;
import com.sys.entity.ChatRecord;
import com.sys.entity.User;


@Component(value="chatRecordDaoImpl")
public class ChatRecordDaoImpl implements ChatRecordDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public ChatRecord getById(Integer id) {
		return hibernateTemplate.get(ChatRecord.class, id);
	}
	
	@Override
	public ChatRecord add(ChatRecord chatRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.save(chatRecord);
		return chatRecord;
	}
	
	@Override
	public void update(ChatRecord chatRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.update(chatRecord);
	}
	
	@Override
	public void delete(ChatRecord chatRecord) {
		// TODO 自动生成的方法存根
		hibernateTemplate.delete(chatRecord);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public List<ChatRecord> getDataByCriteria(Integer page, Integer pageSize,DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		criteria.setProjection(null);
		List<ChatRecord> newChatRecords = new ArrayList<ChatRecord>();
		if(page != null && pageSize != null){
			newChatRecords = (List<ChatRecord>) hibernateTemplate.findByCriteria(criteria,page-1,pageSize);
		}else{
			newChatRecords = (List<ChatRecord>) hibernateTemplate.findByCriteria(criteria);
		}
		return newChatRecords;
	}
	@Override
	public int getAllCountByCriteria(DetachedCriteria criteria) {
		// TODO 自动生成的方法存根
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(this.getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		return totalCount.intValue();
	}
	@Override
	public DetachedCriteria getCriteriaByIds(Integer[] ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChatRecord.class);
		if(ids != null && ids.length != 0){
			criteria.add(Property.forName("chatRecordId").in( ids ) );
		}
		return criteria;
	}
	

	@Override
	public DetachedCriteria getCriteriaForAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChatRecord.class);
		return criteria;
	}

	@Override
	public DetachedCriteria getCriteriaForChatUserListByUser(User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChatRecord.class);
		criteria.add(Restrictions.or(Restrictions.eq("sendUser", user),   
                    Restrictions.eq("receiveUser", user)));
		return criteria;
	}
	
	
	
	
}

