import java.util.*;

public class Team {
	private int number;
	private ArrayList<Match> matches;
	public Team(int _number) {
		number = _number;
		matches = new ArrayList<Match>();
	}
	public int getNumber() {
		return number;
	}
	public void addMatch(String matchString) {
		matches.add(new Match(matchString));
	}
	public ArrayList<Object> getDataArray(String key) {
		ArrayList<Object> output = new ArrayList<Object>();
		for(Match m : matches) {
			output.add(m.getValue(key));
		}
		return(output);
	}
	public Object getAverage(String key) {
		if(matches.size() != 0) {
			ArrayList<Object> allData = getDataArray(key);
			Object typeTester = allData.get(0);
			if(typeTester instanceof Integer) {
				int sum = 0;
				int count = 0;
				for(Object o : allData) {
					sum += (int)o;
					count++;
				}
				return (sum/count);
			} else if(typeTester instanceof Boolean) {
				int trueCount = 0;
				int falseCount = 0;
				for(Object o : allData) {
					boolean b = (boolean)o;
					if(b) {
						trueCount++;
					} else {
						falseCount++;
					}
				}
				if(trueCount >= falseCount) {
					return true;
				} else {
					return false;
				}
			} else {
				return (String)typeTester;
			}
		} else {
			return null;
		}
	}
	public String toString() {
		String output = "Team " + number + "\n";
		for(Match m : matches) {
			output += ("\tMatch - " + m + "\n");
		}
		String avgString = "";
		for(String key : Match.keys) {
			avgString += getAverage(key);
			avgString += "}";
		}
		avgString = avgString.substring(0, avgString.length() - 1);
		output += ("\tAverage - " + new Match(avgString) + "\n");
		return output;
	}
	public boolean equals(Team t) {
		return number == t.number;
	}
}
