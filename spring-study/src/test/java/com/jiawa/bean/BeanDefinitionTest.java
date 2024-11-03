package com.jiawa.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;

public class BeanDefinitionTest {

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
}
