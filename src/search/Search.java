package search;

import java.util.ArrayList;

import map.Node;

public class Search {
	private Node startNode;
	private char[][] map;
	public Search(char[][] map){
		this.map = map;
		this.startNode = findStartNode();
	}
	
	public Node getStartNode() {
		return this.startNode;
	}
	
	public char[][] getMap() {
		return this.map;
	}
	
	public void search() {
		
	}
	
	public Node findStartNode() {
		Node node = new Node(0, 0);
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
		if(isValidChild(x - 1, y)) {
			nextStates.add(new Node(x - 1, y));
	    }
	        
        if(isValidChild(x + 1, y)) {
        	nextStates.add(new Node(x + 1, y));
        }
        
        if(isValidChild(x, y - 1)) {
        	nextStates.add(new Node(x, y - 1));
        }
        
        if(isValidChild(x, y + 1)) {
        	nextStates.add(new Node(x, y + 1));
        }
		
		return nextStates;
	}
	
	private boolean isValidChild(int x, int y) {
		return !(x < 0 || x >= this.map.length || y < 0 || y >= this.map[0].length) && 
				(this.map[x][y] != 'X');
	}
}
