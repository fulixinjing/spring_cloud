package cn.taskSys.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.taskSys.utils.FileUtils;

import com.ckfinder.connector.ConnectorServlet;
import com.ckfinder.connector.ServletContextFactory;

/**
 * CKFinderConnectorServlet
 * @author ThinkGem
 * @version 2013-01-15
 */
public class CKFinderConnectorServlet extends ConnectorServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, false);
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, true);
		super.doPost(request, response);
	}
	
	private void prepareGetResponse(final HttpServletRequest request,
			final HttpServletResponse response, final boolean post) throws ServletException {
		String command = request.getParameter("command");
		String type = request.getParameter("type");
		// 初始化时，如果startupPath文件夹不存在，则自动创建startupPath文件夹
		if ("Init".equals(command)){
			String startupPath = request.getParameter("startupPath");// 当前文件夹可指定为模块名
			if (startupPath!=null){
					String path = "/opt/chpimage/caifu/"+startupPath+"/";
//					String realPath = request.getSession().getServletContext().getRealPath(path);
					FileUtils.createDirectory(path);
					try{
						ServletContextFactory.getServletContext().setAttribute("startupPath", startupPath);
					}catch(Exception e){
						e.printStackTrace();
					}
			} 
		}
		// 快捷上传，自动创建当前文件夹，并上传到该路径
		else if ("FileUpload".equals(command) && type!=null){
			try{
				String path = "/opt/chpimage/caifu/"+ServletContextFactory.getServletContext().getAttribute("startupPath")+"/"+type;
//				String realPath = request.getSession().getServletContext().getRealPath(path);
				FileUtils.createDirectory(path);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
