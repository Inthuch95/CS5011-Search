package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Search {
	private Map<Node, Node> prev = new HashMap<Node, Node>();
	private ArrayList<Node> directions = new ArrayList<Node>();
	private ArrayList<Node> successors = new ArrayList<Node>();
	private ArrayList<Node> explored = new ArrayList<Node>();
	private Node startNode;
	private Node goalNode;
	private char[][] map;
	private int mapNumber, statesExplored;
	public Search(char[][] map, int mapNumber){
		this.map = map;
		this.startNode = findStartNode();
		this.setMapNumber(mapNumber);
		this.setStateExplored(0);
	}
	
	public Node getStartNode() {
		return this.startNode;
	}
	
	public char[][] getMap() {
		return this.map;
	}
	
	public ArrayList<Node> getDirections() {
		return this.directions;
	}
	
	public void constructPathToGoal(Node currentNode) {
		// construct path to goal by backtracking from goal to initial position
		for(Node node = currentNode; node != null; node = prev.get(node)) {
	        directions.add(node);
	    }
		Collections.reverse(directions);
	}
	
	public ArrayList<Node> getSuccessors() {
		return this.successors;
	}
	
	public ArrayList<Node> getExplored() {
		return this.explored;
	}
	
	public Map<Node, Node> getPrev() {
		// get parent node of current node
		return this.prev;
	} 
	
	public Node getGoalNode() {
		return this.goalNode;
	}
	
	public int getMapNumber() {
		// get chosen map number
		return this.mapNumber;
	}
	
	public int getStatesExplored() {
		// count how many states have been explored
		return this.statesExplored;
	}
	
	public void search(char goal) {

	}
	
	public void setStateExplored(int statesExplored) {
		this.statesExplored = statesExplored;
	}
	
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	
	public void setMapNumber(int mapNumber) {
		this.mapNumber = mapNumber;
	}
	
	public void setGoalNode(char goal) {
		// goal can be Bob (B) or safe zone (G) 
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == goal) {
					goalNode = new Node(i, j);
					break;
				}
			}
		}
	}
	
	public Node findStartNode() {
		Node node = new Node(0, 0);
		// find robot's initial position
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[i].length; j++) {
				if (this.map[i][j] == 'I') {
					node.setX(i);
					node.setY(j);
					break;
				}
			}
		}
		
		return node;
	}
	
	public ArrayList<Node> getNextStates(Node node) {
		int x = node.getX();
		int y = node.getY();
		ArrayList<Node> nextStates = new ArrayList<Node>();
		// get the potential next moves (North, South, East, West)
		// North
        if(isValidChild(x - 1, y)) {
        	nextStates.add(new Node(x - 1, y));
        }
        
        // West
        if(isValidChild(x, y - 1)) {
        	nextStates.add(new Node(x, y - 1));
        }
        
        // East
        if(isValidChild(x, y + 1)) {
        	nextStates.add(new Node(x, y + 1));
        }
        
        // South
 		if(isValidChild(x + 1, y)) {
 			nextStates.add(new Node(x + 1, y));
 	    }
		
		return nextStates;
	}
	
	protected boolean isValidChild(int x, int y) {
		// validate if the move is legal or not (out of bound, blocked, etc.)
		return !(x < 0 || x >= map.length || y < 0 || y >= map[0].length) && 
				(map[x][y] != 'X');
	}
}
