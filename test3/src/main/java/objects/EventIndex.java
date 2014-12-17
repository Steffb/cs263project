package objects;

import java.io.Serializable;

/**
 * 
 * @author steffenfb
 * 
 *	The event name and url in an object
 *	Used in the index page for sending right request 
 * 	Needs to be serializable for caching purposes
 * 
 */

public class EventIndex implements Serializable{
	
	public String name;
	public String url;
	
	
	
	public EventIndex(String name,String url) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.url = url;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name: "+name+" url: "+url;
	}

}
