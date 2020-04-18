package apache_lucene6x;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.HMMChineseTokenizer;

/**
 * 自定义 中文 Analyzer，参考SmartChineseAnalyzer
 * 可以使用同义词搜索
 *
 */
public class MySameAnalyzer extends Analyzer{
	//参考SmartChineseAnalyzer 的实现
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer = new HMMChineseTokenizer();
        return new TokenStreamComponents(tokenizer, new MySameTokenFilter(tokenizer));
	} 
}
