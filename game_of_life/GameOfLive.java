/***
 *
 * Conways Game of Live
 *
 * Referenz:
 *
 * German: https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens
 * English: https://en.wikipedia.org/wiki/Conway's_Game_of_Life
 *
 * @author Robert Hingst alias LetsRock13
 *
 */
import java.util.ArrayList;
import java.io.IOException;

class Location {

	public int x;
	public int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// For debuging pupos
	@Override
	public String toString() {
		return String.format("x = %d, y = %d", this.x, this.y); 
	}
}

class Cell {

	public Location loc;
	public char currentState;
	public char newState;

	public Cell(Location loc, char currentState, char newState) {
		this.loc = loc;
		this.currentState = currentState;
		this.newState = newState;
	}
}

public class GameOfLive {

	private final int FIELD = 50; // Field size
	private final char DEAD = 'x';
	private final char ALLIVE = '1';
	private final int ITERATION = 100; // Controlls the max itterations 
	private final long INTERVAL = 250; // Controlls the spead

	private  enum Directions {
			ALL,
			NORTH,
			WEST,
			EAST,
			SOUTH,
	}

	private char[][] field = new char[FIELD][FIELD];

	// The start pattern for the game of live
	private char[][] startPatern = {{'x','x','x','x','x','x'},
									{'x','x','x','x','x','x'},
									{'x','x','x','1','x','x'},
									{'x','1','x','x','1','x'},
									{'x','1','x','x','1','x'},
									{'x','x','1','x','x','x'},
									{'x','x','x','x','x','x'},
									{'x','x','x','x','x','x'},
									{'x','x','x','x','x','x'}};

	private ArrayList<Cell> cells = new ArrayList<Cell>();

	private int it = 0;

	public GameOfLive() {
		init();
	}

	private void init() {
		// End -> Initialize and render the field
		initField();
		run();
	}

