import java.util.*;

public class InClass
{
	public static void main(String[] args)
	{
		int[] ns = new int[3];
		ns[0] = 42;
		ns[1] = 13;
		ns[2] = 99;
		
		System.out.println(Arrays.toString(ns));
		
		ns = new int[5];
		System.out.println(Arrays.toString(ns));
	}
}
