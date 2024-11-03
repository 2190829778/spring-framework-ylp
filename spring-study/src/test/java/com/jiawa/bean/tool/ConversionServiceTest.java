package com.jiawa.bean.tool;

import com.jiawa.bean.User;
import com.jiawa.converter.StringUserConverter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.List;

public class ConversionServiceTest {

	Logger log = LoggerFactory.getLogger(IntrospectorTest.class);

	@Test
	public void testConversionService() {
		//DefaultConversionService中包含了大量的converter
		ConversionService conversionService = DefaultConversionService.getSharedInstance();
		String source = "123";
		boolean canConvert = conversionService.canConvert(String.class, Integer.class);
		if (canConvert) {
			Integer convert = conversionService.convert(source, Integer.class);
			log.info("convert result:{}", convert);
		}
	}

	@Test
	public void testConvertList(){
		String source = "100,12,23,54,56";
		ConversionService conversionService = new DefaultConversionService();
		if(conversionService.canConvert(String.class, List.class)){
			List target = conversionService.convert(source, List.class);
			log.info("The number is {}.", target);
		}
	}

	@Test
	public void testConvertUser(){
		String source = "Tom | 23";
		// 这里必须使用子类的类型，接口并不提供addConverter方法
		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(new StringUserConverter());
		if(conversionService.canConvert(String.class, User.class)){
			User target = conversionService.convert(source, User.class);
			log.info("The user is {}.", target);
		}
	}
}
