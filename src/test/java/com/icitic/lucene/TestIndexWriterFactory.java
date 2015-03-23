package com.icitic.lucene;

import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

public class TestIndexWriterFactory {
	
	@Test
	public void test(){
		IndexWriter writer = IndexWriterFactory.getInstance();
		System.out.println(writer);
	}

}
