package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RescueOperation {
	private Deque<Characters> frontier = new ArrayDeque<Characters>();
	private Map<Characters, Characters> prev = new HashMap<Characters, Characters>();
	private ArrayList<Characters> successors = new ArrayList<Characters>();
	private ArrayList<Characters> explored = new ArrayList<Characters>();
	private ArrayList<Characters> journey = new ArrayList<Characters>();
	private Characters startState;
	private Characters goalState;
	private int stateExplored;
	public RescueOperation(Characters startState) {
		this.startState = startState;
		stateExplored = 0;
		goalState = new Characters(1, 1, 1, 1);
	}
	public void getJourney() {
		// BFS uses Deque to store frontier
		frontier.add(startState);
		Characters currentState = startState;
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
			for(Characters state : successors) {
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
		for (Characters state : journey) {
			System.out.println(state);
		}
		System.out.println("\nPath cost: " + (journey.size()-1));
		System.out.println("State explored: " + stateExplored);
	}
	
	private void constructPathToGoal(Characters currentState) {
		// construct path to goal by backtracking from goal to initial state
		for(Characters state = currentState; state != null; state = prev.get(state)) {
	        journey.add(state);
	    }
		Collections.reverse(journey);
	}
	
	private ArrayList<Characters> Expand(Characters currentState) {
		// expand the current state and return list of successor states
		ArrayList<Characters> nextStates = getNextStates(currentState);
		ArrayList<Characters > successors = new ArrayList<Characters>();
		for(int i = 0; i < nextStates.size(); i++) {
			if (!explored.contains(nextStates.get(i)) &&
					!frontier.contains(nextStates.get(i))) {
				successors.add(nextStates.get(i));
			}
		}
		
		return successors;
	}
	
	private ArrayList<Characters> getNextStates(Characters characters) {
		int robotPos = characters.getRobotPosition();
		int bobPos = characters.getBobPosition();
		int catPos = characters.getCatPosition();
		int dogPos = characters.getDogPosition();
		int posChange;
		if (robotPos == 1) {
			posChange = -1;
		} else {
			posChange = 1;
		}
		ArrayList<Characters> nextStates = new ArrayList<Characters>();
		// get the potential next moves
		// move robot alone
		if (isValidChild (characters, 'r', posChange)) {
			Characters c = new Characters(robotPos+posChange, bobPos, catPos, dogPos);
			nextStates.add(c);
		}
		
		// move bob
		if (isValidChild (characters, 'b', posChange)) {
			Characters c = new Characters(robotPos+posChange, bobPos+posChange, catPos, dogPos);
			nextStates.add(c);
		}
		
		// move cat
		if (isValidChild (characters, 'c', posChange)) {
			Characters c = new Characters(robotPos+posChange, bobPos, catPos+posChange, dogPos);
			nextStates.add(c);
		}
		
		// move dog
		if (isValidChild (characters, 'd', posChange)) {
			Characters c = new Characters(robotPos+posChange, bobPos, catPos, dogPos+posChange);
			nextStates.add(c);
		}
		
		return nextStates;
	}
	private boolean isValidChild (Characters characters, char character, int posChange) {
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
