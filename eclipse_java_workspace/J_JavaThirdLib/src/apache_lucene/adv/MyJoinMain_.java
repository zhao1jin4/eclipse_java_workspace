package apache_lucene.adv;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.SearchGroup;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.util.BytesRef;

public class MyJoinMain_ {
	 
	public static void main(String[] args) {
		//https://lucene.apache.org/core/8_5_1/join/org/apache/lucene/search/join/package-summary.html
		// ¹Ù·½Ê¾Àý
		 /*
		 String fromField = "from"; // Name of the from field
		   boolean multipleValuesPerDocument = false; // Set only to true in the case when your fromField has multiple values per document in your index
		   String toField = "to"; // Name of the to field
		   ScoreMode scoreMode = ScoreMode.Max; // Defines how the scores are translated into the other side of the join.
		   Query fromQuery = new TermQuery(new Term("content", searchTerm)); // Query executed to collect from values to join to the to values
		 
		   Query joinQuery = JoinUtil.createJoinQuery(fromField, multipleValuesPerDocument, toField, fromQuery, fromSearcher, scoreMode);
		   TopDocs topDocs = toSearcher.search(joinQuery, 10); // Note: toSearcher can be the same as the fromSearcher
		   // Render topDocs...
*/
	}

}
