package dynamicprogramming;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Find the max and min value of an expression
 * @author KH348
 *
 */
public class ExpressionLimit {
	
	public static Pattern INTEGER_PATTERN = Pattern.compile("[0-9]+");
	private static final ValueCombination values[] = ValueCombination.values();
	public static void calculateExpressionLimit(String expressionStr) {
		List<AtomicExpression> expression = new ArrayList<AtomicExpression>();
		Matcher matcher = INTEGER_PATTERN.matcher(expressionStr);
		while(matcher.find()) {
			int end = matcher.end();
			String number = matcher.group();
			AtomicExpression atomicExpression = new AtomicExpression();
			atomicExpression.operand = Integer.parseInt(number);
			if(end < expressionStr.length()) {
				char charAt = expressionStr.charAt(end);
				atomicExpression.suffixOperator = getOperatorFrom(charAt);
				
			}
			expression.add(atomicExpression);
		}
		Info[][] infoArray = new Info[expression.size()][expression.size()];
		//length 1
		for(int i = 0; i< expression.size();i++) {
			Info info = new Info();
			info.maxValue = info.minValue = expression.get(i).operand;
			infoArray[i][i] = info;
		}
		for(int lengthItr=2;lengthItr<=expression.size();lengthItr++) {
			for(int startIndexItr = 0; startIndexItr <= expression.size()- lengthItr;startIndexItr++) {
				int endIndexItr = startIndexItr + lengthItr - 1;
				int minValue = Integer.MAX_VALUE;
				int maxValue = Integer.MIN_VALUE;
				int minSplit = -1;
				int maxSplit = -1;
				ValueCombination minValueCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
				ValueCombination maxValueCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
				for(int i = startIndexItr;i<endIndexItr;i++) {
					ValueCombination minValueCombinationItr = null;
					ValueCombination maxValueCombinationItr = null;
					int minValueitr = Integer.MAX_VALUE;
					int maxValueItr = Integer.MIN_VALUE;
					Info leftInfo = infoArray[startIndexItr][i];
					Info rightInfo = infoArray[i+1][endIndexItr];
					OPERATOR suffixOperator = expression.get(i).suffixOperator;
					switch (suffixOperator) {
					case ADD:
						minValueitr = invokeOperation(leftInfo.minValue, rightInfo.minValue, OPERATOR.ADD);
						maxValueItr = invokeOperation(leftInfo.maxValue, rightInfo.maxValue, OPERATOR.ADD);
						minValueCombinationItr = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
						maxValueCombinationItr = ValueCombination.MAX_VALUE_LEFT_MAX_VALUE_RIGHT;
						break;
					case SUB:
						minValueitr = invokeOperation(leftInfo.minValue, rightInfo.maxValue, OPERATOR.SUB);
						maxValueItr = invokeOperation(leftInfo.maxValue, rightInfo.minValue, OPERATOR.SUB);
						minValueCombinationItr = ValueCombination.MIN_VALUE_LEFT_MAX_VALUE_RIGHT;
						maxValueCombinationItr = ValueCombination.MAX_VALUE_LEFT_MIN_VALUE_RIGHT;
						break;
					case DIV:
					case PROD:
						DivProdInfo divProdInfo = getDivProdInfo(new int[]{leftInfo.minValue,leftInfo.maxValue},
								new int[]{rightInfo.minValue,rightInfo.maxValue}, suffixOperator);
						minValueitr = divProdInfo.minValue;
						maxValueItr = divProdInfo.maxValue;
						minValueCombinationItr = divProdInfo.minCombination;
						maxValueCombinationItr = divProdInfo.maxCombination;
						break;
					default:
						break;
					}
					if(minValueitr < minValue) {
						minValue = minValueitr;
						minValueCombination = minValueCombinationItr;
						minSplit = i;
					}
					if(maxValueItr > maxValue){
						maxValue = maxValueItr;
						maxValueCombination = maxValueCombinationItr;
						maxSplit = i;
					}
				}
				Info info = new Info();
				infoArray[startIndexItr][endIndexItr] = info;
				info.maxValue = maxValue;
				info.minValue = minValue;
				info.maxSplit = maxSplit;
				info.minSplit = minSplit;
				info.maxCombination = maxValueCombination;
				info.minCombination = minValueCombination;
				
			}
		}
		System.out.println("Expression is "+expressionStr);
		System.out.println("Minimum Value is " + infoArray[0][expression.size()-1].minValue);
		StringBuilder minBuilder = new StringBuilder(" ");
		buildExpression(expression, 0, expression.size()-1, infoArray,false,minBuilder);
		System.out.println(minBuilder);
		System.out.println("Maximum Value is " + infoArray[0][expression.size()-1].maxValue);
		StringBuilder maxBuilder = new StringBuilder(" ");
		buildExpression(expression, 0, expression.size()-1, infoArray,true,maxBuilder);
		System.out.println(maxBuilder);
		System.out.println();
	}
	
