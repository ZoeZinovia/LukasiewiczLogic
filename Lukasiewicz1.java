

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import edu.princeton.cs.algs4.StdIn;
import pt.ualg.fct.aed.Stack;
import pt.ualg.fct.aed.Strings;
import pt.ualg.fct.aed.HashTable;
import edu.princeton.cs.algs4.StdOut;
import pt.ualg.fct.aed.Arrays;
import pt.ualg.fct.aed.Bag;

import java.lang.Math;
import java.lang.Double;

public class Lukasiewicz1 {
	
 // General
	
	public static final char SPACE = ' ';
	public static final char PERIOD = '.';
	public static final char RPAREN = ')';
	public static final char LPAREN = '(';
	
	public static String[] tokens(String s)
	{
		Bag<String> result = new Bag<>();
		int i = 0;
		while(i < s.length())
		{
			char c = s.charAt(i);
			if(c == SPACE)
				i++;
			else 
			{
				String token;
				if(c == LPAREN || c == RPAREN)
					token = Strings.takeFrom(s, i, 1);
				else if(Character.isLetterOrDigit(c))
					token = Strings.takeWhileFrom(s, i, x -> Character.isLetterOrDigit(x)||x == PERIOD);
				else
					token = Strings.takeFrom(s, i, 1);
				result.add(token);
				i += token.length();
			}
		}
		return result.toArray();
	}
	
	public static double factorial(double x)
	{
		if(x == 0)
			return 1;
		else
			return(x*factorial(x-1));
	}
	
	private static HashTable<String, DoubleBinaryOperator> binaryOp = initBinaryOperators();
	private static HashTable<String, DoubleUnaryOperator> unaryOp = initUnaryOperators();
	
	private static HashTable<String, DoubleBinaryOperator> initBinaryOperators()
	{
		HashTable<String, DoubleBinaryOperator> result = new HashTable<>();
		result = new HashTable<>();
		result.put("+", (x, y) -> x + y);
		result.put("-", (x, y) -> x - y);
		result.put("*", (x, y) -> x*y);
		result.put("/", (x, y) -> x/y);	
		result.put("^", (x, y) -> Math.pow(x, y));
		result.put("min", (x, y) -> Math.min(x, y));
		result.put("max", (x, y) -> Math.max(x, y));
		return result;
	}
	
	private static HashTable<String, DoubleUnaryOperator> initUnaryOperators()
	{
		HashTable<String, DoubleUnaryOperator> result = new HashTable<>();
		result = new HashTable<>();
		result.put("~", (x) -> -x);
		result.put("!", (x) -> factorial(x));
		result.put("sq", (x) -> x*x);
		result.put("sqrt", (x) -> Math.sqrt(x));
		result.put("inv", (x) -> 1/x);
		return result;
	}
	
	private static boolean isOperatorRPN(String s)
	{
		return isBinaryOperatorRPN(s) || isUnaryOperatorRPN(s);
	}
	
	private static boolean isBinaryOperatorRPN(String s)
	{
		return binaryOp.has(s);
	}
	
	private static boolean isUnaryOperatorRPN(String s)
	{
		return unaryOp.has(s);
	}
	
	private static boolean isNumericRPN(String s)
	{
		return Character.isDigit(s.charAt(0));
	}
	
// Problem A
	
	private static String[] binaryOperators = {"A", "K", "I", "E", "D"};
	private static String[] unaryOperators = {"N"};
	
	private static boolean isOperator(String s)
	{
		return isBinaryOperator(s) || isUnaryOperator(s);
	}
	
	private static boolean isBinaryOperator(String s)
	{
		return Arrays.has(binaryOperators, s);
	}
	
	private static boolean isUnaryOperator(String s)
	{
		return Arrays.has(unaryOperators, s);
	}
	
	private static boolean isZero(String s)
	{
		return "O".equals(s);
	}
	
	public static boolean evalExpression(String[] expression)
	{
		Stack<Boolean> bools = new Stack<Boolean>();
		for(int i = expression.length-1; i >= 0; i--)
		{
			if (isOperator(expression[i]))
			{
				String op = (expression[i]);
				boolean z = evalOperator(op, bools);
				bools.push(z);
			}	
			
			else if(isZero(expression[i]))
				bools.push(false);
			
			else throw new UnsupportedOperationException();
		}
		return bools.pop();
	}
	
	private static boolean evalOperator(String op, Stack<Boolean> bools)
	{
		boolean result = false;
		if(isUnaryOperator(op))
			result = evalUnaryOperator(op, bools);
		else if(isBinaryOperator(op))
			result = evalBinaryOperator(op, bools);
		else
			assert false;
		return result;
	}
	
