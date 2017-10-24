package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import map.Node;

public class Search {
	private Map<Node, Node> prev = new HashMap<Node, Node>();
	private ArrayList<Node> directions = new ArrayList<Node>();
	private ArrayList<Node> successors = new ArrayList<Node>();
	private ArrayList<Node> explored = new ArrayList<Node>();
	private Node startNode;
	private Node goalNode = new Node(0, 0);
	private char[][] map;
	private int mapNumber;
	public Search(char[][] map, int mapNumber){
		this.map = map;
		this.startNode = findStartNode();
		this.setGoalNode('B');
		this.setMapNumber(mapNumber);
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
	
	public ArrayList<Node> getSuccessors() {
		return this.successors;
	}
	
	public ArrayList<Node> getExplored() {
		return this.explored;
	}
	
	public Map<Node, Node> getPrev() {
		return this.prev;
	} 
	
	public Node getGoalNode() {
		return this.goalNode;
	}
	
	public int getMapNumber() {
		return this.mapNumber;
	}
	
	public void search() {
		
	}
	
	public void setMapNumber(int mapNumber) {
		this.mapNumber = mapNumber;
	}
	
	public void setGoalNode(char goal) {
		// goal can be Bob (B) or safe zone (G) 
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == goal) {
					goalNode.setX(i);
					goalNode.setY(j);
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
		// get the potential next moves (up, down, left, right)
		// up
        if(isValidChild(x - 1, y)) {
        	nextStates.add(new Node(x - 1, y));
        }
        
        // left
        if(isValidChild(x, y - 1)) {
        	nextStates.add(new Node(x, y - 1));
        }
        
        // right
        if(isValidChild(x, y + 1)) {
        	nextStates.add(new Node(x, y + 1));
        }
        
        // down
 		if(isValidChild(x + 1, y)) {
 			nextStates.add(new Node(x + 1, y));
 	    }
 		// the order of node expansion is random for uninformed search (BFS and DFS)
 		Collections.shuffle(nextStates);
		
		return nextStates;
	}
	
	protected boolean isValidChild(int x, int y) {
		// validate if the move is legal or not (out of bound, blocked, etc.)
		return !(x < 0 || x >= map.length || y < 0 || y >= map[0].length) && 
				(map[x][y] != 'X');
	}
}
