package game;
import java.util.ArrayList;

import processing.core.PApplet;

/**Class to handle Maze, and pathfinding process.
 * InputOutput builds the maze from a text file.
 * Uses SetOfSpaces for search process.
 * 
 * @author Samuel
 * @see SetOfSpaces
 * @see InputOutput
 */
public class Level extends PApplet{
	public static final int X_SIZE = 70;
	public static final int Y_SIZE = 40;
	int difficulty;
	
	private int index;
	private int heroX;
	private int heroY;
	private Space[][] design;
	Player hero;
	boolean exitPlaced;
	private ArrayList<Animus> livingBeings;

	/**Constructor for class. 
	 * Reads file and entered location and generates playerLocation,
	 * maze size, and the 2D char array for the maze itself.
	 * @throws Exception InputOutput file errors
	 */
	public Level(int level, int x, int y, int currentLevel) {
		index = currentLevel;
		difficulty = (int) (level * Math.random()) + 8;
		design = new Space[Y_SIZE][X_SIZE];
		exitPlaced = false;
		livingBeings = new ArrayList<Animus>();
		designGenerate(design, x, y);
		
		/*do {
		heroX = (int) Math.random() * X_SIZE;
		heroY = (int) Math.random() * Y_SIZE;
		} while (design[heroY][heroX].isEmpty() == true);*/
		//Animus[] livingBeings = new Animus[difficulty + 1];

		//hero = new Player(heroX, heroY);
		
	}
	
	void designGenerate(Space[][] design, int x, int y) {
		int chance;
		heroX = x;
		heroY = y;
		
		for(int j = 0; j < Y_SIZE; j++) {
			for(int i = 0; i < X_SIZE; i++) {
				chance = (int) (Math.random() * 3);
				if(chance == 0) {
					design[j][i] = new Space(SpaceType.EMPTY, i, j, index);
				} else {
					design[j][i] = new Space(SpaceType.WALL, i, j, index);
				}
			}
		}
		
		cellularAlter();
		cellularAlter();
		cellularAlter();
		cellularAlter();
		cellularAlter();
		
		
		
		FloorWalker floor = new FloorWalker(heroX, heroY);
		
		for(int i = 0; i < 200; i++) {
			//System.out.println(floor.getY() + ", " + floor.getX());
			design[floor.getY()][floor.getX()].setSpace(SpaceType.EMPTY);
			floor.move();
			if(floor.getIsFar()) {
			//	design[floor.getY()][floor.getX()].setSpace(SpaceType.EXIT);
			}
		}
		
		design[floor.getY()][floor.getX()].setSpace(SpaceType.EXIT);
		
		assertWalls();
		
		for(int j = 0; j < Y_SIZE; j++) {
			for(int i = 0; i < X_SIZE; i++) {
				if(design[j][i].isEmpty()) {
					int monsterGen = (int) (Math.random() * 50);
					if(monsterGen < 3) {
						livingBeings.add(new Jellyfish(i, j));
						design[j][i].setCreature(true);
					}
				}
			}
		}
	}
	
	void cellularAlter() {
		int[][] overlay = new int[Y_SIZE][X_SIZE];
		
		for(int j = 0; j < Y_SIZE; j++) {
			for(int i = 0; i < X_SIZE; i++) {
				overlay[j][i] = localEmpty(design[j][i]);
			}
		}
		
		for(int j = 0; j < Y_SIZE; j++) {
			for(int i = 0; i < X_SIZE; i++) {
				if(overlay[j][i] < 2) {
					design[j][i].setSpace(SpaceType.EMPTY);
				} else if (overlay[j][i] < 6) {
					design[j][i].setSpace(SpaceType.WALL);
				} else {
					design[j][i].setSpace(SpaceType.EMPTY);
				}
			}
		}
	}
	
	void assertWalls() {
		for (int i = 0; i < X_SIZE; i++) {
			design[0][i] = new Space(SpaceType.WALL,i,0, index);
		}
		
		for(int j = 1; j < Y_SIZE; j++) {
			design[j][0] = new Space(SpaceType.WALL,j,0, index);
			design[j][X_SIZE-1] = new Space(SpaceType.WALL,X_SIZE-1,j, index);
		}
		
		for (int i = 0; i < X_SIZE; i++) {
			design[Y_SIZE-1][i] = new Space(SpaceType.WALL,i,0, index);
		}
	}
	
	void monsterMove() {
		for(int i = 0; i < livingBeings.size(); i++) {
			int chance = (int) Math.random() * 9;
			livingBeings.get(i).move(chance);
		}
	}
	
	public int localEmpty(Space p) {
		int emptySpaces = 0;
		int yScale;
		int xScale;
		
		for(int j = -1; j < 2; j++) {
			for(int i = -1; i < 2; i++) {
				yScale = p.getY() + j;
				xScale = p.getX() + i;
				
				if(0 < yScale && yScale < Y_SIZE && 0 < xScale && xScale < X_SIZE) {
					if(design[p.getY() + j][p.getX() + i].getSpace() == SpaceType.EMPTY) {
						emptySpaces++;
					}
				}
				
			}
		}
		
		return emptySpaces;
	}
	
	/**Checks a points neighbors for ones of type EMPTY
	 * and returns a list of them.
	 * @param p reference point
	 * @return list of empty neighbor spaces
	 */
 	public Space[] checkNeighbors(Space p) {
		Space[] emptyPoints = new Space[4];
		
		// check top space
		if (design[p.getY() - 1][p.getX()].getSpace() == SpaceType.EMPTY ||
			design[p.getY() - 1][p.getX()].getSpace() == SpaceType.EXIT) {
			emptyPoints[0] = design[p.getY() - 1][p.getX()];
		}

		// check right space
		if (design[p.getY()][p.getX() + 1].getSpace() == SpaceType.EMPTY ||
			design[p.getY()][p.getX() + 1].getSpace() == SpaceType.EXIT) {
			emptyPoints[1] = design[p.getY()][p.getX() + 1];
		}

		// check bottom space
		if (design[p.getY() + 1][p.getX()].getSpace() == SpaceType.EMPTY ||
			design[p.getY() + 1][p.getX()].getSpace() == SpaceType.EXIT) {
			emptyPoints[2] = design[p.getY() + 1][p.getX()];
		}

		// check left space
		if (design[p.getY()][p.getX() - 1].getSpace() == SpaceType.EMPTY ||
			design[p.getY()][p.getX() - 1].getSpace() == SpaceType.EXIT) {
			emptyPoints[3] = design[p.getY()][p.getX() - 1];

		}
		
		return emptyPoints;
	}

	/**
	 * Displays a printing of the 2D char array that represents the maze
	 */
	@Override
	public String toString() {
		String output = "";
		//output += " Turn: " + Game.getTurn() + " \n";
		output += "Hero Co-Ordinates: " + heroX + ", " + heroY + "\n";
		for (int j = 0; j < Y_SIZE - 1; j++) {
			for (int i = 0; i < X_SIZE; i++) {
				output += design[j][i] + " ";
			}
			output += "\n";
		}

		output += "\n";
		return output;
	}
	
	public ArrayList<Animus> getBeings() {
		return livingBeings;
	}
	
	public Space[][] getDesign() {
		return design;
	}
	
	public int getHeroX() {
		return heroX;
	}
	
	public int getHeroY() {
		return heroY;
	}
}