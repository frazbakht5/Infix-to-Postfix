import java.util.ArrayList;

public class ExpressionCalculator
{
	private String expression;
	private int numOfTerms; 
	private ArrayList<String> terms;


	public ExpressionCalculator(String expression)
	{
		this.expression = expression;
		this.numOfTerms = 0;
		terms = new ArrayList<>();
		this.convertToTerms();
		InfixToPostfix as = new InfixToPostfix();
		System.out.printf("%.2f", as.calculate(this.expression));
	}

	private void convertToTerms()
	{
		while(expression.contains("(") || expression.contains(")")) 
		{
			System.out.println("\nExpression = " + expression);
			InfixToPostfix as = new InfixToPostfix();
			int a = this.expression.lastIndexOf('(');
			StringBuilder copy = new StringBuilder(this.expression);

			int index = 0;
			for (int i = 0; i <= a; i++)
			{
				if(copy.charAt(i) == ')')
				{
					copy.setCharAt(i, '#');
				}
			}			

			int b = copy.indexOf(")");

			String temp1 = copy.substring(a, b) + ")";
			String temp2 = copy.substring(a+1, b);
			System.out.println("temp1 = " + temp1);
			Integer ans = Integer.parseInt(as.calculate(temp2));

			this.expression = this.expression.replaceAll(temp1, ans.toString());

			copy = new StringBuilder(this.expression);

			copy.deleteCharAt(a);
			copy.deleteCharAt(b-3);

			this.expression = copy.toString();
		}
	}
}
