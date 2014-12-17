package objects;

import java.io.IOException;
import java.io.Serializable;

import com.google.gson.Gson;

import test3.test3.JsonServlet;

public class SoccerIndex implements Serializable{
	
	public int id;
    public String caption;
    public String league;
    public int year;
    public String lastUpdated;
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return id+"\t"+caption+"\t"+league+"\t"+year;
    }

    public static void main(String[] args) throws IOException{
    	String uri="http://www.football-data.org/soccerseasons/";
		String jsonStr=JsonServlet.reqToStr(uri);
		Gson gson = new Gson();
		SoccerIndex[] soccerindex =  gson.fromJson(jsonStr, SoccerIndex[].class);
		
		for (int i = 0; i < soccerindex.length; i++) {
			
		
			System.out.println(soccerindex[i].id+"\t"+soccerindex[i].caption+"\t"+soccerindex[i].league+"\t"+soccerindex[i].year);
		}
		
    	
    }

}

