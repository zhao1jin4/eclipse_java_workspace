package quiz.sum.equal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EqualZero {
  //һ�����飬��n��Ԫ�أ��ҳ��������������=0���������ظ���
	public static void main(String[] args) {
		Set<List> res=new HashSet();
		int arr[]=new int[] {-5,4,0,1,2,3,-1,3-2,-3};//3�ɳ��ִ�
		for(int i=0;i<arr.length;i++)
		{
			for(int j=i+1;j<arr.length;j++)
			{
				if(arr[i]==arr[j])
				{
					continue;
				}
				for(int k=j+1;k<arr.length;k++)
				{
					if(arr[i]==arr[k] || arr[j]==arr[k] )
					{
						continue;
					}
					if(arr[i]+arr[j]+arr[k] ==0) {
						//ȥ�أ���ʽ ������ֵ�������ٷ���set�У�
						List row=Arrays.asList(arr[i],arr[j],arr[k]); 
						Collections.sort(row);
						res.add(row);
						//System.out.println(arr[i]+"+"+arr[j]+"+"+arr[k]);
					} 
				}
			}
		}
		System.out.println(res);
		
	}

}
