package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

import map.Node;

public class DFS extends Search {
	private Stack<Node> frontier = new Stack<Node>();
	private Node initialNode;
	private int statesExplored;
	public DFS(char[][] map, int mapNumber) {
		super(map, mapNumber);
		statesExplored = 0;
		initialNode = this.getStartNode();
	}
	
	public void search() {
		char[][] map = this.getMap();
		findBob(map);
		findSafeZone(map);
    }
	
	private void findBob(char[][] map) {
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> directions =  this.getDirections();
		ArrayList<Node> successors = this.getExplored();
		ArrayList<Node> explored = this.getExplored();
		System.out.println("Start node: " + initialNode + "\n");
		// DFS uses Stack to store frontier
		frontier.add(initialNode);
		Node currentNode = new Node(0, 0);
		
		// find Bob
		while(!frontier.isEmpty()) {
			currentNode = frontier.pop();
			explored.add(currentNode);
			System.out.println("current node: " + currentNode);
			System.out.println("frontier: " + frontier);
			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'B') {
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				statesExplored += 1;
				System.out.println("");
				System.out.println("Found Bob");
				printPath("Find Bob", map, directions);
				System.out.println("Path cost: " + (directions.size() - 1));
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
			frontier.addAll(successors);
			for(Node node : successors) {
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
	
	private void findSafeZone(char[][] map) {
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> directions =  this.getDirections();
		ArrayList<Node> successors = this.getExplored();
		ArrayList<Node> explored = this.getExplored();
		this.setGoalNode('G');
		frontier.add(initialNode);
		Node currentNode = new Node(0, 0);
		// get to safety
		while(!frontier.isEmpty()) {
			currentNode = frontier.pop();
			explored.add(currentNode);
			System.out.println("current node: " + currentNode);
			System.out.println("frontier: " + frontier);
			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'G') {
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				statesExplored += 1;
				System.out.println("");
				System.out.println("Arrived at safe zone");
				printPath("Find safe zone", map, directions);
				System.out.println("Path cost: " + (directions.size() - 1));
				System.out.println("State explored: " + statesExplored);
				break;
			}
			successors = Expand(currentNode, frontier, explored);
			frontier.addAll(successors);
			for(Node node : successors) {
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
	
	private void printPath(String objective, char[][] map, ArrayList<Node> directions) {
		System.out.println("--------------------------------------");
		System.out.println("Depth First Search");
		System.out.println("Map " + this.getMapNumber());
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
