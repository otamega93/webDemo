package com.example.web.webDemo.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.example.web.webDemo.RulesClassLoader;

@Component
@ConditionalOnBean(value = RulesClassLoader.class)
public class JarResourceLoader implements ResourceLoader {

	@Autowired
	private RulesClassLoader rulesClassLoader;
	
	@Override
	public Resource getResource(String location) {
		
		return new ClassPathResource(location, this.getClassLoader());
	}

	@Override
	public ClassLoader getClassLoader() {
		
		return rulesClassLoader.getRulesClassLoader();
	}

}
