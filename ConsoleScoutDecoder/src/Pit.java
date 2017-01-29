import java.util.HashMap;
import java.util.Map;

public class Pit {
	private Map<String, Object> data = new HashMap<String,Object>();
	//final static public String[] keys = new String[] {"autoGear","autoStartPlace","autoHighShoot","autoLowShoot","autoHopper","gear","roboSpeed","highShoot","lowShoot","shootSpeed","shootAccuracy","climb","climbSpeed","capacity","endStrat","matchStrat","allianceRole","autoNotes","whereOnField"};
	final static public String[] keys = new String[] {"pitSize","pitQuality","pitOnFire"};
	//keys are all the pieces of info you gather when scouting, and sort by when analyzing
	//to add a new key, just put it in this array - no other changes necessary
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
				if(Match.isNumeric(strVal)) { //puts stuff in as the right type
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
	public static boolean isKey(String str) { //checks if a string is one of the keys
		for(String key : keys) {
			if(key.equals(str)) {
				return true;
			}
		}
		return false;
	}
}
