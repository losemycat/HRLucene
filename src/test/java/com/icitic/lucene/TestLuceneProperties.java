package com.icitic.lucene;

import org.junit.Test;

public class TestLuceneProperties {
	
	@Test
	public void test(){
		String indexPath = LuceneProperties.get(LuceneConstants.INDEX_DIRECTORY);
		String srcPath = LuceneProperties.get(LuceneConstants.SRC_DIRECTORY);
		System.out.println(indexPath);
		System.out.println(srcPath);
	}

}
