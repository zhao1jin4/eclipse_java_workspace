/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apache_lucene6x;

// http://lucene.apache.org/core/6_3_0/index.html          Lucene demo, its usage, and sources:
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

/** Simple command-line based search demo. */
public class SearchFiles {

  private SearchFiles() {}

  /** Simple command-line based search demo. */
  public static void main(String[] args) throws Exception {
    String usage =
      "Usage:\tjava org.apache.lucene.demo.SearchFiles [-index dir] [-field f] [-repeat n] [-queries file] [-query string] [-raw] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";
    if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
      System.out.println(usage);
      System.exit(0);
    }

    String index = "index";
    String field = "contents";
    String queries = null;
    int repeat = 0;
    boolean raw = false;
    String queryString = null;
    int hitsPerPage = 10;
    
    for(int i = 0;i < args.length;i++) {
      if ("-index".equals(args[i])) {
        index = args[i+1];
        i++;
      } else if ("-field".equals(args[i])) {
        field = args[i+1];
        i++;
      } else if ("-queries".equals(args[i])) {
        queries = args[i+1];
        i++;
      } else if ("-query".equals(args[i])) {
        queryString = args[i+1];
        i++;
      } else if ("-repeat".equals(args[i])) {
        repeat = Integer.parseInt(args[i+1]);
        i++;
      } else if ("-raw".equals(args[i])) {
        raw = true;
      } else if ("-paging".equals(args[i])) {
        hitsPerPage = Integer.parseInt(args[i+1]);
        if (hitsPerPage <= 0) {
          System.err.println("There must be at least 1 hit per page.");
          System.exit(1);
        }
        i++;
      }
    }
     
    
    DirectoryReader oldReader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
//    Directory dir = FSDirectory.open(Paths.get(index));

//    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
//    IndexWriter writer = new IndexWriter(dir, iwc);
//    writer.deleteDocuments(new Term("path","C:\\My\\software\\_java\\lucene-6.4.0\\lucene-6.4.0\\docs\\core\\org\\apache\\lucene\\document\\LongPoint.html"));
//    System.out.println("deleteDocuments  " );
//    DirectoryReader oldReader = DirectoryReader.open( writer);
    
    IndexReader   reader=DirectoryReader.openIfChanged(oldReader);//如果索引发生的改变,返回一个新的Reader(测试无用？？？),否则返回null
    if(reader==null) 
    	reader=oldReader;
    if(reader.hasDeletions())
    {
    	System.out.println("maxDoc : "+reader.maxDoc());
        System.out.println("numDocs : "+reader.numDocs());
        System.out.println("numDeletedDocs : "+reader.numDeletedDocs());
    }
    IndexSearcher searcher = new IndexSearcher(reader);
    BufferedReader in = null;
    if (queries != null) {
      in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
    } else {
      in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }
    Analyzer analyzer = new StandardAnalyzer();
    MyFasterQueryParser parser = new MyFasterQueryParser(field, analyzer);//禁用性能低的查询功能,如~,以*开头的通配
  
