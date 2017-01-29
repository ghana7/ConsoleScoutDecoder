import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Team> teams = new ArrayList<Team>();
		Scanner sc = new Scanner(System.in);
		while(true) {
			String input = sc.nextLine();
			String[] inputArray = input.split(" ");
			switch(inputArray[0]) { //inputArray[0] is the command itself
			case "addTeam": 
				addTeam(inputArray[1], teams);
				break;
			case "addMatch":
				addMatch(inputArray[1],inputArray[2],teams);
				break;
			case "addPit":
				addPit(inputArray[1],inputArray[2],teams);
				break;
			case "printTeam":
				printTeam(inputArray[1],teams);
				break;
			case "rankTeams":
				rankTeams(inputArray[1],teams);
				break;
			case "findFile":
				findFile();
				break;
			case "help":
				System.out.println("addTeam <teamNumber>");
				System.out.println("\tAdds a team with number teamNumber");
				System.out.println("addMatch <teamNumber> <matchString>");
				System.out.println("\tAdds data from a match from string matchString to team teamNumber.");
				System.out.println("\tRequires team teamNumber to already exist.");
				System.out.println("addPit <teamNumber> <pitString>");
				System.out.println("\tAdds data from a pit scout from string pitString to team teamNumber.");
				System.out.println("\tRequires team teamNumber to already exist.");
				System.out.println("printTeam <teamNumber>");
				System.out.println("\tPrints team teamNumber to the console");
				System.out.println("ranksTeams <key>");
				System.out.println("\tRanks teams by key");
				break;
			default:
				System.out.println("Command not recognized");
			}
		}
	}
	
	public static void findFile() {
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {// Shows user file chooser 
			File file = fc.getSelectedFile();
			try {
				Scanner inputFile = new Scanner(file);
				while(inputFile.hasNext()) { // Does until there is no more data
					
				}
			} catch (Exception e) {
				System.out.println("Invalid File Input");
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
	public static void addTeam(String teamNumber, ArrayList<Team> teams) {
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
	}
	public static void addMatch(String teamNumber, String matchString, ArrayList<Team> teams) {
		if(Match.isNumeric(teamNumber)) {
			if(teamExists(Integer.parseInt(teamNumber), teams) != -1) {
				if(isLegalMatch(matchString)) {
					teams.get(teamExists(Integer.parseInt(teamNumber), teams)).addMatch(matchString);
					System.out.println("Added match " + matchString);
				} else {
					System.out.println("This is not a legal match format.");
				}
			} else {
				System.out.println("Team " + teamNumber + " does not exist!");
			}
		} else {
			System.out.println(teamNumber + " is not a number. That argument must be a number.");
		}
	}
	public static void addPit(String teamNumber, String pitString, ArrayList<Team> teams) {
		if(Match.isNumeric(teamNumber)) {
			if(teamExists(Integer.parseInt(teamNumber), teams) != -1) {
				if(isLegalMatch(pitString)) {
					teams.get(teamExists(Integer.parseInt(teamNumber), teams)).addMatch(pitString);
					System.out.println("Added match " + pitString);
				} else {
					System.out.println("This is not a legal pit format.");
				}
			} else {
				System.out.println("Team " + teamNumber + " does not exist!");
			}
		} else {
			System.out.println(teamNumber + " is not a number. That argument must be a number.");
		}
	}
	public static void printTeam(String teamNumber, ArrayList<Team> teams) {
		if(Match.isNumeric(teamNumber)) {
			int teamNumberAsInt = Integer.parseInt(teamNumber);
			if(teamExists(teamNumberAsInt, teams) != -1) {
				System.out.println(teams.get(teamExists(Integer.parseInt(teamNumber),teams)));
			} else {
				System.out.println("Team " + teamNumberAsInt + " does not exist.");
			}
		} else {
			System.out.println(teamNumber + " is not a number. That argument must be a number.");
		}
	}
	public static void rankTeams(String key, ArrayList<Team> teams) {
		if(Match.isKey(key)) {
			sortTeams(key, teams);
			for(Team t : teams) {
				System.out.println(t);
			}
		} else {
			System.out.println("" + key + " is not a sortable key.");
		}
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
