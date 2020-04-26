package hw_1;

import java.util.regex.*;

public class Test {
	public static void main(String[] args) {
//		System.out.println(Pattern.matches(".s", " s")); // true
//		System.out.println(Pattern.matches(".s", "s")); // false

//		final Pattern TEST_PATTERN = Pattern.compile("[+]?-?\\d+([+]|-|[*])[+]?-?\\d+");
//		System.out.println(TEST_PATTERN.matcher("10000000000000000+200000000000000000").matches());
//		System.out.println(TEST_PATTERN.matcher("20000000000000000-100000000000000000").matches());
//		System.out.println(TEST_PATTERN.matcher("30000000000000000-200000000000000000").matches());
//		System.out.println(TEST_PATTERN.matcher("50000000*+1000").matches());
//		System.out.println(TEST_PATTERN.matcher("-1000000+0").matches());
		
//		final Pattern EXPRESSION_PATTERN = Pattern.compile("[+]?-?\\d+");
//		String value = "-1000000*+2000000";
//		Matcher m = EXPRESSION_PATTERN.matcher("-1000000*+2000000");
		
    	String[] inputNums = new String[2];
//		if(m.find()) {
//			byte[] numStr1 = m.group().getBytes();
//			System.out.println(numStr1);
//			if(m.find()) {
//				byte[] numStr2 = m.group().getBytes();
//				System.out.println(numStr2);
//			}
//		}
//    	for(int i=0; i<2; i++) {
//			Matcher m = EXPRESSION_PATTERN.matcher(value);
//			if(m.find()) {
//				inputNums[i] = m.group();
//				value = value.replace(inputNums[i], "");
//			}
//			System.out.println(inputNums[i]);
//		}

//		System.out.println("1000000".getBytes());
    	
//    	byte[] array1 = {0, 7, 8, 9};
//    	byte[] array2 = new byte[6];
    	
//		System.arraycopy(big.kichang, 1, small, small.length-big.kichang.length+1, small.length-1);
//    	System.arraycopy(array1, 1, array2, array2.length-array1.length+1, array1.length-1);
//    	
//    	for(int i=0; i<array2.length; i++) {
//    		System.out.println(array2[i]);
//    	}
    	
//    	byte[] tmpResult = {0, 1, 2, 3, 6};
//    	StringBuilder sb = new StringBuilder();
//    	for(int i=0; i<tmpResult.length; i++) {
//    		sb.append(tmpResult[i]);
//    	}
//    	System.out.println(sb.toString());
//    	
//    	byte[] large;
//    	large = tmpResult;
//    	for(int i=0; i<large.length; i++) {
//    		System.out.println(large[i]);
//    	}
    	
//    	byte[] num1 = {2,3,0,5};
//    	byte[] num2 = {2,3,6,5};
//    
//    	System.out.println(absCompare(num1, num2));
    	
//    	int i = 120;
//    	byte[] kichang = new byte[3];
//    	
//		kichang[1] = (byte)(Integer.toString(i).charAt(1)-48); 
//		
//		int j = 120;
//		byte b = (byte) j;
//		System.out.println(b);
    	
    	for(int i=0; i<3; i++) {
    		System.out.println("lol");
    	}
    	
	}
	
//	static boolean absCompare(byte[] num1, byte[] num2) {
//    	if(num1.length > num2.length) {
//    		return true;
//    	} else if(num1.length < num2.length) {
//    		return false;
//    	} else { // 두 수의 자릿수가 같을 때 
//    		int i=0;
//    		while(num1[i] == num2[i]) {
//    			i++;
//    			if(i == num1.length) return true;
//    		}
//    		if(num1[i] > num2[i]) return true;
//    		else return false;
//    	}
//    }

}
