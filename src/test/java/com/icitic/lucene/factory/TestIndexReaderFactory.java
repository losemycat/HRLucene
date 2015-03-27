package com.icitic.lucene.factory;

import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.junit.Test;

import com.icitic.lucene.IndexFiles;


public class TestIndexReaderFactory {

	@Test
	public void test() throws InterruptedException, IOException{
		DirectoryReader reader = IndexReaderFactory.getInstance();
		System.out.println(reader);
		System.out.println(reader.maxDoc());
		System.out.println(reader.numDocs());
		
		IndexFiles index = new IndexFiles();
		index.deleteIndex("id", "000001");
		
		//判断reader是否改变
		System.out.println(reader.isCurrent());
		
		//如果索引改变需要重新在家索引
		reader = IndexReaderFactory.reopen();
		
		
		System.out.println("-------------------");
		System.out.println(reader);
		System.out.println(reader.maxDoc());
		System.out.println(reader.numDocs());
		
		Thread.sleep(10000);
	}
}
