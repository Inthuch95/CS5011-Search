package main;

import map.Map;
import search.BFS;
import search.DFS;
import search.Search;

public class Search1 {

	public static void main(String[] args) {
		char[][] map = Map.getChosenMap(args[1]);
		Search searchAlg = new Search(map);
		if (args[0].equals("BFS")) {
			searchAlg = new BFS(map);
		} else if (args[0].equals("DFS")) {
			searchAlg = new DFS(map);
		} else{
			System.out.println("Invalid search algorithm");
			System.exit(0);
		}
		searchAlg.search();
	}
}
