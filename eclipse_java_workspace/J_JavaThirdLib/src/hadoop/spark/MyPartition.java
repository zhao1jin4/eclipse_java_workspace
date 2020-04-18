package hadoop.spark;

import org.apache.spark.Partitioner;

class MyPartition  extends Partitioner //spark �Զ������
{
	private int nums;
	public MyPartition(int nums)
	{
	 	super();
		this.nums = nums;
	}
	@Override //�������ĸ�����
	public int getPartition(Object obj)
	{
		Integer val=Integer.parseInt(obj.toString());
		return val.intValue()%nums; 
	}
	@Override //���ط�������
	public int numPartitions()
	{
		return this.nums;
	}
}