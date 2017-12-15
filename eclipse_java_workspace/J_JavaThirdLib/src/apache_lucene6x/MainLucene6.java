package apache_lucene6x;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NRTCachingDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BitDocIdSet;
import org.apache.lucene.util.BytesRef;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;


public class MainLucene6 
{
	@Test
	public   void changeExample( ) throws Exception {
		    Directory directory = FSDirectory.open(Paths.get("c:/tmp/lucene_test"));
		    
		    Analyzer analyzer = new StandardAnalyzer();
		    IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    Document doc = new Document();
		    String text = "This is the text to be indexed  . version 2 text ";
		    doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
		    doc.add(new StringField("path","c:/temp/file1.txt",Field.Store.YES));
		    doc.add(new LongPoint("modifyTime",new Date().getTime()));//是index的,但不store
		    doc.add(new StoredField("createTime",new Date().getTime()));//store的
		    doc.add(new TextField("contents", new StringReader("这是一个中中文的测试 ,english text,after change")));
		    //iwriter.addDocument(doc);
		    
		    iwriter.updateDocument(new Term("path", "c:/temp/file1.txt"), doc);
		    
		    iwriter.close();
		    
	}
	
		public static void examples( ) throws Exception {

//		    Directory directory = new RAMDirectory();
		    Directory directory = FSDirectory.open(Paths.get("c:/tmp/lucene_test"));
			Analyzer analyzer = new StandardAnalyzer();
		    IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    Document doc = new Document();
		    String text = "This is the text to be indexed.";
		    doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
		    doc.add(new StringField("path","c:/temp/file1.txt",Field.Store.YES));
		    doc.add(new LongPoint("modifyTime",new Date().getTime()));//是index的,但不store
		    doc.add(new StoredField("createTime",new Date().getTime()));//store的
		    doc.add(new TextField("contents", new StringReader("这是一个中中文的测试 ,english text")));
		    iwriter.addDocument(doc);
		    
		    //iwriter.updateDocument(new Term("path", "c:/temp/file1.txt"), doc);
		    
		    iwriter.close();
		    
		    // Now search the index:
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		    // Parse a simple query that searches for "text":
//		    QueryParser parser = new QueryParser("fieldname", analyzer);
		    MultiFieldQueryParser parser =new MultiFieldQueryParser(new String[]{"fieldname","contents"},new SmartChineseAnalyzer());
		    
		    Query query = parser.parse("text");
		    //Query query =new TermRangeQuery("fieldname",new BytesRef("text"),new BytesRef("text10"),true,true);//测试返回多条记录
		    ScoreDoc[] hits = isearcher.search(query,  1000).scoreDocs;
		    
		    
//		    TermQuery tQuery=new TermQuery(new Term("fieldname","text"));
//		    ScoreDoc[]  hits = isearcher.search(tQuery,  1000).scoreDocs;
		   
		    System.out.println( 1 == hits.length);
		    // Iterate through the results:
		    for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = isearcher.doc(hits[i].doc);
		      System.out.println( "This is the text to be indexed.".equals(hitDoc.get("fieldname")));
		    }
		    ireader.close();
		    directory.close();
		}
		
		public static void analyzer() throws Exception
		{
			
			  // TokenStream 的子类有 TokenFilter, Tokenizer,
			  TokenStream a;
			  Tokenizer b;
			  TokenFilter c;
			  PorterStemFilter f; //会把ing进行时,er比较级,es复数 的单词还原
			  //this is my football ,  I  very like it
			  Analyzer analyzer = new StandardAnalyzer();  // (my)(football)(i)(very)(like)
			  Analyzer analyzerSimple= new SimpleAnalyzer();//(this)(is)(my)(football)(i)(very)(like)(it)
			  Analyzer analyzerStop= new StopAnalyzer();//(my)(football)(i)(very)(like)
			  Analyzer analyzerWhite= new WhitespaceAnalyzer();//(this)(is)(my)(football)(,)(I)(very)(like)(it)
			  //这是我的足球，我非常喜欢它
			  //(这)(是)(我)(的)(足球)(我)(非常)(喜欢)(它)
			  Analyzer analyzerCN= new  SmartChineseAnalyzer() ;//可传停用词,看源码默认停用词只是一些标点,词典在coredict.mem是二进制文件(还有一个bigramdict.mem没找到源码)
			  
			  Analyzer myAnalyzer =new MyStopAnalyzer(new String[]{"this","is"});
			  TokenStream stream=myAnalyzer.tokenStream("contents", "this is my football ,  I  very like it" );
			  OffsetAttribute offsetAttr=stream.addAttribute(OffsetAttribute.class);
			  PositionIncrementAttribute posiIncrementAttr=stream.addAttribute(PositionIncrementAttribute.class);
			  TypeAttribute typeAttr=stream.addAttribute(TypeAttribute.class);
			  CharTermAttribute charAttr=stream.addAttribute(CharTermAttribute.class);
			  
			  stream.reset();//按TokenStream javadoc顺序做
			  while(stream.incrementToken())
			  {
				  System.out.print(posiIncrementAttr.getPositionIncrement()+","); 
				  System.out.print(offsetAttr.startOffset()+"-"+offsetAttr.endOffset());
				  System.out.print(typeAttr.type());//StandardAnalyzer是 <IDEOGRAPHIC>，其它看源码默认是word
				  System.out.println("("+charAttr+")");
			  }
			  stream.end();
			  stream.close();//放 finally  中 
			  analyzer.close();
			  
		}
		
		public static void sameWord() throws Exception
		{
			
			  Analyzer analyzer = new MySameAnalyzer(); 
			  TokenStream stream=analyzer.tokenStream("contents", "我来自中国" );
			  CharTermAttribute charAttr=stream.addAttribute(CharTermAttribute.class);
			  
			  stream.reset(); 
			  while(stream.incrementToken())
			  {
				  System.out.print("("+charAttr+")");
			  }
			  stream.end();
			  stream.close();//放 finally  中 
			  analyzer.close();
		}
		public static void sameWordSearch() throws Exception{
		    Directory directory = new RAMDirectory();
		    Analyzer analyzer = new MySameAnalyzer(); //同义词
		    IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    Document doc = new Document();
		    doc.add(new Field("contents",  "我来自中国" , TextField.TYPE_STORED));
		    iwriter.addDocument(doc);
		    iwriter.close();
		    
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		     TermQuery tQuery=new TermQuery(new Term("contents","大陆"));//可以搜到同义词
		     ScoreDoc[]   hits = isearcher.search(tQuery,  1000).scoreDocs;
		    for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = isearcher.doc(hits[i].doc);
		      System.out.println(  hitDoc.get("contents") );
		    }
		    ireader.close();
		    directory.close();
		}
		 
		public static void tika() {
			//String filePath="C:/tmp/spring-hateoas-reference-0.19.pdf";
			String filePath="C:/_work/员工简历模板.xlsx";
			Parser parser=new AutoDetectParser();
			InputStream input=null;
			try{
				input=new FileInputStream(new File(filePath));
				ParseContext context=new ParseContext();
				context.set(Parser.class, parser);
				BodyContentHandler handler=new BodyContentHandler();
				Metadata meta=new Metadata();
				meta.set(Metadata.RESOURCE_NAME_KEY, filePath);
				parser.parse(input ,handler , meta, context);
				String fileContent=handler.toString();
				for(String name:meta.names())
				{
					System.out.println(name +"," +meta.get(name));
				}
				System.out.println(fileContent);
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				IOUtils.closeQuietly(input);
			}
		}
		public static void tika2() throws Exception {
			//String filePath="C:/tmp/spring-hateoas-reference-0.19.pdf";
			//String filePath="C:/_work/员工简历模板.xlsx";
			String filePath="C:/My/software/_java/lucene-6.4.0/lucene-6.4.0/docs/index.html";
			
			Metadata meta=new Metadata();
			meta.set(Metadata.RESOURCE_NAME_KEY, filePath);
			//方式二，性能 低
			Tika tika =new Tika();
			String fileContent=tika.parseToString(new FileInputStream(filePath),meta);
			for(String name:meta.names())
			{
				System.out.println(name +"," +meta.get(name));
			}
			System.out.println(fileContent);
		}
		public static void tikaSearch() throws Exception{
		    Directory directory = new RAMDirectory();
		    Analyzer analyzer = new SmartChineseAnalyzer();  
		    IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    Document doc = new Document();
		    String filePath="C:/_work/员工简历模板.xlsx";
		    //doc.add(new TextField("contents",  new Tika().parse(new File(filePath))));//Lucene使用tika,不会Store
		    //doc.add(new Field("contents", new Tika().parse(new File(filePath)), TextField.TYPE_STORED));//报错,Reader不能使用Store
		    doc.add(new Field("contents", new Tika().parseToString(new File(filePath)), TextField.TYPE_STORED));
		    iwriter.addDocument(doc);
		    iwriter.close();
		    
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		     TermQuery tQuery=new TermQuery(new Term("contents","经验")); //经验,经历
		     ScoreDoc[]   hits = isearcher.search(tQuery,  1000).scoreDocs;
		    for (int i = 0; i < hits.length; i++) {
		      Document hitDoc = isearcher.doc(hits[i].doc);
		      System.out.println(  hitDoc.get("contents") );
		    }
		    ireader.close();
		    directory.close();
		}
		public static void highLighter() throws Exception
		{
			 org.apache.lucene.index.memory.MemoryIndex memory;
			String txt="我来自中国，中国面积很大,人很多";
			
			//TermQuery query=new TermQuery(new Term("f","中国"));
			
			QueryParser parser=new QueryParser("f",new SmartChineseAnalyzer()); 
			Query query=parser.parse("中国 面积");
			
			QueryScorer scorer=new QueryScorer(query);
			Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
			//Highlighter highLiter =new Highlighter(scorer);//默认是<B> </B>
			
			Formatter formatter=new SimpleHTMLFormatter("<span style='color:red'>","</span>");
			Highlighter highLiter =new Highlighter(formatter,scorer);
			
			highLiter.setTextFragmenter(fragmenter);
			String highTxt=highLiter.getBestFragment(new SmartChineseAnalyzer(),"f",txt);
			System.out.println(highTxt);//变为    
		}	
		
		 
		public static void main(String[] args) throws Exception
		{
			//examples();
			//analyzer();
			//sameWord();
			//sameWordSearch();
			
			DocIdSet set=null;//存.doc　大小同 reader.maxDoc();
			//DocIdSetIterator i=set.iterator() ;
			
			//tika();
			//tika2();
			//tikaSearch();
			//highLighter();
			
			new NearRealTimeQueryThread().start(); //结合changeExample()测试
			
		}
		
}



