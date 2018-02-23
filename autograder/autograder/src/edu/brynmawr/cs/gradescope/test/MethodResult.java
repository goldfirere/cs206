package edu.brynmawr.cs.gradescope.test;

import edu.brynmawr.cs.gradescope.java.*;

public interface MethodResult
{
	// The doc should be something like "calling method foo(bar)\n"
	TestResult checkResult(MethodResult other, String doc) throws BorkedException;
	
	String render();
	
	D<Object> exceptionToD(String doc) throws BorkedException;
}