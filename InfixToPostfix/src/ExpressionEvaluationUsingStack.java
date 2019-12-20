import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class ExpressionEvaluationUsingStack
{
	private String[] postfix = new String[200];
	private int postfixTokens = 0;
	private int numberOfTokens = 0;
	private String[] tokens = new String[200];

	private boolean isDigit(char c)
	{
		if(c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5'|| c=='6' || c=='7' || c=='8' || c=='9')
			return true;
		else 
			return false;
	}

	private void stringToTokens(String prefix)
	{

		int j = 0;
		boolean putBracket = false;

		for (int i = 0; i < prefix.length(); i++)
		{
			char currentChar = prefix.charAt(i);

			if(currentChar == ' ')
				continue;

			if(!isDigit(currentChar))
			{
				if(currentChar == '(' || currentChar == ')') 
				{
					tokens[j] = Character.toString(currentChar);
					j++;
					numberOfTokens++;
				}

				else if(!isDigit(tokens[j-1].charAt(0)) && ( tokens[j-1].charAt(0) != '(' && tokens[j-1].charAt(0) != ')'  ) )
				{
					tokens[j] = "(";
					j++;
					numberOfTokens++;

					tokens[j] = "0";
					j++;
					numberOfTokens++;

					tokens[j] = Character.toString(currentChar);
					j++;
					numberOfTokens++;

					putBracket = true;
				}
				else 
				{
					tokens[j] = Character.toString(currentChar);
					j++;
					numberOfTokens++;
				}
			}
			else
			{
				String num = "";
				while(isDigit(prefix.charAt(i)))
				{
					num = num + "" + prefix.charAt(i);
					i++;
					if(i == prefix.length())
						break;
				}

				tokens[j] = num;
				i--;
				j++;
				numberOfTokens++;

				if(putBracket)
				{
					tokens[j] = ")";
					j++;
					numberOfTokens++;
					putBracket = false;
				}	
			}
		}
	}

	private int precedence(String s)
	{
		int precedence = 0;

		if (s.equals("/"))
			precedence = 4;

		else if (s.equals("*"))
			precedence = 4;

		else if (s.equals("+"))
			precedence = 2;

		else if (s.equals("-"))
			precedence = 2;

		return precedence;

	}

	private void convertToPostfix(String prefix)
	{
		Stack<String> stack = new Stack<>();
		int j = 0;

		for (int i = 0; i < numberOfTokens; i++)
		{			
			boolean isOperand = true;

			if (!tokens.equals(null)) 
				if (tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/") || tokens[i].equals("(") || tokens[i].equals(")"))
					isOperand = false;

			if (isOperand)	
			{
				postfix[j] = tokens[i];
				j++;
			}

			else if(tokens[i].equals("("))
			{
				stack.push(tokens[i]);
			}
			else if(tokens[i].equals(")"))
			{
				while(!stack.peek().equals("("))
				{
					postfix[j] = stack.pop();
					j++;
				}
				stack.pop();
			}
			else	
			{
				if (stack.empty())
				{
					stack.push(tokens[i]);
				} 
				else
				{
					if (precedence(tokens[i]) > precedence(stack.peek())) 
					{
						stack.push(tokens[i]);
					}
					else
					{
						while (precedence(stack.peek()) >= precedence(tokens[i]))
						{
							postfix[j] = stack.pop();
							j++;
							if (stack.empty())
								break;
						}

						stack.push(tokens[i]);
					}
				}
			}
		}

		while (stack.empty() == false)
		{
			postfix[j] = stack.pop();
			j++;
		}

		this.postfixTokens = j;
	}

	public String calculate(String prefix)
	{
		this.stringToTokens(prefix);
		this.convertToPostfix(prefix);

		String[] postfixToCalculate = this.postfix;
		Stack<Double> stack = new Stack<>();
		Double result = 0.0;

		for (int i = 0; i < postfixTokens; i++)
		{
			String currentTtoken = postfix[i];
			boolean isOperand = true;

			if (currentTtoken.equals("+") || currentTtoken.equals("-") || currentTtoken.equals("*") || currentTtoken.equals("/"))
				isOperand = false;

			if (isOperand) 
				stack.push(Double.parseDouble(currentTtoken));

			else
			{
				Double second = stack.pop();
				Double first = stack.pop();
				result = evaluate(first, second, postfixToCalculate[i]);

				stack.push(result);
			}
		}

		result = Math.round(result * 100D) / 100D;
		return prefix + "=" + result.toString();
	}

	private Double evaluate(Double first, Double second, String operator)
	{
		if (operator.equals("+"))
		{
			return first + second;
		}

		if (operator.equals("-"))
		{
			return first - second;
		}

		if (operator.equals("*"))
		{
			return first * second;
		}

		if (operator.equals("/"))
		{
			return first / second;
		}
		return 0.0;
	}

	public static void main(String[] args)
	{
		ExpressionEvaluationUsingStack as = new ExpressionEvaluationUsingStack();

		String fname = "input.txt";
		Scanner s = null;
		PrintWriter out = null;
		try
		{
			out = new PrintWriter("project1_output.txt");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		try 
		{	
			s = new Scanner(new BufferedReader(new FileReader(fname)));
			String line = null;

			while (s.hasNextLine()) 
			{
				String prefix = s.nextLine();
				String output = as.calculate(prefix);
				out.println(output);
				as = new ExpressionEvaluationUsingStack();
			}
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
			return;
		} finally {
			if (s != null) s.close();
			out.close();
		}

		System.out.println("Complete!");
		System.exit(0);
	}
}

