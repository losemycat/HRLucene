package com.icitic.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.icitic.lucene.factory.IndexReaderFactory;

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
	public List<SearchResult> seach(String orgId,String contents, int number) {
		IndexSearcher searcher = null;
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			searcher = new IndexSearcher(IndexReaderFactory.getInstance());
			TopDocs docs = searcher.search(buildQuery(orgId,contents), number);
			for (ScoreDoc doc : docs.scoreDocs) {
				Document document = searcher.doc(doc.doc);
				SearchResult result = new SearchResult();
				result.setId(document.get("id"));
				result.setSummary(document.get("summary"));
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 根据OrgId 和 用户传入的搜索内容构建Query对象
	 * @param orgId 机构ID
	 * @param contents 用户搜索内容
	 * @return
	 */
	private BooleanQuery buildQuery(String orgId, String contents) {
		BooleanQuery query = new BooleanQuery();
		PrefixQuery prefixQuery = new PrefixQuery(new Term("orgid", orgId));
		query.add(prefixQuery, Occur.MUST);
		try {
			buildQueryContents(query,contents);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}


	/**
	 * 拆分用户输入内容，并构建查询
	 * @param query
	 * @param contents
	 * @throws ParseException 
	 */
	private void buildQueryContents(BooleanQuery query, String contents) throws ParseException {
		if(StringUtils.isBlank(contents))
			return;
		QueryParser parser = new QueryParser(Version.LUCENE_47, "contents", new IKAnalyzer());
		query.add(parser.parse(contents.trim()),Occur.MUST);
	}

}
