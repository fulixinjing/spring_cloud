package com.flx.staticHtml;

import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * 页面静态化  StaticFilter拦截请求
 * @author flx
 *
 */
public class StaticHtml {

	public static Map<String, byte[]> htmlByte = new HashMap<String, byte[]>();
	
	//获取静态页面
	public void getStaticHtml(){
		try {
			URL url = new URL("http://localhost:8080/lol/toLol?tt=2");
			BufferedInputStream stream = new BufferedInputStream(url.openStream());
			byte[] bs= new byte[stream.available()];
			stream.read(bs);
			htmlByte.put("toLol", bs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void statusThread(){
		new Thread(new StaticHtmlThread()).start();
	}
	private class StaticHtmlThread implements Runnable{

		@Override
		public void run() {
		   while(true){
			   getStaticHtml();
			   try {
				   Thread.sleep(20*1000L);
			   } catch (InterruptedException e) {
				   e.printStackTrace();
			   }
		   }
		}
		
	}
	
}
