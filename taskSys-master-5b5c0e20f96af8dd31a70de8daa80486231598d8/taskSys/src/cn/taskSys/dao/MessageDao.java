package cn.taskSys.dao;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Message;

public interface MessageDao {
	
	List<Message> getMessage(String userId);
	
	void updateMessage(String id);
	
	Message getMessageById(String id);
	
	void updateMessageByTaskId(String taskInfoId);
	
	List<Message> getMessageForList(Map<String,String> map);
}
