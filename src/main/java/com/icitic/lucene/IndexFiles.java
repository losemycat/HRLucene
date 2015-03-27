package com.icitic.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.MergePolicy.OneMerge;

import com.icitic.lucene.factory.IndexWriterFactory;

public class IndexFiles {

	/**
	 * 声明整个索引周期中使用的核心类
	 */
	private final IndexWriter writer;

	/**
	 * 构造方法中初始化IndeWriter
	 */
	public IndexFiles() {
		writer = IndexWriterFactory.getInstance();
	}
	
	
	
	/**
	 * 测试代码 过后删除
	 */
	public static Map<String, String> map = new HashMap<String, String>();
	static{
		map.put("000001", "姓名：张晓林　性别：男");
		map.put("000002", "姓名：高志鹏    性别：男");
		map.put("000003", "姓名：黄宁    性别：男");
		map.put("000004", "姓名：李亚运    性别：男");
		map.put("000005", "姓名：王晓红    性别：女");
		map.put("000006", "姓名：杨杰    性别：女");
		map.put("000007", "姓名：谢继昂    性别：男");
		map.put("000008", "姓名：张轩    性别：男");

	}

	/**
	 * 源文件所存放目录
	 */
	private String srcPath;

	/**
	 * 从properties文件中获取源文件所存放的目录
	 * 
	 * @return 源文件目录
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
			srcPath = getSrcPath();
			if (StringUtils.isNotBlank(srcPath)) {
				File srcDir = new File(srcPath);
				if (srcDir.exists() && srcDir.isDirectory()) {
					recursionIndexFiles(srcDir);
				} else if (srcDir.exists() && srcDir.isFile()) {
					indexFile(srcDir);
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
	private void recursionIndexFiles(File files) {
		try {
			if (files.exists() && files.isDirectory()) {
				for (File f : files.listFiles()) {
					recursionIndexFiles(f);
				}
			}
			if (files.exists() && files.isFile()) {
				indexFile(files);
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
	private void indexFile(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			Document doc = new Document();
			//建立ID域的索引
			String id = StringUtils.trimToEmpty(splitePerId(file.getName()));
			doc.add(new StringField("id",id,Store.YES));
			//建立orgId域的索引
			StringField orgIdField = new StringField("orgid", spliteOrgId(file.getName()),Store.YES);
			doc.add(orgIdField);
			//建立文件名的索引越
			StringField filename = new StringField("filename", file.getName(), Store.YES);
			doc.add(filename);
			//建立正文域的索引，权值扩大10倍
			TextField contentsField = new TextField("contents", reader);
			contentsField.setBoost(LuceneConstants.DEFAULT_BOOST*10);
			doc.add(contentsField);
			//建立摘要域的索引，权值扩大5倍
			StringField summaryField = new StringField("summary", querySummaryById(id), Store.YES);
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
	 * 
	 * @param id
	 *            人员id
	 * @return
	 */
	private String querySummaryById(String id) {
		return map.get(id);
	}
	
	/**
	 * 根据域名以及域名中的值批量更新索引
	 * @param field
	 * @param ids
	 */
	public void updateIndexs(String field,String[] texts){
		for (String text : texts) {
			updateIndex(field, text);
		}
	}
	
	/**
	 * 
	 * @param field
	 * @param id
	 */
	public void updateIndex(String field,String text){
		try {
			writer.updateDocument(new Term(field,text), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param field
	 * @param texts
	 */
	public void deleteIndexs(String field, String[] texts) {
		for (String text : texts) {
			deleteIndex(field, text);
		}

	}
	
	/**
	 * 
	 * @param field
	 * @param text
	 */
	public void deleteIndex(String field,String text){
		try {
			writer.deleteDocuments(new Term(field, text));
			writer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
