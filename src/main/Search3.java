package main;

import map.Map;
import search.Characters;
import search.InformedSearch;
import search.RescueOperation;

public class Search3 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[1]);
		int mapNumber = Integer.parseInt(args[1]);
		String heuristicType = args[0];
		String algorithm = "A*";
		System.out.println("Map " + args[1]);
		System.out.println("Algorithm: " + algorithm);
		InformedSearch searchAlg = new InformedSearch(map, mapNumber, heuristicType, 
				algorithm);
		// search for Bob, then search for safe goal position (use A*)
		searchAlg.search('B');
		if (!searchAlg.getDirectionBob().isEmpty()) {
			searchAlg.search('G');
		}
		searchAlg.printSummary();
		// initialize all characters at 'B'
		Characters characters = new Characters(0, 0, 0, 0);
		RescueOperation ro = new RescueOperation(characters);
		// search for a way to get everyone to safety (use BFS)
		ro.getJourney();
	}
}
