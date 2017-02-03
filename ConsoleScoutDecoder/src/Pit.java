import java.util.HashMap;
import java.util.Map;

public class Pit {
	private String inputStr;
	private Map<String, Object> data = new HashMap<String,Object>();
	final static public String[] keys = new String[] {"autoGearPit","autoHighPit","autoLowPit","autoHopperPit","autoStartPit","gearPit","highGoalPit",
													  "lowGoalPit","driveSpeedPit","shootSpeedPit","shootAccuracyPit","climbPit","climbSpeedPit",
													  "endStrategyPit","strategyPit","idealAlly","ballCapacityPit","fieldUsagePit","scouterName",
													  "notesPit","intakePit"};
	//keys are all the pieces of info you gather when scouting, and sort by when analyzing
	//to add a new key, just put it in this array - no other changes necessary
	public Pit() {
		for(String k : keys) {
			data.put(k, null);  
		}
	}
	public Pit(String inputString) {
		inputStr = inputString;
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
			//failure
		}
	}
	
	public String getInput() {
		return inputStr;
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
