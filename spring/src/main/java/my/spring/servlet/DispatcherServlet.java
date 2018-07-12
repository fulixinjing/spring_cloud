package my.spring.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.mybatis.MapperProxy;
import my.mybatis.MySqlSession;
import my.spring.annotation.Autowired;
import my.spring.annotation.Controller;
import my.spring.annotation.Mapper;
import my.spring.annotation.RequestMapping;
import my.spring.annotation.Service;


/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	List<String> cls = new ArrayList<String>();
	Map<String,Object> obj =new HashMap<String,Object>();
	Map<String,Object> handerMethod =new HashMap<String,Object>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	try{
	    	//1.扫描包
	    	classFile("my");
	    	//2.实例化类
	    	classInstance();
	    	//3.依赖注入
	    	inject();
	    	//4.处理映射
	    	handlerMapping();
	    	
	    	//mybatis 映射xml
	    	xmlFile("mapper");
	    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	private void classFile(String path) throws Exception {
		String pathUrl = path.replace(".", "/");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> urls = loader.getResources(pathUrl);
		while(urls.hasMoreElements()) {
			String url=urls.nextElement().getFile();
			File file = new File(url);
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				//判断是不是目录
				if(file2.isDirectory()){
					classFile(path+"."+file2.getName());
				}else{
					if(file2.getName().endsWith(".class")){
						
						cls.add(path+"."+file2.getName());
						System.out.println(path+"."+file2.getName());
					}
				}
				
			}
		}
	}
	private void classInstance() throws Exception {
		if(cls.isEmpty()){
			System.out.println("空类");
		}else{
			for (String cls1 : cls) {
				String clsName = cls1.substring(0, cls1.length()-6);
				Class<?> clazz = Class.forName(clsName);
				//判断类上是否有controller注解
				if(clazz.isAnnotationPresent(Controller.class)){
					RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
					Object newInstance = clazz.newInstance();
					obj.put(requestMapping.value(), newInstance);
				}else if(clazz.isAnnotationPresent(Service.class)){
					Object newInstance = clazz.newInstance();
					Service service = clazz.getAnnotation(Service.class);
					if("".equals(service.value())){
						String name =clazz.getSimpleName();
						name = name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toLowerCase());
						obj.put(name, newInstance);
					}else{
						obj.put(service.value(), newInstance);
					}
				}else if(clazz.isAnnotationPresent(Mapper.class)){
					String name =clazz.getSimpleName();
					name = name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toLowerCase());
					//TODO MyBatis 需要优化
					Object newInstance=Proxy.newProxyInstance(clazz.getClassLoader(),new Class[] {clazz},new MapperProxy(new MySqlSession(), clazz));
					obj.put(name, newInstance);
				}
				
			}
		}
	}
	private void inject() throws Exception {
		if(cls.isEmpty()){
			System.out.println("空类");
		}else{
			for(Map.Entry<String, Object> entry :obj.entrySet()){
				Object instance = entry.getValue();
				Field[] fields = instance.getClass().getDeclaredFields();
				for (Field field : fields) {
					if(field.isAnnotationPresent(Autowired.class)){
						Autowired autowired = field.getAnnotation(Autowired.class);
						String value = autowired.value();
						field.setAccessible(true);
						if("".equals(value)){
							String name =field.getType().getSimpleName();
							value = name.replaceFirst(name.substring(0, 1),name.substring(0, 1).toLowerCase());
						}
						//给依赖注入属性赋值
						field.set(instance, obj.get(value));
					}
				}
			}
		}
	}
	private void handlerMapping() throws Exception {
		if(cls.isEmpty()){
			System.out.println("空类");
		}else{
			for(Map.Entry<String, Object> entry :obj.entrySet()){
				Object instance = entry.getValue();
				if(instance.getClass().isAnnotationPresent(Controller.class)){
					RequestMapping requestMapping = instance.getClass().getAnnotation(RequestMapping.class);
					//类上的requestMapping的值
					String path = requestMapping.value();
					//获取方法上的
					Method[] methods = instance.getClass().getMethods();
					for (Method method : methods) {
						if(method.isAnnotationPresent(RequestMapping.class)){
							RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
							String value = methodMapping.value();
							handerMethod.put(path+value, method);
						}
					}
					
				}
			}
		}
	}
	private void xmlFile(String path) {
		String url = path.replace(".", "/");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			Enumeration<URL> resources = classLoader.getResources(url);
			
			while(resources.hasMoreElements()){
				String fileUrl = resources.nextElement().getFile();
				File file =new File(fileUrl);
				File[] listFiles = file.listFiles();
				for (File file2 : listFiles) {
					if(file2.isDirectory()){//是目录
						xmlFile(path+"."+file2.getName());
					}else{
						System.out.println(file2.getPath());
						
						 InputStream openStream = new FileInputStream(file2);
						 Properties properties =new Properties();
						 properties.load(openStream);
						 for(Entry e: properties.entrySet()){
							 System.out.println(e.getKey()+"===="+e.getValue());
						 }
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String path = requestURI.replaceAll(request.getContextPath(), "");
		Method method = (Method) handerMethod.get(path);
		
		Object object = obj.get("/"+path.split("/")[1]);
		try {
			method.invoke(object, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
