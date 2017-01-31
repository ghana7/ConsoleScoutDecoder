import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Main {

//	private static ArrayList<Team> allTeams = new ArrayList<Team>();
	
	public static void main(String[] args) {
		ArrayList<Team> teams = new ArrayList<Team>();
		Scanner sc = new Scanner(System.in);
		while (true) {
			String input = sc.nextLine();
			String[] inputArray = input.split(" ");
			switch (inputArray[0]) { // inputArray[0] is the command itself
			case "addTeam": //adds a team to the data
				addTeam(inputArray[1], teams);
				break;
			case "addMatch": //adds a match to a team
				addMatch(inputArray[1], inputArray[2], teams);
				break;
			case "addPit": //adds a pit to a team
				addPit(inputArray[1], inputArray[2], teams);
				break;
			case "printTeam": //prints a team and its data
				printTeam(inputArray[1], teams);
				break;
			case "rankTeams": //sorts teams by a piece of info and prints them in order
				rankTeams(inputArray[1], teams);
				break;
			case "findFile": //gets info from file
				findFile(teams);
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
	public static void findFile(ArrayList<Team> teams) {//this is basically all from Eric's code so blame him if it doesn't make sense
		JFileChooser fc = new JFileChooser();
		boolean workaround = true;
		
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {// Shows user file chooser 
			File file = fc.getSelectedFile();
			try {
				Scanner inputFile = new Scanner(file);
				
				while(inputFile.hasNext()) { // Does until there is no more data
				//	ArrayList<String> decoded = new ArrayList<String>();
					String input;
					int index;
					input = inputFile.nextLine(); // Grabs string from file
					if(!input.split("}")[2].equals("true") || !input.split("}")[2].equals("false")) // Accounts for the new line bug error in scouting app 
						if (workaround)
						{
							inputFile.nextLine();
							workaround = false;
						}
						input += inputFile.nextLine();
						if (inputFile.hasNext())
							inputFile.nextLine();
					if (input.startsWith("\uFEFF")) { // Workaround for UTF-8 BOM encoding 
				        input = input.substring(1);
				    }
					try{
						String[] accessData = input.split("}");
						if(teamExists(Integer.parseInt(accessData[0]), teams) == -1) {
							addTeam(accessData[0],teams); //adds the team if it does not exist yet
						} else { //if it does, it adds the data to it
							if(accessData[2].equals("true") || accessData[2].equals("false")) {
								addPit(accessData[0],input.substring(2),teams);

							} else {
								addMatch(accessData[0],input.substring(2),teams);
							}
						}
					} catch (Exception error) {
						System.out.println("Error: Invalid Input");
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: File not found");
			}
		} 
	}
	public static int teamExists(int t, ArrayList<Team> teams) { //determines if team t exists in teams, returns index if it does
		for (Team team : teams) {
			if (team.getNumber() == t) {
				return teams.indexOf(team);
			}
		}
		return -1;
	}

	public static boolean isLegalMatch(String s) { //checks if match string is in legal format
		return (s.split("}").length == Match.keys.length);
	}

	public static void addTeam(String teamNumber, ArrayList<Team> teams) { //adds team teamNumber to arraylist teams
		if (Match.isNumeric(teamNumber)) {
			if (teamExists(Integer.parseInt(teamNumber), teams) == -1) {
				teams.add(new Team(Integer.parseInt(teamNumber)));
				System.out.println("Added team " + teamNumber);
		//		teams.add(new Team(Integer.parseInt(teamNumber)));
			} else {
				System.out.println("Team " + teamNumber + " already exists!");
			}
		} else {
			System.out.println(teamNumber + " is not a number. That argument must be a number.");
		}
	}

	public static void addMatch(String teamNumber, String matchString, ArrayList<Team> teams) { //adds match by matchstring to team teamnumber
		if (Match.isNumeric(teamNumber)) {
			if (teamExists(Integer.parseInt(teamNumber), teams) != -1) {
				if (isLegalMatch(matchString)) {
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

	public static void addPit(String teamNumber, String pitString, ArrayList<Team> teams) { //adds pit scout data by pitString to team teamNumber
		if (Match.isNumeric(teamNumber)) {
			if (teamExists(Integer.parseInt(teamNumber), teams) != -1) {
				if (isLegalMatch(pitString)) {
					teams.get(teamExists(Integer.parseInt(teamNumber), teams)).addMatch(pitString);
					System.out.println("Added pit " + pitString);
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

	public static void printTeam(String teamNumber, ArrayList<Team> teams) { //prints team teamNumber and its data
		if (Match.isNumeric(teamNumber)) {
			int teamNumberAsInt = Integer.parseInt(teamNumber);
			if (teamExists(teamNumberAsInt, teams) != -1) {
				System.out.println(teams.get(teamExists(Integer.parseInt(teamNumber), teams)));
			} else {
				System.out.println("Team " + teamNumberAsInt + " does not exist.");
			}
		} else {
			System.out.println(teamNumber + " is not a number. That argument must be a number.");
		}
	}

	public static void rankTeams(String key, ArrayList<Team> teams) { //sorts teams by key then prints all of them
		if (Match.isKey(key)) {
			sortTeams(key, teams);
			for (Team t : teams) {
				System.out.println(t);
			}
		} else {
			System.out.println("" + key + " is not a sortable key.");
		}
	}

	public static void sortTeams(String key, ArrayList<Team> teams) { //sorts teams by a key
		if (teams.get(0).getDataArray(key).get(0) instanceof Integer) { //if int, sorts numericalls
			Collections.sort(teams, (a, b) -> (Integer) a.getAverage(key) < (Integer) b.getAverage(key) ? -1
					: (Integer) a.getAverage(key) == (Integer) b.getAverage(key) ? 0 : 1);
		} else if (teams.get(0).getDataArray(key).get(0) instanceof Boolean) {//if boolean, puts those with majority true to front
			for (int i = 0; i < teams.size(); i++) {                          //eventually should be by % of true
				if (!(Boolean) teams.get(i).getAverage(key)) {
					for (int j = i + 1; j < teams.size(); j++) {
						if ((Boolean) teams.get(j).getAverage(key)) {
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
