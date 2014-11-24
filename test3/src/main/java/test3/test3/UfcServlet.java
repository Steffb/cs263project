package test3.test3;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import test3.test3.Datascraper;

public class UfcServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{ 
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		//Add the event to db
		String url = "http://en.wikipedia.org/wiki/UFC_180";
		

		UfcEvent u= new UfcEvent();
		u.eventname=Datascraper.getwikiheader(Datascraper.urlToDoc(url));
		u.fights=Datascraper.createFights(Datascraper.urlToDoc(url));
		

		Key ufcKey = KeyFactory.createKey("UfcEvent",u.eventname );
		Entity league = new Entity("UfcEvent",ufcKey);
		league.setProperty("Eventname", u.eventname);
		

		datastore.put(league);
		
		for (int i = 0; i < u.fights.size(); i++) {
	
			Fight f=u.fights.get(i);
			
			Entity e = new Entity("Fight", Long.valueOf(i+1), ufcKey);
			e.setProperty("loser", f.loser);
			e.setProperty("winner", f.winner);
			e.setProperty("weightclass", f.weightclass);
			e.setProperty("hasbeen", f.hasbeen);
			e.setProperty("method", f.method);
			e.setProperty("note", f.note);
			e.setProperty("round", f.round);
			e.setProperty("stopped", f.stopped);
			e.setProperty("time",f.time);
			
			datastore.put(e);
			
			
		
		}
		
		
		
		
		resp.getWriter().write("hello this should be added ");
		
		
		
	}
	

}
