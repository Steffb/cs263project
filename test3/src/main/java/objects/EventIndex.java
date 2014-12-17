package objects;

import java.io.Serializable;

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
