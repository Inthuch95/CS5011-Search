package main;

import map.Map;
import search.BFS;
import search.DFS;
import search.Search;

public class Search1 {

	public static void main(String[] args) {
		Search searchAlg;
		char[][] map = getChosenMap(args[1]);
		if (args[0].equals("B")) {
			searchAlg = new BFS(map);
		} else {
			searchAlg = new DFS(map);
		}
		searchAlg.search();
	}
	
	public static char[][] getChosenMap(String mapChoice) {
		int option = Integer.parseInt(mapChoice);
		char[][] map;
		switch(option) {
		case 1:
			map = Map.map1;
			break;
		case 2:
			map = Map.map2;
			break;
		case 3:
			map = Map.map3;
			break;
		case 4:
			map = Map.map4;
			break;
		case 5:
			map = Map.map5;
			break;
		case 6:
			map = Map.map6;
			break;
		default:
			map = Map.map1;
			break;
		}
		return map;
	}

}
