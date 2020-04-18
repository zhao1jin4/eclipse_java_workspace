package util;

public class Roman2IntNet {

	public static final int I = 1;
	public static final int V = 5;
	public static final int X = 10;
	public static final int L = 50;
	public static final int C = 100;
	public static final int D = 500;
	public static final int M = 1000;

	public static int[] byteToInt(byte[] bt) {
		int[] rom = new int[10];
		int k = 0;
		for (byte b : bt) {
			switch (b) {
			case 'I':
				rom[k] = I;
				break;
			case 'V':
				rom[k] = V;
				break;
			case 'X':
				rom[k] = X;
				break;
			case 'L':
				rom[k] = L;
				break;
			case 'C':
				rom[k] = C;
				break;
			case 'D':
				rom[k] = D;
				break;
			case 'M':
				rom[k] = M;
				break;
			default:

			}
			k++;
		}
		return rom;
	}

	public static int romanToInt(String s) {
		byte[] rom = s.getBytes();
		int[] rom_int = byteToInt(rom);
		//获取符号数组的过程
		int[] flag = new int[rom_int.length];
		boolean positive_flag = true;
		for (int i = 0; i < rom_int.length - 1; i++) {
			positive_flag = true;
			for (int j = i + 1; j < rom_int.length; j++) {
				if (rom_int[j] > rom_int[i]) {
					positive_flag = false;
					break;
				}
			}
			if (positive_flag) {
				flag[i] = 1;
			} else {
				flag[i] = -1;
			}
		}
		int result = arrayMulti(rom_int, flag);

		return result;
	} 
	//整数和其负号相乘
	private static int arrayMulti(int[] rom_int, int[] flag) {
		int result = 0;
		for (int i = 0; i < flag.length; i++) {
			result = result + rom_int[i] * flag[i];
		}
		return result;
	}

	public static void main(String args[]) {
		System.out.println(romanToInt("DCXXI"));//621
		System.out.println(romanToInt("XCM"));//890???
		System.out.println(romanToInt("DCCCXC"));//890
		System.out.println(romanToInt("MXC"));
		
		
	}
}
