package apache_lucene6x;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * �Զ���Analyzer
 *
 */
public class MyStopAnalyzer extends Analyzer{
	private CharArraySet stopWords;
	public MyStopAnalyzer(String stopWord[])
	{
		stopWords=StopFilter.makeStopSet(stopWord,true);
		//stopWords.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);//�°汾8û��ENGLISH_STOP_WORDS_SET
	}	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		//Tokenizer source = new LowerCaseTokenizer();//�°汾8û��LowerCaseTokenizer
		Tokenizer source = new StandardTokenizer();
		
        return new TokenStreamComponents(source, new StopFilter(source, stopWords));
	} 
}
