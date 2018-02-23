package edu.brynmawr.cs.gradescope.test;

import java.text.*;

public class TestPartialSuccess extends TestResult
{
	private String description;
	
	public TestPartialSuccess(double p, String doc)
	{
		super(p);
		
		description = doc;
	}

	@Override
	public String render()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		if(getPercentage() < 1)
		{
			return "Test partially succeeded (" + df.format(getPercentage() * 100) + "%):\n" + description;
		}
		else
		{
			return "Test succeeded!\n\n" + description;
		}
	}
	
}
