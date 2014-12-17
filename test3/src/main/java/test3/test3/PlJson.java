package test3.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author steffenfb
 *
 * The class to parse Soccer league json to java
 * Represents a match that has been 
 * the id is given by the api and used throughout as identificator
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
	 * All matches that are not played has the score -1 vs -1 so 
	 * one check is something is under 0 is enough to determine 
	 */
	public boolean isPlayed(){
		if(goalsAwayTeam<0){
			return false;
		}else return true;
	}
	/**
	 * coutns the total amount of goals in a match
	 * @return
	 */
	public int goalCount(){
		return goalsAwayTeam + goalsHomeTeam;
	}
	/**
	 * returns a boolean on wether or not there were goals in this match
	 * @return
	 */
	public boolean wasGoal(){
		if (goalsAwayTeam + goalsHomeTeam !=0){
			return true;
		}else return false;
	}
	/** 
	 * returns the league name
	 * @return
	 */
	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		
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

