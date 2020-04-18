package hadoop.spark;

import org.apache.spark.Partitioner;

class MyPartition  extends Partitioner //spark 自定义分区
{
	private int nums;
	public MyPartition(int nums)
	{
	 	super();
		this.nums = nums;
	}
	@Override //决定在哪个分区
	public int getPartition(Object obj)
	{
		Integer val=Integer.parseInt(obj.toString());
		return val.intValue()%nums; 
	}
	@Override //返回分区总数
	public int numPartitions()
	{
		return this.nums;
	}
}