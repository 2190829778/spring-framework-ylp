package com.jiawa.converter;

import com.jiawa.bean.User;
import org.springframework.core.convert.converter.Converter;

public class StringUserConverter implements Converter<String, User> {


	// "yangleping | 123"
	@Override
	public User convert(String source) {
		if (source.contains("|")) {
			String[] split = source.split("\\|");
			User user = new User();
			user.setName(split[0].trim());
			user.setAge(Integer.parseInt(split[1].trim()));
			return user;
		}
		return null;
	}
}
