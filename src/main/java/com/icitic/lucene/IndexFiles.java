package com.icitic.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import com.icitic.lucene.factory.IndexWriterFactory;

public class IndexFiles {

	/**
	 * 源文件所存放目录
	 */
	private String srcPath;

	/**
	 * 从properties文件中获取源文件所存放的目录
	 * 
	 * @return
	 */
	private String getSrcPath() {
		String path = LuceneProperties.get(LuceneConstants.SRC_DIRECTORY);
		return StringUtils.isNotBlank(path) ? path : "";
	}

	/**
	 * 递归遍历目录建立索引
	 */
	public void indexFiles() {
		try {
			IndexWriter writer = IndexWriterFactory.getInstance();
			srcPath = getSrcPath();
			if (StringUtils.isNotBlank(srcPath)) {
				File srcDir = new File(srcPath);
				if (srcDir.exists() && srcDir.isDirectory()) {
					recursionIndexFiles(writer, srcDir);
				} else if (srcDir.exists() && srcDir.isFile()) {
					indexFile(writer, srcDir);
				}
			}
			writer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对文件进行递归索引
	 * 
	 * @param writer
	 *            IndexWriter建立索引的核心类
	 * @param srcDir
	 *            源文件所存放的目录
	 */
	private void recursionIndexFiles(IndexWriter writer, File file) {
		try {
			if (file.exists() && file.isDirectory()) {
				for (File f : file.listFiles()) {
					recursionIndexFiles(writer, f);
				}
			}
			if (file.exists() && file.isFile()) {
				indexFile(writer, file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 为文件建立索引
	 * 
	 * @param writer
	 *            IndexWriter建立索引的核心类
	 * @param file
	 *            文件对象
	 */
	private void indexFile(IndexWriter writer, File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			Document doc = new Document();
			//建立ID域的索引
			String id = StringUtils.trimToEmpty(splitePerId(file.getName()));
			doc.add(new StringField("id",id,Store.YES));
			//建立orgId域的索引
			doc.add(new StringField("orgid", spliteOrgId(file.getName()),Store.YES));
			//建立正文域的索引，权值扩大10倍
			TextField contentsField = new TextField("contents", reader);
			contentsField.setBoost(LuceneConstants.DEFAULT_BOOST*10);
			doc.add(contentsField);
			//建立摘要域的索引，权值扩大5倍
			TextField summaryField = new TextField("summary", querySummaryById(id), Store.YES);
			summaryField.setBoost(LuceneConstants.DEFAULT_BOOST*5);
			doc.add(summaryField);
			//将Document写入IndexWriter
			writer.addDocument(doc);
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


	/**
	 * 从文件名中分割出人员ID
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	private String splitePerId(String fileName) {
		if (StringUtils.isBlank(fileName))
			return "";
		return fileName.substring(fileName.indexOf("id_") + 3,fileName.indexOf(".txt"));
	}

	/**
	 * 从文件命中分割出用户的机构号
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	private static String spliteOrgId(String fileName) {
		if (StringUtils.isBlank(fileName))
			return "";
		return fileName.substring(0, fileName.indexOf("_id_"));
	}
	
	/**
	 * 根据id 查找的summary
	 * @param id 人员id
	 * @return
	 */
	private String querySummaryById(String id) {
		return null;
	}
}
