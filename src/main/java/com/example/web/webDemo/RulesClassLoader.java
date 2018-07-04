package com.example.web.webDemo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class RulesClassLoader extends ClassLoader {

	private static final String PREFIX = "file:/";

	private static final String JAR_FILE_PATH = "/Desktop/coreDemo.jar";

	private static final String USER_HOME_PROPERTY = "user.home";
	
	private ClassLoader rulesClassLoader;
	
	@PostConstruct
	public void init() throws MalformedURLException, URISyntaxException {
		createRulesClassLoader();
	}	

	public RulesClassLoader() {
		super();
	}

	public ClassLoader createRulesClassLoader() throws MalformedURLException, URISyntaxException {

		URL urlFile = new URL(PREFIX + System.getProperty(USER_HOME_PROPERTY) + JAR_FILE_PATH);

		URL urlWeb = urlFile.toURI().toURL();
		URL[] urlsWeb = new URL[] { urlWeb };

		ClassLoader urlClassLoader = new URLClassLoader(urlsWeb, this.getClass().getClassLoader());
		
		rulesClassLoader = urlClassLoader;
		
		return urlClassLoader;

	}
	
	public ClassLoader getRulesClassLoader() {
		
		return rulesClassLoader;
	}

}
