package com.icitic.lucene;

import java.io.IOException;
import java.util.Properties;

public class LucenePropertiesFactory {

	/**
	 * 用于封装从classpath下读取的lucene.properties文件中属性
	 */
	private static Properties instance = null;

	/**
	 * JVM启动时加载properties文件
	 */
	static {
		instance = new Properties();
		loadProperties("/lucene.properties");
	}

	/**
	 * 加载properties
	 * 
	 * @param path
	 *            文件路径
	 */
	private static void loadProperties(String path) {
		if (instance == null)
			instance = new Properties();
		try {
			instance.load(LucenePropertiesFactory.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取properties的实例对象
	 * 
	 * @return
	 */
	public static Properties getInstance() {
		if (instance == null) {
			synchronized (LucenePropertiesFactory.class) {
				loadProperties("/lucene.properties");
			}
		}
		return instance;
	}

}
