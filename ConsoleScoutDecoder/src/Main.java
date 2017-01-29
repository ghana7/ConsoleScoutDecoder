import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Team> teams = new ArrayList<Team>();
		Scanner sc = new Scanner(System.in);
		while(true) {
			String input = sc.nextLine();
			String[] inputArray = input.split(" ");
			String teamToAdd;
			switch(inputArray[0]) { //inputArray[0] is the command itself
			case "addTeam": 
				String teamNumber = inputArray[1];
				if(Match.isNumeric(teamNumber)) {
					if(teamExists(Integer.parseInt(teamNumber), teams) == -1) {
						teams.add(new Team(Integer.parseInt(teamNumber)));
						System.out.println("Added team " + teamNumber);
					} else {
						System.out.println("Team " + teamNumber + " already exists!");
					}
				} else {
					System.out.println(teamNumber + " is not a number. That argument must be a number.");
				}
				break;
			case "addMatch":
				teamToAdd = inputArray[1];
				String matchString = inputArray[2];
				if(Match.isNumeric(teamToAdd)) {
					if(teamExists(Integer.parseInt(teamToAdd), teams) != -1) {
						if(isLegalMatch(matchString)) {
							teams.get(teamExists(Integer.parseInt(teamToAdd), teams)).addMatch(matchString);
							System.out.println("Added match " + matchString);
						} else {
							System.out.println("This is not a legal match format.");
						}
					} else {
						System.out.println("Team " + teamToAdd + " does not exist!");
					}
				} else {
					System.out.println(teamToAdd + " is not a number. That argument must be a number.");
				}
				break;
			case "addPit":
				teamToAdd = inputArray[1];
				String pitString = inputArray[2];
				if(Match.isNumeric(teamToAdd)) {
					if(teamExists(Integer.parseInt(teamToAdd), teams) != -1) {
						if(isLegalMatch(pitString)) {
							teams.get(teamExists(Integer.parseInt(teamToAdd), teams)).addMatch(pitString);
							System.out.println("Added pit " + pitString);
						} else {
							System.out.println("This is not a legal pit format.");
						}
					} else {
						System.out.println("Team " + teamToAdd + " does not exist!");
					}
				} else {
					System.out.println(teamToAdd + " is not a number. That argument must be a number.");
				}
				break;
			case "printTeam":
				String teamNumberToPrint = inputArray[1];
				if(Match.isNumeric(teamNumberToPrint)) {
					int teamNumberAsInt = Integer.parseInt(teamNumberToPrint);
					if(teamExists(teamNumberAsInt, teams) != -1) {
						System.out.println(teams.get(teamExists(Integer.parseInt(teamNumberToPrint),teams)));
					} else {
						System.out.println("Team " + teamNumberAsInt + " does not exist.");
					}
					
				} else {
					System.out.println(teamNumberToPrint + " is not a number. That argument must be a number.");
				}
				break;
			case "sort":
				String keyToSort = inputArray[1];
				if(Match.isKey(keyToSort)) {
					sortTeams(keyToSort, teams);
					for(Team t : teams) {
						System.out.println(t);
					}
				} else {
					System.out.println("" + keyToSort + " is not a sortable key.");
				}
				break;
			default:
				System.out.println("Command not recognized");
			}
		}
	}
	public static int teamExists(int t, ArrayList<Team> teams) {
		for(Team team : teams) {
			if(team.getNumber() == t) {
				return teams.indexOf(team);
			}
		}
		return -1;
	}
	public static boolean isLegalMatch(String s) {
		return (s.split("}").length == Match.keys.length);
	}
	public static void sortTeams(String key, ArrayList<Team> teams) {
		if(teams.get(0).getDataArray(key).get(0) instanceof Integer) {
			Collections.sort(teams, (a,b) -> (Integer)a.getAverage(key) < (Integer)b.getAverage(key) ? -1 : (Integer)a.getAverage(key) == (Integer)b.getAverage(key) ? 0 : 1);
		} else if(teams.get(0).getDataArray(key).get(0) instanceof Boolean) {
			//eventually make to sort by most true
			for(int i = 0; i < teams.size();i++) {
				if (!(Boolean)teams.get(i).getAverage(key)) {
					for(int j = i + 1; j<teams.size();j++) {
						if((Boolean)teams.get(j).getAverage(key)) {
							Team temp = teams.get(i);
							teams.set(i, teams.get(j));
							teams.set(j, temp);
							break;
						}
					}
				}
			}
		}
	}
}
