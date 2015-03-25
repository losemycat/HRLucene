package com.icitic.lucene;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LuceneProperties {

	/**
	 * 用于存储在properties文件中读取的变量
	 */
	private static Map<String, String> map = new HashMap<String, String>();

	/**
	 * properties文件的路径
	 */
	private static String path = "/lucene.properties";

	/**
	 * JVM启动时将properties文件加载进入
	 */
	static {
		loadProperties(path);
	}

	/**
	 * 加载properties
	 * 
	 * @param path
	 *            文件路径
	 */
	private static void loadProperties(String path) {
		InputStream is = null;
		Properties properties = new Properties();
		is = LuceneProperties.class.getResourceAsStream("/lucene.properties");
		try {
			properties.load(is);
			Enumeration<?> en = properties.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	/**
	 * 当lucene.properties文件修改时，重新加载进Map集合中
	 */
	public static void refreshProperties() {
		map.clear();
		loadProperties(path);
	}

	/**
	 * 获取properties文件中的值
	 * 
	 * @return 对应的value
	 */
	public static String get(String key) {
		if (map.size() == 0) {
			synchronized (LuceneProperties.class) {
				if (map.size() == 0) {
					loadProperties(path);
				}
			}
		}
		return map.get(key);
	}

}
