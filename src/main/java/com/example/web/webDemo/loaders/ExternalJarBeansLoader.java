package com.example.web.webDemo.loaders;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.web.webDemo.RulesClassLoader;

@Component
@ConditionalOnBean(value = { RulesClassLoader.class, JarResourceLoader.class })
public class ExternalJarBeansLoader {

	private static final String XML_RESOURCE_FILE_PATH = "resources/applicationContext.xml";
	
	@Autowired
	private JarResourceLoader resourceLoader;
	
	@Autowired
	private RulesClassLoader rulesClassLoader;
	
	@Autowired
	private ConfigurableApplicationContext configurableApplicationContext;
	
	@PostConstruct
	public void init() {
		loadExternalJarBeansIntoMainSpringContext();
	}

	public ExternalJarBeansLoader() {
		super();
	}

	public void loadExternalJarBeansIntoMainSpringContext() {
		
		Resource resource = resourceLoader.getResource(XML_RESOURCE_FILE_PATH);
		
		GenericXmlApplicationContext springContext = new GenericXmlApplicationContext();
		springContext.setClassLoader(rulesClassLoader.getRulesClassLoader());
		springContext.refresh();
		springContext.load(resource);
		
		springContext.registerShutdownHook();
		
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) configurableApplicationContext).getBeanFactory();
		
		String[] beanNames = springContext.getBeanDefinitionNames();
		
		for (String beanName : beanNames) {
			
			if (!beanName.startsWith("org.springframework"))
				beanFactory.registerSingleton(beanName, springContext.getBean(beanName));
		}
	}
}
