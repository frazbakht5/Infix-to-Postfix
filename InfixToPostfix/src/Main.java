
public class Main
{

	public static void main(String[] args)
	{
		InfixToPostfix as = new InfixToPostfix();

		String prefix = "1-(2+3+(4-5*6)*7)+8*9";

		System.out.println(as.calculate(prefix));
	}

}
