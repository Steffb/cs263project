package test3.test3;

public class PlJson {
	
	
	String league;
	
	/**
	 * 
	 * @return Returns normal string
	 */

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		System.out.println("heeey");
		this.league = league;
	}
	
	/**
	 * @return return string
	 */

	@Override
	public String toString() {
		return "PlJson [league=" + league + "]";
	}
	
	

}
