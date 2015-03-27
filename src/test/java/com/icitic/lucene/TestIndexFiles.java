package com.icitic.lucene;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.junit.Test;

import com.icitic.lucene.factory.IndexReaderFactory;
import com.icitic.lucene.factory.IndexWriterFactory;

public class TestIndexFiles {
	
	
	@Test
	public void test() throws InterruptedException, IOException{
		IndexFiles index = new IndexFiles();
	    index.deleteIndex("filename", "001001_id_000007.txt");	
		//writer.deleteDocuments(new Term("filename","001001_id_000006.txt"));
		
		Thread.sleep(5000);
		
		IndexReader reader = IndexReaderFactory.getInstance();
		
		System.out.println(reader.maxDoc());
		System.out.println(reader.numDocs());
		
		Thread.sleep(5000);

	}

}
