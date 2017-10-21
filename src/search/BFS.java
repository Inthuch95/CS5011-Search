package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import map.Node;

public class BFS extends Search {
	public BFS(char[][] map) {
		super(map);
	}
	public void search() {
		char[][] map = this.getMap();
		Node initialNode = this.getStartNode();
		System.out.println("Start node: " + initialNode);
		Deque<Node> frontier = new ArrayDeque<Node>();
		ArrayList<Node> explored = new ArrayList<Node>();
		frontier.add(initialNode);
		Node currentNode;
		int count = 0;
		
		// find Bob
		while(!frontier.isEmpty()) {
			currentNode = frontier.remove();
			explored.add(currentNode);
//			System.out.println("current node: " + currentNode);
//			System.out.println("frontier: " + frontier);
//			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'B') {
				System.out.println("");
				System.out.println("Found Bob");
				System.out.println("State explored: " + count + "\n");
				// reset the states
				initialNode = currentNode;
				frontier.clear();
				explored.clear();
				count = 0;
				break;
			}
			frontier.addAll(Expand(currentNode, map, frontier, explored));
			if (frontier.isEmpty()) {
				System.out.println("Failed to find Bob :(");
			}
			count++;
		}
		
		frontier.add(initialNode);
		// get to safety
		while(!frontier.isEmpty()) {
			currentNode = frontier.remove();
			explored.add(currentNode);
//			System.out.println("current node: " + currentNode);
//			System.out.println("frontier: " + frontier);
//			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'G') {
				System.out.println("");
				System.out.println("Arrived at safe zone");
				System.out.println("State explored: " + count);
				break;
			}
			frontier.addAll(Expand(currentNode, map, frontier, explored));
			if (frontier.isEmpty()) {
				System.out.println("Failed to get to safety :(");
			}
			count++;
		}
    }
	
	private ArrayList<Node> Expand(Node node, char[][] map, Deque<Node> frontier, 
			ArrayList<Node> explored) {
		ArrayList<Node> nextStates = this.getNextStates(node);
		ArrayList<Node> successors = new ArrayList<Node>();
		for(int i = 0; i < nextStates.size(); i++) {
			if (!explored.contains(nextStates.get(i)) &&
					!frontier.contains(nextStates.get(i))) {
				successors.add(nextStates.get(i));
			}
		}
		
		return successors;
	}
}
