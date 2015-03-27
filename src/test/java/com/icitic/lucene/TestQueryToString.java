package com.icitic.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.junit.Assert;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestQueryToString {
	
	@Test
	public void test(){
		BooleanQuery query = new BooleanQuery();
		query.add(new TermQuery(new Term("filename", "aaa")), Occur.MUST);
		query.add(new TermQuery(new Term("contents","bbb")), Occur.SHOULD);
		System.out.println(query.toString());
		System.out.println("--------------");
		System.out.println(query.toString("contents"));
	}
	
	@Test
	public void test2() throws ParseException{
		Query query = new QueryParser(Version.LUCENE_47,"contents",new IKAnalyzer()).parse("filename:aaa AND contents:bbb");
		System.out.println("匹配结果："+(query instanceof BooleanQuery));
		
	}

}
