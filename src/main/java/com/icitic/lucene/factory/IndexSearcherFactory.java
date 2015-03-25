package com.icitic.lucene.factory;

import org.apache.lucene.search.IndexSearcher;

/**
 * 单例IndexSearcher工厂
 * @author hyt
 *
 */
public class IndexSearcherFactory {

	/**
	 * 声明单例的IndexSearcher引用
	 */
	private static IndexSearcher instance = null;

	/**
	 * 私有化构造方法
	 */
	private IndexSearcherFactory() {

	}

	/**
	 * 采用懒加载的形式获取单例对象
	 * 
	 * @return IndexSearcher对象
	 */
	public static IndexSearcher getInstance() {
		if (instance == null) {
			synchronized (IndexSearcherFactory.class) {
				if (instance == null)
					instance = new IndexSearcher(IndexReaderFactory.getInstance());
			}
		}
		return instance;
	}

}
