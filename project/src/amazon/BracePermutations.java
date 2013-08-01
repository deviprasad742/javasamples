package amazon;

import java.util.Stack;

public class BracePermutations {
   private int n1 = 4;//[
   private int n2 = 0;//(
   private int n3  = 0;//<
   private char[] combination = new char[2*(n1+n2+n3)];
   private int count = 0;
   
   public void permute() {
	   permute(n1, n2, n3, 0, 0, 0, combination, 0);
	   System.out.println("Total number of permutations: " + count);
   }

	private void permute(int n1Open, int n2Open, int n3Open, int n1Closed, int n2Closed, int n3Closed, char[] combination, int index) {
       if (index == combination.length) {
    	   System.out.println(new String(combination));
    	   count++;
    	   return;
       }
       
       if (n1Open > 0) {
    	   combination[index] = '[';
    	   permute(n1Open - 1, n2Open, n3Open, n1Closed + 1, n2Closed, n3Closed, combination, index + 1);
       }
       
       if (n2Open > 0) {
    	   combination[index] = '(';
    	   permute(n1Open, n2Open - 1, n3Open, n1Closed, n2Closed + 1, n3Closed, combination, index + 1);
       }
       
       if (n3Open > 0) {
    	   combination[index] = '<';
    	   permute(n1Open, n2Open, n3Open - 1, n1Closed, n2Closed, n3Closed + 1, combination, index + 1);
       }
       
       char lastOpenChar = getLastOpenChar(combination, index);
      
		if (n1Closed > 0 && lastOpenChar == '[') {
			combination[index] = ']';
			permute(n1Open, n2Open, n3Open, n1Closed - 1, n2Closed, n3Closed, combination, index + 1);
		}

		if (n2Closed > 0 && lastOpenChar == '(') {
			combination[index] = ')';
			permute(n1Open, n2Open, n3Open, n1Closed, n2Closed - 1, n3Closed, combination, index + 1);
		}

		if (n3Closed > 0 && lastOpenChar == '<') {
			combination[index] = '>';
			permute(n1Open, n2Open, n3Open, n1Closed, n2Closed, n3Closed - 1, combination, index + 1);
		}
       
	}

	private char getLastOpenChar(char[] combination, int index) {
		Stack<Character> stack = new Stack<Character>();
		int i = 0;
		while (i != index) {
			char currentChar = combination[i];
			if (currentChar == '[' || currentChar == '(' || currentChar == '<') {
				stack.push(currentChar);
			} else if (currentChar == ']' && stack.peek() == '[') {
				stack.pop();
			} else if (currentChar == ')' && stack.peek() == '(') {
				stack.pop();
			} else if (currentChar == '>' && stack.peek() == '<') {
				stack.pop();
			}
			i ++;
		}
		return stack.isEmpty() ? 0 : stack.peek();
	}
	
	public static void main(String[] args) {
		new BracePermutations().permute();
	}

	
}
