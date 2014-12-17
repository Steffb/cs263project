package test3.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author steffenfb
 *
 * The class to parse Premier league json object to java
 * Represents a match 
 */
public class PlJson {
	
	public int id;
	public String date;
	public int matchday;
    public String homeTeam;
    public String awayTeam;
    public int goalsHomeTeam;
    public int goalsAwayTeam;
    public ArrayList<String> comments= new ArrayList<String>();
	
	
	String league;
	
	List<List<Ranking>> ranking  = new ArrayList<List<Ranking>>();
	
	/**
	 * 
	 * @return Returns normal string
	 */
	public boolean isPlayed(){
		if(goalsAwayTeam<0){
			return false;
		}else return true;
	}
	
	public int goalCount(){
		return goalsAwayTeam + goalsHomeTeam;
	}
	public boolean wasGoal(){
		if (goalsAwayTeam + goalsHomeTeam !=0){
			return true;
		}else return false;
	}
	
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		System.out.println("heeey");
		this.league = league;
	}
	
	/**
	 * @return return string
	 */

	@Override
	public String toString() {
		return "PlJson [league=" + league + "] and first team "+ranking.get(0).get(0).team;
	}
	
	

}

class Ranking  {
	int rank;
	
    String team;
   // pictuer "http://upload.wikimedia.org/wikipedia/de/5/5c/Chelsea_crest.svg",
    int points;
    int goals;
    int goalsAgainst;
    int goalDifference;
	
	
	
}

