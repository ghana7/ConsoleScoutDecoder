import java.io.*;
import java.util.*;
public class Match {
	private Map<String, Object> data = new HashMap<String,Object>();
	final static public String[] keys = new String[] {"highGoals","quality","driverIsAnGirl"};
	public Match() {
		for(String k : keys) {
			data.put(k, null);
		}
	}
	public Match(String inputString) {
		Object[] values = inputString.split("}");
		if(values.length == keys.length) {
			for(int i = 0; i < keys.length; i++) {
				String strVal = (String)values[i];
				if(isNumeric(strVal)) { //puts shit in as the right type
					data.put(keys[i], Integer.parseInt(strVal));
				} else if(strVal.equals("true") || strVal.equals("false")) {
					data.put(keys[i], Boolean.parseBoolean(strVal));
				} else {
					data.put(keys[i], strVal);
				}
			}
		} else {
			//failure
		}
	}
	public Object getValue(String key) {
		return data.get(key);
	}
	public String toString() {
		String output = "";
		for(String s : keys) {
			output += (s + ": ");
			output += (data.get(s) + " ");
		}
		return output;
	}
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	public static boolean isKey(String str) {
		for(String key : keys) {
			if(key.equals(str)) {
				return true;
			}
		}
		return false;
	}
}