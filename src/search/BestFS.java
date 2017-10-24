package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import map.Node;

public class BestFS extends Search {
	private PriorityQueue<Node> frontier;
	private String heuristicType;
	public BestFS(char[][] map, String heuristicType, int mapNumber) {
		super(map, mapNumber);
		this.heuristicType = heuristicType;
		frontier = new PriorityQueue<Node>(new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				if (n1.getScore() < n2.getScore()) {
					return -1;
				}
				if (n1.getScore() > n2.getScore()) {
					return 1;
				}
				return 0;
			}
		});
	}
	
	public void search(char goal) {
		clearData();
		char[][] map = this.getMap();
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> successors = this.getSuccessors();
		ArrayList<Node> explored = this.getExplored();
		Node startNode = this.getStartNode();
		System.out.println("Start node: " + startNode + "\n");
		// BestFS uses PriorityQueue to store frontier
		frontier.add(startNode);
		Node currentNode = new Node(0, 0);
		
		// Perform Best First Search
		while(!frontier.isEmpty()) {
			// remove the first item from the frontier
			currentNode = frontier.poll();
			explored.add(currentNode);
			printStatus(goal, currentNode, explored);
			// check if the robot has reached the goal
			if(map[currentNode.getX()][currentNode.getY()] == goal) {
				this.constructPathToGoal(currentNode);
				this.setStateExplored(this.getStatesExplored() + 1);
				printSummary(goal);
				// assign new initial state
				this.setStartNode(currentNode);
				break;
			}
			// expand the nodes
			successors = Expand(currentNode, frontier, explored);
			frontier.addAll(successors);
			for(Node node : successors) {
				prev.put(node, currentNode);
			}
			checkFailure(goal);
			// keep track of states explored
			if (!currentNode.equals(startNode)) {
				this.setStateExplored(this.getStatesExplored() + 1);
			}
		}
    }
	
	private void printSummary(char goal) {
		// print the search summary once the robot reached the goal
		System.out.println("");
		if (goal == 'B') {
			System.out.println("Found Bob");
			printPath("Find Bob", this.getMap(), this.getDirections());
		} else {
			System.out.println("Arrived at safe zone");
			printPath("Find safe zone", this.getMap(), this.getDirections());
		}
		System.out.println("Path cost: " + (this.getDirections().size() - 1));
		System.out.println("State explored: " + this.getStatesExplored() + "\n");
	}
	
	private void printStatus(char goal, Node currentNode, ArrayList<Node> explored) {
		System.out.println("current node: " + currentNode);
		System.out.println("frontier: " + frontier);
		System.out.println("explored: " + explored + "\n");
	}
	
	private void checkFailure(char goal) {
		// if there's no more nodes to expand then the search has failed
		if (frontier.isEmpty() && goal == 'B') {
			System.out.println("Failed to find Bob :(");
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
	
	private ArrayList<Node> Expand(Node node, PriorityQueue<Node> frontier, 
			ArrayList<Node> explored) {
		// retrieve successors nodes
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
	
	public ArrayList<Node> getNextStates(Node node) {
		int x = node.getX();
		int y = node.getY();
		Node goalNode = this.getGoalNode();
		ArrayList<Node> nextStates = new ArrayList<Node>();	
		/**
		 * BestFS uses estimated cost from current node to goal as a score 
		 */
		// up
        if(isValidChild(x - 1, y)) {
        	Node up = new Node(x - 1, y);
        	up.setHeuristic(heuristicType, goalNode);
        	up.setScore(up.getHeuristic());
        	nextStates.add(up);
        }
        
        // down
        if(isValidChild(x + 1, y)) {
        	Node down = new Node(x + 1, y);
        	down.setHeuristic(heuristicType, goalNode);
        	down.setScore(down.getHeuristic());
        	nextStates.add(down);
        }
        
        // left
        if(isValidChild(x, y - 1)) {
        	Node left = new Node(x, y - 1);
        	left.setHeuristic(heuristicType, goalNode);
        	left.setScore(left.getHeuristic());
        	nextStates.add(left);
        }
        
        // right
 		if(isValidChild(x, y + 1)) {
 			Node right = new Node(x, y + 1);
 			right.setHeuristic(heuristicType, goalNode);
 			right.setScore(right.getHeuristic());
 			nextStates.add(right);
 	    }
		
		return nextStates;
	}
	
	private void printPath(String objective, char[][] map, ArrayList<Node> directions) {
		// show the path that the robot took in grid format
		String heuristic = checkHeuristic();
		System.out.println("--------------------------------------");
		System.out.println("Best First Search");
		System.out.println("Map " + this.getMapNumber());
		System.out.println("Objective: " + objective);
		System.out.println("Heuristic: " + heuristic);
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
	
	private String checkHeuristic() {
		// determines the chosen heuristic
		String heuristic = "";
		if (heuristicType.equals("M")) {
			heuristic =  "Manhattan distance";
		} else if (heuristicType.equals("E")) {
			heuristic =  "Euclidean distance";
		} else if (heuristicType.equals("C")) {
			heuristic =  "Chebyshev distance";
		}
		
		return heuristic;
	}
}
