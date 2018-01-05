package cn.taskSys.filter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.taskSys.shiro.realm.CFUsernamePasswordToken;

/** 
 * @author	sun
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {

		return captchaParam;

	}

	protected String getCaptcha(ServletRequest request) {

		return WebUtils.getCleanParam(request, getCaptchaParam());

	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

		String username = getUsername(request)==null?"":getUsername(request);

		String password = getPassword(request)==null?"":getPassword(request);

		String captcha = getCaptcha(request)==null?"":getCaptcha(request);

		boolean rememberMe = isRememberMe(request);

		String host = getHost(request);
		
		return new CFUsernamePasswordToken(username,
				password.toCharArray(), rememberMe, host, captcha);

	}

}
