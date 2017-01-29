import java.util.HashMap;
import java.util.Map;

public class Pit {
	private Map<String, Object> data = new HashMap<String,Object>();
	final static public String[] keys = new String[] {"autoGear","autoStartPlace","autoHighShoot","autoLowShoot","autoHopper","gear","roboSpeed","highShoot","lowShoot","shootSpeed","shootAccuracy","climb","climbSpeed","capacity","endStrat","matchStrat","allianceRole","autoNotes","whereOnField"};					
	public Pit() {
		for(String k : keys) {
			data.put(k, null);
		}
	}
	public Pit(String inputString) {
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
			System.out.println("ERROR");
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
}