	public static void buildExpression(List<AtomicExpression> expression,int startIndex,
			int endIndex,Info[][] infoArray,boolean isMaximum,StringBuilder stringBuilder) {
		if(startIndex != -1 && endIndex != -1) {
			if(startIndex <= endIndex) {
				AtomicExpression startExpr = expression.get(startIndex);
				AtomicExpression endExpr = expression.get(endIndex);
				if(startIndex < endIndex-1) {
					Info info = infoArray[startIndex][endIndex];
					int split = isMaximum?info.maxSplit:info.minSplit;
					ValueCombination valueCombination =isMaximum?info.maxCombination:info.minCombination;
					//Left part print
					if(split - startIndex > 0) {
						if(stringBuilder.charAt(stringBuilder.length()-1) != '(') {
							int startIndexPrev = startIndex - 1;
							if(startIndexPrev != -1) {
								stringBuilder.append(expression.get(startIndexPrev).suffixOperator.symbol);
							}
						}
						stringBuilder.append("(");
					}
					switch (valueCombination) {
					case MAX_VALUE_LEFT_MAX_VALUE_RIGHT:
						buildExpression(expression, startIndex, split, infoArray,true,stringBuilder);
						break;
					case MAX_VALUE_LEFT_MIN_VALUE_RIGHT:
						buildExpression(expression, startIndex, split, infoArray,true,stringBuilder);
						break;
					case MIN_VALUE_LEFT_MAX_VALUE_RIGHT:
						buildExpression(expression, startIndex, split, infoArray,false,stringBuilder);
						break;
					case MIN_VALUE_LEFT_MIN_VALUE_RIGHT:
						buildExpression(expression, startIndex, split, infoArray,false,stringBuilder);
						break;
					default:
						break;
					}
					if(split - startIndex > 0) {
						stringBuilder.append(")");
					}
					//Right part print
					if(endIndex - (split+1) > 0) {
						if(stringBuilder.charAt(stringBuilder.length()-1) != '(') {
							stringBuilder.append(expression.get(split).suffixOperator.symbol);
						}
						stringBuilder.append("(");
					}
					switch (valueCombination) {
					case MAX_VALUE_LEFT_MAX_VALUE_RIGHT:
						buildExpression(expression, split+1, endIndex, infoArray,true,stringBuilder);
						break;
					case MAX_VALUE_LEFT_MIN_VALUE_RIGHT:
						buildExpression(expression, split+1, endIndex, infoArray,false,stringBuilder);
						break;
					case MIN_VALUE_LEFT_MAX_VALUE_RIGHT:
						buildExpression(expression, split+1, endIndex, infoArray,true,stringBuilder);
						break;
					case MIN_VALUE_LEFT_MIN_VALUE_RIGHT:
						buildExpression(expression, split+1, endIndex, infoArray,false,stringBuilder);
						break;
					default:
						break;
					}
					if(endIndex - (split+1) > 0) {
						stringBuilder.append(")");
					}
				} else if(startIndex == endIndex) {
					if(stringBuilder.charAt(stringBuilder.length()-1) != '(') {
						int startIndexPrev = startIndex - 1;
						if(startIndexPrev != -1) {
							stringBuilder.append(expression.get(startIndexPrev).suffixOperator.symbol);
						}
					}
					stringBuilder.append(startExpr.operand);
				} else if(endIndex - startIndex == 1) {
					stringBuilder.append(startExpr);
					stringBuilder.append(endExpr.operand);
				}
			}
		}
	}
	
	
	private static DivProdInfo getDivProdInfo(int[] leftSubArrayMinMax,int [] rightSubArrayMinMax,OPERATOR operator) {
		int numberItr = 0;
		int minValue = Integer.MAX_VALUE;
		int maxvalue = Integer.MIN_VALUE;
		int result = 0;
		ValueCombination maxValueCombination = null;
		ValueCombination minValueCombination = null;
		for(int i = 0;i<leftSubArrayMinMax.length;i++) {
			for(int j = 0;i<rightSubArrayMinMax.length;i++) {
				result = invokeOperation(leftSubArrayMinMax[i], rightSubArrayMinMax[j], operator);
				if(result < minValue) {
					minValue = result;
					minValueCombination = values[numberItr];
				} else if(result > maxvalue){
					maxvalue = result;
					maxValueCombination = values[numberItr];
				}
				numberItr++;
			}
		}
		DivProdInfo divProdInfo = new DivProdInfo();
		divProdInfo.maxValue = maxvalue;
		divProdInfo.minValue = minValue;
		divProdInfo.maxCombination = maxValueCombination;
		divProdInfo.minCombination = minValueCombination;
		return divProdInfo;
	}
	
