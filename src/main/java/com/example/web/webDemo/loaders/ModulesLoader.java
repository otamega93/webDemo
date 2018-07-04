package com.example.web.webDemo.loaders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.core.interfaces.modules.Modulo;
import com.example.web.webDemo.RulesClassLoader;

@Component
public class ModulesLoader {

	@Autowired
	private RulesClassLoader myClassLoader;

	private List<String> modules = new ArrayList<>();

	@PostConstruct
	public void init() {
		reload();
	}

	public ModulesLoader() {
		super();
	}

	public Set<Class<? extends Modulo>> reload() {

		Configuration config = new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forClassLoader(myClassLoader.getRulesClassLoader()))
				.addClassLoader(myClassLoader.getRulesClassLoader())
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.example.core.modules")))
				.setScanners(new SubTypesScanner(false), new ResourcesScanner(), new TypeAnnotationsScanner());

		Reflections reflections = new Reflections(config);

		Set<Class<? extends Modulo>> setModulos = reflections.getSubTypesOf(Modulo.class);

		for (Class<? extends Modulo> modulo : setModulos) {

			modules.add(modulo.getName());
		}

		return setModulos;

	}

	public void showAll() {

		for (String module : modules) {
			System.out.println(module);
		}
	}
}
