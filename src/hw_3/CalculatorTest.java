package hw_3;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class CalculatorTest {
	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				
				// delete spaces 
				input = input.replaceAll(" ", "");
				command(input);
			}
			catch (Exception e) {
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) {
		// TODO : 아래 문장을 삭제하고 구현해라.
		System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
		
		HashMap<String, Integer> opMap = new HashMap<String, Integer>();
		
		opMap.put("+", 1);
		opMap.put("-", 1);
		opMap.put("*", 2);
		opMap.put("/", 2);
		opMap.put("%", 2);
		opMap.put("~", 3); // unary
		opMap.put("^", 4);
		opMap.put("(", 5);
		opMap.put(")", 5);
				
		Stack<Object> opStack = new Stack<Object>();
		StringBuilder in2post = new StringBuilder();
		
		for(int i=0; i<input.length(); i++) {
			String tmpString = String.valueOf(input.charAt(i));
		
			// if negative(unary)
			if(tmpString.equals("-") && (i == 0 || checkInt(String.valueOf(input.charAt(i-1))) == false)) { 
//				System.out.println("unary");
				tmpString = "~";
			} 
			
			// if number;
			if(checkInt(tmpString)) {
				in2post.append(tmpString);
				continue;
			} 
			
			// if operator;
			if(tmpString.equals("+") || tmpString.equals("-") || tmpString.equals("*") || tmpString.equals("/") || tmpString.equals("%") || tmpString.equals("~")) {
				if(opStack.isEmpty()) {
					opStack.push(tmpString);
				} else {
					while(opMap.get(opStack.peek()) >= opMap.get(tmpString) && !opStack.peek().equals("(")) {
						in2post.append(opStack.pop());
						if(opStack.isEmpty()) break;
					}
					opStack.push(tmpString);
				}				
			} else if(tmpString.equals("^") || tmpString.equals("(")) {
				opStack.push(tmpString);
			} else if(tmpString.equals(")")) {
				while(!opStack.peek().equals("(")) {
					in2post.append(opStack.pop());
				}
				opStack.pop(); // delete "("
			} else { 
				System.out.print("wrong input");
			}	
		}
		
		while(!opStack.isEmpty()) {
			in2post.append(opStack.pop());
		}
		
		System.out.println(in2post);		
	}
	
	private static boolean searchStack(String value) {
		return true;
	}
	
	private static boolean checkInt(String value) {
		try {
			int num = Integer.parseInt(value);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
