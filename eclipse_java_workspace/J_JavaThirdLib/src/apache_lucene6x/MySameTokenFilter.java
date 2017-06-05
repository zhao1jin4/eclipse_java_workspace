package apache_lucene6x;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

public class MySameTokenFilter extends TokenFilter {
	private CharTermAttribute charTermAttr = null;
	private AttributeSource.State state=null;
	private  PositionIncrementAttribute posiIncrementAttr=null;
	private Stack<String> sameWordStack=null;
	Map<String,String[]> maps = new HashMap<String,String[]>();
	protected MySameTokenFilter(TokenStream input) {
		super(input);
		charTermAttr = this.addAttribute(CharTermAttribute.class);
		posiIncrementAttr=this.addAttribute(PositionIncrementAttribute.class);
		sameWordStack=new Stack<>();
		maps.put("中国",new String[]{"天朝","大陆"});
		maps.put("我",new String[]{"咱","俺"});
	}
	@Override
	public boolean incrementToken() throws IOException {
		/*
		 if (!input.incrementToken())
			return false;
		if(charTermAttr.toString().equals("中国"))
		{
			charTermAttr.setEmpty();
			charTermAttr.append("大陆");
		}
		if(charTermAttr.toString().equals("我"))
		{
			charTermAttr.setEmpty();
			charTermAttr.append("唵");
		}
		*/
		//---------------
		while(sameWordStack.size()>0)
		{
			String word=sameWordStack.pop();
			restoreState(state);
			charTermAttr.setEmpty();
			charTermAttr.append(word);
			posiIncrementAttr.setPositionIncrement(0);
			return true;
		}
		 if (!input.incrementToken())
				return false;
		 if(maps.containsKey(charTermAttr.toString()))
		 {
			for(String word:maps.get(charTermAttr.toString()))
			{
				sameWordStack.add(word);
			}
			state=captureState();
		}
		return true;
	}
	 
}
