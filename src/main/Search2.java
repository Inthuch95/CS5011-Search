package main;

import map.Map;
import search.InformedSearch;

public class Search2 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[2]);
		int mapNumber = Integer.parseInt(args[2]);
		String heuristicType = args[1];
		String algorithm = args[0];
		System.out.println("Map " + args[2]);
		System.out.println("Algorithm: " + algorithm);
		InformedSearch searchAlg = new InformedSearch(map, mapNumber, heuristicType, 
				algorithm);
		// search for Bob, then search for safe goal position
		searchAlg.search('B');
		// only search for goal position if the robot managed to find a way to get to Bob
		if (!searchAlg.getDirectionBob().isEmpty()) {
			searchAlg.search('G');
		}
		searchAlg.printSummary();
	}

}
