package com.icitic.lucene;

import java.io.IOException;
import java.util.Properties;

public class LuceneProperties {
	
	/**
	 * 用于封装从classpath下读取的lucene.properties文件中属性
	 */
	private static Properties properties = null;
	
	/**
	 * JVM启动时加载properties文件
	 */
	static{
		properties = new Properties();
		loadProperties("/lucene.properties");
	}

	/**
	 * 加载properties
	 * @param path 文件路径
	 */
	private static void loadProperties(String path) {
		if (properties==null)
			properties = new Properties();
		try {
			properties.load(LuceneProperties.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取properties的实例对象
	 * @return
	 */
	public static synchronized Properties getInstance(){
		if (properties!=null)
			return properties;
		loadProperties("/lucene.properties");
		return properties;
	}
	
}
