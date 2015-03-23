package com.icitic.lucene;

import java.util.List;

import org.junit.Test;

public class TestSearcher {

	@Test
	public void test(){
		Searcher searcher = new Searcher();
		List<String> list = searcher.seach("lucene", 10);
		System.out.println(list.size());
		System.out.println(list.toString());
	}
}
