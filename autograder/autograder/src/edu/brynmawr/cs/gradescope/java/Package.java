package edu.brynmawr.cs.gradescope.run;

import java.util.*;

/** Represents a Java package
 * 
 * @author Richard Eisenberg
 */
public final class Package
{
	private final List<String> components;
	
	/** Create a package from a list of component strings
	 * @param cs The component strings of the package name.
	 */
	public Package(List<String> cs)
	{
		components = Collections.unmodifiableList(cs);
	}
	
	/**
	 * Creates the default package
	 */
	public Package()
	{
		components = Collections.emptyList();
	}
	
	@Override
	public String toString()
	{
		if(components.isEmpty())
		{
			return "the default package";
		}
		else
		{
			return "package " + getPackage();
		}
	}

	/** Returns the number of components in this package name
	 * @return The number of package components
	 */
	public int getNumComponents()
	{
		return components.size();
	}
	
	/** Get a rendering of the package name, as it would appear in Java code
	 * @return The package name
	 */
	public String getPackage()
	{
		return String.join(".", components);
	}
}
