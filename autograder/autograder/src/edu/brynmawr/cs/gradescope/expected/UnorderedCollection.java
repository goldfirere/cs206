package edu.brynmawr.cs.gradescope.expected;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class UnorderedCollection implements Expected
{
	private List<Expected> expecteds;
	private String extra;
	
	public UnorderedCollection(Expected... exps)
	{
		expecteds = new ArrayList<>(Arrays.asList(exps));
	}
	
	@Override
	public D<Double> matches(Object other) throws BorkedException
	{
		List<Object> acts;
		
		// handle only arrays for now.
		if(other instanceof Object[])
		{
			acts = new ArrayList<>(Arrays.asList((Object[])other));
		}
		else if(other instanceof ArrayList<?>)
		{
			acts = new ArrayList<>((ArrayList<?>)other);
		}
		else
		{
			return D.of(0.0);
		}
		
		List<Expected> exps = new ArrayList<>(expecteds);
		
		double total = 0;
		double maxTotal = exps.size();
		
		for(Iterator<Expected> i = exps.iterator(); i.hasNext(); )
		{
			Expected exp = i.next();
			
			for(Iterator<Object> j = acts.iterator(); j.hasNext(); )
			{
				Object act = j.next();
				
				D<Double> doesMatch = exp.matches(act);
				if(doesMatch.isValid())
				{
					if(doesMatch.get() > 0)
					{
						i.remove();
						j.remove();
						
						total += doesMatch.get();
						
						break;
					}
				}
				else
				{
					return D.err(doesMatch.getError());
				}
			}
		}
		
		if(total < maxTotal)
		{
			extra = "Unmatched expected values: " + exps + "\n" + "Unmatched actual values: " + acts + "\n";
		}
		else
		{
			extra = "";
		}
		
		return D.of(total / maxTotal);
	}
	
	@Override
	public String toString()
	{
		return "Some reordering of: " + expecteds.toString();
	}
	
	@Override
	public String getExtra()
	{
		return extra;
	}
}
