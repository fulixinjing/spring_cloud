package cn.taskSys.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户和密码（包含验证码）令牌
 * @author sun
 * @version 2014-11-12
 */
public class CFUsernamePasswordToken extends UsernamePasswordToken {

	private String captcha;
	private String userSource;

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CFUsernamePasswordToken() {
		super();
	}

	public CFUsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}
	

}