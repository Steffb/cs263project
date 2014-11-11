package test3.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;



public class JsonServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{


		//String uri ="http://echo.jsontest.com/key/value/one/two";
		String uri="";
		String s= req.getParameter("sport");
		if (s.equals("plranking")){

			System.out.println("got the message"+s);

			uri+="http://www.football-data.org/soccerseasons/354/ranking";

		}

		else if(s.equals("nhl")){

			String nhlkey="jks2rshurjcc4ymj2z9r4d63";
			String apikey=nhlkey;


			uri= "http://api.sportsdatallc.org/nhl-t3/games/2e4543e6-b42a-4cc4-a1e1-21208289b75d/summary.json?api_key="+apikey;
		}




		//trengs ikke
		String jsonStr=reqToStr(uri);
		resp.setContentType("text/plain");
		resp.getWriter().println("her kommer det "+jsonStr);

		Gson gson = new Gson();
		//Streng fra json objekt, og klasse som matcher feltene du vil ha ut



		//		NhlJson tc = gson.fromJson(jsonStr, NhlJson.class);
		//		System.out.println(tc.toString());
		//		resp.getWriter().println(tc);

		PlJson plj = gson.fromJson(jsonStr, PlJson.class);

		resp.getWriter().println(plj);

	}

	public static String reqToStr(String uri) throws IOException{
		System.out.println("works");
		System.out.println("works");
		System.out.println("works");
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
			System.out.println(inputStr);
		}
		
		is.close();
		connection.disconnect();

		return responseStrBuilder;

	}

	public static void main(String[] args) throws IOException {
		// just for local testing
		Gson gson = new Gson();
		String ss = reqToStr("http://www.football-data.org/soccerseasons/354/ranking");
		
		// Made object
		PlJson pp  = gson.fromJson(ss, PlJson.class);
		System.out.println("object Made");
		System.out.println(pp);
		
		
		
		
	}


}
