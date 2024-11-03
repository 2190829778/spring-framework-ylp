package com.jiawa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Dog {
	private Integer age;
	private String name;
	private String color;

	@Override
	public String toString() {
		return "Dog{" +
				"age=" + age +
				", name='" + name + '\'' +
				", color='" + color + '\'' +
				'}';
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
