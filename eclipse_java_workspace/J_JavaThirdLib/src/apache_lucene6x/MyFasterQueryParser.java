package apache_lucene6x;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

public class MyFasterQueryParser extends QueryParser {
	public MyFasterQueryParser(String f, Analyzer a) {
		super(f, a);
	}
	@Override
	protected Query getFuzzyQuery(String field, String termStr, float minSimilarity)
			throws ParseException {
		throw new ParseException("由于性能原因禁用 FuzzyQuery  ");
	}
	@Override
	protected  Query getWildcardQuery(String field, String termStr) throws ParseException {
		throw new ParseException("由于性能原因禁用 WildcardQuery  ");
	}
	/* */
	@Override
	protected   Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
			throws ParseException 
	{
	      if(field.equals("size"))//报错???
	     	 return  LongPoint.newRangeQuery(field,Long.parseLong(part1),Long.parseLong(part2));
		  else  if(field.equals("modified")) 
		  {
			  SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
			  Date start;
			  Date end;
			try {
				start = format.parse(part1);
				end=format.parse(part2);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				throw new ParseException("日期格式不对，应为yyyy-MM-dd ");
			}
			  return  LongPoint.newRangeQuery(field,start.getTime(),end.getTime());
		  }
			  
		return super.newRangeQuery(field, part1, part2, startInclusive, endInclusive);
	}
	
}
