package test3.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author steffenfb
 *
 * The class to parse Premier lueage json object
 */
public class PlJson {
	
	int id;
    String date;
    int matchday;
    String homeTeam;
    String awayTeam;
    int goalsHomeTeam;
    int goalsAwayTeam;
	
	
	String league;
	
	List<List<Ranking>> ranking  = new ArrayList<List<Ranking>>();
	
	/**
	 * 
	 * @return Returns normal string
	 */

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

