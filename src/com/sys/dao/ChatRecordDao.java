package com.sys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sys.entity.ChatRecord;
import com.sys.entity.User;


public interface ChatRecordDao {
	public ChatRecord add(ChatRecord chatRecord);
	public void update(ChatRecord chatRecord);
	public void delete(ChatRecord chatRecord);
	public ChatRecord getById(Integer id);
	public List<ChatRecord> getDataByCriteria(Integer page,Integer pageSize,DetachedCriteria criteria);
	public int getAllCountByCriteria(DetachedCriteria criteria);
	public DetachedCriteria getCriteriaByIds(Integer[] ids);
	public DetachedCriteria getCriteriaForAll();
	public DetachedCriteria getCriteriaForChatUserListByUser(User user);
}
