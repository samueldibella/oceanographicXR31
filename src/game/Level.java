package game;
import game.enemies.Barricuda;
import game.enemies.Eel;
import game.enemies.Jellyfish;
import game.enemies.Shark;
import game.enums.SpaceType;
import game.player.Player;

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
		difficulty = (int) ((level + 10) * Math.random());
		design = new Space[Y_SIZE][X_SIZE];
		exitPlaced = false;
		livingBeings = new ArrayList<Animus>();
		designGenerate(design, x, y);
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
		
		for(int i = 0; i < 5; i++) {
			cellularAlter();
		}
		
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
				if(design[j][i].getSpace() == SpaceType.EMPTY) {
					int monsterGen = (int) (Math.random() * 100) - difficulty;
					
					if(monsterGen < 3) {
						livingBeings.add(new Jellyfish(i, j));
						design[j][i].setSpace(SpaceType.JELLYFISH);
					} else if(monsterGen < 6) {
						livingBeings.add(new Eel(i, j));
						design[j][i].setSpace(SpaceType.EEL);
					} else if(monsterGen < 7) {
						livingBeings.add(new Barricuda(i,j));
						design[j][i].setSpace(SpaceType.BARRICUDA);
					} else if(monsterGen < 8) {
						livingBeings.add(new Shark(i,j));
						design[j][i].setSpace(SpaceType.SHARK);
					}
				}
			}
		}
	}
	
	//iterates cellular automation
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
	
	//builds walls all around edge of map
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
	
	//orders all enemies on level to execute their move
	void monsterMove() {
		for(int i = 0; i < livingBeings.size(); i++) {
			int chance = (int) (Math.random() * 9);
			livingBeings.get(i).move(chance);
		}
		
		Game.setPlayerTurn(true);
	}
	
	//attempt to tell if space between two points is totally empty
	public boolean rayCast(int x1, int y1, int x2, int y2) {
		int rise = (int) (y2 - y1);
		int run = (int) (x2 - x1);
		int slope = rise/run;
		
		System.out.printf("%d rise %d run %d slope", rise, run, slope);
		for(int i = 0; i < Math.abs(run); i++) {
			for(int j = 0; j < Math.abs(run); j++) {
				if(design[j][i].getSpace() == SpaceType.WALL) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	//total empty spaces next to passed space
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