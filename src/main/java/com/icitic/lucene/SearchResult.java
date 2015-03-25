package com.icitic.lucene;

import java.io.Serializable;

/**
 * 封装查询结果
 * @author hyt
 *
 */
public class SearchResult implements Serializable{

	/**
	 * 随机生成的serialVersionUID
	 */
	private static final long serialVersionUID = 5671700976041689992L;
	
	/**
	 * ID主键
	 */
	private String id;
	
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 正文
	 */
	private String contents;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
