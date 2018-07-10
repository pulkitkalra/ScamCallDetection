package entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DFMapping {

	private static final Map<Integer, String> myMap;
	static {
		Map<Integer, String> aMap = new HashMap<>();
		aMap.put(1, "one");
		aMap.put(2, "two");
		myMap = Collections.unmodifiableMap(aMap);
	}

}
