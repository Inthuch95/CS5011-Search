package map;

public class Map {
	public static char[][] map1 = new char [][] {
		{'I', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'O'},
		{'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'G'},
		};


	public static char[][] map2 = new char [][] {
		{'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O', 'X'},
		{'O', 'X', 'O', 'X', 'O', 'O', 'B', 'O', 'O', 'X'},
		{'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O'},
		{'O', 'O', 'X', 'X', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'X', 'X', 'X', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'G', 'O', 'I', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		};


    public static char[][] map3 = new char [][] {
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O'},
		{'O', 'I', 'O', 'O', 'O', 'X', 'X', 'O', 'B', 'O'},
		{'O', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'G', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
		};
		
	public static char[][] map4 = new char [][] {
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O'},
		{'O', 'I', 'O', 'O', 'O', 'X', 'X', 'O', 'B', 'O'},
		{'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'O', 'X'},
		{'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'G', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
		};
		
	public static char[][] map5 = new char [][] {
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'B', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'X', 'O', 'O', 'X'},
		{'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O', 'O'},
		{'O', 'X', 'O', 'O', 'G', 'O', 'X', 'O', 'I', 'O'},
		{'X', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O'}
		};
	
	public static char[][] map6 = new char [][] {
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X'},
		{'O', 'O', 'B', 'O', 'O', 'X', 'X', 'O', 'O', 'O'},
		{'O', 'X', 'X', 'X', 'O', 'O', 'X', 'O', 'O', 'I'},
		{'O', 'X', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O'},
		{'O', 'O', 'O', 'O', 'O', 'X', 'X', 'X', 'X', 'O'},
		{'X', 'O', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O'},
		{'X', 'X', 'O', 'O', 'O', 'X', 'X', 'O', 'X', 'X'},
		{'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O', 'X'},
		{'O', 'X', 'X', 'X', 'G', 'O', 'X', 'X', 'O', 'X'},
		{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O'}
		};
		
		public static char[][] getChosenMap(String mapChoice) {
			// get the chosen map
			int option = Integer.parseInt(mapChoice);
			char[][] map = map1;
			switch(option) {
			case 1:
				map = map1;
				break;
			case 2:
				map = map2;
				break;
			case 3:
				map = map3;
				break;
			case 4:
				map = map4;
				break;
			case 5:
				map = map5;
				break;
			case 6:
				map = map6;
				break;
			default:
				System.out.println("Invalid map option");
				System.exit(0);
				break;
			}
			return map;
		}
}