	private static boolean evalUnaryOperator(String op, Stack<Boolean> bools)
	{
		boolean result = false;
		boolean x = bools.pop();
		if("N".equals(op))
			result = !x;
		else
			assert false;
		return result;
	}
	
	private static boolean evalBinaryOperator(String op, Stack<Boolean> bools)
	{
		boolean result = false;
		boolean x = bools.pop();
		boolean y = bools.pop();
		if("A".equals(op))
			result = x||y;
		else if("K".equals(op))
			result = x && y;
		else if ("I".equals(op))
			result = (!x) || y;
		else if ("E".equals(op))
			result = (x == y);
		else if ("D".equals(op))
			result = !(x&&y);
		else
			assert false;
		return result;
	}
	
	public static boolean polish(String s)
	{
		String[] a = s.split("");
		boolean result = evalExpression(a);
		return result;
	};
	
	public static void testPolish()
	{
		while(!StdIn.isEmpty())
		{
			String s = StdIn.readLine();
			boolean bool = polish(s);
			StdOut.println(bool);
		}
	};
	
	// Problem B
	
	private static double evalUnaryOperatorRPN(String op, Stack<Double> values)
	{
		double x = values.pop();
		DoubleUnaryOperator uop = unaryOp.get(op);
		double result = uop.applyAsDouble(x);
		return result;
	}	
	
	private static double evalBinaryOperatorRPN(String op, Stack<Double> values)
	{
		double y = values.pop();
		double x = values.pop();
		DoubleBinaryOperator bop = binaryOp.get(op);
		double result = bop.applyAsDouble(x, y);
		return result;
	}
	
	private static double evalOperatorRPN(String op, Stack<Double> values)
	{
		double result = 0.0;
		if(isUnaryOperatorRPN(op))
			result = evalUnaryOperatorRPN(op, values);
		else if(isBinaryOperatorRPN(op))
			result = evalBinaryOperatorRPN(op, values);
		else
			assert false;
		return result;
	}
	
	public static double evalExpressionRPN(String[] expression)
	{ 
		Stack<Double> values = new Stack<Double>();
		for(int i = 0; i < expression.length; i++)
		{
			if (isOperatorRPN(expression[i]))
			{
				double z = evalOperatorRPN(expression[i], values);
				values.push(z);
			}
			else if(isNumericRPN(expression[i]))
				values.push(Double.parseDouble(expression[i]));
			else throw new UnsupportedOperationException();
		}
		return values.pop();
	}
	
	public static double evaluateRPN(String s)
	{
		String[] a = tokens(s);
		double result = evalExpressionRPN(a);
		return result;
	}
	
	
	public static void testEvaluateRPN()
	{
		while(!StdIn.isEmpty())
		{
			String s = StdIn.readLine();
			double z = evaluateRPN(s);
			StdOut.println(z);
		}
	}
	
	//Problem C
	
	private static boolean isNumericInfix(String s)
	{
		return Character.isDigit(s.charAt(0));
	}
	
	public static void RParen(Stack<String> operatorStack, Bag<String> bagInfixToRPN)
	{
		{
			String op = operatorStack.pop();
			bagInfixToRPN.add(op);		
		}
	}
	
	public static String[] infixFullParenToRPN(String[] expression)
	{
		Stack<String> operatorStack = new Stack<String>();
		Bag<String> bagInfixToRPN = new Bag<String>();
		for(int i = 0; i < expression.length; i++)
		{
			if("(".equals(expression[i]))
				{}
			else if(isOperatorRPN(expression[i]))
				operatorStack.push(expression[i]);
			else if(isNumericInfix(expression[i]))
				bagInfixToRPN.add(expression[i]);
			else if(")".equals(expression[i]))
				RParen(operatorStack, bagInfixToRPN);
			else
				throw new UnsupportedOperationException();
		}
		return bagInfixToRPN.toArray(String.class);
	}
	
	public static void testInfixFullParenToRPN()
	{

		while(StdIn.hasNextLine())
		{
			String s = StdIn.readLine();
			String[] tokens = tokens(s); 
			String[] z = infixFullParenToRPN(tokens);
			String result = Arrays.mkString(z);
			StdOut.println(result);
		}
	}
	
	//Problem D
	
	private static HashTable<String, Integer> operatorsPriority = initOperatorsPriority();
	
