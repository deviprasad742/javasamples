package dynamicprogramming;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class MaxMinArithmetic {

	private static final char SUB = '-';
	private static final char DIV = '/';
	private static final char MULT = '*';
	private static final char PLUS = '+';
	private int[][] maxValue;
	private int[][] minValue;

	private int[][] maxBreakIndices;
	private int[][] minBreakIndices;
	private List<Integer> operands = new ArrayList<Integer>();
	private List<Character> operators = new ArrayList<Character>();
	
	public MaxMinArithmetic(String expression) {
		char[] charArray = expression.toCharArray();
		String lastChar = "";
		for (char nextChar : charArray) {
			if (nextChar == PLUS || nextChar == SUB ||nextChar == MULT ||nextChar == DIV) {
				int value = Integer.valueOf(lastChar);
				operands.add(value);
				operators.add(nextChar);
				lastChar = "";
			} else {
				lastChar = lastChar + nextChar;
			}
		}
		operands.add(Integer.valueOf(lastChar));
		System.out.println(expression);
		
	}
	
	public void compute() {
		int operandsLength = operands.size();
		assert (operands.size() - 1 == operators.size());
		int arraySize = operandsLength;
		maxValue = new int[arraySize][arraySize];
		minValue = new int[arraySize][arraySize];
		maxBreakIndices = new int[arraySize][arraySize];
		minBreakIndices = new int[arraySize][arraySize];
		for (int i = 0; i < operandsLength; i++) {// compute matrix which involves only one operand
			int intValue = operands.get(i);
			maxValue[i][i] = intValue; // value at ith position is the max value for i to i 
			minValue[i][i] = intValue;// value at ith position is min value for i to i;
//			System.out.println(i + "-->" + intValue);
		}

		int loop_count = 0;
		for (int processedOperands = 2; processedOperands <= operandsLength; processedOperands++) {//
			for (int start_index = 0; start_index <= operandsLength - processedOperands; start_index++) {
				// compute max for different blocks 0-3,1-4,2-5 etc
				int blockMax = Integer.MIN_VALUE;
				int blockMin = Integer.MAX_VALUE;

				int maxBlockBrkIdx = 0;
				int minBlockBrkIdx = 0;
				for (int k = 0; k < processedOperands - 1; k++) { //compute different combinations for 0-3 block i.e 0-1x2-3, 0-2x3-3
					int currentMax = computeBlockMaxValue(start_index, k, processedOperands);
					int currentMin = computeBlockMinValue(start_index, k, processedOperands);
					if (currentMax > blockMax) {
						blockMax = currentMax;
						maxBlockBrkIdx = start_index + k;
					}
					if (currentMin < blockMin) {
						blockMin = currentMin;
						minBlockBrkIdx = start_index + k;
					}
					loop_count++;
				}
				int endIndex = start_index + processedOperands - 1;
				maxValue[start_index][endIndex] = blockMax;
				minValue[start_index][endIndex] = blockMin;
				maxBreakIndices[start_index][endIndex] = maxBlockBrkIdx;
				minBreakIndices[start_index][endIndex] = minBlockBrkIdx;

//				System.out.println("Max,Min break index=" + maxBlockBrkIdx + "," + minBlockBrkIdx);
//				System.out.println(start_index + "---" + (endIndex) + " Max:" + blockMax + " Min:" + blockMin);
			}
		}
//		System.out.println("-----------------------------Final Result-----------------------");
		StringBuilder maxBuilder = new StringBuilder();
		buildExpression(0, arraySize-1, maxBuilder,true);
		System.out.println(maxBuilder.toString());
		System.out.println("Max Value: " +  maxValue[0][arraySize -1]);
		
		StringBuilder minBuilder = new StringBuilder();
		buildExpression(0, arraySize-1, minBuilder, false);
		System.out.println(minBuilder.toString());
		System.out.println("Min Value: " +  minValue[0][arraySize -1]);
		System.out.println("Total number of loops for '" + operands.size() + "' is " + loop_count);
	}

	private int computeBlockMaxValue(int start_index, int k, int noOfOperands) {
		int breakIndex = start_index + k;
		char operator = operators.get(breakIndex);
		int left = 0;
		int right = 0;
		left =  maxValue[start_index][breakIndex];
		
		int right_start = breakIndex + 1;
		int right_end = start_index + noOfOperands - 1;
		
		if ((operator == PLUS || operator == MULT) || left < 0) {
			right = maxValue[right_start][right_end];
		} else {
			right = minValue[right_start][right_end];
		}
		
		return computeValue(operator, left, right);
	}

	private int computeValue(char operator, int left, int right) {
		int value = 0;
		if (operator == PLUS) {
			value = left + right;
		} else if (operator == SUB) {
			value = left - right;
		}  else if (operator == MULT) {
			value = left * right;
		} else if (operator == DIV) {
//			if (right == 0) {
//				right = 1;
//			}
			value = left/right;
		}
		return value;
	}
	
	private int computeBlockMinValue(int start_index, int k, int noOfOperands) {
		int breakIndex = start_index + k;
		char operator = operators.get(breakIndex);
		int left = 0;
		int right = 0;
		left = minValue[start_index][breakIndex];

		int right_start = breakIndex + 1;
		int right_end = start_index + noOfOperands - 1;

		if ((operator == PLUS || operator == MULT) || left < 0) {
			right = minValue[right_start][right_end];
		} else {
			right = maxValue[right_start][right_end];
		}

		return computeValue(operator, left, right);
	}

	

	private void buildExpression(int start, int end, StringBuilder builder, boolean computeMax) {
		if (start == end) {
			builder.append(operands.get(start));
			return;
		}
		
		builder.append("(");
		int breakIndex = computeMax ? maxBreakIndices[start][end] : minBreakIndices[start][end];
		
//		System.out.println("Start, End, Break Index :" + start + "," + end + "," + breakIndex);
		
		if (start == breakIndex) {
			builder.append(operands.get(breakIndex));
		} else {
			buildExpression(start, breakIndex, builder, computeMax);
		}
		
		Character operator = operators.get(breakIndex);
		
		if (isNegativeOperator(operator)) {
			computeMax = !computeMax; // reverse the index to be used for sub level
		}
		
		builder.append(operator);
		if (end == breakIndex) {
			builder.append(operands.get(breakIndex));
		} else {
			buildExpression(breakIndex + 1, end, builder, computeMax);
		}
		builder.append(")");
	}
	
	private boolean isNegativeOperator(char operator) {
		return operator == SUB || operator == DIV;
	}
	
// 0-4 = 0-2 3-4 
	public static void main(String[] args) {
		List<String> testCases = new ArrayList<String>();
		testCases.add("2*9+3-16");
		testCases.add("2*3+8-18/3+6");
		testCases.add("4*5+2/9-2*5+7");
		testCases.add("4*5+2/9-2*5+7+12+8*3/5");
		testCases.add("4*5+2/9-2*5+7+12+8*3/5+4*5+2");
		
        int i = 1;
        long start_time = new GregorianCalendar().getTimeInMillis();
		for (String testCase : testCases) {
			System.out.println("-------------------------Test Case " + i + "---------------------------------");
			new MaxMinArithmetic(testCase).compute();
			i++;
		}
		long end_time = new GregorianCalendar().getTimeInMillis();
		System.out.println("Time taken in milliseconds:" + (end_time - start_time));
	}
	
}
