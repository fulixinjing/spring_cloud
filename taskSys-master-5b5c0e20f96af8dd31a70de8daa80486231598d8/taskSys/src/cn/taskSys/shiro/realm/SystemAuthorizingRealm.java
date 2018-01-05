package cn.taskSys.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.common.cache.CacheMgr;
import cn.taskSys.common.cache.UserCachedEntity;
import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.User;
import cn.taskSys.service.IUserService;

/**
 * 系统安全认证实现
 * @author sun
 * @version 2014-11-12
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;
	
	public SystemAuthorizingRealm() {
		super();

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		/* 这里编写授权代码 */
		
		String username = (String) principals.fromRealm(getName()).iterator().next(); 
		if(username!=null && !"".equals(username)){
			Set<String> roleNames = new HashSet<String>();
			Set<String> permissions = new HashSet<String>();
			try{
				List<Role> rolelist =userService.getUserRole(username);
				
				if(rolelist != null && rolelist.size() >0 ){
					for(int i=0;i<rolelist.size();i++){
						roleNames.add(((Role)rolelist.get(i)).getRoleCode());
						
					}
					List<Menu> menulist =userService.getUserPermission(username);
					if(menulist != null && menulist.size() >0){
						for(int i=0;i<menulist.size();i++){
							 permissions.add(((Menu)menulist.get(i)).getPrivileges_code());
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		   
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
		    info.setStringPermissions(permissions);
		    return info;
		}
		return null;
	}
	
	

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		/* 认证代码 */ 
		User user=new User();
		CFUsernamePasswordToken token = (CFUsernamePasswordToken) authcToken;
		
		if(token.getUsername()==null || "".equals(token.getUsername())){
			throw new AccountException("用户名密码错误！");//没找到帐号
		}
		else if(token.getPassword()==null || "".equals(token.getPassword())){
			throw new IncorrectCredentialsException(); //如果密码错误  
		}
		else {
			// 判断验证码
				User login=new User();
				login.setLoginName(token.getUsername());
				login.setUserSource(token.getUserSource());
				try{
					user= userService.getIsLogin(login);
					
			        if (user == null) {
			        	throw new UnknownAccountException("用户不存在！");//没找到帐号
			        }else{
			        	addUserOnline(user, token.getHost(),token.getUserSource());
			        }
			        
				}catch(Exception e){
					e.printStackTrace();
					
				}
		}
		return new SimpleAuthenticationInfo(user.getLoginName(), 
				user.getPassword(), getName());

	}
	
	
	/**
     * 添加在线用户
     *
     * @param userLogin
     * @param host
     * @since 2014.11.20
     */
    private void addUserOnline(User userLogin, String host,String source) {
        UserCachedEntity<?> user = new UserCachedEntity<Object>();
        user = new UserCachedEntity<Object>();
        user.setLoginUUID(String.valueOf(userLogin.getUserId()));
        user.setLoginName(userLogin.getLoginName());
        user.setLoginCaption(userLogin.getUserName());
        user.setEmpCode(userLogin.getEmpCode());
        user.setLoginIP(host);
        user.setLoginHeartbeatDate(CacheMgr.getUserHeartbeat());
        user.setLoginType(CacheMgr.CONST_LOGIN_TYPE_OK);
        user.setLoginDesc("Login Successful");
        user.setSource(source);
        CacheMgr.addUserOnline(user);
    }

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
}
