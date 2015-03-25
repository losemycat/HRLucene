package com.icitic.lucene;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

public class TestProperties {
	
	@Test
	public void test() throws IOException{
		Properties properties = new Properties();
		properties.load(TestProperties.class.getResourceAsStream("/lucene.properties"));
		Enumeration<?> en = properties.propertyNames();
		while(en.hasMoreElements()){
			System.out.println(en.nextElement());
		}
	}

}
