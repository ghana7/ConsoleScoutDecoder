import java.util.ArrayList;
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

}
