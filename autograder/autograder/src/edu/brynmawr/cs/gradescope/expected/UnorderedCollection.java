package edu.brynmawr.cs.gradescope.expected;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class UnorderedCollection implements Expected
{
	private List<Expected> expecteds;
	
	public UnorderedCollection(Expected... exps)
	{
		expecteds = new ArrayList<>(Arrays.asList(exps));
	}
	
	@Override
	public D<Double> matches(Object other)
	{
		// handle only arrays for now.
		if(other instanceof Object[])
		{
			Object[] array = (Object[])other;
			
			List<Object> acts = new ArrayList<>(Arrays.asList(array));
			List<Expected> exps = new ArrayList<>(expecteds);
			
			double total = 0;
			
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
			
			return D.of(total);
		}
		else
		{
			return D.of(0.0);
		}
	}
	
	@Override
	public String toString()
	{
		return expecteds.toString();
	}
}
