package main;

import map.Map;
import search.CharactersNode;
import search.InformedSearch;
import search.RescueOperation;

public class Search3 {

	public static void main(String[] args) {
		// Use A* search with Manhattan distance in map 1
		char[][] map = Map.getChosenMap("1");
		int mapNumber = 1;
		String algorithm = "A*";
		String heuristicType = "M";
		System.out.println("Map " + 1);
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
		CharactersNode characters = new CharactersNode(0, 0, 0, 0);
		RescueOperation ro = new RescueOperation(characters);
		// search for a way to get everyone to safety (use BFS)
		ro.getJourney();
	}
}
