package map;

public class Node {
	private int x, y;
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		String output;
		output = "Node(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
		
		return output;
	}
	
	@Override
	public boolean equals(Object node) {
	    if (!(node instanceof Node)) {
	        return false;
	    }

	    Node n2 = (Node) node;

	    // Custom equality check here.
	    return (this.x == n2.x) && (this.y == n2.y);
	}
}
