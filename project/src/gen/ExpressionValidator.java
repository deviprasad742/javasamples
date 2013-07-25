package gen;

import java.util.Stack;

public class ExpressionValidator {

	public static void main(String[] args) {
		String input = "{(([]))}";
		char[] charArray = input.toCharArray();
		Stack<Character> stack = new Stack<Character>();
		for (Character curr : charArray) {
			boolean remove = false;
			if (!stack.isEmpty()) {
				Character peek = stack.peek();
				if (peek == '(' && curr == ')') {
					remove = true;
				} else if (peek == '[' && curr == ']') {
					remove = true;
				} if (peek == '{' && curr == '}') {
					remove = true;
				}
			}
			
			if (remove) {
				stack.pop();
			} else {
				stack.push(curr);
			}
		}
		
		if (stack.isEmpty()) {
			System.out.println("MATCH");
		} else {
			System.out.println("NO MATCH");
		}
		
	}
	
}