	private static HashTable<String, Integer> initOperatorsPriority()
	{
		HashTable<String, Integer> result = new HashTable<>();
		
		result = new HashTable<>();
		result.put("+", 1);
		result.put("-", 1);
		result.put("*", 2);
		result.put("/", 2);
		result.put("^", 3);
		result.put("min", 4);
		result.put("max", 4);
		result.put("sqrt", 5);
		result.put("~", 5);
		result.put("!", 5);
		result.put("sq", 5);
		result.put("inv", 5);
		
		return result;
	}
	
	private static boolean isOperatorInfixD(String s)
	{
		return operatorsPriority.has(s);
	}
	
	public static boolean comparePrecidence(String a, String b)
	{
		return operatorsPriority.get(a) >= operatorsPriority.get(b);
	}
	
	public static void RparenD(Stack<String> operatorStack, Bag<String> bagInfixToRPN)
	{
		while(!operatorStack.top().equals("(") && !operatorStack.isEmpty())
		{
			String op = operatorStack.pop();
			bagInfixToRPN.add(op);	
		}
		if(!operatorStack.isEmpty()) //if stack is not empty
			operatorStack.pop(); //pop the opening parenthesis
	}	
	
	public static void operatorInfixD(Stack<String> operatorStack, Bag<String> bagInfixToRPN, String token)
	{
		while(!operatorStack.isEmpty() && !operatorStack.top().equals("(") && comparePrecidence(operatorStack.top(), token))
		{
			String op = operatorStack.pop();
			bagInfixToRPN.add(op);
		}
		operatorStack.push(token);
	}
	
	public static void popRestOfStack(Stack<String> operatorStack, Bag<String> bagInfixToRPN)
	{
		while(!operatorStack.isEmpty())
		{
			String op = operatorStack.pop();
			bagInfixToRPN.add(op);	
		}
	}
	
	public static String[] infixToRPN(String[] expression)
	{
		Stack<String> operatorStack = new Stack<String>();
		Bag<String> bagInfixToRPN = new Bag<String>();
		for(int i = 0; i < expression.length; i++)
		{
			if("(".equals(expression[i]))
				operatorStack.push(expression[i]);
			else if(isOperatorInfixD(expression[i]) && !(operatorsPriority.get(expression[i]) == 5))			
				operatorInfixD(operatorStack, bagInfixToRPN, expression[i]);				
			else if(isOperatorInfixD(expression[i]) && (operatorsPriority.get(expression[i]) == 5))			
				operatorStack.push(expression[i]);	
			else if(isNumericInfix(expression[i]))
				bagInfixToRPN.add(expression[i]);
			else if(")".equals(expression[i]))
				RparenD(operatorStack, bagInfixToRPN);
			else
				throw new UnsupportedOperationException();
		}
		popRestOfStack(operatorStack, bagInfixToRPN);
		return bagInfixToRPN.toArray();
	}
	
	public static void testInfixToRPN()
	{
		while(StdIn.hasNextLine())
		{
			String s = StdIn.readLine();
			String[] tokens = tokens(s); 
			String[] z = infixToRPN(tokens);
			String result = Arrays.mkString(z);
			StdOut.println(result);
		}
	}
	
	//Problem E
	
	public static double evaluateInfix(String expression) //how to use as non-static?
	{
		String[] tokens = tokens(expression); 
		String[] infix = infixToRPN(tokens);
		return evalExpressionRPN(infix);
	}
	
	public static void testEvaluateInfix()
	{
		while(!StdIn.isEmpty())
		{
			String s = StdIn.readLine();
			double z = evaluateInfix(s);
			StdOut.println(z);
		}
	}
	
	public static void main(String[] args)
	{
		char choice = 'B';
		if (args.length == 1)
			choice = args[0].charAt(0);
		else if (args.length > 1)
		{
			choice = args[0].charAt(0);
		}
		if (choice == 'A')
			testPolish();
		else if (choice == 'B')
			testEvaluateRPN();
		else if (choice == 'C')
			testInfixFullParenToRPN();
		else if (choice == 'D')
			testInfixToRPN();
		else if (choice == 'E')
			testEvaluateInfix();
		else 
			StdOut.println("Illegal option: " + choice);
	};
}

//public static String[] bagToString(Bag<String> a)
//{
//	String[] s = new String[a.size()];
//	int i = 0;
//	for(String x : a)
//	{
//		s[i++] = x;
//	}
//	return s;
//}

//public static void printRPN(String[] a)
//{
//	StdOut.printf("%s ", a[0]);
//	for(int k = 1; k < a.length-1; k++)
//	{
//		StdOut.printf("%s ", a[k]);
//	}
//	StdOut.printf("%s\n", a[a.length-1]);
//}
