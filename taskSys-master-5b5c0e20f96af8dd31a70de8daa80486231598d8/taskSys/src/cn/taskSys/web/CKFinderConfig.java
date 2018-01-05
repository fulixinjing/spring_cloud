/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.taskSys.web;

import javax.servlet.ServletConfig;

import com.ckfinder.connector.ServletContextFactory;
import com.ckfinder.connector.configuration.Configuration;

/**
 * CKFinder配置
 * @author ThinkGem
 * @version 2013-01-15
 */
public class CKFinderConfig extends Configuration {

	public static final String CK_BASH_URL = "/opt/chpimage/caifu/";

	public static final String CK_BASH_DIR="/opt/chpimage/caifu/";
	
	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);  
    }  
	
	@Override
    protected Configuration createConfigurationInstance() {
		try {
			
			this.baseURL = CK_BASH_URL+
					ServletContextFactory.getServletContext().getAttribute("startupPath")+"/";
			this.baseDir=CK_BASH_DIR+
					ServletContextFactory.getServletContext().getAttribute("startupPath")+"/";
					
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
    }


}
