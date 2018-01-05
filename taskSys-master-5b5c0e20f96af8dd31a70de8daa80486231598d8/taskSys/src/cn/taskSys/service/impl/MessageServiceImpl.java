package cn.taskSys.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.MessageDao;
import cn.taskSys.entity.Message;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {
	
	@Autowired
	private MessageDao messageDao;
	
	/**
	 * 根据状态检索消息内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	
	@LogAnnotation(eventCode="1022006",eventProcess="")
	public List<Message> getMessage(String userId) {	
				
		return (List<Message>) messageDao.getMessage(userId);
	}

	@Override
	@LogAnnotation(eventCode="1022006",eventProcess="")
	public void updateMessage(String id) {
		
		messageDao.updateMessage(id);
	}

	@LogAnnotation(eventCode="1116001", eventProcess="")
	@Override
	public Message getMessageById(String id) {
		return messageDao.getMessageById(id);
	}

	@Override
	public List<Message> getMessageForList(Map<String, String> map) {
		return messageDao.getMessageForList(map);
	}
	@Override
	public void updateMessageForTaskId(String taskInfoId){
		messageDao.updateMessageByTaskId(taskInfoId);
	}
	
}
