package com.jiawa.bean.tool;

import com.jiawa.Dog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.ResolvableType;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntrospectorTest {

	Logger log = LoggerFactory.getLogger(IntrospectorTest.class);

	@Test
	public void testIntrospect1() throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(Dog.class);
		BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
		log.info("beanDescriptor -> {}", beanDescriptor.getName());

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			log.info("属性名：{}", propertyDescriptor.getName());
			log.info("属性类型：{}", propertyDescriptor.getPropertyType());
			log.info("get方法：{}", propertyDescriptor.getReadMethod());
			log.info("set方法：{}", propertyDescriptor.getWriteMethod());
		}
	}

	@Test
	public void testIntrospect2() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		Dog dog = new Dog();
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor("name", Dog.class);
		Method writeMethod = propertyDescriptor.getWriteMethod();
		writeMethod.invoke(dog, "ylp");
		log.info("dog -> {}", dog);
		Method readMethod = propertyDescriptor.getReadMethod();
		Object result = readMethod.invoke(dog);
		log.info("result -> {}", result);
	}

	@Test
	public void testBeanWrapper() throws IntrospectionException {
		// 在构造的过程中就会实例化
		BeanWrapper beanWrapper = new BeanWrapperImpl(Dog.class);
		// 通过beanWrapper设置属性
		beanWrapper.setPropertyValue("color", "red");
		log.info("bean -> {}", beanWrapper.getWrappedInstance());

		// 通过beanWrapper使用map批量设置属性
		Map<String, Object> map = Map.of("color", "black", "age", 10);
		beanWrapper.setPropertyValues(map);
		log.info("bean -> {}", beanWrapper.getWrappedInstance());

		// 通过beanWrapper使用MutablePropertyValues批量设置属性
		MutablePropertyValues pvs = new MutablePropertyValues();  // 是不是beanDefinition中的内容
		pvs.addPropertyValue("color","blue");
		pvs.addPropertyValue("age", 20);
		beanWrapper.setPropertyValues(pvs);
		log.info("bean -> {}", beanWrapper.getWrappedInstance());
	}

	@Test
	public void testBatchCreate() throws IntrospectionException, ClassNotFoundException {
		// 1、创建一个beanDefinitionRegistry
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		// 2、创建一个beanDefinitionReader
		BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(registry);
		beanDefinitionReader.loadBeanDefinitions("bean.xml");

		// 3、遍历beanDefinitionRegistry中的beanDefinition
		String[] beanDefinitionNames = registry.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			log.info("beanDefinitionName -> {}", beanDefinitionName);
			BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
			// 4、对beanDefinition进行包装,进行实例化
			Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
			BeanWrapper beanWrapper = new BeanWrapperImpl(beanClass);
			// 5、进行属性填充(异常，xml中读取的是统一的"1" "0.1" "aaa"  --> 各自的真实属性类型)
			beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
			log.info("bean -> {}", beanWrapper.getWrappedInstance());

		}
	}

	private HashMap<Integer, List<String>> myMap;
	@Test
	public void example() throws NoSuchFieldException {
		ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
		t.getSuperType(); // AbstractMap<Integer, List<String>>
		t.asMap(); // Map<Integer, List<String>>
		t.getGeneric(0).resolve(); // Integer  // 获取泛型
		t.getGeneric(1).resolve(); // List
		t.getGeneric(1); // List<String>
		//第二个泛型,里面的泛型,即List<String>里面的String
		t.resolveGeneric(1, 0); // String
	}
}
