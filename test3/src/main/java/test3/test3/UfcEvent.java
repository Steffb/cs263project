package test3.test3;

import java.util.ArrayList;


/**
 * 
 * @author steffenfb
 * Object for a ufc fightcard
 *  Has a title and list containing fights that again contains 
 *  the interesting information of all the fights
 *  
 */
public class UfcEvent {
	
	public String eventname;
	
	public ArrayList<Fight> fights;

	@Override
	public String toString() {
		return "UfcEvent [eventname=" + eventname + ", fights=" + fights + "]";
	}
	
	
	
	
	

}

class Fight{
	//need to do check if draw
	
	String weightclass;
	String winner;
	String loser;
	String method;
	String round;
	String time;
	String note;
	boolean stopped;
	boolean hasbeen;
	
	public String toString() {
		return "Weighclass: "+weightclass+" winner: "+winner+" loser: "+loser+" method: "+method+" round: "+round
				+" time: "+time+" note: "+note+" stopped: "+stopped+" hasbeen: "+hasbeen;
	}
	
}
