package com.flx.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证处理类
 * @author flx
 *
 */
public class ValidateUtil {
	
	public static Map<String, Object> validate(Object bean){
		 Map<String, Object> result = new HashMap<String, Object>();  
	        result.put("message", "验证通过");  
	        result.put("result", true);  
		try{
			 Class<?> cls = bean.getClass();
			 Field[] fields = cls.getDeclaredFields();
			 for(Field f : fields){
				 // 通过反射获取该属性对应的值  
				 f.setAccessible(true);  
				 // 获取字段值  
				 Object value = f.get(bean);  
				 // 获取字段上的注解集合  
			     Annotation[] arrayAno = f.getAnnotations();  
			     for (Annotation annotation : arrayAno) {  
			    	 // 获取注解calss（注解类的Class）  
	                 Class<?> clazz = annotation.annotationType();
	                 // 获取注解类中的方法集合  
	                 Method[] methodArray = clazz.getDeclaredMethods();
	                 for (Method method : methodArray) {  
	                        // 获取方法名  
	                        String methodName = method.getName();  
	                        // 过滤错误提示方法的调用  
	                        if(methodName.equals("message")) {  
	                            continue;  
	                        }
	                        // 初始化注解验证的方法处理类 （我的处理方法卸载本类中）  
	                        Object obj = ValidateUtil.class.newInstance(); 
	                        // 根据方法名获取该方法  
                            Method m = obj.getClass().getDeclaredMethod(methodName, Object.class, Field.class);
                            // 调用该方法  
                            result = (Map<String, Object>)m.invoke(obj, value, f);  
	                 }
			     }
			 }
		}catch(Exception e){
			
		}
		return result;
	}
		/**
		 * 验证最大值  
		 * @param value
		 * @param field
		 * @return
		 */
	    public Map<String, Object> max(Object value, Field field) {
	        Map<String, Object> validateResult = new HashMap<String, Object>();  
	        ValidateAnnotation annotation = field.getAnnotation(ValidateAnnotation.class);
	        if(annotation.max()!=0){
		        if(value != null && Integer.valueOf(value.toString()) > annotation.max()) {  
					validateResult.put("message", annotation.message());  
					validateResult.put("result", false);  
		        }else{
		        	validateResult.put("message", "验证通过");  
					validateResult.put("result", true);
		        }
	        }  
	        return validateResult;  
	    }
	   
}
