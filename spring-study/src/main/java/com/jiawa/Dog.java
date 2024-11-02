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

	@Override
	public String toString() {
		return "Dog{" +
				"age=" + age +
				'}';
	}
}
