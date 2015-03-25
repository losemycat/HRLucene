package com.icitic.lucene.factory;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import com.icitic.lucene.factory.IndexReaderFactory;

public class TestIndexReaderFactory {

	@Test
	public void test() throws InterruptedException{
		IndexReader reader = IndexReaderFactory.getInstance();
		System.out.println(reader);
	}
}
