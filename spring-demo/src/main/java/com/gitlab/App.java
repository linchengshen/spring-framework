package com.gitlab;

import com.gitlab.spring.config.B;
import com.gitlab.spring.config.DemoConfig;
import com.gitlab.spring.factory.MyBeanDefinitionRegistryPostProcessor;
import com.gitlab.spring.factory.MyBeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) throws InterruptedException {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(DemoConfig.class);
		annotationConfigApplicationContext.addBeanFactoryPostProcessor(new MyBeanDefinitionRegistryPostProcessor());
		annotationConfigApplicationContext.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
		annotationConfigApplicationContext.refresh();
		//B bean = annotationConfigApplicationContext.getBean(B.class);
		System.out.println(111);

	}
}