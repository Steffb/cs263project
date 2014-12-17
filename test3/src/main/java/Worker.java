
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test3.test3.JsonServlet;
import test3.test3.PlJson;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;

import java.util.Date;


/**
 * 
 * @author steffenfb
 * This worker takes over after you go to the soccer page
 * It checks if the db version of the table is up to date with the api version
 * 
 * It does this by checking the amount of played matches 
 * If the Json version has higher count of played matches, the db needs to be updated
 * 
 * 
 */
public class Worker extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//need to do the request again because it was to large to send with worker 

		String uri="http://www.football-data.org/soccerseasons/"+request.getParameter("lid")+"/fixtures";
		String jsonStr=JsonServlet.reqToStr(uri);

		System.out.println("worker is working");
		//adding new entries to db

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key LeagueKey = KeyFactory.createKey("LeagueEntity", request.getParameter("lid"));


		Query query = new Query("LeagueEntity",LeagueKey);
		System.out.println("This is the query "+query.toString());

		//check if that league has been created
		Entity league = datastore.prepare(query).asSingleEntity();

		String count=request.getParameter("count");
		int jsonPlayed = Integer.parseInt(count);
		boolean update = true;

		//checks if league already in db
		if (league!=null) {
			// league is in db
			System.out.println("exists in db");

			long longplayed =(Long)league.getProperty("matchcount");
			int played =(int)longplayed;

			/** 
			 * Check wheter we need to update the database.
			 */
			if(!(played<jsonPlayed)){
				//we do not need to update the database
				System.out.println("No need to update");
				update=false;
			}}
		//if we do need to update
		if (update){
			System.out.println("we do update");
			System.out.println("leaguekey is "+LeagueKey);
			Entity newleague = new Entity("LeagueEntity", LeagueKey);
			
			
			// sets the new matchcount accordingly
			newleague.setProperty("matchcount", jsonPlayed);
			datastore.put(newleague);
			System.out.println("added league to ds");
			//create the object again

			Gson gson = new Gson();
			PlJson[] plj =  gson.fromJson(jsonStr, PlJson[].class);

			System.out.println("starting forloop");
			System.out.println("this is the lenght"+plj.length);

			for (int i = 0; i < plj.length; i++) {
				//only adding matches that have been played
				System.out.println("before if statement");
				if(plj[i].isPlayed()){

					//Key matchKey = KeyFactory.createKey("LeagueMatch", plj[i].id);
					Entity match = new Entity("LeagueMatch", plj[i].id, LeagueKey);

					match.setProperty("awayteam", plj[i].awayTeam);
					match.setProperty("hometeam", plj[i].homeTeam);
					match.setProperty("id",plj[i].id);
					match.setProperty("matchday",plj[i].matchday);
					match.setProperty("goalsHomeTeam",plj[i].goalsHomeTeam);
					match.setProperty("goalsAwayTeam",plj[i].goalsAwayTeam);
					match.setProperty("vote", 0);
					datastore.put(match);

				}	
			}System.out.println("Worker completed");

			//adding sub> matches


		}

	}



}