    //QueryParser parser = new QueryParser(field, analyzer);//field
    while (true) {
      if (queries == null && queryString == null) {                        // prompt the user
        System.out.println("Enter query: ");
      }

      String line = queryString != null ? queryString : in.readLine();

      if (line == null || line.length() == -1) {
        break;
      }

      line = line.trim();
      if (line.length() == 0) {
        break;
      }
     
      //Query query = parser.parse(line);
      //parser.setDefaultOperator(Operator.AND); //所有的词都要出现
      //Query query = parser.parse("Morphology KStemFilter");//有空格默认就是OR
      //Query query = parser.parse("Morphology AND KStemFilter");//也可这样用AND,OR
      //Query query = parser.parse("(Morphology AND KStemFilter) OR　name:Long*");//使用()做多个组合
      
      
      //Query query =parser.parse("name:LongPoint");//改变搜索域
//      Query query =parser.parse("name:Long*");//通配
      
      //parser.setAllowLeadingWildcard(true);
      //Query query =parser.parse("name:*Point");//默认不能以*开头的通配，因为效率低，要setAllowLeadingWildcard开启
      
      //Query query =parser.parse("-name:LongPoint +Morphology");//前面-表示不能出现,+表示要出现
      //Query query =parser.parse("NOT name:LongPoint AND Morphology"); //NOT同-,AND同+ ,功能同上
     // Query query =parser.parse("size:[5317 TO 33337 ]");//没用???
    
      //Query query =parser.parse("id:[2 TO 4 ]");//TO 必须大写,id是符类型的,但显示结果为什么多??
      //Query query =parser.parse("id:{2 TO 4 ]");//{开区间,[闭区间,同数学
      //Query query =parser.parse("\"fuzzy searches\"");//必须是两个词紧挨着,用""
      //Query query =parser.parse("\"jakarta apache\"~10");//两个词间距离小于10的
      //文档上说的
      //Query query =parser.parse("roam~");//模糊查会显示 foam 或者 roams 
      //Query query =parser.parse("/[mb]oat/");//会显示  "moat" 或者 "boat" 
      //Query query =new TermRangeQuery(field,new BytesRef(line),new BytesRef(line+"10"),true,true);//无用??
       
//       Calendar c=Calendar.getInstance();
//       c.set(Calendar.YEAR, 2016);
//       Query query =  LongPoint.newRangeQuery("modified",c.getTime().getTime(),Calendar.getInstance().getTimeInMillis());
      
      Query query =parser.parse("modified:[2016-01-23 TO 2017-01-23]");//MyFasterQueryParser
      
      //PrefixQuery query=new PrefixQuery(new Term("path","C:\\My\\software\\_java\\lucene-6.4.0\\lucene-6.4.0\\docs\\core\\org\\apache\\lucene\\document\\Long"));
      //PrefixQuery query=new PrefixQuery(new Term("contents","LongPoint"));//这样是没用的
      //WildcardQuery query=new WildcardQuery(new Term("path","*LongPoint*"));
      
//      BooleanQuery.Builder builder= new BooleanQuery.Builder();
//      Term term=new Term("path","C:\\My\\software\\_java\\lucene-6.4.0\\lucene-6.4.0\\docs\\core\\org\\apache\\lucene\\document\\LongPoint.html");
//      builder.add(new TermQuery(term),Occur.MUST);//有MUST,SHOULD,MUST_NOT
//      Calendar c=Calendar.getInstance();
//      c.set(Calendar.YEAR, 2016);
//      builder.add(LongPoint.newRangeQuery("modified",c.getTime().getTime(),Calendar.getInstance().getTimeInMillis()),Occur.MUST);
//      BooleanQuery query=builder.build();
      
      
//      PhraseQuery.Builder builder = new PhraseQuery.Builder();
//      builder.setSlop(1);// 表示跳1个词 ， 如有 see this message ,先see,再message就可 查到
//      builder.add(new Term("contents", "see"));
//      builder.add(new Term("contents", "message"));
//      PhraseQuery query = builder.build();
      
      // FuzzyQuery query=new FuzzyQuery(new Term("contents","mike"));////对于  make 可查出  mike或者 jake,可能对于查不到结果时
      System.out.println("Searching for: " + query.toString(field));
            
      if (repeat > 0) {                           // repeat & time as benchmark
        Date start = new Date();
        for (int i = 0; i < repeat; i++) {
          searcher.search(query, 100,Sort.INDEXORDER);
          //searcher.search(query, 100,Sort.RELEVANCE);//以scoreDoc.doc 的ID来排序
          //searcher.search(query, 100,new Sort(new SortField("size",SortField.Type.LONG)));//没效果????
          //searcher.search(query, 100,new Sort(new SortField("name",SortField.Type.STRING,true),SortField.FIELD_SCORE));//反向排序,Sort中可传多个SortField,没效果????
         // searcher.search(query, Collector);//Collector 文档有警告可能不兼容后期版本，可自定义排序和过滤词,聚合,如何做????
        //新版本没有search的Filter参数,也不能自定义评分
        }
        Date end = new Date();
        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
      }
      //doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);
      doPagingSearchMyAfter(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);
      if (queryString != null) {
        break;
      }
    }
    reader.close();
  }

  /**
   * This demonstrates a typical paging search scenario, where the search engine presents 
   * pages of size n to the user. The user can then go to the next page if interested in
   * the next hits.
   * 
   * When the query is executed for the first time, then only enough results are collected
   * to fill 5 result pages. If the user wants to page beyond this limit, then the query
   * is executed another time and all hits are collected.
   * 
   */
  public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
                                     int hitsPerPage, boolean raw, boolean interactive) throws IOException {
 
    // Collect enough docs to show 5 pages
    TopDocs results = searcher.search(query, 5 * hitsPerPage);//最多显示少个结果
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = results.totalHits;//如果实际结果大于最多显示,totalHits存实际结果
    System.out.println(numTotalHits + " total matching documents");

    int start = 0;
    int end = Math.min(numTotalHits, hitsPerPage);
        
    while (true) {
      if (end > hits.length) {
        System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
        System.out.println("Collect more (y/n) ?");
        String line = in.readLine();
        if (line.length() == 0 || line.charAt(0) == 'n') {
          break;
        }

        hits = searcher.search(query, numTotalHits).scoreDocs;
      }
      
      end = Math.min(hits.length, start + hitsPerPage);
      
      for (int i = start; i < end; i++) {
        if (raw) {                              // output raw format
          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
          continue;
        }

        Document doc = searcher.doc(hits[i].doc);
        String path = doc.get("path");
        if (path != null) {
          System.out.println("score="+hits[i].score+",id="+(doc.get("id")) + ",path=" + path);
          String title = doc.get("title");
          if (title != null) {
            System.out.println("   Title: " + doc.get("title"));
          }
        } else {
          System.out.println((i+1) + ". " + "No path for this document");
        }
                  
      }

      if (!interactive || end == 0) {
        break;
      }

      if (numTotalHits >= end) {
        boolean quit = false;
        while (true) {
          System.out.print("Press ");
          if (start - hitsPerPage >= 0) {
            System.out.print("(p)revious page, ");  
          }
          if (start + hitsPerPage < numTotalHits) {
            System.out.print("(n)ext page, ");
          }
          System.out.println("(q)uit or enter number to jump to a page.");
          
          String line = in.readLine();
          if (line.length() == 0 || line.charAt(0)=='q') {
            quit = true;
            break;
          }
          if (line.charAt(0) == 'p') {
            start = Math.max(0, start - hitsPerPage);
            break;
          } else if (line.charAt(0) == 'n') {
            if (start + hitsPerPage < numTotalHits) {
              start+=hitsPerPage;
            }
            break;
          } else {
            int page = Integer.parseInt(line);
            if ((page - 1) * hitsPerPage < numTotalHits) {
              start = (page - 1) * hitsPerPage;
              break;
            } else {
              System.out.println("No such page");
            }
          }
        }
        if (quit) break;
        end = Math.min(numTotalHits, start + hitsPerPage);
      }
    }
  }
  
  public static void doPagingSearchMyAfter(BufferedReader in, IndexSearcher searcher, Query query, 
          int hitsPerPage, boolean raw, boolean interactive) throws IOException {
	    
	  TopDocs results = searcher.search(query,  hitsPerPage); 
	  //    results.totalHits>0;
	  while(results.scoreDocs.length>0)
	    {
	    	System.out.println("--------------------");
	    	ScoreDoc lastScoreDoc=null;
	    	for(ScoreDoc scoreDoc:results.scoreDocs )
		    {
	    		lastScoreDoc=scoreDoc;
		    	 Document doc = searcher.doc(scoreDoc.doc);
		         System.out.println("score="+scoreDoc.score+",doc="+scoreDoc.doc+",size="+doc.get("size")+",modifed="+doc.get("modifed")+",id="+ doc.get("id") + ",name="+doc.get("name")+ "\t\t,path=" + doc.get("path"));
		    }
	    	results=searcher.searchAfter( lastScoreDoc, query, hitsPerPage);  //可以使用searchAfter来分页
	    }
		   
      
  }
}