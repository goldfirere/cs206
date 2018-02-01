package edu.brynmawr.cs.gradescope.run;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.json.*;

import edu.brynmawr.cs.gradescope.util.*;

public class JSONArrayConcatCollector implements Collector<JSONArray, JSONArray, JSONArray>
{
	@Override
	public Supplier<JSONArray> supplier()
	{
		return () -> new JSONArray();
	}

	@Override
	public BiConsumer<JSONArray, JSONArray> accumulator()
	{
		return Util.ignoreResult(combiner());
	}

	@Override
	public BinaryOperator<JSONArray> combiner()
	{
		return (arr1, arr2) -> {
			for(Object obj : arr2)
			{
				arr1.put(obj);
			}
			return arr1;
		};
	}

	@Override
	public Function<JSONArray, JSONArray> finisher()
	{
		return (arr) -> arr;
	}

	@Override
	public Set<Characteristics> characteristics()
	{
		Set<Characteristics> result = new HashSet<>();
		result.add(Characteristics.IDENTITY_FINISH);
		return Collections.unmodifiableSet(result);
	}
}
