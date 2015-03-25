package com.icitic.lucene;

import org.apache.lucene.util.Version;

/**
 * 包含Lucene的一些常量以及参数
 * @author hyt
 *
 */
public class LuceneConstants {
	
	/**
	 * Lucene的版本号
	 */
	public static final Version VERSION = Version.LUCENE_47;
	
	/**
	 * Lucene的默认权值，加权时乘以默认值的倍数
	 */
	public static final float DEFAULT_BOOST = 1.0f;
	
	/**
	 * 高亮显示的开始标签
	 */
	public static final String HL_START_TAG = "hl_start_tag";
	
	/**
	 * 高亮显示的借书标签
	 */
	public static final String HL_END_TAG = "hl_end_tag";
	
	/**
	 * 目标索引文件存放的目录
	 */
	public static final String INDEX_DIRECTORY = "indexPath";
	
	/**
	 * 源文件所存放的目录
	 */
	public static final String SRC_DIRECTORY = "srcPath";
	
}
