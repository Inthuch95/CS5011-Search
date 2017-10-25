package main;

import map.Map;
import search.UninformedSearch;

public class Search1 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[1]);
		int mapNumber = Integer.parseInt(args[1]);
		System.out.println("Map " + args[1]);
		String algorithm = args[0];
		UninformedSearch searchAlg = new UninformedSearch(map, mapNumber, algorithm);
		// Search for Bob, then search for safe goal position
		searchAlg.search('B');
		searchAlg.search('G');
	}
}
