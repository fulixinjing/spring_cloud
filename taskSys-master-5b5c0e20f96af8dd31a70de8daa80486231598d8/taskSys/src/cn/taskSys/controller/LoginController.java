package cn.taskSys.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.taskSys.base.action.BaseAction;
import cn.taskSys.entity.Login;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.service.IUserService;
import cn.taskSys.shiro.realm.CFUsernamePasswordToken;
import cn.taskSys.utils.CommonUtil;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.StringUtil;

@Controller
public class LoginController extends BaseAction{
	@Autowired
	private IUserService userService;
	
	/**
	 * 单点登录
	 */
	
	@RequestMapping(value = "/ssoLogin", method = RequestMethod.GET)
	public String ssoLogin(@RequestParam(required = false,defaultValue = "usercenter") String cnsSSOPort,
			@RequestParam String uc, @RequestParam String ssoValue, 
			HttpServletRequest request,HttpSession session,
			HttpServletResponse response) {
		//解密cookie值,然后根据用工号查询是否有这个员工,如果有则进行权限分配,进入资产首页,匹配不到进入登录首页,解析错误提示"解析错误"
		//如果跳转地址不存在，则直接返回
		//token名称为空
		if (null == uc || uc.trim().compareTo("") == 0)
		{
			try {
				response.getWriter().print("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			//解密token的值
	    	try {
	    		String strDecryptTokenValue = CommonUtil.decryptSSOToken(ssoValue.trim());
	    		Map<String,String> tempMap = new HashMap<String,String>();
	    		String[] split = strDecryptTokenValue.split("&");
	    		for (int i = 0; i < split.length; i++) {
	    			String[] split2 = split[i].split("=");
	    			tempMap.put(split2[0], split2[1]);
				}
	    		String cnsUsername = tempMap.get("cnsUsername");
				//解密正常后返回true
				if (cnsSSOPort!=null&&cnsSSOPort.length() > 0)
				{
					StringTokenizer strTemp = new StringTokenizer(strDecryptTokenValue);
					while(strTemp.hasMoreTokens())
					{
						String temp = strTemp.nextToken("&");
						if ( temp.substring(0, temp.indexOf("=")).compareTo("cnsUserApp") == 0 )
						{
							String userAppTemp = temp.substring(temp.indexOf("=")+1);
							StringTokenizer str = new StringTokenizer(userAppTemp);
							boolean result = false;
							while(str.hasMoreTokens())
							{
								String strUserAppId = str.nextToken("|"); //应用标识
								if ( strUserAppId.compareTo(cnsSSOPort) == 0 )
								{
									result = true;
									break;
								}
							}
							Subject currentUser = SecurityUtils.getSubject();
							String dbSource="0";
							//放进共公变量里
							Map<String,String> m=new HashMap<String,String>();
							m.put("login", cnsUsername);
							User user=null;
							user=userService.getSessionUser(m);	
							CFUsernamePasswordToken token = new CFUsernamePasswordToken();
							token.setUsername(cnsUsername);							
							token.setPassword(user.getPassword().toCharArray());
							token.setRememberMe(true);
							token.setHost(request.getRemoteAddr());
							token.setUserSource(dbSource);
							currentUser.login(token);
							response.getWriter().print(result);
							if (currentUser.isAuthenticated()) {
								try{
									Map<String,String> map=new HashMap<String,String>();
											map.put("login", cnsUsername);
											map.put("dbSource", dbSource);
									User userinfo=userService.getSessionUser(map);	
									if(userinfo!=null){
										if("1".equals(userinfo.getUserType())){
											userinfo.setIds(userinfo.getUserId()+"");
											session.setAttribute("CKFinder_UserRole", "ROLE_LCJL");
										}else{
											String ids="";
											 map=new HashMap<String,String>();
											 	map.put("userId", cnsUsername);
												map.put("dbSource", dbSource);
												List<User> lu=userService.getTDUser(map);
											for(int a=0;a<lu.size();a++){
												if(lu.size()==a+1){
													ids=ids+lu.get(a).getIds();
												}else{
													ids=ids+lu.get(a).getIds()+",";
												}
											}
											userinfo.setIds(ids);
											session.setAttribute("CKFinder_UserRole", "ROLE_ADMIN");
										}
										userinfo.setLoginIp(request.getRemoteAddr());
										
										Map<Object,User> mapSession=new HashMap<Object, User>();
										mapSession.put(userinfo.getUserId(), userinfo);	
										session.setAttribute("JX_USERINFO", userinfo);//当前用户信息
										if("e10adc3949ba59abbe56e057f20f883e".equals(user.getPassword())){
											return "redirect:/index.do?menu="+EncodeUtil.BZ+"&upPass="+2+"&paword=123456";
										}else{
											return  "redirect:/index.do?menu="+EncodeUtil.BZ+"&upPass="+1;//业绩统计报表
										}
									}
								}catch(Exception e){
									return "login2";
								}
							}
						}
					}
				}else
				{
					response.getWriter().print("true");
				}
			}catch (Exception e) {
				return "login2";
			}
		}
		return "login2";
	}
	
	
	@RequestMapping("logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			
			}
		return "login2";
		}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@LogAnnotation(eventCode="1021002",eventProcess="")
	public String loginShow(
			HttpSession session,HttpServletRequest req, HttpServletResponse resp,Model m) {
		return "login2";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@LogAnnotation(eventCode="1021003",eventProcess="")
	public String login(Login login,HttpSession session,
			HttpServletRequest request, HttpServletResponse response, Model m) {
		Subject currentUser = SecurityUtils.getSubject();
		String dbSource="0";
		String username=StringUtil.nvlString(login.getUsername());
		String paword=StringUtil.nvlString(login.getPassword());
		
		if("".equals(username)){
			m.addAttribute("message","请输入用户名！");
		}
		else if("".equals(paword)){
			m.addAttribute("message","请输入密码！");
		}else if(username.getBytes().length != username.length()){
			m.addAttribute("message","用户名输入有误！");
		}else if(paword.getBytes().length != paword.length()){
			m.addAttribute("message","密码输入有误！");
		}
//		else if("".equals(StringUtil.nvlString(login.getCaptcha()))){
//			m.addAttribute("message","请输入验证码！");
//		}
		else {
//			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
//			if (!login.getCaptcha().toUpperCase().equals(code)){
//				m.addAttribute("message","验证码错误!");
//			}else{
			
			String ck_auto=StringUtil.nvlString(request.getParameter("ck_auto"));
			String ck_rmbUser=StringUtil.nvlString(request.getParameter("ck_rmbUser"));
			//是否自动登录
			if("1".equals(ck_auto)){
				Cookie Cauto = new Cookie("auto","true") ;
				Cauto.setMaxAge(60*60*60*12*30) ;
				response.addCookie(Cauto) ;
			}else{
				Cookie cookies[] = request.getCookies() ;
				Cookie c = null ;
				for(int i=0;i<cookies.length;i++){
					c = cookies[i] ;
					if(c.getName().equals("auto")){
						c.setMaxAge(0);
						response.addCookie(c) ;
					}
				}
			}
			//是否保存密码
			if("1".equals(ck_rmbUser)){
				try {
					Cookie CuserName = new Cookie("username",java.net.URLEncoder.encode(username,"utf-8"));

					Cookie CpassWord = new Cookie("password",StringUtil.nvlString(login.getPassword())) ;
					Cookie CrmbUser = new Cookie("rmbUser","true") ;
					CuserName.setMaxAge(60*60*60*12*30) ;
					CpassWord.setMaxAge(60*60*60*12*30) ;
					CrmbUser.setMaxAge(60*60*60*12*30) ;
					response.addCookie(CuserName) ;
					response.addCookie(CpassWord) ;
					response.addCookie(CrmbUser) ;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				Cookie cookies[] = request.getCookies() ;
				Cookie c = null ;
				for(int i=0;i<cookies.length;i++){
					c = cookies[i] ;
					if(c.getName().equals("username")){
						c.setMaxAge(0);
						response.addCookie(c) ;
					}else if(c.getName().equals("password")){
						c.setMaxAge(0);
						response.addCookie(c) ;
					}
					else if(c.getName().equals("rmbUser")){
						c.setMaxAge(0);
						response.addCookie(c) ;
					}
				}
			}
				
				//放进共公变量里
				CFUsernamePasswordToken token = new CFUsernamePasswordToken();
				token.setUsername(login.getUsername());
				token.setPassword(EncodeUtil.encode(login.getPassword().getBytes()).toCharArray());
				token.setCaptcha(login.getCaptcha());
				token.setRememberMe(true);
				token.setHost(request.getRemoteAddr());
				token.setUserSource(dbSource);
				try {
					
					currentUser.login(token);
					
				} catch (UnknownAccountException uae) {
					uae.printStackTrace();
					m.addAttribute("message", "账号不存在或已被停用!");
					return "login2";
				} catch (IncorrectCredentialsException ice) {
					ice.printStackTrace();
					m.addAttribute("username", login.getUsername());
					m.addAttribute("message", "密码错误!");
					return "login2";
				} catch (LockedAccountException lae) {
					lae.printStackTrace();
					m.addAttribute("message", "账号被锁定!");
					return "login2";
				} catch (AuthenticationException e) {
					e.printStackTrace();
					m.addAttribute("message", "用户名密码错误或账户已被停用！");
					return "login2";
				}catch (Exception e) {
					e.printStackTrace();
					m.addAttribute("message", "用户名密码错误，请重新输入！");
					return "login2";
				}
				if (currentUser.isAuthenticated()) {
					try{
						Map<String,String> map=new HashMap<String,String>();
								map.put("login", login.getUsername());
								map.put("dbSource", dbSource);
						User userinfo=userService.getSessionUser(map);	
						if(userinfo!=null){
							if("1".equals(userinfo.getUserType())){
								userinfo.setIds(userinfo.getUserId()+"");
								session.setAttribute("CKFinder_UserRole", "ROLE_LCJL");
							}else{
								String ids="";
								 map=new HashMap<String,String>();
								 	map.put("userId", login.getUsername());
									map.put("dbSource", dbSource);
									List<User> lu=userService.getTDUser(map);
								for(int a=0;a<lu.size();a++){
									if(lu.size()==a+1){
										ids=ids+lu.get(a).getIds();
									}else{
										ids=ids+lu.get(a).getIds()+",";
									}
								}
								userinfo.setIds(ids);
								session.setAttribute("CKFinder_UserRole", "ROLE_ADMIN");
							}
							userinfo.setLoginIp(request.getRemoteAddr());
							
							Map<Object,User> mapSession=new HashMap<Object, User>();
							mapSession.put(userinfo.getUserId(), userinfo);	
							
							session.setAttribute("JX_USERINFO", userinfo);//当前用户信息
							if("123456".equals(paword)){
								return "redirect:/index.do?menu="+EncodeUtil.BZ+"&upPass="+2+"&paword="+paword;
							}else{
								return  "redirect:/index.do?menu="+EncodeUtil.BZ+"&upPass="+1;//业绩统计报表
							}
						}else{
							m.addAttribute("message", EncodeUtil.BUSY);
						}
					}catch(Exception e){
						e.printStackTrace();
						m.addAttribute("message", EncodeUtil.BUSY);
					}
					
				}else{
					String str = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
					if (!"".equals(str)) { 
						m.addAttribute("message", "用户名密码错误！");
					}
				}
//			}
		}
		m.addAttribute("username", login.getUsername());
		m.addAttribute("password", login.getPassword());
		m.addAttribute("dbSource", dbSource);
		return "login2";
	}
	
}
