package collection;

public class TestHash
{
	private static MyHashTable table=null;//哈希表。
	//以空间换时间
	//数组的定位速度最快
	//就是把Key通过一个固定的算法函数，既所谓的哈希函数转换成一个整型数字，
	//然后就将该数字对数组长度进行取余，取余结果就当作数组的下标，出现重复,解决冲突的办法其实有很多种，
	//a)开放地址法,产生冲突的时候的增量序列,1)线性探测再散列+1，值到不冲突,2)二次探测再散列,3)伪随机再散列； 
	//c)链地址法,同义词的记录存储在同一线性链表中
	
	public static void hashFunction()//哈希函数
	{

	}
	public static void main(String[] args)
	{

	}

}
class MyHashTable
{
	
}