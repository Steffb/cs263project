package test3.test3;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;



public class Datascraper {

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


	public static Document urlToDoc(String url) throws IOException{
		Document doc;
		return doc= Jsoup.connect(url).get();
	}


	public static String  getwikiheader(Document d){
		return d.select("#firstHeading span").html();
	}

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

	public static String  removeLink(Element e){
		String data;
		if(e.select("a").hasText()){
			data=e.select("a").html(); 
		}else{
			data=e.html(); }

		return data;

	}






	public static void main(String[] args) throws IOException {

		//wikiTablePrinter("http://en.wikipedia.org/wiki/UFC_180");
		String url = "http://en.wikipedia.org/wiki/UFC_180";
		//System.out.println(getwikiheader(urlToDoc(url)));

		UfcEvent u= new UfcEvent();
		u.eventname=getwikiheader(urlToDoc(url));
		u.fights=createFights(urlToDoc(url));


		System.out.println(u.toString());

	}
}
