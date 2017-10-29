package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class InformedSearch extends Search {
	private PriorityQueue<Node> frontier;
	private String heuristicType;
	private ArrayList<Node> directionBob = new ArrayList<Node>();
	private ArrayList<Node> directionGoal = new ArrayList<Node>();
	private String algorithm;
	public InformedSearch(char[][] map, int mapNumber, String heuristicType, 
			String algorithm) {
		super(map, mapNumber);
		this.heuristicType = heuristicType;
		this.algorithm = algorithm;
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
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> successors = this.getSuccessors();
		ArrayList<Node> explored = this.getExplored();
		Node startNode = this.getStartNode();
		startNode.setPathCost(0);
		this.setGoalNode(goal);
		System.out.println("Start node: " + startNode + "\n");
		// BFS uses Deque to store frontier
		frontier.add(startNode);
		Node currentNode = startNode;
		
		// Perform search
		while(!frontier.isEmpty()) {
			// remove the the first node from the frontier 
			currentNode = frontier.poll();
			printStatus(goal, currentNode, explored);
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
			frontier.addAll(successors);
			for(Node node : successors) {
				prev.put(node, currentNode);
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
	
	private ArrayList<Node> Expand(Node node, PriorityQueue<Node> frontier, 
			ArrayList<Node> explored) {
		// retrieve successors nodes
		ArrayList<Node> nextStates = getNextStates(node);
		ArrayList<Node> successors = new ArrayList<Node>();
		for(int i = 0; i < nextStates.size(); i++) {
			if (algorithm.equals("BestFS")) {
				if (!explored.contains(nextStates.get(i)) &&
						!frontier.contains(nextStates.get(i))) {
					successors.add(nextStates.get(i));
				}
			}
			/**  
			 * if state is in a node in frontier but with higher PATH-COST then
			 * replace old node with new node (A*)
			 */
			else {
				if (!explored.contains(nextStates.get(i)) &&
						!frontier.contains(nextStates.get(i))) {
					successors.add(nextStates.get(i));
				} else if (frontier.contains(nextStates.get(i))) {
					Node oldNode = new Node(0, 0);
					for (Node n : frontier) {
						if (n.equals(nextStates.get(i))) {
							oldNode = n;
							break;
						}
					}
					if (oldNode.getPathCost() > nextStates.get(i).getPathCost()) {
						frontier.remove(oldNode);
						successors.add(nextStates.get(i));
					}
				}
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
		 * BestFS score f(n) = h(n)
		 * A* score f(n) = g(n) + h(n)
		 * 
		 * h(n) = estimated cost of the path from the state at node n to the goal
		 * g(n) = the cost of the path from the start to the node n 
		 */
		// North
        if(isValidChild(x - 1, y)) {
        	Node up = new Node(x - 1, y);
        	up.setPathCost(node.getPathCost() + 1);
        	up.setHeuristic(heuristicType, goalNode);
        	if (algorithm.equals("BestFS")) {
        		up.setScore(up.getHeuristic());
        	} else {
        		up.setScore(up.getHeuristic() + up.getPathCost());
        	}
        	nextStates.add(up);
        }
        
        // South
        if(isValidChild(x + 1, y)) {
        	Node down = new Node(x + 1, y);
        	down.setPathCost(node.getPathCost() + 1);
        	down.setHeuristic(heuristicType, goalNode);
        	if (algorithm.equals("BestFS")) {
        		down.setScore(down.getHeuristic());
        	} else {
        		down.setScore(down.getHeuristic() + down.getPathCost());
        	}
        	nextStates.add(down);
        }
        
        // West
        if(isValidChild(x, y - 1)) {
        	Node left = new Node(x, y - 1);
        	left.setPathCost(node.getPathCost() + 1);
        	left.setHeuristic(heuristicType, goalNode);
        	if (algorithm.equals("BestFS")) {
        		left.setScore(left.getHeuristic());
        	} else {
        		left.setScore(left.getHeuristic() + left.getPathCost());
        	}
        	nextStates.add(left);
        }
        
        // East
 		if(isValidChild(x, y + 1)) {
 			Node right = new Node(x, y + 1);
 			right.setPathCost(node.getPathCost() + 1);
 			right.setHeuristic(heuristicType, goalNode);
 			if (algorithm.equals("BestFS")) {
        		right.setScore(right.getHeuristic());
        	} else {
        		right.setScore(right.getHeuristic() + right.getPathCost());
        	}
 			nextStates.add(right);
 	    }
		
		return nextStates;
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
	
	private void printObjectiveCompleted(char goal) {
		if (goal == 'B') {
			System.out.println("Found Bob!");
		}
		else {
			System.out.println("Arrived at safe zone");
		}
	}
	
	private void printStatus(char goal, Node currentNode, ArrayList<Node> explored) {
		// print current node, frontier, explored
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
		// show the path that the robot took in grid format
		String heuristic = checkHeuristic();
		System.out.println("--------------------------------------");
		if (algorithm.equals("BestFS")) {
			System.out.println("Best First Search");
		} else {
			System.out.println("A*");
		}
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
		} else {
			heuristic =  "Combination";
		}
		
		return heuristic;
	}
}
