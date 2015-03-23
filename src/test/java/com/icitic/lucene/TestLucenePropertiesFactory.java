package com.icitic.lucene;

import java.util.Properties;

import org.junit.Test;

public class TestLucenePropertiesFactory {
	
	@Test
	public void test(){
		Properties properties = LucenePropertiesFactory.getInstance();
		System.out.println(properties);
	}

}
