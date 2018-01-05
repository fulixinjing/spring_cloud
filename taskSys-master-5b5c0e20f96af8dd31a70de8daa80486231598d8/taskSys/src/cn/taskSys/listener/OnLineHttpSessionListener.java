package cn.taskSys.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class OnLineHttpSessionListener implements HttpSessionListener {
	
	private static final Logger logger = Logger.getLogger(OnLineHttpSessionListener.class);
	
	private static Set<HttpSession> sessions = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		try {
			HttpSession session = event.getSession();
			ServletContext servletContext = session.getServletContext();
			sessions = (Set<HttpSession>) servletContext.getAttribute("sessions");
			if (sessions==null) {
				sessions = new HashSet<HttpSession>();
				servletContext.setAttribute("sessions", sessions);
			}
			sessions.add(session);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增session时监听异常！");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			HttpSession session = event.getSession();
			ServletContext servletContext = session.getServletContext();
			sessions = (Set<HttpSession>) servletContext.getAttribute("sessions");
			if (sessions!=null) {
				sessions.remove(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("销毁session时监听异常！");
		}
	}

}
