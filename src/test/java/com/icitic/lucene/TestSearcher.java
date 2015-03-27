package com.icitic.lucene;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.icitic.lucene.factory.IndexReaderFactory;

public class TestSearcher {

	@Test
	public void test(){
		Searcher searcher = new Searcher();
		List<SearchResult> list = searcher.seach("001", "黄宁", 100, 0, 50);
		System.out.println(list.size());
		for (SearchResult searchResult : list) {
			System.out.println("ID: "+searchResult.getId());
			System.out.println("Summary: "+searchResult.getSummary());
		}
	}
	
	/**
	 * 测试IndexReader重新打开后是否可以反映到IndexSearcher中
	 */
	@Test
	public void test2(){
		test();
		
		//中途修改索引，并且重新在家IndexReader
		IndexFiles indexFiles = new IndexFiles();
		indexFiles.indexFiles();
		
		IndexReaderFactory.reopen();
		
		test();
	}
}
