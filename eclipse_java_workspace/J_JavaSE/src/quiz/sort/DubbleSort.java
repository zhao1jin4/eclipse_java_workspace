package quiz.sort;

public class DubbleSort {
	public static void Maopao(int a[]) {

		for (int i = 1; i < a.length; i++) {
			for (int j = 0; j < a.length - i; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j + 1];
					a[j + 1] = a[j];
					a[j] = temp;
				}
			}
		}
		System.out.println("\n" + "采用冒泡排序法：");
	}

	public static void bubbleSort(int[] numbers) {
		int temp; // 记录临时中间值
		int size = numbers.length; // 数组大小
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (numbers[i] > numbers[j]) { // 交换两数的位置
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] data1=new int[]{3,8,2,9,6,4,5,7,1};
		 //bubbleSort(data1);
		Maopao(data1);
		 for (int i=0;i<data1.length;i++)
		  {
			  System.out.println(data1[i]+",");
		  }
		 
	}
}
