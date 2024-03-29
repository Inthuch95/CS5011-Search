package search;

public class CharactersNode {
	private int bob, dog, cat, robot;
	public CharactersNode(int robot, int bob, int cat, int dog) {
		// a node contains position of robot, bob, cat, and dog
		this.robot = robot;
		this.bob = bob;
		this.cat = cat;
		this.dog = dog;
	}
	
	// methods to get position of each character
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
		// print Nodes and their positions
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
		if (!(c1 instanceof CharactersNode)) {
	        return false;
	    }

		CharactersNode c2 = (CharactersNode) c1;

	    // custom equality check here.
	    return (this.robot == c2.robot) && (this.bob == c2.bob) && (this.cat == c2.cat) && 
	    		 (this.dog == c2.dog);
	}
}
