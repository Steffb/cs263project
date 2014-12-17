package test3.test3;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.SoccerIndex;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

import com.google.gson.Gson;

/**
 * 
 * @author steffenfb
 *	Servlet for handling json requests and making it to gson
 *Handles all the soccer requests
 *
 *
 */

public class JsonServlet extends HttpServlet{

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	
	/**
	 * 
	 *The doGet here take the request for a particular league and 
	 *checks params to decide what to do.
	 * If it comes from a leauge page we know we have an updated version in our db and fetches comments that may be
	 * connected, and returns with data from db or cache
	 * 
	 * if it does not, that means it comes form the index page, so we do an api call. We then send go to the page and 
	 * send a request to the worker to see if we need to update the datastore
	 * 
	 */

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

		DatastoreService datastore;
		String uri="";
		String s= req.getParameter("sport");
		String lid= req.getParameter("id");
		req.setAttribute("leaguename", req.getParameter("leaguename"));

		/**
		 * This is if you want to see the current tables of a played matches of particular season
		 */
		if (s.equals("plranking")){
			
			//this is if you come from the ranking page through comment or show comment
			if (req.getParameter("post")!=null && req.getParameter("post").equals("yes")){
				System.out.println("this is from comment");

				datastore = DatastoreServiceFactory.getDatastoreService();

				Key LeagueKey = KeyFactory.createKey("LeagueEntity", lid);

				Query query=new Query("LeagueMatch").setAncestor(LeagueKey);

				List <Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
				//nessesary to get it in right order
				Collections.reverse(results);
				PlJson[] plj = new PlJson[results.size()];
				long l;
				int count=0;
				Query commentq=new Query("comment");
				List <Entity> comments = datastore.prepare(commentq).asList(FetchOptions.Builder.withDefaults());
				
				for (Entity e:results) {
					PlJson p =new PlJson();
					p.homeTeam=(String) e.getProperty("hometeam");
					l =(Long) e.getProperty("goalsHomeTeam");
					p.goalsHomeTeam =(int) l;
					p.awayTeam = (String) e.getProperty("awayteam");
					l = (Long) e.getProperty("goalsAwayTeam");
					p.goalsAwayTeam =(int) l;
					l= (Long) e.getProperty("id");
					p.id = (int) l;
					Key mykey = KeyFactory.createKey(LeagueKey,"LeagueMatch",l);

					l = (Long) e.getProperty("matchday");

					Query comment2=new Query("comment",mykey);
					List <Entity> comments2 = datastore.prepare(comment2).asList(FetchOptions.Builder.withDefaults());
					for (Entity e2:comments2){

						p.comments.add(e2.getProperty("author")+": "+(String) e2.getProperty("text"));

					}

					p.matchday = (int) l;

					plj[count]=p;
					count+=1;

				}

				//get the comments to matches 
				
				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/pl.jsp");
				req.setAttribute("league", plj);
				//forward query to page
				System.out.println("forwarding");
				req.setAttribute("lid", lid);
				req.setAttribute("comments", comments);
				rd.forward(req, resp);
			}else{


				//Coming from indexpage 

				uri+="http://www.football-data.org/soccerseasons/"+lid+"/fixtures";
				String jsonStr=reqToStr(uri);
				Gson gson = new Gson();
				PlJson[] plj =  gson.fromJson(jsonStr, PlJson[].class);

				ServletContext sc = this.getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/pl.jsp");
				req.setAttribute("league", plj);
				//forward query to page
				req.setAttribute("lid", lid);
				rd.forward(req, resp);
			


				// counts how many matches that are played so we know who to add later
				int jsonPlayed=0;

				//does not live update
				for (int i = 0; i < plj.length; i++) {
					if (plj[i].isPlayed()){
						jsonPlayed+=1;
					}

				}

				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(withUrl("/worker").param("lid", lid).param("count", ""+jsonPlayed));

			}	
		}// it is a request for the index table
		else if( s.equals("index")){
			SoccerIndex[] soccerindex;


			if (!memcache.contains("soccerindex")){
				memcache.get("soccerindex");

				uri+="http://www.football-data.org/soccerseasons/";
				String jsonStr=reqToStr(uri);
				Gson gson = new Gson();


				soccerindex =  gson.fromJson(jsonStr, SoccerIndex[].class);
				memcache.put("soccerindex", soccerindex);
			}else{

				soccerindex = (SoccerIndex[]) memcache.get("soccerindex");

			}


			ServletContext sc = this.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/soccerindex.jsp");
			req.setAttribute("index", soccerindex);
			//forward query to indexpage
			rd.forward(req, resp);

		}




	}


	/**
	 * This is for Comments to the matches
	 * 
	 * This can be either a comment posted or a show comment
	 * 
	 * If it is a posted comment we add it to the database.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {



		try {
			String lid = req.getParameter("parentname");
			System.out.println("first check"+lid);
			String league =req.getParameter("leaguename");

			if(req.getParameter("iscomment").equals("yes")){

				Key parent =KeyFactory.createKey(req.getParameter("parentkind"),req.getParameter("parentname"));
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Long l =Long.parseLong(req.getParameter("id"));
				Key mykey = KeyFactory.createKey(parent,"LeagueMatch",l);


				Entity e =datastore.get(mykey);
				System.out.println("found the element "+e.getKey());
				System.out.println("got fount the entity");


				System.out.print("hey it worked");
				Entity com= new Entity("comment", mykey);
				com.setProperty("author", req.getParameter("sender"));
				com.setProperty("text", req.getParameter("comment"));
				datastore.put(com);

			}

			

			resp.sendRedirect("/soccerleagues?sport=plranking&id="+lid+"&post=yes&leaguename="+league);



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
