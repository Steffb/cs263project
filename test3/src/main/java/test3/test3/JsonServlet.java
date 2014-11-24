package test3.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

/**
 * 
 * @author steffenfb
 *	Servlet for handling json requests and making it to gson
 */

public class JsonServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

		
		
		//String uri ="http://echo.jsontest.com/key/value/one/two";
		String uri="";
		String s= req.getParameter("sport");
		System.out.println("this is s"+s);
		String lid= req.getParameter("id");

		if (s.equals("plranking")){

			System.out.println("got the message"+s);

			uri+="http://www.football-data.org/soccerseasons/"+lid+"/fixtures";
			String jsonStr=reqToStr(uri);
			Gson gson = new Gson();
			PlJson[] plj =  gson.fromJson(jsonStr, PlJson[].class);

			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/pl.jsp");
			req.setAttribute("league", plj);
			//forward query to page
			rd.forward(req, resp);

			//resp.sendRedirect("pl.jsp");

		}else if(s.equals("footballold")){
			System.out.println("running football old");
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			Key LeagueKey = KeyFactory.createKey("LeagueEntity", lid);
			

			Query query = new Query("LeagueEntity",LeagueKey);
			System.out.println("This is the query "+query.toString());

			//check if that league has been created
			List<Entity> League = datastore.prepare(query).asList(
					FetchOptions.Builder.withLimit(5));

			uri+="http://www.football-data.org/soccerseasons/"+lid+"/fixtures";
			//gettin get
			String jsonStr=reqToStr(uri);
			Gson gson = new Gson();
			//List of all league matches
			PlJson[] plj =  gson.fromJson(jsonStr, PlJson[].class);

			if (League.isEmpty()) {
				// creating a new league
				System.out.println("it is empty");

				Entity league = new Entity("LeagueEntity",LeagueKey);
				league.setProperty("apiId", lid);
				league.setProperty("leaguename", "premierleague");

				datastore.put(league);

				System.out.println("put entity league");

				//adding sub> matches
				for (int i = 0; i < plj.length; i++) {
					System.out.println("running for loop");
					if(plj[i].isPlayed()){
						//check if it needs to be added to db
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
						System.out.printf("adding %s",match);

					}




				}
			}System.out.println("already added");

		}

		else if(s.equals("nhl")){

			String nhlkey="jks2rshurjcc4ymj2z9r4d63";
			String apikey=nhlkey;


			uri= "http://api.sportsdatallc.org/nhl-t3/games/2e4543e6-b42a-4cc4-a1e1-21208289b75d/summary.json?api_key="+apikey;
			String jsonStr=reqToStr(uri);
		}






		//resp.getWriter().println("her kommer det "+jsonStr);


		//Streng fra json objekt, og klasse som matcher feltene du vil ha ut



		//		NhlJson tc = gson.fromJson(jsonStr, NhlJson.class);
		//		System.out.println(tc.toString());
		//		resp.getWriter().println(tc);



	}

	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		System.out.println("here comes the shit: "
				+ req.getParameter("type")+req.getParameter("id")+",parent kind "+req.getParameter("parentkind")
				+", parent id "+req.getParameter("parentid")+", parent "+req.getParameter("parent")+", parent name "+req.getParameter("parentname")
				+ "");
		
		
		Key parent =KeyFactory.createKey(req.getParameter("parentkind"),req.getParameter("parentname"));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Long l =Long.parseLong(req.getParameter("id"));
		Key mykey = KeyFactory.createKey(parent,"LeagueMatch",l);
		
		try {
			Entity e =datastore.get(mykey);
			long v = (Long) e.getProperty("vote");
			v+=1;
			e.setProperty("vote", v);
			
			datastore.put(e);
			System.out.print("hey it worked");
			resp.getWriter().println("it worked");
			//needs to be dynamic
			resp.sendRedirect("votepage.jsp?id=354");
			
			
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	
		
		
		
		
		
		
	}
	public static String reqToStr(String uri) throws IOException{

		URL url = new URL(uri);
		HttpURLConnection connection =
				(HttpURLConnection) url.openConnection();
		InputStream is = connection.getInputStream();
		System.out.println("works2");
		String responseStrBuilder="";
		String inputStr;
		//lager strengobjektet fra is


		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((inputStr = streamReader.readLine()) != null){
			responseStrBuilder+=inputStr;

		}

		is.close();
		connection.disconnect();

		return responseStrBuilder;

	}



	public static void main(String[] args) throws IOException {
		// just for local testing
		Gson gson = new Gson();
		String ss = reqToStr("http://www.football-data.org/soccerseasons/354/fixtures");
		System.out.println(ss);
		// Made object
		PlJson[] pp  = gson.fromJson(ss, PlJson[].class);
		//PlFJson[] pp  = gson.fromJson(ss, PlFJson[].class);

		System.out.println("object Made");
		System.out.println(pp);
		System.out.println(pp[0].homeTeam);


		for (int i = 0; i < pp.length; i++) {
			PlJson plJson = pp[i];
			if(pp[i].isPlayed()){
				System.out.println(pp[i].homeTeam+"scored"+pp[i].goalsHomeTeam);
				System.out.println(pp[i].awayTeam+"scored"+pp[i].goalsAwayTeam);

			}
		}










	}


}
