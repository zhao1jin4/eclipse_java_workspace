package hadoop.hbase;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

public class MainHBase 
{ 
	//javac -d . -Djava.ext.dirs=/opt/hbase-2.2.0/lib/  MainHBase.java 
	//java -Djava.ext.dirs=/opt/hbase-2.2.0/lib/  hadoop.hbase.MainHBase
	public static void main(String[] args) throws Exception
	{
		com.google.protobuf.GeneratedMessage y;
		
		 //org.cloudera.htrace.Trace x;
		 //org.apache.hadoop.hbase.protobuf.generated.HBaseProtos abc;
		 
		 
		String tableName = "myTable";
		 
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.rootdir", "file:///opt/hbase-2.2.0/hdata");  
		config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.dataDir", "/opt/hbase-2.2.0/zkData"); 
		config.set("zookeeper.znode.parent", "/hbase"); // 默认是　/hbase
		 
    	HBaseAdmin admin = new HBaseAdmin(config);   
    	
    	HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
    	HTable htable = new HTable(config,tableName);
    	
    	HTableDescriptor [] tables = admin.listTables();  
 	    if(tables.length>1 )
 	    {
 	    	byte [] byteTableName= htd.getName();
 	    	for (HTableDescriptor tab:tables)
 	    	{
 	    		if(Bytes.equals(byteTableName,tab.getName())) 
 	    		{
 	    			admin.disableTable(tableName);
 	    	    	admin.deleteTable(tableName);
 	    	    	System.out.println("  table dropted");
 	    			 break;
 	    		}
 	    	}
 	    }
 		admin.createTable(htd);
    	admin.disableTable(tableName);
    	HColumnDescriptor cf1 = new HColumnDescriptor("cf");
    	admin.addColumn(tableName, cf1);      // adding new ColumnFamily
    	admin.enableTable(tableName); 
    	
    	admin.close();
    	System.out.println(" table created");
    	
    	 
	   	{
			Put put = new Put(Bytes.toBytes("row8"));
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("attr"), Bytes.toBytes( "data8"));
			htable.put(put);
	    }
	   	{
      		Put put = new Put( Bytes.toBytes("row9" ));
			long explicitTimeInMs = 555;  // just an example
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("attr"), explicitTimeInMs, Bytes.toBytes("data9"));
			htable.put(put);
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("attr"), explicitTimeInMs, Bytes.toBytes("data99"));
			htable.put(put);
	    }
		   	
	   	
	  //----
  		{
  			Get get = new Get(Bytes.toBytes("row8"));
  	        Result r = htable.get(get);
  	        byte[] b = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("attr"));  // returns current version of value
  	        System.out.println("-111===="+ Bytes.toString( b));
  	        
  		}
          //----
  		{
  	        Get get = new Get(Bytes.toBytes("row9"));
  	        get.setMaxVersions(3);  // will return last 3 versions of row
  	        Result r = htable.get(get);
  	        
  	        byte[] b = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("attr"));  // returns current version of value
  	        System.out.println("-222===="+ Bytes.toString( b));

  	        List<Cell> kv = r.getColumnCells(Bytes.toBytes("cf"), Bytes.toBytes("attr"));  // returns all versions of this column
			for(Cell cell:kv)//为什么没有老版本的???
			{
				  System.out.println(new String(CellUtil.cloneValue(cell)));
			}
  		}
  		
  		
		//----
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("cf"),Bytes.toBytes("attr"));
//		scan.setStartRow( Bytes.toBytes("row"));                   // start key is inclusive
//		scan.setStopRow( Bytes.toBytes("row" +  (char)2));  // stop key is exclusive
		
		//--filter
		FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE);
		SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
			Bytes.toBytes("cf"),
			Bytes.toBytes("attr"),
			CompareOp.EQUAL,
			//Bytes.toBytes("value1")
			//new RegexStringComparator("my.")
			new SubstringComparator("dat")
			);
		list.addFilter(filter1);
		list.addFilter(new ColumnPrefixFilter(Bytes.toBytes("att")));
		//list.addFilter(new MultipleColumnPrefixFilter(new byte[][]{Bytes.toBytes("att"),Bytes.toBytes("myattr")}));
		list.addFilter(new ColumnRangeFilter(Bytes.toBytes("attr"), true, Bytes.toBytes("myattr"), true));
		scan.setFilter(list);
		
		scan.addFamily(Bytes.toBytes("cf"));
  
		//--
		
		ResultScanner rs = htable.getScanner(scan);
		try 
		{
		  for (Result r = rs.next(); r != null; r = rs.next()) 
		  {
			  Cell[] cells=r.rawCells();
			  for(Cell cell:cells)
			  {
				  System.out.println(new String(CellUtil.cloneValue(cell)));
			  }
			  System.out.println("----------Result:  "+r.toString());
		  }
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally 
		{
		  rs.close();
		  htable.close();
		}
	}

}
