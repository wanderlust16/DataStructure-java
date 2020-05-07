package hw_3;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
//import java.util.Queue;
//import java.util.LinkedList;
import java.util.regex.*;

public class CalculatorTest {

	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			try {
				String input = br.readLine();
				if (input.compareTo("q") == 0) {
//					break;
					System.exit(0);
				}
				
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
		
		String operators = "+-*/%^";
				
		Stack<String> inOpStack = new Stack<String>();
		Stack<String> postStack = new Stack<String>();
		
		StringBuilder tmpNum = new StringBuilder(); // 2자리 수 이상의 숫자일 경우 
						
		for(int i=0; i<input.length(); i++) {
			if(i<input.length()-1) {
				String firstVal = String.valueOf(input.charAt(i));
				String secondVal = String.valueOf(input.charAt(i+1));
				if(operators.contains(firstVal) && operators.contains(secondVal)) {
					System.out.println("ERROR");
					System.exit(0);
				}
			}
			
			String tmpString = String.valueOf(input.charAt(i));
		
			// if negative(unary)
			if(tmpString.equals("-") && (i == 0 || !checkLong(String.valueOf(input.charAt(i-1))))) { 
				tmpString = "~";
			} 
			
			// if number;
			if(checkLong(tmpString)) {
				tmpNum.append(tmpString);
				if(i == input.length()-1 || !checkLong(String.valueOf(input.charAt(i+1))) ) { // 더 이상 숫자가 아니라면 
					postStack.push(tmpNum.toString());
					tmpNum = new StringBuilder(); // tmpNum을 비움 
				}
				continue;
			} 
			
			// if operator;
			if(tmpString.equals("+") || tmpString.equals("-") || tmpString.equals("*") || tmpString.equals("/") || tmpString.equals("%")) {
				if(inOpStack.isEmpty()) {
					inOpStack.push(tmpString);
				} else {
					while(opMap.get(inOpStack.peek()) >= opMap.get(tmpString) && !inOpStack.peek().equals("(")) {
						String tmpOp = inOpStack.pop();
						postStack.push(tmpOp);
						if(inOpStack.isEmpty()) break;
					}
					inOpStack.push(tmpString);
				}				
			} else if(tmpString.equals("^") || tmpString.equals("(") || tmpString.equals("~")) {
				inOpStack.push(tmpString);
			} else if(tmpString.equals(")")) {
				while(!inOpStack.peek().equals("(")) {
					String tmpOp = inOpStack.pop();
					postStack.push(tmpOp);
				}
				inOpStack.pop(); // delete "("
			} else { 
				System.out.print("wrong input");
			}	
		}
		
		while(!inOpStack.isEmpty()) {
			String tmpOp = inOpStack.pop();
			postStack.push(tmpOp);
		}

		for(Object item : postStack) {
			System.out.print(item + " ");
		}
		System.out.println();
		
		Iterator stackItr = postStack.iterator();
		Stack<String> calStack = new Stack<String>();
		
		while(stackItr.hasNext()) {
			// if number 
			String tmpVal = stackItr.next().toString();
			if(checkLong(tmpVal)) { // 어차피 String인데 왜 또 String으로 type cast를 해야 하는지? 
				calStack.push(tmpVal);
			} else if(tmpVal.equals("+")){
				long currCal = Long.parseLong(calStack.pop()) + Long.parseLong(calStack.pop());
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("-")) {
				long secondNum = Long.parseLong(calStack.pop());
				long firstNum = Long.parseLong(calStack.pop());
				long currCal = firstNum - secondNum;
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("*")) {
				long currCal = Long.parseLong(calStack.pop()) * Long.parseLong(calStack.pop());
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("/")) {
				long secondNum = Long.parseLong(calStack.pop());
				long firstNum = Long.parseLong(calStack.pop());
				if(secondNum == 0) {
					System.out.println("ERROR");
					System.exit(0);
				}
				long currCal = firstNum / secondNum;
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("%")) {
				long secondNum = Long.parseLong(calStack.pop());
				long firstNum = Long.parseLong(calStack.pop());
				if(secondNum == 0) {
					System.out.println("ERROR");
					System.exit(0);
				}
				long currCal = firstNum % secondNum;
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("^")) {
				long secondNum = Long.parseLong(calStack.pop());
				long firstNum = Long.parseLong(calStack.pop());
				if(secondNum < 0) {
					System.out.println("ERROR");
					System.exit(0);
				}
				long currCal = (long)Math.pow(firstNum, secondNum);
				calStack.push(Long.toString(currCal));
			} else if(tmpVal.equals("~")) {
				long currNum = Long.parseLong(calStack.pop());
				currNum = (-1) * currNum;
				calStack.push(Long.toString(currNum));
			} else {
				System.out.println("wrong cal");
			}
		}
		
//		System.out.println(calStack.size()); // 1이 나와야 함 
		System.out.println(calStack.pop());
	}
	
	private static boolean checkLong(String value) {
		try {
			long longNum = Long.parseLong(value);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
