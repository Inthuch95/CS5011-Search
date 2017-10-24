package main;

import map.Map;
import search.BFS;
import search.DFS;
import search.Search;

public class Search1 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[1]);
		int mapNumber = Integer.parseInt(args[1]);
		System.out.println("Map " + args[1]);
		Search searchAlg = new Search(map, mapNumber);
		if (args[0].equals("BFS")) {
			searchAlg = new BFS(map, mapNumber);
		} else if (args[0].equals("DFS")) {
			searchAlg = new DFS(map, mapNumber);
		} else{
			System.out.println("Invalid search algorithm");
			System.exit(0);
		}
		// Search for Bob, then search for safe goal position
		searchAlg.search('B');
		searchAlg.search('G');
	}
}
