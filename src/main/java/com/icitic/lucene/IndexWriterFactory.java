package com.icitic.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexWriterFactory {

	/**
	 * 声明IndexWriter单例对象
	 */
	private static IndexWriter instance = null;

	/**
	 * Lucene版本
	 */
	private static Version version = Version.LUCENE_47;

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static IndexWriter getInstance() {
		if (instance == null) {
			synchronized (IndexWriterFactory.class) {
				if (instance == null) {
					String path = LucenePropertiesFactory.getInstance().getProperty("targetPath");
					try {
						Directory directory = FSDirectory.open(new File(path));
						Analyzer analyzer = new WhitespaceAnalyzer(version);
						IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
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
