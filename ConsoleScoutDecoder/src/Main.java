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
			switch(inputArray[0]) {
			case "addTeam":
				if(Match.isNumeric(inputArray[1])) {
					if(teamExists(Integer.parseInt(inputArray[1]), teams) == -1) {
						teams.add(new Team(Integer.parseInt(inputArray[1])));
						System.out.println("Added team " + inputArray[1]);
					} else {
						System.out.println("Team " + inputArray[1] + " already exists!");
					}
				} else {
					System.out.println(inputArray[1] + " is not a number. That argument must be a number.");
				}
				break;
			case "addMatch":
				if(Match.isNumeric(inputArray[1])) {
					if(teamExists(Integer.parseInt(inputArray[1]), teams) != -1) {
						if(isLegalMatch(inputArray[2])) {
							teams.get(teamExists(Integer.parseInt(inputArray[1]), teams)).addMatch(inputArray[2]);
							System.out.println("Added match " + inputArray[2]);
						} else {
							System.out.println("This is not a legal match format.");
						}
					} else {
						System.out.println("Team " + inputArray[1] + " does not exist!");
					}
				} else {
					System.out.println(inputArray[1] + " is not a number. That argument must be a number.");
				}
				break;
			case "printTeam":
				if(Match.isNumeric(inputArray[1])) {
					int teamNumber = Integer.parseInt(inputArray[1]);
					if(teamExists(teamNumber, teams) != -1) {
						System.out.println(teams.get(teamExists(Integer.parseInt(inputArray[1]),teams)));
					} else {
						System.out.println("Team " + teamNumber + " does not exist.");
					}
					
				} else {
					System.out.println(inputArray[1] + " is not a number. That argument must be a number.");
				}
				break;
			case "rank":
				if(Match.isKey(inputArray[1])) {
					sortTeams(inputArray[1], teams);
					for(Team t : teams) {
						System.out.println(t);
					}
				} else {
					System.out.println("" + inputArray[1] + " is not a sortable key.");
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
//			ArrayList<Team> temp = new ArrayList<Team>();
//			for(Team t : teams) {
//				if((Boolean)t.getAverage(key)) {
//					temp.add(0,t);
//				} else {
//					temp.add(t);
//				}
//			}
//			teams = temp;
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
