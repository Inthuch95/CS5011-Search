package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import map.Node;

public class DFS extends Search {
	private Map<Node, Node> prev = new HashMap<Node, Node>();
	private ArrayList<Node> directions = new ArrayList<Node>();
	private ArrayList<Node> successors = new ArrayList<Node>();
	private Stack<Node> frontier = new Stack<Node>();
	private ArrayList<Node> explored = new ArrayList<Node>();
	private char[][] map = this.getMap();
	private Node initialNode = this.getStartNode();
	private int statesExplored = 0;
	public DFS(char[][] map) {
		super(map);
	}
	
	public void search() {
		findBob();
		findSafeZone();
    }
	
	private void findBob() {
		System.out.println("Start node: " + initialNode);
		frontier.add(initialNode);
		Node currentNode = new Node(0, 0);
		
		// find Bob
		while(!frontier.isEmpty()) {
			currentNode = frontier.pop();
			explored.add(currentNode);
//			System.out.println("current node: " + currentNode);
//			System.out.println("frontier: " + frontier);
//			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'B') {
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				statesExplored += 1;
				System.out.println("");
				System.out.println("Found Bob");
				printPath("Find Bob");
				System.out.println("State explored: " + statesExplored + "\n");
				// reset the states
				initialNode = currentNode;
				frontier.clear();
				explored.clear();
				directions.clear();
				prev.clear();
				break;
			}
			successors = Expand(currentNode, frontier, explored);
			for(Node node : successors) {
				frontier.push(node);
				prev.put(node, currentNode);
			}
			if (frontier.isEmpty()) {
				System.out.println("Failed to find Bob :(");
			}
			if (!currentNode.equals(initialNode)) {
				statesExplored += 1;
			}
		}
	}
	
	private void findSafeZone() {
		frontier.add(initialNode);
		Node currentNode = new Node(0, 0);
		// get to safety
		while(!frontier.isEmpty()) {
			currentNode = frontier.pop();
			explored.add(currentNode);
//			System.out.println("current node: " + currentNode);
//			System.out.println("frontier: " + frontier);
//			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'G') {
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				statesExplored += 1;
				System.out.println("");
				System.out.println("Arrived at safe zone");
				printPath("Find safe zone");
				System.out.println("State explored: " + statesExplored);
				break;
			}
			successors = Expand(currentNode, frontier, explored);
			for(Node node : successors) {
				frontier.push(node);
				prev.put(node, currentNode);
			}
			if (frontier.isEmpty()) {
				System.out.println("Failed to get to safety :(");
			}
			if (!currentNode.equals(initialNode)) {
				statesExplored += 1;
			}
		}
	}
	
	private ArrayList<Node> Expand(Node node, Stack<Node> frontier, 
			ArrayList<Node> explored) {
		ArrayList<Node> nextStates = getNextStates(node);
		ArrayList<Node> successors = new ArrayList<Node>();
		for(int i = 0; i < nextStates.size(); i++) {
			if (!explored.contains(nextStates.get(i)) &&
					!frontier.contains(nextStates.get(i))) {
				successors.add(nextStates.get(i));
			}
		}
		
		return successors;
	}
	
	private void printPath(String objective) {
		System.out.println("--------------------------------------");
		System.out.println("Depth First Search");
		System.out.println("Objective: " + objective);
		System.out.println("--------------------------------------");
		String line = "";
		Node node;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				node = new Node(i, j);
				if (directions.contains(node) && directions.indexOf(node) == 0) {
					line = "I";
				} else if (directions.contains(node) && map[i][j] == 'G' && 
						directions.indexOf(node) == directions.size()-1) {
					line = "G";
				} else if (directions.contains(node) && map[i][j] == 'B' && 
						directions.indexOf(node) == directions.size()-1) {
					line = "B";
				}else if (directions.contains(node)) {
					line = Integer.toString(directions.indexOf(node));
				}else if (!directions.contains(node) && map[i][j] == 'I') {
					line = "O";
				}else {
					line = map[i][j] + " ";
				}
				System.out.printf("%-4s", line);
			}
			System.out.println("");
			line = "";
		}
		System.out.println("--------------------------------------");
	}
}
