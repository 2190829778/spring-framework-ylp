package com.jiawa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.jiawa");
		Dog dog = applicationContext.getBean(Dog.class);
		System.out.println(dog);
	}
}