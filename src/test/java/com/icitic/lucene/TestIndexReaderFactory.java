package com.icitic.lucene;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

public class TestIndexReaderFactory {

	@Test
	public void test() throws InterruptedException{
		IndexReader reader = IndexReaderFactory.getInstance();
		System.out.println(reader);
	}
}
