package apache_lucene6x;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.HMMChineseTokenizer;

/**
 * �Զ��� ���� Analyzer���ο�SmartChineseAnalyzer
 * ����ʹ��ͬ�������
 *
 */
public class MySameAnalyzer extends Analyzer{
	//�ο�SmartChineseAnalyzer ��ʵ��
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer = new HMMChineseTokenizer();
        return new TokenStreamComponents(tokenizer, new MySameTokenFilter(tokenizer));
	} 
}
