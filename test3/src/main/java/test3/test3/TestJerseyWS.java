package test3.test3;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/jerseyws")
public class TestJerseyWS {
	@GET
	@Path("/test")
	public String testMethod(){
		return "This is asssjijijij test2323 second";
	}
	public String testMethod3(){
		return "Is this showing";
	}
	
	@GET
	@Path("/test2")
	public String testMethod2(){
		return "This is asss test2.1";
	}
	
	@GET @Path("sync/")
	public String my(){
		return "this works";
				
	}

	
}

