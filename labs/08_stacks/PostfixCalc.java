import java.util.*;

public class PostfixCalc
{
	public static void main(String[] args)
	{
		System.out.println("Welcome to the postfix calculator.");
		
		Scanner in = new Scanner(System.in);
		
		for(;;)  // this loops forever
		{
			System.out.print("Enter a postfix expression: ");
			String expr = in.nextLine();
			
			if(expr.length() == 0)
			{
				System.out.println("Good-bye.");
				return;
			}
			
			String[] tokens = expr.split(" ");
			
			try
			{
				int result = eval(tokens);
				System.out.println(result);
			}
			catch(NumberFormatException e)
			{
				System.out.println("Token is not a known symbol or number: " + e.getMessage());
			}
			catch(EmptyStackException e)
			{
				System.out.println("Too many operators; not enough numbers");
			}
			catch(LeftoverNumbersException e)
			{
				System.out.println("Too many numbers; leftovers = " + e.getMessage());
			}
		}
	}
	
	public static int eval(String[] tokens)
	  throws NumberFormatException, EmptyStackException, LeftoverNumbersException
	{
		StackInt<Integer> stack = new KWArrayListEnd<>();
		
		for(String token : tokens)
		{
			if(token.equals("+"))
			{
				int top = stack.pop();
				int next = stack.pop();

				stack.push(next + top);
			}
			else if(token.equals("-"))
			{
				int top = stack.pop();
				int next = stack.pop();

				stack.push(next - top);
			}
			else if(token.equals("*"))
			{
				int top = stack.pop();
				int next = stack.pop();

				stack.push(next * top);
			}
			else if(token.equals("/"))
			{
				int top = stack.pop();
				int next = stack.pop();

				stack.push(next / top);
			}
			else if(token.equals("%"))
			{
				int top = stack.pop();
				int next = stack.pop();

				stack.push(next % top);
			}
			else
			{
				int value = Integer.parseInt(token);
				stack.push(value);
			}
		}
		
		int result = stack.pop();
		
		if(!stack.empty())
		{
			throw new LeftoverNumbersException(stack.toString());
		}
		
		return result;
	}
}
