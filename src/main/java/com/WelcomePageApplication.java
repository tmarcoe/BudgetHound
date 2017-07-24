package com;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.dao", "com.entity", "com.service", "com.controllers", "com.config",
		"com.rest" })
public class WelcomePageApplication implements CommandLineRunner  {
	
    @Autowired
    DataSource dataSource;

	public static void main(String[] args) throws IOException, URISyntaxException {

		SpringApplication.run(WelcomePageApplication.class, args);
		WelcomePageApplication.launchBrowser();
	}
	
	@Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

    }
	
	public static void launchBrowser() throws IOException {
		String url = "http://localhost:8080";
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();
		
		if (os.indexOf("win") >= 0) {
			rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
		} else if (os.indexOf("mac") >= 0) {
			rt.exec("open " + url);
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links", "lynx" };

			// Build a command string which looks like "browser1 "url" ||
			// browser2 "url" ||..."
			StringBuffer cmd = new StringBuffer();
			for (int i = 0; i < browsers.length; i++)
				cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

			rt.exec(new String[] { "sh", "-c", cmd.toString() });
		}
	
	}

}
