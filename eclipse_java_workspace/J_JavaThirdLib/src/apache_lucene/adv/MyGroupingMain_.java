package apache_lucene.adv;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.SearchGroup;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.util.BytesRef;

public class MyGroupingMain_ {
 
	public static void main(String[] args) {
		//https://lucene.apache.org/core/8_5_1/grouping/org/apache/lucene/search/grouping/package-summary.html
		// ¹Ù·½Ê¾Àý
		/*
		GroupingSearch groupingSearch = new GroupingSearch("author");
		   groupingSearch.setGroupSort(groupSort);
		   groupingSearch.setFillSortFields(fillFields);
		 
		   if (useCache) {
		     // Sets cache in MB
		     groupingSearch.setCachingInMB(4.0, true);
		   }
		 
		   if (requiredTotalGroupCount) {
		     groupingSearch.setAllGroups(true);
		   }
		 
		   TermQuery query = new TermQuery(new Term("content", searchTerm));
		   TopGroups<BytesRef> result = groupingSearch.search(indexSearcher, query, groupOffset, groupLimit);
		 
		   // Render groupsResult...
		   if (requiredTotalGroupCount) {
		     int totalGroupCount = result.totalGroupCount;
		   }
		   */

	}

}
