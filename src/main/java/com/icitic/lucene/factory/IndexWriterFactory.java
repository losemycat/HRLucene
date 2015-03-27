package com.icitic.lucene.factory;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.icitic.lucene.LuceneConstants;
import com.icitic.lucene.LuceneProperties;

/**
 * 单例IndexWriter工厂
 * @author hyt
 *
 */
public class IndexWriterFactory {

	/**
	 * 声明IndexWriter单例对象
	 */
	private static IndexWriter instance = null;


	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static IndexWriter getInstance() {
		if (instance == null) {
			synchronized (IndexWriterFactory.class) {
				if (instance == null) {
					String path = LuceneProperties.get(LuceneConstants.INDEX_DIRECTORY);
					try {
						Directory directory = FSDirectory.open(new File(path));
						Analyzer analyzer = new IKAnalyzer();
						IndexWriterConfig config = new IndexWriterConfig(LuceneConstants.VERSION, analyzer);
						instance = new IndexWriter(directory, config);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

}
