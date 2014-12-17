package test3.test3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objects.EventIndex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * 
 * @author steffenfb
 * 
 * Datascraper it used to scrape Wikipedia pages to get the sportsevent information I need
 * 
 * Use it kinda like a get api. Use url patterns that math wikipedia setup, and scrape it before returning
 * it as objects i can use
 * 
 *
 */


public class Datascraper {
	
	/**
	 * A helping function to display the tables i need to scrape so i can see how to scrape them
	 * @param url
	 */

	public static void wikiTablePrinter(String url){
		Document doc2;
		try {

			doc2= Jsoup.connect(url).get();
			Elements table= doc2.select(".toccolours");

			for (Element row : table.select("tr")) {
				System.out.println();
				System.out.println("___________________new row___________________");
				Elements ths = row.select("th");

				if(ths.hasText()){
					for (Element t : ths){

						System.out.print(" |");

						if(t.select("a").hasText()){
							System.out.print(t.select("a").html()); 
						}else{


							System.out.print(t.html()); }


					}
				}


				Elements tds = row.select("td");
				if(tds.hasText()){
					for (Element t : tds){

						System.out.print(" |");

						if(t.select("a").hasText()){
							System.out.print(t.select("a").html()); 
						}else{


							System.out.print(t.html()); }


					}
				}

			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param url 
	 * Url for wiki destination 
	 * 
	 * Use Jsoup that returns a document
	 * @return a DOM that can easily be scraped
	 * @throws IOException
	 */
	public static Document urlToDoc(String url) throws IOException{
		Document doc;
		return doc= Jsoup.connect(url).get();
	}

	/**
	 * Takes the document of a wikipage and gets the header, this is the event title
	 * 
	 * @param d
	 * @return
	 */
	public static String  getwikiheader(Document d){
		return d.select("#firstHeading span").html();
	}

	
	/**
	 *  Takes the document that is a wikipedia page, finds the result table and traverse it and collect the data
	 *  Use the fact that they are all structured identical, hopefully these will stay the way they are since
	 *  there probably is used a framework for constructing them 
	 *  
	 * @param d
	 * @return a list of fight objects, these are fights belonging to the same event, and a fight contains all the data about
	 * a particular fight.
	 */
	public static ArrayList<Fight> createFights(Document d){
		ArrayList<Fight> fights= new ArrayList<Fight>();

		Elements table= d.select(".toccolours");


		for (Element row : table.select("tr")) {

			Elements tds = row.select("td");
			if(tds.hasText()){

				Fight f= new Fight();
				f.weightclass = tds.get(0).html();
				f.winner= removeLink(tds.get(1));
				f.loser= removeLink(tds.get(3));
				f.method= tds.get(4).html();
				f.round= tds.get(5).html();
				f.time= tds.get(6).html();
				f.note= tds.get(7).html();

				if(tds.get(4).hasText()){
					f.hasbeen=true;
				}
				//how about draw
				if (tds.get(4).html().contains("Dec")){
					f.stopped=false;
				}else{
					f.stopped=true;
				}




				fights.add(f);

			}

		}

		return fights;



	}

	/**
	 * some table field in wikipedia are links, this is to clean the tags away to 
	 * avoid collecting link tags
	 * @param e
	 * @return
	 */
	public static String  removeLink(Element e){
		String data;
		if(e.select("a").hasText()){
			data=e.select("a").html(); 
		}else{
			data=e.html(); }

		return data;

	}


	/** 
	 * This creates the data for the indexpage to show UFC bouts.
	 * Takes a table that contains all the events that has been collects the names and corresponding links for each
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<EventIndex> createIndex() throws IOException{
		//wikiTablePrinter("http://en.wikipedia.org/wiki/UFC_180");
				String url = "http://en.wikipedia.org/wiki/List_of_UFC_events";
				//System.out.println(getwikiheader(urlToDoc(url)));

				List<EventIndex> l =  new ArrayList<EventIndex>();
				Document d =urlToDoc(url);
				
				Elements table= d.select("table tbody");
				Element e = table.get(1);
				Elements rows = e.select("tr");
				for (Element row:rows){
					//System.out.println("new for ");
					Elements divs = row.select("td");
					if(divs.size()>0){
					Element ee = divs.get(1);
					Elements links = ee.select("a");
					//System.out.println(links.html());
					//System.out.println(links.attr("href"));
					EventIndex ei= new EventIndex(links.html(), links.attr("href"));
					l.add(ei);
					
					}
					}
				return l;
	}

	/**
	 * This is just for quicker testing 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		//wikiTablePrinter("http://en.wikipedia.org/wiki/UFC_180");
		String url = "http://en.wikipedia.org/wiki/List_of_UFC_events";
		//System.out.println(getwikiheader(urlToDoc(url)));

		List<EventIndex> l =  new ArrayList<EventIndex>();
		Document d =urlToDoc(url);
		
		Elements table= d.select("table tbody");
		Element e = table.get(1);
		Elements rows = e.select("tr");
		for (Element row:rows){
			//System.out.println("new for ");
			Elements divs = row.select("td");
			if(divs.size()>0){
			Element ee = divs.get(1);
			Elements links = ee.select("a");
			//System.out.println(links.html());
			//System.out.println(links.attr("href"));
			EventIndex ei= new EventIndex(links.html(), links.attr("href"));
			l.add(ei);
			
			}
			}
		System.out.println(l.get(2).toString());
			
		}
		
		

		
		
		
		


		

	
}
