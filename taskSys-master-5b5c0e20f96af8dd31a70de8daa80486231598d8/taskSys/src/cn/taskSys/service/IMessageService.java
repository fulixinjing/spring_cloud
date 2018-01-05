package cn.taskSys.service;

import java.util.List;
import java.util.Map;

import cn.taskSys.entity.Message;

public interface IMessageService {

	List<Message> getMessage(String userId);
	
	void updateMessage(String id);
	
	Message getMessageById(String id);
	
	List<Message> getMessageForList(Map<String,String> map);
	
	void updateMessageForTaskId(String taskInfoId);
}
