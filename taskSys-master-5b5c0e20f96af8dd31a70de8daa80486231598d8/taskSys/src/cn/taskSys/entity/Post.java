package cn.taskSys.entity;

public class Post {
	/**
	 * 岗位ID
	 */
	private String postId;		
	/**
	 * 岗位名称
	 */
	private String postName;
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		if(postName != null){
			this.postName = postName.trim();
		}else{
			this.postName = postName;			
		}
	}			
	/**
	 * 用于角色弹出多选框是否被选中判断
	 */
	private boolean isChecked;
	
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}	
}