class NearRealTimeQueryThread extends Thread
{
	 SearcherManager manager;
	 Directory directory ;
	 boolean isStop=false;
	 public NearRealTimeQueryThread() throws Exception
	 {
		 directory = FSDirectory.open(Paths.get("c:/tmp/lucene_test"));
		 manager=new SearcherManager(directory,new SearcherFactory());//SearcherManager 官方doc说是线程安全的
	 }
	@Override
	public void run() {
//		Directory fsDir = FSDirectory.open(new File("/path/to/index").toPath());
//		   NRTCachingDirectory cachedFSDir = new NRTCachingDirectory(fsDir, 5.0, 60.0);
//		   IndexWriterConfig conf = new IndexWriterConfig(analyzer);
//		   IndexWriter writer = new IndexWriter(cachedFSDir, conf);
		
		try{
			
			DirectoryReader ireader = DirectoryReader.open(directory);
			 while(!isStop)
			  {
					IndexSearcher isearcher = manager.acquire();
					 try {
						 manager.maybeRefresh();//官方doc说，应周期调用, 查询之前,在单独线程中,如果其它地有删除,可以很反应到查询
						 
						
					     MultiFieldQueryParser parser =new MultiFieldQueryParser(new String[]{"fieldname" },new SmartChineseAnalyzer());
					 	 Query query = parser.parse("text");
					     ScoreDoc[] hits = isearcher.search(query,  1000).scoreDocs;
					    for (int i = 0; i < hits.length; i++) {
					      Document hitDoc = isearcher.doc(hits[i].doc);
					      System.out.println(hitDoc.get("fieldname") );
					    }
					 } finally {
					   manager.release(isearcher);//finally中做
					 }
					 isearcher = null;  
					 Thread.sleep(5*1000);
			  }
			 directory.close();
			 ireader.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}