	private static class DivProdInfo {
		
		private int minValue = Integer.MAX_VALUE;
		private int maxValue = Integer.MIN_VALUE;
		
		private ValueCombination minCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
		private ValueCombination maxCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
		
	}
	
	private static enum ValueCombination {
		MIN_VALUE_LEFT_MIN_VALUE_RIGHT,
		MIN_VALUE_LEFT_MAX_VALUE_RIGHT,
		MAX_VALUE_LEFT_MIN_VALUE_RIGHT,
		MAX_VALUE_LEFT_MAX_VALUE_RIGHT;
		
	}
	
	public static void main(String[] args) {
		List<String> testCases = new ArrayList<String>();
		testCases.add("2*9+3-16");
		testCases.add("2*3+8-18/3+6");
		testCases.add("4*5+2/9-2*5+7");
		testCases.add("4*5+2/9-2*5+7+12+8*3/5");
		testCases.add("4*5+2/9-2*5+7+12+8*3/5+4*5+2");
		testCases.add("4*5+2/9-2*5+7+12+8*3/5+4*5+2+9-2*5+7");

        int i = 1;
        long start_time = new GregorianCalendar().getTimeInMillis();
		for (String testCase : testCases) {
			System.out.println("-------------------------Test Case " + i + "---------------------------------");
			calculateExpressionLimit(testCase);
			i++;
		}
		long end_time = new GregorianCalendar().getTimeInMillis();
		System.out.println("Time taken in milliseconds:" + (end_time - start_time));
		
//		calculateExpressionLimit("2*9-16");
	}
	
	private static class AtomicExpression {
		
		private OPERATOR suffixOperator = OPERATOR.NONE;
		private int operand;
		
		@Override
		public String toString() {
			return operand + Character.toString(suffixOperator.symbol);
		}
		
	}
	
	private static class Info {
		private int minValue = Integer.MAX_VALUE;
		private int maxValue = Integer.MIN_VALUE;
		private int minSplit = -1;
		private int maxSplit = -1;
		
		private ValueCombination minCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
		private ValueCombination maxCombination = ValueCombination.MIN_VALUE_LEFT_MIN_VALUE_RIGHT;
		@Override
		public String toString() {
			return "[min=" + minValue + ",max=" + maxValue
					+ /*",minS=" + minSplit + ",maxS=" + maxSplit+ */"]";
		}
		
		
	}
	
	private static enum OPERATOR {
		
		
		ADD('+'),
		SUB('-'),
		PROD('*'),
		DIV('/'),
		NONE(' ');
		
		private OPERATOR(Character symbol) {
			this.symbol = symbol;
		}
		
		private Character symbol;
		
		@Override
		public String toString() {
			return symbol + "";
		}
		
	}
	
	private static OPERATOR getOperatorFrom(Character character) {
		for (OPERATOR operator : OPERATOR.values()) {
			if(operator.symbol.equals(character))
				return operator;
		}
		return OPERATOR.NONE;
	}

	public static int invokeOperation(int operand1,int operand2,OPERATOR operator) {
		switch (operator) {
		case ADD:
			return operand1 + operand2;
		case SUB:
			return operand1 - operand2;
		case PROD:
			return operand1 * operand2;
		case DIV:
			return operand1/operand2;
		default:
			break;
		}
		return 0;
	}
}
