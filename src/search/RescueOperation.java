package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RescueOperation {
	private Deque<CharactersNode> frontier = new ArrayDeque<CharactersNode>();
	private Map<CharactersNode, CharactersNode> prev = new HashMap<CharactersNode, CharactersNode>();
	private ArrayList<CharactersNode> successors = new ArrayList<CharactersNode>();
	private ArrayList<CharactersNode> explored = new ArrayList<CharactersNode>();
	private ArrayList<CharactersNode> journey = new ArrayList<CharactersNode>();
	private CharactersNode startState;
	private CharactersNode goalState;
	private int stateExplored;
	public RescueOperation(CharactersNode startState) {
		this.startState = startState;
		stateExplored = 0;
		goalState = new CharactersNode(1, 1, 1, 1);
	}
	public void getJourney() {
		// BFS uses Deque to store frontier
		frontier.add(startState);
		CharactersNode currentState = startState;
		// Perform search
		while(!frontier.isEmpty()) {
			// remove the the first state from the frontier 
			currentState = frontier.poll();
			explored.add(currentState);
			// check if the robot has evacuated everyone to safety
			if(currentState.equals(goalState)) {
				stateExplored += 1;
				constructPathToGoal(currentState);
				// print evacuation procedures
				printEvacProcedures();
				break;
			}
			// expand the states
			successors = Expand(currentState);
			for(CharactersNode state : successors) {
				prev.put(state, currentState);
				frontier.addLast(state);
			}
			// keep track of states explored
			if (!currentState.equals(startState)) {
				stateExplored += 1;
			}
		}
    }
	
	private void printEvacProcedures() {
		System.out.println("Evacuation procedures\n");
		// print every stage of the evacuation process
		for (CharactersNode state : journey) {
			System.out.println(state);
		}
		System.out.println("\nPath cost: " + (journey.size()-1));
		System.out.println("State explored: " + stateExplored);
	}
	
	private void constructPathToGoal(CharactersNode currentState) {
		// construct path to goal by backtracking from goal to initial state
		for(CharactersNode state = currentState; state != null; state = prev.get(state)) {
	        journey.add(state);
	    }
		Collections.reverse(journey);
	}
	
	private ArrayList<CharactersNode> Expand(CharactersNode currentState) {
		// expand the current state and return list of successor states
		ArrayList<CharactersNode> nextStates = getNextStates(currentState);
		ArrayList<CharactersNode > successors = new ArrayList<CharactersNode>();
		for(int i = 0; i < nextStates.size(); i++) {
			if (!explored.contains(nextStates.get(i)) &&
					!frontier.contains(nextStates.get(i))) {
				successors.add(nextStates.get(i));
			}
		}
		
		return successors;
	}
	
	private ArrayList<CharactersNode> getNextStates(CharactersNode characters) {
		int robotPos = characters.getRobotPosition();
		int bobPos = characters.getBobPosition();
		int catPos = characters.getCatPosition();
		int dogPos = characters.getDogPosition();
		int posChange;
		// move to G if at position B. Other wise move to position B
		if (robotPos == 1) {
			posChange = -1;
		} else {
			posChange = 1;
		}
		ArrayList<CharactersNode> nextStates = new ArrayList<CharactersNode>();
		// get the potential next moves
		// move robot alone
		if (isValidChild (characters, 'r', posChange)) {
			CharactersNode c = new CharactersNode(robotPos+posChange, bobPos, catPos, dogPos);
			nextStates.add(c);
		}
		
		// move bob
		if (isValidChild (characters, 'b', posChange)) {
			CharactersNode c = new CharactersNode(robotPos+posChange, bobPos+posChange, catPos, dogPos);
			nextStates.add(c);
		}
		
		// move cat
		if (isValidChild (characters, 'c', posChange)) {
			CharactersNode c = new CharactersNode(robotPos+posChange, bobPos, catPos+posChange, dogPos);
			nextStates.add(c);
		}
		
		// move dog
		if (isValidChild (characters, 'd', posChange)) {
			CharactersNode c = new CharactersNode(robotPos+posChange, bobPos, catPos, dogPos+posChange);
			nextStates.add(c);
		}
		
		return nextStates;
	}
	private boolean isValidChild (CharactersNode characters, char character, int posChange) {
		int robotPos = characters.getRobotPosition();
		int bobPos = characters.getBobPosition();
		int catPos = characters.getCatPosition();
		int dogPos = characters.getDogPosition();
		if (character == 'r') {
			// check if robot can be moved
			int newPos = robotPos + posChange;
			boolean invalidPos = (bobPos == dogPos && newPos != bobPos) || 
					(catPos == dogPos && newPos != catPos);
			if (!invalidPos) {
				return true;
			} else {
				return false;
			}
		} else if(character == 'b' && robotPos == bobPos) {
			// check if Bob can be moved
			int newPos = robotPos + posChange;
			boolean invalidPos = (catPos == dogPos && newPos != catPos);
			if (!invalidPos) {
				return true;
			} else {
				return false;
			}
		} else if (character == 'c' && robotPos == catPos) {
			// check if the cat can be moved
			int newPos = robotPos + posChange;
			boolean invalidPos = (bobPos == dogPos && newPos != bobPos);
			if (!invalidPos) {
				return true;
			} else {
				return false;
			}
		} else if (character == 'd' && robotPos == dogPos) {
			// check if the dog can be moved
			return true;
		} else {
			return false;
		}
	}
}
