package com.icitic.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
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
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.icitic.lucene.factory.IndexReaderFactory;

/**
 * 主要搜索类
 * @author hyt
 *
 */
public class Searcher {

	/**
	 * 根据传入条件进行相关查询
	 * 
	 * @param orgId
	 *            机构号，用于控制机构权限
	 * @param contents
	 *            搜索内容
	 * @param max
	 *            最大查询数量
	 * @param currentPage
	 *            当前第几页
	 * @param pageSize
	 *            每页多少条记录
	 * @return List<SearchResult>
	 */
	public List<SearchResult> seach(String orgId, String contents, int max,
			int currentPage, int pageSize) {
		IndexSearcher searcher = null;
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			searcher = new IndexSearcher(IndexReaderFactory.getInstance());
			Analyzer analyzer = new IKAnalyzer();
			Query query = this.buildQuery(analyzer,orgId, contents);
			TopDocs docs = searcher.search(query, max);
			for (ScoreDoc doc : docs.scoreDocs) {
				Document document = searcher.doc(doc.doc);
				SearchResult result = new SearchResult();
				result.setId(this.toHighlighter(query, analyzer, document, "id"));
				result.setId(this.toHighlighter(query, analyzer, document, "summary"));
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 根据OrgId 和 用户传入的搜索内容构建Query对象
	 * 
	 * @param analyzer
	 *            分词器
	 * @param orgId
	 *            机构ID
	 * @param contents
	 *            用户搜索内容
	 * @return
	 */
	private BooleanQuery buildQuery(Analyzer analyzer, String orgId, String contents) {
		BooleanQuery query = new BooleanQuery();
		PrefixQuery prefixQuery = new PrefixQuery(new Term("orgid", orgId));
		query.add(prefixQuery, Occur.MUST);
		try {
			buildQueryContents(analyzer,query,contents);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}


	/**
	 * 构建查询QueryPaser查询
	 * 
	 * @param analyzer
	 *            分词器
	 * @param query
	 *            Query对象
	 * @param contents
	 *            查询内容
	 * @throws ParseException
	 */
	private void buildQueryContents(Analyzer analyzer, BooleanQuery query, String contents) throws ParseException {
		if(StringUtils.isBlank(contents))
			return;
		QueryParser parser = new QueryParser(Version.LUCENE_47, "contents", analyzer);
		query.add(parser.parse(contents.trim()),Occur.MUST);
	}
	
	/**
	 * 高亮设置
	 * 
	 * @param query
	 *            查询的Query对象
	 * @param doc
	 *            查询结果所封装的Document
	 * @param analyzer
	 *            分词器
	 * @param field
	 *            查询的域
	 * @return String
	 */
	private String toHighlighter(Query query,Analyzer analyzer, Document doc, String field) {
		String text = doc.get(field);
		try {
			// 获取高亮的开始标签和结束标签
			String startTag = LuceneProperties.get(LuceneConstants.HL_START_TAG);
			String endTag = LuceneProperties.get(LuceneConstants.HL_END_TAG);
			// 定义高亮标签格式类
			SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(StringUtils.trimToEmpty(startTag),StringUtils.trimToEmpty(endTag));
			Highlighter highlighter = new Highlighter(simpleHtmlFormatter,new QueryScorer(query));
			TokenStream tokens = analyzer.tokenStream(field,new StringReader(StringUtils.trimToEmpty(text)));
			// 对标签进行高亮操作
			String hlStr = highlighter.getBestFragment(tokens,text);
			return StringUtils.isEmpty(hlStr) ? text : hlStr;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return text;
	}
	
}
