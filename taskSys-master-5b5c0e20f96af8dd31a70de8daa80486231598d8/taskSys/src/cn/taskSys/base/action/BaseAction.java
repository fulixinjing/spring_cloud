package cn.taskSys.base.action;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.taskSys.common.JsonMapper;


public class BaseAction<T> {
	
	protected HttpServletRequest request;
	
	static JsonMapper jsonMapper = new JsonMapper();
	
	/**
	 * 添加Flash消息
     * @param messages 消息 
	 */
	protected void addMessage(Model m, String... messages) {
		StringBuilder sb = new StringBuilder();    
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		m.addAttribute("message", sb.toString());
	}
	  
	
	/**
	 * 添加Flash消息
     * @param messages 消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
    
	
	protected int getPageSize() {
		if (null == request.getParameter("axgrid_pageSize"))
			return 10;
		return Integer.parseInt(request.getParameter("axgrid_pageSize"));

	}

	protected int getStartIndex() {
		if (null == request.getParameter("axgrid_startIndex"))
			return 0;
		return Integer.parseInt(request.getParameter("axgrid_startIndex"));
	}

	public String getTrimParameter(String name) {
		String v = request.getParameter(name);
		if (null == v)
			return null;
		v = v.trim();
		return v;
	}
	
	public String toListJson(int totalCount, List<T> lst, String[] listfields) {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");
		jsonBuilder.append("rowcount:" + totalCount);
		if (lst != null) {
			jsonBuilder.append(",rows:");
			jsonBuilder.append("[");
			for (int i = 0; i < lst.size(); i++) {
				if (i > 0)
					jsonBuilder.append(",");
				if (null != listfields)
					jsonBuilder.append(toJsonStr(lst.get(i), listfields));
				else
					jsonBuilder.append(toJsonStr(lst.get(i)));

			}
			jsonBuilder.append("]");
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}
	
	public static String toJsonStr(Object obj) {
		if (obj == null)
			return null;
		String result = null;
		try {
			result = jsonMapper.toJsonStr(obj);
		} catch (JsonGenerationException e) { // 
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String toJsonStr(Object value, String[] properties) {
		String result = null;

		try {
			result = jsonMapper.toJsonStr(value, properties);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
