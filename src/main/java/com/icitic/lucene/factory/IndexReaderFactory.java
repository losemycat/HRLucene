package com.icitic.lucene.factory;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.icitic.lucene.LuceneConstants;
import com.icitic.lucene.LuceneProperties;

/**
 * 单例IndexReader工厂
 * @author hyt
 *
 */
public class IndexReaderFactory {

	/**
	 * 声明IndexReader的对象
	 */
	private static DirectoryReader instance = null;

	/**
	 * 获取IndexReader的单例对象
	 * 
	 * @return
	 */
	public static synchronized DirectoryReader getInstance() {

		if (instance == null) {
			synchronized (IndexReaderFactory.class) {
				if (instance == null) {
					String path = LuceneProperties.get(LuceneConstants.INDEX_DIRECTORY);
					Directory directory = null;
					try {
						directory = FSDirectory.open(new File(path));
						instance = DirectoryReader.open(directory);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

	/**
	 * 索引更新后需要重新打开IndexReader载入新的索引
	 */
	public static DirectoryReader reopen() {
		if (instance == null) {
			instance = getInstance();
		} else {
			try {
				if (!instance.isCurrent()){
					instance = instance.openIfChanged(instance);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
