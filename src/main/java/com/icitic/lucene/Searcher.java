package com.icitic.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class Searcher {

	/**
	 * 查询关键字
	 * 
	 * @param key
	 *            关键字
	 * @param number
	 *            显示条数
	 * @return
	 */
	public List<String> seach(String key, int number) {
		IndexSearcher searcher = null;
		List<String> list = new ArrayList<String>();
		String contents = "";
		try {
			searcher = new IndexSearcher(IndexReaderFactory.getInstance());
			TopDocs docs = searcher.search(buildQuery(key), number);
			for (ScoreDoc doc : docs.scoreDocs) {
				Document document = searcher.doc(doc.doc);
				contents = document.get("contents");
				System.out.println(contents);
				list.add(contents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 建立Query用于查询
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	private Query buildQuery(String key) {
		Query query = null;
		try {
			QueryParser parser = new QueryParser(Version.LUCENE_47, "contents",new WhitespaceAnalyzer(Version.LUCENE_47));
			query = parser.parse(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}

}
