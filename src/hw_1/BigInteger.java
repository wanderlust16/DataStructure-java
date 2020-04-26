package hw_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
	public static String operator;
    public boolean isPos; 
  
    // implement this
	public static final Pattern EXPRESSION_PATTERN = Pattern.compile("[+]?-?\\d+");
    private static Matcher m;
    private byte[] kichang; // static이면 num1, num2를 모두 이용하는 데 있어서 문제가 발생 
    
//    public BigInteger(int i) { // int로 들어온 생성자
//    	for(int j=0; j<Integer.toString(i).length(); j++) {
//    		kichang[0] = (byte) (Integer.toString(i).charAt(j)-48);
//    	}
//    }
  
    public BigInteger(int[] num1) { // int[]로 들어온 생성자 
    	kichang = new byte[num1.length];
    	for(int i=0; i<num1.length; i++) {
    		kichang[i] = (byte) num1[i];
    	}	
    }
  
    public BigInteger(String s) { // string으로 들어온 생성자 -> byte[]로 저장 
    	if(operator.contains("+") || operator.contains("-")) { // 덧셈 혹은 뺄셈일 때 
    		kichang = new byte[s.length()+1];
    		kichang[0] = 0; // 맨 앞자리는 비워두기 (연산과정에서 자릿수가 올라가는 경우 대비) 
    		for(int i=1; i<kichang.length; i++) {
    			kichang[i] = (byte)(s.charAt(i-1)-48);
    		}
    	} else { // 곱셈일 때는 맨 앞자리를 비워두지 않음 
    		kichang = new byte[s.length()];
    		for(int i=0; i<s.length(); i++) {
        		kichang[i] = (byte)(s.charAt(i)-48); 
        	}
    	}
    }
  
    public BigInteger add(BigInteger big) {
    	
    	System.out.println("덧셈하는 중");
      	    	
    	int numDigit = Math.max(kichang.length, big.kichang.length);
    	byte[] large = new byte[numDigit];
    	byte[] small = new byte[numDigit]; // 큰 수의 자릿수만큼 앞에 0을 채움 

    	if(kichang.length >= big.kichang.length) {
    		large = kichang;
    		System.arraycopy(big.kichang, 1, small, small.length-big.kichang.length+1, big.kichang.length-1);
    	} else {
    		large = big.kichang;
    		System.arraycopy(kichang, 1, small, small.length-kichang.length+1, kichang.length-1);
    	}
    	
    	byte[] tmpResult = new byte[numDigit];
    	    	
    	// 음수 + 음수 또는 양수 + 양수 
    	if((isPos == false && big.isPos) == false || (isPos == true && big.isPos == true)) {
    		for(int i=numDigit-1; i>0; i--) {
				tmpResult[i] += (byte)(small[i] + large[i]);
    			tmpResult[i-1] += (byte)(tmpResult[i] / 10); // 자릿수의 합이 10이 넘으면 윗 자릿수에 1을 더함 
				if(tmpResult[i] >= 10) {
	    			tmpResult[i] %= (byte)(tmpResult[i]);	
				}
    		}       
    		
    		// byte[]로 저장한 tmpResult를 String으로 만들어줌 
    		StringBuilder sb = new StringBuilder();
        	for(int i=0; i<tmpResult.length; i++) {
        		sb.append(tmpResult[i]);
        	}
        	BigInteger biResult = new BigInteger(sb.toString());
        	
			// 음수 + 음수  
			if(isPos == false && big.isPos == false) {
				biResult.isPos = false;
				return biResult;
			} else { // 양수 + 양수 
				biResult.isPos = true;
				return biResult;
			}
    	}

    	// 음수 + 양수
    	else if(isPos == false && big.isPos == true) {
    		this.isPos = true;
    		return big.subtract(this); // 양수 - 양수 
    	}

    	// 양수 + 음수 
    	else {
    		big.isPos = true;
    		return this.subtract(big); // 양수 - 양수  
    	}
    }
    
    // 절댓값이 큰 수를 리턴 (두 수가 일치하면 true든 false든 상관 없음) 
    static byte[] absCompare(byte[] num1, byte[] num2) {
    	if(num1.length > num2.length) {
    		return num1;
    	} else if(num1.length < num2.length) {
    		return num2;
    	} else { // 두 수의 자릿수가 같을 때 
    		int i=0;
    		while(num1[i] == num2[i]) {
    			i++;
    			if(i == num1.length) return num1; // 두 수가 정확히 같으면 
    		}
    		if(num1[i] > num2[i]) return num1;
    		else return num2;
    	}
    }
  
    public BigInteger subtract(BigInteger big) {
    	
    	System.out.println("뺄셈하는 중");
    	
    	int numDigit = Math.max(kichang.length, big.kichang.length);
    	byte[] tmpResult = new byte[numDigit];
    	byte[] absSmall = new byte[numDigit]; // 큰 수의 자릿수만큼 앞에 0을 채움 
    	
   		byte[] absLarge = absCompare(kichang, big.kichang);
   		byte[] absSmallTmp;
   		if(absLarge == kichang) absSmallTmp = big.kichang;
   		else absSmallTmp = kichang;
   		
    	if(absLarge.length != absSmallTmp.length) {
    		System.arraycopy(absSmallTmp, 1, absSmall, absSmall.length-absSmallTmp.length+1, absSmallTmp.length-1);
    	} else {
    		absSmall = absSmallTmp;
    	}	
    	
       	// 양수 - 양수 또는 음수 - 음수 // 일단 절댓값이 큰 수에서 작은 수를 빼고, 부호는 나중에 결정 
       	if((isPos == true && big.isPos == true) || (isPos == false && big.isPos == false)) {           		
       		for(int i=numDigit-1; i>0; i--) {
       			if(absLarge[i] - absSmall[i] < 0) {
       				absLarge[i] += 10;
       				absLarge[i-1] -= 1;
       				tmpResult[i] = (byte)(absLarge[i] - absSmall[i]);
       			} else {
       				tmpResult[i] = (byte)(absLarge[i] - absSmall[i]);
       			}
       		}
       		
       		// byte[]로 저장한 tmpResult를 String으로 만들어줌 
    		StringBuilder sb = new StringBuilder();
        	for(int i=0; i<tmpResult.length; i++) {
        		sb.append(tmpResult[i]);
        	}
        	BigInteger biResult = new BigInteger(sb.toString());
        	
			// 음수 - 음수  
			if(isPos == false && big.isPos == false) {
				if(absLarge == kichang) biResult.isPos = false;
				else biResult.isPos = true;
				return biResult;
			} else { // 양수 - 양수
				if(absLarge == kichang) biResult.isPos = true;
				else biResult.isPos = false;
				return biResult;
			} 	
    	}
    	
    	// 양수 - 음수  
    	else if(isPos == true && big.isPos == false) {
    		big.isPos = true;
    		return this.add(big); // 양수 + 양수 
    	}
    	// 음수 - 양수 
    	else {
    		big.isPos = false; 
    		return big.add(this); // 음수 + 음수 
    	}
    }
  
    public BigInteger multiply(BigInteger big) {
    	System.out.println("곱셈하는 중");
    	// 자릿수끼리 곱했을 때 127 이상의 값이 나오진 않음 
    	byte[] multiResult = new byte[kichang.length * big.kichang.length];
    	// 입력받는 갹 수의 최대 크기는 100자리고, 100자리의 두 수를 곱했을 때 자릿수에 0이 10000개 들어가야 할 수도 있으므로 byte에 담을 수 없어 int 배열을 사용 
    	int[] numZero = new int[kichang.length * big.kichang.length];
    	int cnt = 0;
    		
    	for(int i=kichang.length-1; i>=0; i--) {
    		for(int j=big.kichang.length-1; j>=0; j--) {
				multiResult[cnt] = (byte)(kichang[i] * big.kichang[j]); 
//				System.out.println("line197: " + cnt + " : " + multiResult[cnt]);
    			numZero[cnt] = (kichang.length-i-1) + (big.kichang.length-j-1);
//				System.out.println("line199: " + numZero[cnt]);	
				cnt++;
    		}
    	}
    	
    	// 곱셈 결과 각 수 중 가장 큰 숫자로 배열을 초기화 
    	int[] tmpArray = new int[numZero[numZero.length-1]+2]; // new int[5];
    	BigInteger biResult = new BigInteger(tmpArray); // tmpResult.kichang = byte[] {0, 0, 0, 0, 0} 
    	biResult.isPos = true;
		operator = "+"; // 이제부터 더하기 

    	for(int i=0; i<kichang.length*big.kichang.length; i++) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(multiResult[i]);
    		for(int j=0; j<numZero[i]; j++) {
    			sb.append(0);
    		}
    		BigInteger numToAdd = new BigInteger(sb.toString());
    		numToAdd.isPos = true;    		
    		biResult = biResult.add(numToAdd); 
       	}
    	    	
    	if(isPos == big.isPos) { // 두 수의 부호가 같으면 
    		biResult.isPos = true;
    		return biResult;
    	} else { // 두 수의 부호가 다르면 
    		biResult.isPos = false;
    		return biResult;
    	}
    	
    }
  
    @Override
    public String toString() { // 출력 시 사용 (BigInteger type을 String으로 출력) 
    	
    	// 여기서 kichang은 result.kichang 
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<kichang.length; i++) {
    		sb.append(kichang[i]);
    	}
    	
		// 앞의 0들을 제거 
    	while((String.valueOf(sb.charAt(0))).equals("0")) {
    		sb.deleteCharAt(0);
    		if(sb.toString().length() == 0) break;
    	} 
        	
    	if(sb.toString().isBlank()) return "0";
    	
    	// 원래 결과값이 0이었다면 
    	
    	if(isPos == true) {
    		return sb.toString();
    	} else {
    		return "-" + sb.toString();
    	}
    	
    	
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
  
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;	
    	
    	String[] inputNums = new String[2];
    	String numStr1, numStr2 = null;
    	String value = input.replaceAll(" ", ""); // input String에서 공백 제거 
    	BigInteger num1, num2; 
		
	
		m = EXPRESSION_PATTERN.matcher(value);
		if(m.find()) {
			inputNums[0] = m.group(); // 첫 번째 수 분리 
		}		
		m = EXPRESSION_PATTERN.matcher(value.substring(m.end()));
		if(m.find()) {
			inputNums[1] = m.group();
		}
		value = value.replace(inputNums[0], "");
		value = value.replace(inputNums[1], "");
		
		// 연산자 분리 
		if(value.length() != 0) {  
			// 이게 오는 중 
			operator = value;
		} else {
			operator = String.valueOf(inputNums[1].charAt(0));
		}		
		
		System.out.println("첫 번째 수: " + inputNums[0]);
		System.out.println("두 번째 수: " + inputNums[1]);
		System.out.println("연산자: " + operator);
				

		// 첫 번째 수의 부호 결정 
		if(String.valueOf(inputNums[0].charAt(0)).contains("-")) {
			num1 = new BigInteger(inputNums[0].substring(1)); // - 부호 빼고 저장 
			num1.isPos = false;

		} else if(String.valueOf(inputNums[0].charAt(0)).contains("+")) {
			num1 = new BigInteger(inputNums[0].substring(1)); // + 부호 빼고 저장 
			num1.isPos = true;

		} else { 
			num1 = new BigInteger(inputNums[0]);
			num1.isPos = true;	
		}
				
		// 두 번째 수의 부호 결정 
		if(String.valueOf(inputNums[1].charAt(0)).contains("-")) {
			if(operator.contains("-")) {
				num2 = new BigInteger(inputNums[1].substring(1)); // - 부호 빼고 저장 
				num2.isPos = true;
			} else {
				num2 = new BigInteger(inputNums[1].substring(1)); // - 부호 빼고 저장 
				num2.isPos = false;
			}
		} else if(String.valueOf(inputNums[1].charAt(0)).contains("+")) {
			num2 = new BigInteger(inputNums[1].substring(1)); // + 부호 빼고 저장 
			num2.isPos = true;
		} else { // 두 번째 수의 부호가 *인 경우 
			num2 = new BigInteger(inputNums[1]);
			num2.isPos = true;
		}
		
		if(operator.contains("+")) {
			return num1.add(num2); // num1, num2는 byte[]가 아닌 BigInteger 객체 
		} else if(operator.contains("-")) {
			return num1.subtract(num2);
		} else { // 곱셈일 때 
			return num1.multiply(num2);
		}
    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done) // = while(true) 
                {
                    String input = reader.readLine(); // 입력 받음 
  
                    try
                    {
                        done = processInput(input); // done이 true이면 (즉 input이 quit이라면) while문을 종료  
                    }
                    catch (IllegalArgumentException e)
                    {
//                        System.err.println(MSG_INVALID_INPUT);
                    	e.printStackTrace();
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true; // quit을 입력 받으면 프로그램을 종료 
        }
        else
        {
            BigInteger result = evaluate(input); 
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input) // quit의 대소문자 구분 통일 
    {
        return input.equalsIgnoreCase(QUIT_COMMAND); // quit을 입력 받으면 true를 리턴 
    }
}
