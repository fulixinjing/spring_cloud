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

import cn.taskSys.dao.TestOrgDao;
import cn.taskSys.entity.TaskOrg;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.ITestService;
import cn.taskSys.utils.StringUtil;

@Service("testService")
public class testServiceImpl implements ITestService {
	HttpSession session;

	@Autowired
	private TestOrgDao testOrgDao;
	
	//测试
	@LogAnnotation(eventCode="10000107",eventProcess="")
	public	String getModuleTree(String nodeId)throws Exception{
		if ("".equals(StringUtil.nvlString(nodeId))) {
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
			sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");
			sb.append("</item></tree>");
			return sb.toString();
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", nodeId);
		
		List<TaskOrg> list =  testOrgDao.loadTaskOrgTree(map);
		String rootId = "", rootName = "";
		for (int i = 0; i < list.size(); i++) {
			if (nodeId.equals(list.get(i).getId())) {
				rootId = nodeId;
				rootName = list.get(i).getOrgName() + "";
				list.remove(i);
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
		sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");
		getChild(rootId, sb, list);
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
	private void getChild(String rootId, StringBuffer sb, List<TaskOrg> l) throws Exception {
		TaskOrg map = null;
		List<TaskOrg> ltemp = new ArrayList<TaskOrg>();
		for (int i = 0; i < l.size(); i++) {
			map = l.get(i);
			if (rootId.equals(map.getParentId())) {
				ltemp.add(map);
			}
		}
		for (int i = 0; i < ltemp.size(); i++) {
			map =  ltemp.get(i);
			if (!"".equals(StringUtil.nvlString(map.getIds()))) {
				
				sb.append("<item open=\"1\" text=\"" + map.getOrgName() + "\" id=\"" + map.getId() + "\" im0=\"folderClosed.gif\" " + "im1=\"folderOpen.gif\"" + " im2=\"folderClosed.gif\">");
			} else {
				sb.append("<item open=\"1\" text=\"" + map.getOrgName() + "\" id=\"" + map.getId() + "\" im0=\"achive.gif\" " + "im1=\"achive.gif\"" + " im2=\"achive.gif\">");
			}
			getChild((String) map.getId(), sb, l);
			sb.append("</item>");
		}
	}

	/**
	 * 按照用户id返回这个用户所具有的模块树,异步树的第一层
	 * 
	 * @return List
	 */
	
	public String getTimerTree(String orgId) throws Exception {
		if ("".equals(StringUtil.nvlString(orgId))) {
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
			sb.append("<item text=\"数据错误\" open=\"1\" id=\"-1\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");
			sb.append("</item></tree>");
			return sb.toString();
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", orgId);
		List<TaskOrg> list =testOrgDao.loadTaskOrgTree(map); 
		
		String rootId = "", rootName = "";
		for (int i = 0; i < list.size(); i++) {
			if (orgId.equals(StringUtil.nvlString(list.get(i).getId()))) {
				rootId = orgId;
				rootName = StringUtil.nvlString(list.get(i).getOrgName());
				list.remove(i);
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<tree id=\"0\">");
		sb.append("<item text=\"" + rootName + "\" open=\"1\" id=\"" + rootId + "\" im0=\"globe.gif\" " + "im1=\"globe.gif\"" + "  im2=\"globe.gif\">");
		
		getChild(rootId, sb, list);
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
		
		List<TaskOrg> list = testOrgDao.getOrgListId(orgId); 
		TaskOrg map = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = new JSONObject();
			map = list.get(i);
			json.put("nodeId", map.getId());
			json.put("nodeName", map.getOrgName());
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
	
}
