package com.jiawa.bean;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.ClassReader;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.io.IOException;

public class BeanDefinitionTest {

	Logger log = LoggerFactory.getLogger(BeanDefinitionTest.class);

	@Test
	public void testGenericBeanDefinition() {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName("com.jiawa.bean.User");
		beanDefinition.setLazyInit(false);
		//创建一个MutablePropertyValues
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("name", "yangleping");
		propertyValues.add("age", 18);
		beanDefinition.setPropertyValues(propertyValues);
		System.out.println(beanDefinition);

		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
		rootBeanDefinition.overrideFrom(beanDefinition);
		System.out.println("----------");
		System.out.println(rootBeanDefinition);
	}

	@Test
	public void testBeanDefinitionRegistry() {
		// 创建一个beanDefinition
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName("com.ydlclass.bean.User");
		beanDefinition.setLazyInit(false);
		MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
		mutablePropertyValues.add("name", "ydl");
		mutablePropertyValues.add("age", 18);
		beanDefinition.setPropertyValues(mutablePropertyValues);

		// 我们将搜集到的元数据统一进行注册，统一管理
		// 注意：BeanDefinitionRegistry管理的的是bean的元数据（beanDefinition）
		// BeanFactory管理的是bean的实例
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		registry.registerBeanDefinition("user",beanDefinition);
	}

	@Test
	public void testClassPathBeanDefinitionScanner() {
		// 1、创建一个beanDefinitionRegistry
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		// 2、创建一个beanDefinitionReader
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		// 3、扫描com.jiawa.bean这个包下的bean
		scanner.scan("com.jiawa.bean");
		System.out.println(registry.getBeanDefinitionCount());
	}

	@Test
	public void testAsm() throws IOException {
		// com.jiawa.bean.User -> com/jiawa/bean/User.class
		ClassReader classReader = new ClassReader("com.jiawa.bean.User");

		String className = classReader.getClassName();
		log.info("className:{}",className);
		String superName = classReader.getSuperName();
		log.info("superName:{}",superName);
		String[] interfaces = classReader.getInterfaces();
		for (String interfaceName : interfaces) {
			log.info("interfaceName:{}",interfaceName);
		}
		// 操作常量池，常量池中的偏移量+1
		int firstItem = classReader.getItem(1);
		log.info("firstItem:{}",firstItem);
		int secondItem = classReader.getItem(2);
		log.info("secondItem:{}",secondItem);
		// #0表示不引用任何一个常量池项目, #0 - #44
		int itemCount = classReader.getItemCount();
		log.info("itemCount:{}",itemCount);
	}
}
