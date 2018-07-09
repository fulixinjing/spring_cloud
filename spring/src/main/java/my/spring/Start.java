package my.spring;

import org.mortbay.jetty.Server;

import my.spring.util.JettyUtils;

public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/spring";
	public static final String BASE_URL = "http://localhost:8080/spring";
	
	public static void main(String[] args) throws Exception {
		System.setProperty("java.awt.headless", "true");
		Server server = JettyUtils.buildDebugServer(PORT, CONTEXT);
		server.start();

		System.out.println("Hit Enter in console to stop server");
		
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
		
	}
}
