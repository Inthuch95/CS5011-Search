package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import map.Node;

public class BFS extends Search {
	public BFS(char[][] map) {
		super(map);
	}
	public void search() {
		Map<Node, Node> prev = new HashMap<Node, Node>();
		ArrayList<Node> directions = new ArrayList<Node>();
		ArrayList<Node> successors = new ArrayList<Node>();
		Deque<Node> frontier = new ArrayDeque<Node>();
		ArrayList<Node> explored = new ArrayList<Node>();
		char[][] map = this.getMap();
		Node initialNode = this.getStartNode();
		System.out.println("Start node: " + initialNode);
		
		frontier.add(initialNode);
		Node currentNode = new Node(0, 0);
		int count = 0;
		
		// find Bob
		while(!frontier.isEmpty()) {
			currentNode = frontier.remove();
			explored.add(currentNode);
//			System.out.println("current node: " + currentNode);
//			System.out.println("frontier: " + frontier);
//			System.out.println("explored: " + explored + "\n");
			if(map[currentNode.getX()][currentNode.getY()] == 'B') {
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				count += 1;
				System.out.println("");
				System.out.println("Found Bob");
				System.out.println("Path: " + directions);
				System.out.println("State explored: " + count + "\n");
				// reset the states
				initialNode = currentNode;
				frontier.clear();
				explored.clear();
				directions.clear();
				prev.clear();
				break;
			}
			successors = Expand(currentNode, map, frontier, explored);
			frontier.addAll(successors);
			for(Node node : successors) {
				prev.put(node, currentNode);
			}
			if (frontier.isEmpty()) {
				System.out.println("Failed to find Bob :(");
			}
			count += 1;
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
				for(Node node = currentNode; node != null; node = prev.get(node)) {
			        directions.add(node);
			    }
				Collections.reverse(directions);
				count += 1;
				System.out.println("");
				System.out.println("Arrived at safe zone");
				System.out.println("Path: " + directions);
				System.out.println("State explored: " + count);
				break;
			}
			successors = Expand(currentNode, map, frontier, explored);
			frontier.addAll(successors);
			for(Node node : successors) {
				prev.put(node, currentNode);
			}
			if (frontier.isEmpty()) {
				System.out.println("Failed to get to safety :(");
			}
			count += 1;
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
