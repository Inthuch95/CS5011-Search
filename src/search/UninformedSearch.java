package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

public class UninformedSearch extends Search {
	private Deque<Node> frontier = new ArrayDeque<Node>();
	private ArrayList<Node> directionBob = new ArrayList<Node>();
	private ArrayList<Node> directionGoal = new ArrayList<Node>();
	private String algorithm;
	public UninformedSearch(char[][] map, int mapNumber, String algorithm) {
		super(map, mapNumber);
		this.algorithm = algorithm;
	}
	
	public void search(char goal) {
		clearData();
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> successors = this.getSuccessors();
		ArrayList<Node> explored = this.getExplored();
		Node startNode = this.getStartNode();
		this.setGoalNode(goal);
		System.out.println("Start node: " + startNode + "\n");
		// BFS uses Deque to store frontier
		frontier.add(startNode);
		Node currentNode = startNode;
		printStatus(goal, currentNode, explored);
		
		// Perform search
		while(!frontier.isEmpty()) {
			// remove the the first node from the frontier 
			currentNode = frontier.poll();
			explored.add(currentNode);
			// check if the robot has reached the goal
			if(currentNode.equals(this.getGoalNode())) {
				// assign new initial state
				this.setStartNode(currentNode);
				this.constructPathToGoal(currentNode);
				this.setStateExplored(this.getStatesExplored() + 1);
				saveObjectivePath(goal);
				printObjectiveCompleted(goal);
				break;
			}
			// expand the nodes
			successors = Expand(currentNode, frontier, explored);
			for(Node node : successors) {
				prev.put(node, currentNode);
				if (algorithm.equals("BFS")) {
					frontier.addLast(node);
				} else {
					frontier.addFirst(node);
				}
			}
			printStatus(goal, currentNode, explored);
			checkFailure(goal);
			// keep track of states explored
			if (!currentNode.equals(startNode)) {
				this.setStateExplored(this.getStatesExplored() + 1);
			}
		}
    }
	
	public void printSummary() {
		// print the search summary once the robot reached the goal
		int pathCost = (directionBob.size() + directionGoal.size()) - 2;
		System.out.println("\nSummary");
		if (directionBob.isEmpty() || directionGoal.isEmpty()) {
			System.out.println("Unsuccessful search operation");
			System.out.println("Algorithm: " + algorithm);
			if (directionBob.isEmpty()) {
				System.out.println("Cannot get to Bob");
			} else {
				printPath("Find Bob", this.getMap(), directionBob);
				System.out.println("Cannot get Bob to safety");
			}
		} else {
			printPath("Find Bob", this.getMap(), directionBob);
			printPath("Find safe zone", this.getMap(), directionGoal);
			System.out.println("Path cost: " + pathCost);
		}
		System.out.println("State explored: " + this.getStatesExplored() + "\n");
	}
	
	public ArrayList<Node> getDirectionBob() {
		return this.directionBob;
	}
	
	private void saveObjectivePath(char goal) {
		// save path to Bob or path to goal
		ArrayList<Node> directions = this.getDirections();
		for (Node node : directions) {
			if (goal == 'B') {
				directionBob.add(node);
			} else {
				directionGoal.add(node);
			}
		}
	}
	
	private void checkFailure(char goal) {
		// if there's no more nodes to expand then the search has failed
		if (frontier.isEmpty() && goal == 'B') {
			System.out.println("Failed to find Bob");
		} else if (frontier.isEmpty() && goal == 'G') {
			System.out.println("Failed to get to safety");
		}
	}
	
	private void clearData () {
		// clear everything in order to prepare for new search operation
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> directions =  this.getDirections();
		ArrayList<Node> successors = this.getSuccessors();
		ArrayList<Node> explored = this.getExplored();
		frontier.clear();
		explored.clear();
		directions.clear();
		prev.clear();
		successors.clear();
	}
	
	private ArrayList<Node> Expand(Node node, Deque<Node> frontier, 
			ArrayList<Node> explored) {
		// expand the nodes and return list of successor nodes
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
	
	private void printObjectiveCompleted(char goal) {
		// print objective complete message
		if (goal == 'B') {
			System.out.println("Found Bob!");
		}
		else {
			System.out.println("Arrived at safe zone");
		}
	}
	
	private void printStatus(char goal, Node currentNode, ArrayList<Node> explored) {
		// print the current node, frontier, explored 
		System.out.println("--------------------------------------");
		String printOut = "";
		Node node;
		char[][] map = this.getMap();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				node = new Node(i, j);
				if (node.equals(currentNode)) {
					printOut = "C";
				} else if (frontier.contains(node)) {
					printOut = "F";
				} else if (explored.contains(node)) {
					printOut = "E";
				} else {
					printOut = Character.toString(map[i][j]);
				}
				System.out.printf("%-4s", printOut);
			}
			System.out.println("");
			printOut = "";
		}
		System.out.println("--------------------------------------");
		
	}
	
	private void printPath(String objective, char[][] map, ArrayList<Node> directions) {
		// print path from initial position to goal
		System.out.println("--------------------------------------");
		if (algorithm.equals("BFS")) {
			System.out.println("Breadth First Search");
		} else {
			System.out.println("Depth First Search");
		}
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
