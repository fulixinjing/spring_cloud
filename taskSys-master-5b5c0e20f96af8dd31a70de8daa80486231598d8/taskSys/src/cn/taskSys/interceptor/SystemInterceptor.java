package cn.taskSys.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.taskSys.entity.Dictionary;
import cn.taskSys.service.UtilsService;

  
/** 
 *  
 * @author	sun
 */  
@Repository  
public class SystemInterceptor extends HandlerInterceptorAdapter {  
  
	@Autowired
	private UtilsService utilsService;
	
    @SuppressWarnings("unchecked")
	@Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
    	try{
	    	HttpSession session=request.getSession();
	    	if(session.getAttribute("Globe_Dictionary") == null
	    			|| ((List<Dictionary>)session.getAttribute("Globe_Dictionary")).size()<=0){
	    			Map map=new HashMap<String,String>();
					List<Dictionary> dlist=utilsService.getDictionaryList(map);
					session.setAttribute("Globe_Dictionary", dlist);
					
	    	}	    
    	
    	}catch(Exception e){
			e.printStackTrace();
		}  
    	
        return super.preHandle(request, response, handler);  
    }  
  
}  
