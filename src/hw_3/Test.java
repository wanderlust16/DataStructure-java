package hw_3;

import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Test {
	
	public static void main(String[] args) {
		HashMap<String, Integer> testMap = new HashMap<String, Integer>();
		Queue<Object> calPost = new LinkedList<Object>();
		
		testMap.put("+", 1);
		testMap.put("-", 1);
		testMap.put("*", 2);
		testMap.put("/", 2);
		testMap.put("%", 2);
		testMap.put("-", 3); // unary
		testMap.put("^", 4);
		testMap.put("(", 5);
		testMap.put(")", 5);
		
//		System.out.print(testMap.get("+") + testMap.get("*"));
		
		Stack<Object> opStack = new Stack<Object>();
		opStack.push("1");
		opStack.push("2");
		opStack.push("3");
//		System.out.print(opStack.pop());
		
		calPost.offer("a");
		calPost.offer("b");
		calPost.offer("c");
		calPost.poll();
		
		for(Object item : calPost) {
			System.out.print(item);
		}
	}

	
}
