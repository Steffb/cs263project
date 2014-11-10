package test3.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;





public class MyServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);


		//String uri ="http://api.flurry.com/eventMetrics/Event?apiAccessCode=YHJBA13CSKTMS6XHTM6M&apiKey=6XQY729FDU1CR9FKXVZP&startDate=2011-2-28&endDate=2011-3-1&eventName=Tip%20Calculated";
		String uri ="http://api.sportsdatallc.org/mma-t1/schedule.xml?api_key=5qdshzyuvstb9kyd3u44u972";
		

		URL url = new URL(uri);
		HttpURLConnection connection =
				(HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream xml = connection.getInputStream();
		//Needs fix
		
		String s= convertStreamToString(xml);
		


		resp.setContentType("text/plain");

		resp.getWriter().println("hello");

		resp.getWriter().println(xml);
		resp.getWriter().println(s);
		


	}
	protected String getLoc(HttpServletRequest req){
		String s= req.getHeader("X-AppEngine-Country")+"<br>";
		s+= req.getHeader("X-AppEngine-Region")+"<br>";
		s+= req.getHeader("X-AppEngine-City")+"<br>";
		s+= req.getHeader("X-AppEngine-CityLatLong")+"<br>";
		return s;


	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	


}

