import java.util.*;

public class Team {
	private int number;
	private ArrayList<Match> matches;
	private ArrayList<Pit> pits;
	public Team(int _number) {
		number = _number;
		matches = new ArrayList<Match>();
		pits = new ArrayList<Pit>();
	}
	public int getNumber() {
		return number;
	}
	public void addMatch(String matchString) {
		matches.add(new Match(matchString));
	}
	public void addPit(String pitString) {
		pits.add(new Pit(pitString));
	}
	public ArrayList<Object> getDataArray(String key) { //gets an arraylist of all pieces of info for key
		ArrayList<Object> output = new ArrayList<Object>();
		for(Match m : matches) {
			output.add(m.getValue(key));
		}
		return(output);
	}
	public Object getAverage(String key) { //gets average of all info for key
		if(matches.size() != 0) {
			ArrayList<Object> allData = getDataArray(key);
			Object typeTester = allData.get(0);
			if(typeTester instanceof Integer) { //if int, returns mean
				int sum = 0;
				int count = 0;
				for(Object o : allData) {
					sum += (int)o;
					count++;
				}
				return (sum/count);
			} else if(typeTester instanceof Boolean) { //if boolean, returns most common of true/false
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
				return (String)typeTester; //if string, returns first (change to something more useful eventually)
			}
		} else {
			return null;
		}
	}
	public String toString() {
		String output = "Team " + number + "\n"; 
		for(Match m : matches) {
			output += ("\tMatch - " + m + "\n"); //prints all matches, indented
		}
		String avgString = "";
		for(String key : Match.keys) {
			avgString += getAverage(key); //creates a fake match to serve as the average
			avgString += "}";
		}
		avgString = avgString.substring(0, avgString.length() - 1); //takes off hanging }
		output += ("\tAverage - " + new Match(avgString) + "\n"); //prints average, indented
		for(Pit p : pits) {
			output += "\tPit - " + p + "\n"; //prints all pits, indented
		}
		return output;
	}
	public boolean equals(Team t) {
		return number == t.number;
	}
	
	public String toData() { //exports the data of the 
		String temp = "";
		
		if(pits.size() > 0) {
			temp += pits.get(0).getInput();
		}
		
		for(Match match : matches) {
			temp += "\n" + match.getInput();
		}
		return temp + "\n";
	}
}
