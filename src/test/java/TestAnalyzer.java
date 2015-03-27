import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;


public class TestAnalyzer {
	
	@Test
	public void test() throws IOException{
		SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_47);
		TokenStream stream = analyzer.tokenStream("contents", "hello,i am learning lucene,the");
		System.out.println(stream.toString());
	}

}
