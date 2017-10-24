package map;

import java.util.ArrayList;
import java.util.Collections;

public class Node {
	private int x, y;
	private double heuristic, score;
	public Node(int x, int y) {
		// set x and y coordinates
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public double getHeuristic() {
		return this.heuristic;
	}
	
	public double getScore() {
		return this.score;
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
		} else if (heuristic.equals("C")) {
			// Chebyshev distance
			ArrayList<Double> chevDist = new ArrayList<Double>();
			chevDist.add((double) Math.abs(goalNode.getX() - this.getX()));
			chevDist.add((double) Math.abs(goalNode.getY() - this.getY()));
			Collections.sort(chevDist);
			this.heuristic = chevDist.get(chevDist.size() - 1);
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

	    // Custom equality check here.
	    return (this.x == n2.x) && (this.y == n2.y);
	}
}
