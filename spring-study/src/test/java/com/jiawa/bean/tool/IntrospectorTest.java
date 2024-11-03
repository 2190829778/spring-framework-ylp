package com.jiawa.bean.tool;

import com.jiawa.Dog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
}
