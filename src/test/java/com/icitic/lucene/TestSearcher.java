package com.icitic.lucene;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
}
