package cn.taskSys.interceptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.taskSys.log.LogAnnotation;
/**
 * SystemLogAspect
 * @author sun
 *
 */
public class SystemLogAspect {     

	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
	  
	public Object writeLogInfo(ProceedingJoinPoint  joinPoint) throws Exception,  
            IllegalAccessException {  
        String temp = joinPoint.getStaticPart().toShortString();  
        String longTemp = joinPoint.getStaticPart().toLongString();  
        joinPoint.getStaticPart().toString();  
        String classType = joinPoint.getTarget().getClass().getName();  
        String methodName = temp.substring(10, temp.length() - 1);  
        Class<?> className = Class.forName(classType);  
        Class[] args = new Class[joinPoint.getArgs().length];  
        String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1,  
                longTemp.length() - 2)).split(",");  
        for (int i = 0; i < args.length; i++) {  
            if (sArgs[i].endsWith("String[]")) {  
                args[i] = Array.newInstance(Class.forName("java.lang.String"),  
                        1).getClass();  
            } else if (sArgs[i].endsWith("Long[]")) {  
                args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1)  
                        .getClass();  
            } else if (sArgs[i].indexOf(".") == -1) {  
                if (sArgs[i].equals("int")) {  
                    args[i] = int.class;  
                } else if (sArgs[i].equals("char")) {  
                    args[i] = char.class;  
                } else if (sArgs[i].equals("float")) {  
                    args[i] = float.class;  
                } else if (sArgs[i].equals("long")) {  
                    args[i] = long.class;  
                }  
            } else {  
                args[i] = Class.forName(sArgs[i]);  
            }  
        }  
        Method method = className.getMethod(  
                methodName.substring(methodName.indexOf(".") + 1,  
                        methodName.indexOf("(")), args);  
        Object resObj = null;
        try {
        	resObj = joinPoint.proceed();
        } catch (Throwable e) {
        	resObj = "{\"success\":false}";
        }
        // 如果该方法写了注解才做操�? 
        if (method.isAnnotationPresent(LogAnnotation.class)) {
            LogAnnotation logAnnotation = method  
                    .getAnnotation(LogAnnotation.class);  
            String eventCode = logAnnotation.eventCode();  
            String operateFunc = logAnnotation.eventProcess();  
            String operateMode = logAnnotation.operateModule();
            String operateType = logAnnotation.type();
            logger.info("ID:"+eventCode+",INFO:"+operateFunc);
        }
        return resObj;
    }
	
	
    /**
     * getIpAddr
     * @param request
     * @return
     */
	public static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
}  