	private final void run() {


		// Rules from Wikipedia -> https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens
		// 1. Stays alive >= 2 && <= 3 neighboars
		// xx1
		// x1x Middle cell stays alive
		// xx1
		//
		// 2. Dead cell will be born == 3 neighbors 
		// xxx
		// 1xx Middle cell will be born
		// x11
		//
		// 3. Dies < 2 || Dies > 3 neighbors 
	
		renderField();
		it++;

		for(int y = 0; y < FIELD; y++) {
			for(int x = 0; x < FIELD; x++) {
				cells.add(new Cell(new Location(x, y), field[y][x], DEAD));
			}
		}

		for(Cell c : cells) {

			int count = 0;
			if(c.currentState == DEAD) {
				if (c.loc.x == 0 && (c.loc.y > 0 && c.loc.y < FIELD -1)) {
					// Code for WEST Edge
					if (checkDirection(c.loc.x, c.loc.y, Directions.WEST) == 3 ) {
						c.newState = ALLIVE;
					}
					//assert true : "WEST Edge: Not Implemented yet";
				} else if(c.loc.x == FIELD - 1 && (c.loc.y > 0 && c.loc.y < FIELD -1)) {
					// Code for EAST Edge
					if (checkDirection(c.loc.x, c.loc.y, Directions.EAST) == 3 ) {
						c.newState = ALLIVE;
					}
				}

				if (c.loc.y == 0 && (c.loc.x > 0 && c.loc.x < FIELD -1)) {
					// Code for NORTH Edge
					if (checkDirection(c.loc.x, c.loc.y, Directions.NORTH) == 3 ) {
						c.newState = ALLIVE;
					}
				} else if(c.loc.y == FIELD - 1 && (c.loc.x > 0 && c.loc.x < FIELD -1)) {
					// Code for SOUTH Edge
					if (checkDirection(c.loc.x, c.loc.y, Directions.SOUTH) == 3 ) {
						c.newState = ALLIVE;
					}
				}

				if(c.loc.y > 0 && c.loc.y < FIELD - 1) {
					if(c.loc.x > 0 && c.loc.x < FIELD - 1) {
						if (checkDirection(c.loc.x, c.loc.y, Directions.ALL) == 3) {
							c.newState = ALLIVE;
						}
					}
				}

			} else if (c.currentState == ALLIVE) {	
				if (c.loc.x == 0 && (c.loc.y > 0 && c.loc.y < FIELD -1)) {
					// Code for WEST Edge
					
					count = checkDirection(c.loc.x, c.loc.y, Directions.WEST);  

					if (count >= 2 && count <= 3) {
						c.newState = ALLIVE;
					} else {
						c.newState = DEAD;
					}
				} else if(c.loc.x == FIELD - 1 && (c.loc.y > 0 && c.loc.y < FIELD -1)) {
					// Code for EAST Edge
					count = checkDirection(c.loc.x, c.loc.y, Directions.EAST);  

					if (count >= 2 && count <= 3) {
						c.newState = ALLIVE;
					} else {
						c.newState = DEAD;
					}
				}

				if (c.loc.y == 0 && (c.loc.x > 0 && c.loc.x < FIELD -1)) {
					// Code for NORTH Edge
					count = checkDirection(c.loc.x, c.loc.y, Directions.NORTH);  

					if (count >= 2 && count <= 3) {
						c.newState = ALLIVE;
					} else {
						c.newState = DEAD;
					}

				} else if(c.loc.y == FIELD - 1 && (c.loc.x > 0 && c.loc.x < FIELD -1)) {
					// Code for SOUTH Edge
					count = checkDirection(c.loc.x, c.loc.y, Directions.SOUTH);  

					if (count >= 2 && count <= 3 ) {
						c.newState = ALLIVE;
					} else {
						c.newState = DEAD;
					}
				}

				if(c.loc.y > 0 && c.loc.y < FIELD - 1) {
					if(c.loc.x > 0 && c.loc.x < FIELD - 1) {

						count = checkDirection(c.loc.x, c.loc.y, Directions.ALL); 

						if (count >= 2 && count <= 3) {
							c.newState = ALLIVE;
						} else {
							c.newState = DEAD;
						}
					}
				}
			}
			
		}

		// Pass the newState to the field
		// and let it become the currentState
		for(Cell c : cells) {
			field[c.loc.y][c.loc.x] = c.newState;
		}

		// limit the recursiv runs
		if (it != ITERATION) {
			try {
				// slow it down so that you can
				// actually see things
				Thread.sleep(INTERVAL);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			run();
		}
	}

	private void initField() {

		// Start -> Initialize and render the field
		for(int i = 0; i < FIELD; i++) {
			for(int j = 0; j < FIELD; j++) {
				field[i][j] = DEAD;
			}
		}

		// load the stating pattern
		int k = 0;
		for(int i = (field.length / 2) - startPatern.length; i <= i + startPatern.length - 1; i++) {
			int l = 0;

			for(int j = (FIELD / 2) - startPatern.length; j <= j + startPatern.length - 1; j++) {

				field[i][j] = startPatern[k][l];

				if(l == startPatern[0].length - 1) break;

				l++;
			}
			if(k == startPatern.length - 1) break;	

			k++;
		}
	}
	
	// Count the sourounding ALLIVE cells 
	//
	// Note: the naming of the variables
	// is a bit wired i now
	// but for the time being it was ok for
	// me.
	//
	// In case of the directions if you
	// give ALL that mean all directions are valid.
	// In case of other directions, like NORTH or WEST it
	// means this specific directions is excluded from the calulation.
	//
	// I know it's super wired but i didn't but i couldn't
	// thisk of somthing better.
	private int checkDirection(int x, int y, Directions dir) {

		int count = 0;

		switch (dir) {
			case Directions.ALL:
				if(field[y - 1][x] == ALLIVE) { // North
					count++;								
				} 

				if(field[y - 1][x - 1] == ALLIVE) { // North West
					count++;
				}

				if(field[y - 1][x + 1] == ALLIVE) { // North East
					count++;
				}

				if(field[y][x - 1] == ALLIVE) { // West
					count++;
				}

				if(field[y][x + 1] == ALLIVE) { // East
					count++;
				}

				if(field[y + 1][x] == ALLIVE) { // South
					count++;
				} 

				if(field[y + 1][x - 1] == ALLIVE) { // South West
					count++;
				} 

				if(field[y + 1][x + 1] == ALLIVE) { // South East
					count++;
				}
				break;
			case Directions.NORTH:
				if(field[y][x - 1] == ALLIVE) { // West
					count++;
				}

				if(field[y][x + 1] == ALLIVE) { // East
					count++;
				}

				if(field[y + 1][x] == ALLIVE) { // South
					count++;
				} 

				if(field[y + 1][x - 1] == ALLIVE) { // South West
					count++;
				} 

				if(field[y + 1][x + 1] == ALLIVE) { // South East
					count++;
				}
				break;
			case Directions.WEST:
				if(field[y - 1][x] == ALLIVE) { // North
					count++;								
				} 

				if(field[y - 1][x + 1] == ALLIVE) { // North East
					count++;
				} 

				if(field[y][x + 1] == ALLIVE) { // East
					count++;
				} 

				if(field[y + 1][x] == ALLIVE) { // South
					count++;
				} 

				if(field[y + 1][x + 1] == ALLIVE) { // South East
					count++;
				}
				break;
			case Directions.EAST:
				if(field[y - 1][x] == ALLIVE) { // North
					count++;								
				} 

				if(field[y - 1][x - 1] == ALLIVE) { // North West
					count++;
				} 

				if(field[y][x - 1] == ALLIVE) { // West
					count++;
				} 

				if(field[y + 1][x] == ALLIVE) { // South
					count++;
				} 

				if(field[y + 1][x - 1] == ALLIVE) { // South West
					count++;
				}
				break;
			case Directions.SOUTH:
				if(field[y - 1][x] == ALLIVE) { // North
					count++;								
				} 

				if(field[y - 1][x - 1] == ALLIVE) { // North West
					count++;
				}

				if(field[y - 1][x + 1] == ALLIVE) { // North East
					count++;
				}

				if(field[y][x - 1] == ALLIVE) { // West
					count++;
				}

				if(field[y][x + 1] == ALLIVE) { // East
					count++;
				}
				break;
			default:
				break;
		}

		return count;
	}
	
	// Renders the fiel in then termianl
	private void renderField() {

		StringBuilder strBuilder = new StringBuilder();

		for(int i = 0; i < FIELD; i++) {
			for(int j = 0; j < FIELD; j++) {
				strBuilder.append(field[i][j]);
			}
			strBuilder.append("\n");
		}

		clearConsole();
		System.out.print(strBuilder.toString());
    	System.out.flush();
	}

	// Clears the termianl
	// something like an "Animation" :)
	//
	// It should work under Windows
	private void clearConsole() {
 		try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		new GameOfLive();
	}	

}
