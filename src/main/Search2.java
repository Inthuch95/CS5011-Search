package main;

import map.Map;
import search.AStar;
import search.BestFS;
import search.Search;

public class Search2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] map = Map.getChosenMap(args[1]);
		Search searchAlg = new Search(map);
		if (args[0].equals("BestFS")) {
			searchAlg = new BestFS(map);
		} else if (args[0].equals("AStar")) {
			searchAlg = new AStar(map);
		} else{
			System.out.println("Invalid search algorithm");
			System.exit(0);
		}
		searchAlg.search();
	}

}
