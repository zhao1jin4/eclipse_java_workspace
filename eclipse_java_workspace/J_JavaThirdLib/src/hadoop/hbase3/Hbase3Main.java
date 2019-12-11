package hadoop.hbase3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;
public class Hbase3Main {

	 /*
	  	<dependency>
		    <groupId>org.apache.hbase</groupId>
		    <artifactId>hbase-client</artifactId>
		    <version>2.2.2</version>
		</dependency>
		hbase-client-2.2.0.jar 
		hbase-common-2.2.0.jar 
		hbase-protocol-2.2.0.jar
			protobuf-java-3.5.1.jar
	-- hbase ����ʱҪ��jar��	
		hbase-shaded-miscellaneous-2.2.1.jar
		commons-configuration-1.6.jar
		hadoop-auth-2.8.5.jar 
		hbase-protocol-shaded-2.2.0.jar 
		hbase-shaded-protobuf-2.2.1.jar
		hbase-shaded-netty-2.2.1.jar
		metrics-core-3.2.6.jar
		htrace-core4-4.2.0-incubating.jar
	 */
	public static void main(String[] args) throws Exception
	{
		//��־��ʾHADOOP_HOME or hadoop.home.dir are not set.
		System.setProperty("hadoop.home.dir", "/opt/hadoop-3.2.0");
				
		Configuration configuration = HBaseConfiguration.create();
		//�ͻ���ֻҪzookeeper��Ϣ����
		configuration.set("hbase.zookeeper.quorum", "localhost");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//configuration.set("zookeeper.znode.parent", "/hbase"); // Ĭ���ǡ�/hbase  
		
		Connection connection = ConnectionFactory.createConnection(configuration);
		Admin admin = connection.getAdmin();
		//admin.compact(tableName);//admin�ܶ๦������������
		
		String nsName="myns";
		String colFamily="cf";
		//û�з������ж� ���ƿռ� �Ƿ����
		NamespaceDescriptor[] nameSpaces=admin.listNamespaceDescriptors();
		for(NamespaceDescriptor ns: nameSpaces)
		{
			if(nsName.equals(ns.getName()))
			{
				List<TableDescriptor>  tables=admin.listTableDescriptorsByNamespace(Bytes.toBytes(nsName));
				for(TableDescriptor table: tables)
				{
					admin.disableTable(table.getTableName());
					admin.deleteTable(table.getTableName());
				}
				admin.deleteNamespace(nsName);//�������Ѿ����ڵ�,����û�б�
			}
		}
		NamespaceDescriptor nameSpace=  NamespaceDescriptor.create(nsName).build();
		admin.createNamespace(nameSpace);
		NamespaceDescriptor getNameSpace=admin.getNamespaceDescriptor(nsName);//�������Ѿ����ڵ�
		
		TableName tableName=TableName.valueOf(nsName+":myTable");
		if(admin.tableExists(tableName)) //�������Ҫ�����ı���ô��ɾ�����ٴ���
		{
			if(!admin.isTableDisabled(tableName)) 
				admin.disableTable(tableName);
		    admin.deleteTable(tableName);
		}
		ColumnFamilyDescriptor cf= ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(colFamily)).build();
		List<ColumnFamilyDescriptor> cols= Arrays.asList(cf);
		TableDescriptor tableDescriptor=TableDescriptorBuilder.newBuilder(tableName)
		   .setColumnFamilies(cols)
		   .build();
		//admin.createTable(tableDescriptor);
		//���Զഫ����������
		admin.createTable(tableDescriptor, new byte[][] {Bytes.toBytes("100"),Bytes.toBytes("500"),Bytes.toBytes("900")});
		//admin.createTable(tableDescriptor,Bytes.toBytes("100"),Bytes.toBytes("900"),10);
		Table table = connection.getTable(tableName); 
		
		for(int i=1;i<=3;i++)
		{
			Put row = new Put (Bytes.toBytes("row"+i));
			row.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("name"), Bytes.toBytes("lisi"+i));
			row.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("age"), Bytes.toBytes("20"+i));
			table.put(row);//������Ӧ������
		}

		Get get = new Get(Bytes.toBytes("row1"));
		//get.addFamily(Bytes.toBytes(colFamily));//ֻҪ������
		get.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("name"));
		
		//get.readAllVersions();
		get.readVersions(5);//describe 'myTable' ��ʾ��VERSION��ֵ��ȡ�����ʷ�汾��
		/*
 		alter 'myns:myTable' , {NAME => 'cf' , VERSIONS=>'3'} 
		put 'myns:myTable' ,'row1','cf:name', '1111'
		put 'myns:myTable' ,'row1','cf:name', '2222'
		put 'myns:myTable' ,'row1','cf:name', '3333'
		scan  'myns:myTable',{RAW=>true,VERSIONS=>10}
		*/
		Result result=table.get(get);
		myPrintResult(result);
		
		Scan scan =new Scan();
		//scan.addFamily(Bytes.toBytes(colFamily)); 
		scan.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes("name"));//�ɶ�����壬�����
		
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);//MUST_PASS_ONE��MUST_PASS_ALL
		SingleColumnValueFilter filter1 =new SingleColumnValueFilter(
				Bytes.toBytes(colFamily),Bytes.toBytes("name")
				, CompareOperator.EQUAL, Bytes.toBytes("lisi1"));
		filterList.addFilter(filter1);
		
		//ColumnPrefixFilter filter2=new  ColumnPrefixFilter(Bytes.toBytes("name"));//Ч����ǰ���scan.addColumn����
		//���������дأ���������Ϊname����ȫ��ƥ��
		//filterList.addFilter(filter2);
		//MultipleColumnPrefixFilter filter3=new MultipleColumnPrefixFilter(new byte[][] {Bytes.toBytes("name")});
		//filterList.addFilter(filter3);
		
		RowFilter filter4=new RowFilter( CompareOperator.EQUAL,new RegexStringComparator("row."));//rowkey������ʽ
		filterList.addFilter(filter4);
		
		scan.setFilter(filterList);//���ö������
		//scan.setFilter(filter1);//����һ������
		
		ResultScanner scanner = table.getScanner(scan);
		//ResultScanner scanner = table.getScanner(Bytes.toBytes(colFamily));//ֻ��һ���������
		//ResultScanner scanner = table.getScanner(Bytes.toBytes(colFamily),Bytes.toBytes("name"));
		
		
		Iterator<Result> iterator=scanner.iterator();
		while(iterator.hasNext())
		{
			Result res=iterator.next();
			myPrintResult(res);
		}
		
		Delete delete=new Delete(Bytes.toBytes("row1"));
		//�粻��addColumn�൱��deleteall
		//delete.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes("name"));//ɾ�����°汾�ģ� ��ʱ�����ʾɾָ��ʱ��汾
		//delete.addColumns() //ɾȫ���汾�� ��ʱ�����ʾɾ<=ָ��ʱ��汾
		table.delete(delete);
		
		admin.close();
		connection.close();
	}
	private static void myPrintResult(Result result)
	{
		Cell[] cells=result.rawCells();
		for(Cell cell:cells)
		{
			String family=Bytes.toString(cell.getFamilyArray());//ȡ����ֵ
			String family2=Bytes.toString(CellUtil.cloneFamily(cell));
			String col=Bytes.toString(CellUtil.cloneQualifier(cell));
			String rowkey=Bytes.toString(CellUtil.cloneRow(cell));
			String value=Bytes.toString(CellUtil.cloneValue(cell));
			System.out.println( "rowkey="+rowkey+"__"+family2+":"+col+",value="+value+",timestamp="+cell.getTimestamp());
		}
	}
}
