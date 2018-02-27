package edu.brynmawr.cs.gradescope.expected;

import java.util.*;

import edu.brynmawr.cs.gradescope.java.*;

public class ColonSeparatedString implements Expected
{
	private JavaClass<Object> klass;
	private Object[] fields;
	
	public ColonSeparatedString(JavaClass<Object> k, Object... f)
	{
		klass = k;
		fields = f;
	}
	
	@Override
	public D<Double> matches(Object other) throws BorkedException
	{
		if(klass.isValid())
		{
			if(!klass.get().equals(other.getClass()))
			{
				return D.of(0.0);
			}
			
			String[] actFields = other.toString().split(":");
			
			if(fields.length != actFields.length)
			{
				return D.of(0.0);
			}
			
			for(int i = 0; i < fields.length; i++)
			{
				boolean oneMatch;
				try
				{
					if(fields[i] instanceof Integer)
					{
						oneMatch = fields[i].equals(Integer.parseInt(actFields[i]));
					}
					else if(fields[i] instanceof Short)
					{
						oneMatch = fields[i].equals(Short.parseShort(actFields[i]));
					}
					else if(fields[i] instanceof Byte)
					{
						oneMatch = fields[i].equals(Byte.parseByte(actFields[i]));
					}
					else if(fields[i] instanceof Long)
					{
						oneMatch = fields[i].equals(Long.parseLong(actFields[i]));
					}
					else if(fields[i] instanceof Float)
					{
						oneMatch = fields[i].equals(Float.parseFloat(actFields[i]));
					}
					else if(fields[i] instanceof Double)
					{
						oneMatch = fields[i].equals(Double.parseDouble(actFields[i]));
					}
					else if(fields[i] instanceof Boolean)
					{
						oneMatch = fields[i].toString().equals(actFields[i]);
					}
					else if(fields[i] instanceof Character)
					{
						oneMatch = fields[i].toString().equals(actFields[i]);
					}
					else if(fields[i] instanceof String)
					{
						oneMatch = fields[i].equals(actFields[i]);
					}
					else
					{
						throw new BorkedException("ColonSeparatedString contains exotic type at position " + i + ": " + fields[i]);
					}
				}
				catch (NumberFormatException e)
				{
					oneMatch = false;
				}
				
				if(!oneMatch)
				{
					return D.of(0.0);
				}
			}
			
			return D.of(1.0);
		}
		else
		{
			return D.err(klass.getError());
		}
	}
	
	@Override
	public String toString()
	{
		return String.join(":", Arrays.stream(fields).map(Object::toString).toArray(String[]::new));
	}
}
