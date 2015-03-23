package com.icitic.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;


public class IndexFiles {
	
	/**
	 * 源文件所存放目录
	 */
	private String srcPath;
	
	/**
	 * 从properties文件中获取源文件所存放的目录
	 * @return
	 */
	private String getSrcPath() {
		String path = LucenePropertiesFactory.getInstance().getProperty("srcPath");
		return StringUtils.isNotBlank(path) ? path : "";
	}
	
	/**
	 * 为文件建立索引
	 */
	public void indexFiles() {
		try {
			IndexWriter writer = IndexWriterFactory.getInstance();
			srcPath = getSrcPath();
			if (StringUtils.isNotBlank(srcPath)) {
				File srcDir = new File(srcPath);
				if (srcDir.exists() && srcDir.isDirectory()) {
					for (File file : srcDir.listFiles()) {
						indexFile(writer,file);
					}
				}
			}
			writer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 为文件建立索引
	 * @param writer
	 * @param file
	 */
	private void indexFile(IndexWriter writer, File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = "";
			Document document = new Document();
			while ((line = reader.readLine()) != null) {
				document.add(new TextField("contents", line, Store.YES));
			}
			writer.addDocument(document);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
