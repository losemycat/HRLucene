package com.icitic.lucene.factory;

import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import com.icitic.lucene.factory.IndexWriterFactory;

public class TestIndexWriterFactory {
	
	@Test
	public void test(){
		IndexWriter writer = IndexWriterFactory.getInstance();
		System.out.println(writer);
	}

}
