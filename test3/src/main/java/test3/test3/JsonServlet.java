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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;



public class JsonServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{


		//String uri ="http://echo.jsontest.com/key/value/one/two";
		String uri="";
		String s= req.getParameter("sport");
		System.out.println("hello");
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
			rd.forward(req, resp);
			
			
			
			
			

			
			//resp.sendRedirect("pl.jsp");
			
			
			
			

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
