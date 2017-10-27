package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BidirectionalSearch extends Search {
	private PriorityQueue<Node> frontier;
	private PriorityQueue<Node> frontierEnd;
	private String heuristicType;
	private ArrayList<Node> directionBob = new ArrayList<Node>();
	private ArrayList<Node> directionGoal = new ArrayList<Node>();
	private String algorithm;
	public BidirectionalSearch(char[][] map, int mapNumber, String heuristicType, 
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
		frontierEnd = new PriorityQueue<Node>(new Comparator<Node>() {
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
		// from start
		clearData();
		Map<Node, Node> prev = this.getPrev();
		ArrayList<Node> successors = this.getSuccessors();
		ArrayList<Node> explored = this.getExplored();
		Node startNode = this.getStartNode();
		this.setGoalNode(goal);
		this.getGoalNode();
		// BFS uses Deque to store frontier
		frontier.add(startNode);
		Node currentNode = startNode;
		
		// from goal
		Map<Node, Node> prevEnd = new HashMap<Node, Node>();
		ArrayList<Node> successorsEnd = new ArrayList<Node>();
		ArrayList<Node> exploredEnd = new ArrayList<Node>();
		Node startNodeEnd = this.getGoalNode();
		Node goalNodeEnd = new Node(startNode.getX(), startNode.getY());
		frontierEnd.add(startNodeEnd);
		Node currentNodeEnd = startNodeEnd;
		
		// perform search
		while (!frontier.isEmpty() && !frontierEnd.isEmpty()) {
			// remove the the first node from the frontier 
			currentNode = frontier.poll();
			explored.add(currentNode);
			currentNodeEnd = frontierEnd.poll();
			exploredEnd.add(currentNode);
			// check if the robot has reached the goal
			if(currentNode.equals(currentNodeEnd)) {
				System.out.println("Path found");
				break;
			}
			// expand the nodes
			successors = Expand(currentNode, frontier, explored);
			successorsEnd = Expand(currentNodeEnd, frontierEnd, exploredEnd);
			frontier.addAll(successors);
			frontierEnd.addAll(successorsEnd);
			for(Node node : successors) {
				prev.put(node, currentNode);
				prevEnd.put(node, currentNode);
			}
			// keep track of states explored
			if (!currentNode.equals(startNode)) {
				this.setStateExplored(this.getStatesExplored() + 1);
			}
		}
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
	
	private void clearData() {
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
						frontier.add(nextStates.get(i));
					}
				}
			}
		}
		
		return successors;
	}
}
