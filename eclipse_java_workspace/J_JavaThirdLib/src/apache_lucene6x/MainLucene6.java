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
		    doc.add(new LongPoint("modifyTime",new Date().getTime()));//��index��,����store
		    doc.add(new StoredField("createTime",new Date().getTime()));//store��
		    doc.add(new TextField("contents", new StringReader("����һ�������ĵĲ��� ,english text,after change")));
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
		    doc.add(new LongPoint("modifyTime",new Date().getTime()));//��index��,����store
		    doc.add(new StoredField("createTime",new Date().getTime()));//store��
		    doc.add(new TextField("contents", new StringReader("����һ�������ĵĲ��� ,english text")));
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
		    //Query query =new TermRangeQuery("fieldname",new BytesRef("text"),new BytesRef("text10"),true,true);//���Է��ض�����¼
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
			
			  // TokenStream �������� TokenFilter, Tokenizer,
			  TokenStream a;
			  Tokenizer b;
			  TokenFilter c;
			  PorterStemFilter f; //���ing����ʱ,er�Ƚϼ�,es���� �ĵ��ʻ�ԭ
			  //this is my football ,  I  very like it
			  Analyzer analyzer = new StandardAnalyzer();  // (my)(football)(i)(very)(like)
			  Analyzer analyzerSimple= new SimpleAnalyzer();//(this)(is)(my)(football)(i)(very)(like)(it)
			  Analyzer analyzerStop= new StopAnalyzer();//(my)(football)(i)(very)(like)
			  Analyzer analyzerWhite= new WhitespaceAnalyzer();//(this)(is)(my)(football)(,)(I)(very)(like)(it)
			  //�����ҵ������ҷǳ�ϲ����
			  //(��)(��)(��)(��)(����)(��)(�ǳ�)(ϲ��)(��)
			  Analyzer analyzerCN= new  SmartChineseAnalyzer() ;//�ɴ�ͣ�ô�,��Դ��Ĭ��ͣ�ô�ֻ��һЩ���,�ʵ���coredict.mem�Ƕ������ļ�(����һ��bigramdict.memû�ҵ�Դ��)
			  
			  Analyzer myAnalyzer =new MyStopAnalyzer(new String[]{"this","is"});
			  TokenStream stream=myAnalyzer.tokenStream("contents", "this is my football ,  I  very like it" );
			  OffsetAttribute offsetAttr=stream.addAttribute(OffsetAttribute.class);
			  PositionIncrementAttribute posiIncrementAttr=stream.addAttribute(PositionIncrementAttribute.class);
			  TypeAttribute typeAttr=stream.addAttribute(TypeAttribute.class);
			  CharTermAttribute charAttr=stream.addAttribute(CharTermAttribute.class);
			  
			  stream.reset();//��TokenStream javadoc˳����
			  while(stream.incrementToken())
			  {
				  System.out.print(posiIncrementAttr.getPositionIncrement()+","); 
				  System.out.print(offsetAttr.startOffset()+"-"+offsetAttr.endOffset());
				  System.out.print(typeAttr.type());//StandardAnalyzer�� <IDEOGRAPHIC>��������Դ��Ĭ����word
				  System.out.println("("+charAttr+")");
			  }
			  stream.end();
			  stream.close();//�� finally  �� 
			  analyzer.close();
			  
		}
		
		public static void sameWord() throws Exception
		{
			
			  Analyzer analyzer = new MySameAnalyzer(); 
			  TokenStream stream=analyzer.tokenStream("contents", "�������й�" );
			  CharTermAttribute charAttr=stream.addAttribute(CharTermAttribute.class);
			  
			  stream.reset(); 
			  while(stream.incrementToken())
			  {
				  System.out.print("("+charAttr+")");
			  }
			  stream.end();
			  stream.close();//�� finally  �� 
			  analyzer.close();
		}
		public static void sameWordSearch() throws Exception{
		    Directory directory = new RAMDirectory();
		    Analyzer analyzer = new MySameAnalyzer(); //ͬ���
		    IndexWriterConfig config = new IndexWriterConfig(analyzer);
		    IndexWriter iwriter = new IndexWriter(directory, config);
		    Document doc = new Document();
		    doc.add(new Field("contents",  "�������й�" , TextField.TYPE_STORED));
		    iwriter.addDocument(doc);
		    iwriter.close();
		    
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		     TermQuery tQuery=new TermQuery(new Term("contents","��½"));//�����ѵ�ͬ���
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
			String filePath="C:/_work/Ա������ģ��.xlsx";
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
			//String filePath="C:/_work/Ա������ģ��.xlsx";
			String filePath="C:/My/software/_java/lucene-6.4.0/lucene-6.4.0/docs/index.html";
			
			Metadata meta=new Metadata();
			meta.set(Metadata.RESOURCE_NAME_KEY, filePath);
			//��ʽ�������� ��
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
		    String filePath="C:/_work/Ա������ģ��.xlsx";
		    //doc.add(new TextField("contents",  new Tika().parse(new File(filePath))));//Luceneʹ��tika,����Store
		    //doc.add(new Field("contents", new Tika().parse(new File(filePath)), TextField.TYPE_STORED));//����,Reader����ʹ��Store
		    doc.add(new Field("contents", new Tika().parseToString(new File(filePath)), TextField.TYPE_STORED));
		    iwriter.addDocument(doc);
		    iwriter.close();
		    
		    DirectoryReader ireader = DirectoryReader.open(directory);
		    IndexSearcher isearcher = new IndexSearcher(ireader);
		     TermQuery tQuery=new TermQuery(new Term("contents","����")); //����,����
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
			String txt="�������й����й�����ܴ�,�˺ܶ�";
			
			//TermQuery query=new TermQuery(new Term("f","�й�"));
			
			QueryParser parser=new QueryParser("f",new SmartChineseAnalyzer()); 
			Query query=parser.parse("�й� ���");
			
			QueryScorer scorer=new QueryScorer(query);
			Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
			//Highlighter highLiter =new Highlighter(scorer);//Ĭ����<B> </B>
			
			Formatter formatter=new SimpleHTMLFormatter("<span style='color:red'>","</span>");
			Highlighter highLiter =new Highlighter(formatter,scorer);
			
			highLiter.setTextFragmenter(fragmenter);
			String highTxt=highLiter.getBestFragment(new SmartChineseAnalyzer(),"f",txt);
			System.out.println(highTxt);//��Ϊ    
		}	
		
		 
		public static void main(String[] args) throws Exception
		{
			//examples();
			//analyzer();
			//sameWord();
			//sameWordSearch();
			
			DocIdSet set=null;//��.doc����Сͬ reader.maxDoc();
			//DocIdSetIterator i=set.iterator() ;
			
			//tika();
			//tika2();
			//tikaSearch();
			//highLighter();
			
			new NearRealTimeQueryThread().start(); //���changeExample()����
			
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
		 manager=new SearcherManager(directory,new SearcherFactory());//SearcherManager �ٷ�doc˵���̰߳�ȫ��
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
						 manager.maybeRefresh();//�ٷ�doc˵��Ӧ���ڵ���, ��ѯ֮ǰ,�ڵ����߳���,�����������ɾ��,���Ժܷ�Ӧ����ѯ
						 
						
					     MultiFieldQueryParser parser =new MultiFieldQueryParser(new String[]{"fieldname" },new SmartChineseAnalyzer());
					 	 Query query = parser.parse("text");
					     ScoreDoc[] hits = isearcher.search(query,  1000).scoreDocs;
					    for (int i = 0; i < hits.length; i++) {
					      Document hitDoc = isearcher.doc(hits[i].doc);
					      System.out.println(hitDoc.get("fieldname") );
					    }
					 } finally {
					   manager.release(isearcher);//finally����
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