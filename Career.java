
public class Career {
	private String name;
	private int salary;
	private boolean isTaken;
	
	public Career(String name, int salary) {
		this.name = name;
		this.salary = salary;
		isTaken = false;
	}
	
	public Career (String name) {
		this.name = name;
		salary = 0;
		isTaken = false;
	}
	
	//ACCESSOR METHODS
	public String getName() {
		return name;
	}
	
	public int getSalary() {
		return salary;
	}
	
	public boolean getIsTaken() {
		return isTaken;
	}
	
	//set isTaken to true
	public void takeCareer() {
		isTaken = true;
	}	
	
	public String toString() {
		return name + ", $" + salary;
	}
}
