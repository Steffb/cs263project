package test3.test3;

public class TestClass {
	String one;
	String key;
	
	public TestClass() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "The values are "+one+" "+key;
	}



	public String getKey() {
		return key;
	}



	public String getOne() {
		return one;
	}



	public void setOne(String one) {
		this.one = one;
	}



	public void setKey(String key) {
		this.key = key;
	}

}
