package main;

import map.Map;
import search.AStar;
import search.BestFS;
import search.Search;

public class Search2 {

	public static void main(String[] args) {
		String heuristicType = args[1];
		char[][] map = Map.getChosenMap(args[2]);
		int mapNumber = Integer.parseInt(args[2]);
		System.out.println("Map " + args[2]);
		Search searchAlg = new Search(map, mapNumber);
		if (args[0].equals("BestFS")) {
			searchAlg = new BestFS(map, heuristicType, mapNumber);
		} else if (args[0].equals("AStar")) {
			searchAlg = new AStar(map, heuristicType, mapNumber);
		} else{
			System.out.println("Invalid search algorithm");
			System.exit(0);
		}
		searchAlg.search();
	}

}
