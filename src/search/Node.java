package search;

public class Node {
	private int x, y;
	private double heuristic, score, pathCost;
	public Node(int x, int y) {
		// set x and y coordinates (row, column)
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	// estimated cost to goal h(n)
	public double getHeuristic() {
		return this.heuristic;
	}
	
	// cost from initial node to current node g(n)
	public double getPathCost() {
		return this.pathCost;
	}
	
	// score f(n) determines the order of node expansion for BestFS and A*
	public double getScore() {
		return this.score;
	}
	
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setHeuristic(String heuristic, Node goalNode) {
		// get the chosen heuristic
		if (heuristic.equals("M")) {
			// Manhattan distance
			this.heuristic = Math.abs(goalNode.getX() - this.getX()) + 
					Math.abs(goalNode.getY() - this.getY());
		} else if (heuristic.equals("E")) {
			// Euclidean distance
			this.heuristic = Math.sqrt(Math.pow(goalNode.getX() - this.getX(), 2) + 
					Math.pow(goalNode.getY() - this.getY(), 2));
		} else if (heuristic.equals("all")) {
			// choose the maximum between Manhattan distance and Euclidean distance 
			double manDist = Math.abs(goalNode.getX() - this.getX()) + 
					Math.abs(goalNode.getY() - this.getY());
			double euclidDist = Math.sqrt(Math.pow(goalNode.getX() - this.getX(), 2) + 
					Math.pow(goalNode.getY() - this.getY(), 2));
			this.heuristic = Math.max(manDist, euclidDist);
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		// print Node in the form: Node(row, column)
		String output;
		output = "Node(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
		
		return output;
	}
	
	@Override
	public boolean equals(Object node) {
	    // this will be used to check if particular node is in frontier/explored or not
		if (!(node instanceof Node)) {
	        return false;
	    }

	    Node n2 = (Node) node;

	    // custom equality check here.
	    return (this.x == n2.x) && (this.y == n2.y);
	}
}
