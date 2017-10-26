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
		// Search for Bob, then search for safe goal position
		searchAlg.search('B');
		searchAlg.search('G');
		searchAlg.printSummary();
	}

}
