package search;

public class Characters {
	private int bob, dog, cat, robot;
	public Characters(int robot, int bob, int cat, int dog) {
		this.robot = robot;
		this.bob = bob;
		this.cat = cat;
		this.dog = dog;
	}
	
	public int getRobotPosition() {
		return this.robot;
	}
	
	public int getBobPosition() {
		return this.bob;
	}
	
	public int getCatPosition() {
		return this.cat;
	}
	
	public int getDogPosition() {
		return this.dog;
	}
	
	@Override
	public String toString() {
		// print Node in the form: Node(row, column)
		String left = "Position B: ";
		String right = "Position G: ";
		String output;
		if (robot == 0) {
			left += "Robot ";
		} else {
			right += "Robot ";
		}
		
		if (bob == 0) {
			left += "Bob ";
		} else {
			right += "Bob ";
		}
		
		if (cat == 0) {
			left += "Cat ";
		} else {
			right += "Cat ";
		}
		
		if (dog == 0) {
			left += "Dog ";
		} else {
			right += "Dog ";
		}
		output = String.format("%-40s%-40s", left, right);
		
		return output;
	}
	
	@Override
	public boolean equals(Object c1) {
	    // this will be used to check if the state of the characters
		if (!(c1 instanceof Characters)) {
	        return false;
	    }

		Characters c2 = (Characters) c1;

	    // custom equality check here.
	    return (this.robot == c2.robot) && (this.bob == c2.bob) && (this.cat == c2.cat) && 
	    		 (this.dog == c2.dog);
	}
}
