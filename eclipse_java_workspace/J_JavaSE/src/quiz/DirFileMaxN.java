package quiz;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DirFileMaxN {

	public static void main(String[] args) {
		 
//		List<String> res=new DirFileMaxN().getMaxFileInDir(new File("c:/tmp/"),3);
//		System.out.println(res);
		
//	    List<String> res=new DirFileMaxN().	getMaxFileInDir2(new File("c:/tmp/"),3);
//		System.out.println(res);
// 
		  List<String> res=new DirFileMaxN().getMaxFileInDir3(new File("c:/tmp/"),3);
			System.out.println(res);
	 
			
	}
	
	/*
	public List<String> getMaxFileInDir(File dir,int n)
	{
		
		List<String> res=new ArrayList<String>();
		
		Map<String,Long> map=new HashMap<String,Long>();
		for(File file:dir.listFiles())
		{
			if(file.isFile())
			{
				long size=file.length();
				map.put(file.getAbsolutePath(),  size);
			}
		}
		long[] sort=new long[map.size()];
		int i=0;
		for(Map.Entry<String, Long> entry:map.entrySet())
		{
			sort[i++]=entry.getValue() ; 
		}
		Arrays.sort(sort, 0, map.size());
		
		for( i=1;i<=n;i++)
		{
			for(Map.Entry<String, Long> entry:map.entrySet())
			{
				if(entry.getValue()==sort[map.size()-i])//如两个文件大小相同,就只保留后面的了
				{
					res.add(entry.getKey());
					break;
				}
			}
		}
		return res;
	}
	*/
	class FileItem  // implements Comparable<FileItem>
	{
		String path;
		long size;
//		@Override
//		public int hashCode() {
//			return  path.hashCode();
//		}
//		@Override
//		public boolean equals(Object obj) {
//			 if(obj instanceof FileItem )
//			 {
//				 return this.path.equals(((FileItem)obj).path);
//			 }
//			return false;
//		}
//		@Override
//		public String toString() {
//			return path;
//		}
//		@Override
//		public int compareTo(FileItem o) {
//			return  (o.size==this.size)?0:(o.size>this.size)?1:-1;
//		}
 
	}
	
	public List<String> getMaxFileInDir2(File dir,int n)
	{
//		dir.listFiles(new FileFilter(){
//		@Override
//		public boolean accept(File pathname) {
//			return (pathname.isFile());
//		}});
		
		List<FileItem> remList =new ArrayList<FileItem>();
		
		for(File file:dir.listFiles())
		{
			if(file.isFile())
			{
				FileItem i=new FileItem();
				i.path=file.getAbsolutePath();
				i.size= file.length();
				remList.add(i);
			}
		}
	   Comparator<FileItem> comparator=new Comparator<FileItem>()
		{
			@Override
			public int compare(FileItem o1, FileItem o2) {
				  return  (o1.size==o2.size)?0:(o1.size>o2.size)?1:-1;  
			}
		};
		
		Collections.sort(remList, comparator );  //Collections.reverseOrder(comparator)
		//Collections.max(remList);
		 List<FileItem>  reverseList= remList.subList(remList.size()-n, remList.size());
		 Collections.reverse(reverseList);
		 List<String> res=new ArrayList<String>();
		 for(FileItem item:reverseList )
		 {
			 res.add( item.path);
		 }
		
//		
//		for(int i=0;i<n;i++)
//		{
//			res.add( remList.get(remList.size()-i-1).path);
//		}
		return res;
	}
	 
	class FileItem2  // implements Comparable<FileItem>
	{
		String path;
		long size;
		@Override
		public int hashCode() {
			return  path.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			 if(obj instanceof FileItem2 )
			 {
				 return this.path.equals(((FileItem2)obj).path);
			 }
			return false;
		} 
	}
	public List<String> getMaxFileInDir3(File dir,int n)
	{
		List<FileItem2> remList =new ArrayList<FileItem2>();
		
		for(File file:dir.listFiles())
		{
			if(file.isFile())
			{
				FileItem2 i=new FileItem2();
				i.path=file.getAbsolutePath();
				i.size= file.length();
				remList.add(i); //重写equals  根据文件名
			}
		}
		  Comparator<FileItem2> comparator=new Comparator<FileItem2>()
			{
				@Override
				public int compare(FileItem2 o1, FileItem2 o2) {
					  return  (o1.size==o2.size)?0:(o1.size>o2.size)?1:-1;  
				}
			};
			
		List<String> res=new ArrayList<String>();
		for(int i=0;i<n;i++)
		{
			FileItem2 item2 =Collections.max(remList,comparator);
			boolean isOK=remList.remove(item2);//使用equals方法删
			res.add(item2.path);
		}
		return res;
		
	}
}
