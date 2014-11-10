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
		String nhlkey="jks2rshurjcc4ymj2z9r4d63";
		String apikey=nhlkey;
		
		
		String uri= "http://api.sportsdatallc.org/nhl-t3/games/2e4543e6-b42a-4cc4-a1e1-21208289b75d/summary.json?api_key="+apikey;
		
		URL url = new URL(uri);
		HttpURLConnection connection =
				(HttpURLConnection) url.openConnection();
		
		InputStream is = connection.getInputStream();
		
		is.close();
		connection.disconnect();


		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String responseStrBuilder="";
		String inputStr;
		
		//lager strengobjektet fra is
		while ((inputStr = streamReader.readLine()) != null){
			responseStrBuilder+=inputStr;
			System.out.println(inputStr);
		}
		
		//trengs ikke
		String jsonStr=responseStrBuilder;
		resp.setContentType("text/plain");
		resp.getWriter().println("her kommer det ");
		
		Gson gson = new Gson();
		//Streng fra json objekt, og klasse som matcher feltene du vil ha ut
		
		NhlJson tc = gson.fromJson(jsonStr, NhlJson.class);
		System.out.println(tc.toString());
		resp.getWriter().println(tc);

	}


	public static void main(String[] args) {
		// just for local testing
		Gson gson = new Gson();
		String jsonStr= "{\"string\":\"DeviceId\",\"integer\":2}";
		TestClass tc = gson.fromJson(jsonStr, TestClass.class);
		System.out.println(tc);
	}


}
