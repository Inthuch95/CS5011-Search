package main;

import map.Map;
import search.UninformedSearch;

public class Search1 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[1]);
		int mapNumber = Integer.parseInt(args[1]);
		String algorithm = args[0];
		System.out.println("Map " + args[1]);
		System.out.println("Algorithm: " + algorithm);
		UninformedSearch searchAlg = new UninformedSearch(map, mapNumber, algorithm);
		// search for Bob, then search for safe goal position
		searchAlg.search('B');
		// only search for goal position if the robot managed to find a way to get to Bob
		if (!searchAlg.getDirectionBob().isEmpty()) {
			searchAlg.search('G');
		}
		searchAlg.printSummary();
	}
}
