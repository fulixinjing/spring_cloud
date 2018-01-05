package cn.taskSys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.dao.TaskQueryDao;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.ITaskQueryService;
import cn.taskSys.utils.StringUtil;

@Service("taskQueryService")
public class TaskQueryServiceImpl implements ITaskQueryService {
	HttpSession session;

	@Autowired
	private TaskQueryDao taskQueryDao;
	
	//测试
	@LogAnnotation(eventCode="10000107",eventProcess="")
	public	String getModuleTree(String nodeId)throws Exception{
		if ("".equals(StringUtil.nvlString(nodeId))) {
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
			/*sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe_2.gif\" " + "im1=\"globe_2.gif\"" + "  im2=\"globe_2.gif\">");*/
			sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe_2.png\" " + "im1=\"globe_2.png\"" + "  im2=\"globe_2.png\">");
			sb.append("</item></tree>");
			return sb.toString();
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", nodeId);
		
		List<TaskInfo> list =  taskQueryDao.loadTaskOrgTree(map);
		String rootId = "", rootName = "";
		for (int i = 0; i < list.size(); i++) {
			if (nodeId.equals(list.get(i).getId())) {
				rootId = nodeId;
				rootName = list.get(i).getTaskname() + "";
				list.remove(i);
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
		/*sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe_2.gif\" " + "im1=\"globe_2.gif\"" + "  im2=\"globe_2.gif\">");*/
		sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe_2.png\" " + "im1=\"globe_2.png\"" + "  im2=\"globe_2.png\">");
		getChild(rootId, sb, list, null, null);
		sb.append("</item></tree>");
		list.clear();
		return sb.toString();
	}
	/**
	 * 递归增加字节点
	 * 
	 * @param rootId
	 * @param sb
	 * @param l
	 * @throws Exception
	 */
	private void getChild(String rootId, StringBuffer sb, List<TaskInfo> l, String userRole, User currentUser) throws Exception {
		TaskInfo map = null;
		List<TaskInfo> ltemp = new ArrayList<TaskInfo>();
		for (int i = 0; i < l.size(); i++) {
			map = l.get(i);
			//如果是团队经理要查询的包括自己创建的任务和上级分配的任务；
			//rootId.equals(map.getTaskpid())匹配自己创建的任务，
			//!currentUser.getPosition_id().equals("10035")匹配接收的任务
			if (rootId.equals(map.getTaskpid())) {
				ltemp.add(map);
			}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {
				if (!currentUser.getPosition_id().equals("10035") && currentUser.getUserId().equals(map.getExecutedevtasksys())) {//非部门经理
					ltemp.add(map);
				}
			}

		}
		for (int i = 0; i < ltemp.size(); i++) {
			map =  ltemp.get(i);
			if (!"".equals(StringUtil.nvlString(map.getIds()))) {
				sb.append("<item open=\"1\" text=\"" + map.getTaskContent() + "\" id=\"" + map.getId() + "\" im0=\"achive_2.png\" " + "im1=\"achive_2.png\"" + " im2=\"achive_2.png\">");
			} else {
				sb.append("<item open=\"1\" text=\"" + map.getTaskContent() + "\" id=\"" + map.getId() + "\" im0=\"folderClosed_2.png\" " + "im1=\"folderClosed_2.png\"" + " im2=\"folderClosed_2.png\">");
			/*	sb.append("<item open=\"1\" text=\"" + map.getTaskContent() + "\" id=\"" + map.getId() + "\" im0=\"folderClosed_2.gif\" " + "im1=\"folderOpen_2.gif\"" + " im2=\"folderClosed_2.gif\">");
			} else {
				sb.append("<item open=\"1\" text=\"" + map.getTaskContent() + "\" id=\"" + map.getId() + "\" im0=\"achive_2.gif\" " + "im1=\"achive_2.gif\"" + " im2=\"achive_2.gif\">");*/
			}
			getChild((String) map.getId(), sb, l, null, null);
			sb.append("</item>");
		}
	}

	/**
	 * 按照用户id返回这个用户所具有的模块树,异步树的第一层
	 * 
	 * @return List
	 */
	
	public String getTimerTree(Map<String, Object> mapParam) throws Exception {
		String nodeId = (String) mapParam.get("nodeId");
		String conQuarter=StringUtil.nvlString(mapParam.get("conQuarter"));//季度
		String conYear=StringUtil.nvlString(mapParam.get("conYear"));//年份
		String userRole = (String) mapParam.get("userRole");
		User currentUser = (User)mapParam.get("currentUser");
		if ("".equals(StringUtil.nvlString(nodeId))) {
			StringBuffer sb = new StringBuffer();
			/*sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
			sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");
			sb.append("</item></tree>");*/
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
			sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe_2.png\" " + "im1=\"globe_2.png\"" + "  im2=\"globe_2.png\">");
			/*sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe_2.gif\" " + "im1=\"globe_2.gif\"" + "  im2=\"globe_2.gif\">");*/
			sb.append("</item></tree>");
			return sb.toString();
		}
		Map<String,Object> map=new HashMap<String, Object>();

		if(!StringUtil.isNUll(userRole) && (userRole.equals("BMJL") || userRole.equals("XMJL"))){
			map.put("departmentId", currentUser.getDepartment_id());
		}else if (!StringUtil.isNUll(userRole) && userRole.equals("TDJL")) {
			map.put("departmentId", currentUser.getDepartment_id());
			map.put("teamId", currentUser.getTeam_id());
		}
		map.put("nodeId", nodeId);
		
		//季度、年份
		map.put("conQuarter", conQuarter);
		map.put("conYear", conYear);
		
		
		List<TaskInfo> list =taskQueryDao.loadTaskOrgTree(map); 
		
		String rootId = "1", rootName = "任务结构树";
		for (int i = 0; i < list.size(); i++) {
			if (nodeId.equals(StringUtil.nvlString(list.get(i).getId()))) {
				rootId = nodeId;
				rootName = StringUtil.nvlString(list.get(i).getTaskContent());
				list.remove(i);
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		/*sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
		sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");*/
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
		sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe_2.png\" " + "im1=\"globe_2.png\"" + "  im2=\"globe_2.png\">");
		
		getChild(rootId, sb, list, userRole, currentUser);
		sb.append("</item></tree>");
		list.clear();
		return sb.toString();
	}
	
	/**
	 * 按照用户id返回这个用户所具有的模块树,异步树的子界面
	 * 
	 * @return List
	 */
	public JSONArray getSubTimerTree(String orgId) throws Exception {
		JSONArray jsonArray = new JSONArray();
		if ("".equals(StringUtil.nvlString(orgId))) {
			return jsonArray;
		}
		
		List<TaskInfo> list = taskQueryDao.getOrgListId(orgId); 
		TaskInfo map = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			map = list.get(i);
			json.put("nodeId", map.getId());
			json.put("nodeName", map.getTaskContent());
			if (!"".equals(StringUtil.nvlString(map.getIds()))) {
				json.put("child", "1");
			}else{
				json.put("child", "0");
			}
			jsonArray.put(json);
			
		}
		list.clear();
		return jsonArray;
	}
    @Override
    public TaskInfo getTaskInfoById(String id)
    {
        return taskQueryDao.getTaskInfoById(id);
    }
	
}
