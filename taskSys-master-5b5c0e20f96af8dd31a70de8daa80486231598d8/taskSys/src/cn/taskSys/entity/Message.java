package cn.taskSys.entity;

import java.io.Serializable;

/**
 * 站内信
 *
 */
public class Message implements Serializable {
	private String id;					//ID
	private String receiverUserId;		//消息接收者ID
	private String senderUserId;		    //消息发送者ID
	private String sendTime;		        //消息发送时间
	private String content;				//消息内容
	private String title;				//消息标题
	private String isdel ;              //是否删除
	private String isread ;             //是否已读0未读;1已读
	private String taskInfoId;			//任务id
	private String messageStatus;		//消息标记（1分配，2接受，3.提交，4.确认，5.拒绝，6.任务延期）
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public String getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}
	public String getTaskInfoId() {
		return taskInfoId;
	}
	public void setTaskInfoId(String taskInfoId) {
		this.taskInfoId = taskInfoId;
	}
	

	
}
