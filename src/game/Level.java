package game;
import game.enemies.Barricuda;
import game.enemies.Eel;
import game.enemies.Jellyfish;
import game.enemies.Shark;
import game.enums.ItemType;
import game.enums.SpaceType;
import game.enums.Visibility;
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

		for(int i = 0; i < 6; i++) {
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
					int randomGen = (int) (Math.random() * 400) - difficulty;

					if(randomGen < 6) {
						livingBeings.add(new Jellyfish(i, j));
						design[j][i].setSpace(SpaceType.JELLYFISH);
					} else if(randomGen < 12) {
						livingBeings.add(new Eel(i, j));
						design[j][i].setSpace(SpaceType.EEL);
					} else if(randomGen < 14) {
						livingBeings.add(new Barricuda(i,j));
						design[j][i].setSpace(SpaceType.BARRICUDA);
					} else if(randomGen < 16) {
						livingBeings.add(new Shark(i,j));
						design[j][i].setSpace(SpaceType.SHARK);
					} else if(randomGen > (399 - difficulty)) {
						design[j][i].setItem(ItemType.DRILL);
					} else if(randomGen > (398 - difficulty)) {
						design[j][i].setItem(ItemType.ECHO);
					} else if(randomGen > (397 - difficulty)) {
						design[j][i].setItem(ItemType.HARPOON);
					} else if(randomGen > (396 - difficulty)) {
						design[j][i].setItem(ItemType.OXYGEN);
					} else if(randomGen > (395 - difficulty)) {
						design[j][i].setItem(ItemType.RELAY);
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
			design[0][i] = new Space(SpaceType.WALL,i,0, index, Visibility.VISITED);
		}

		for(int j = 1; j < Y_SIZE; j++) {
			design[j][0] = new Space(SpaceType.WALL,j,0, index, Visibility.VISITED);
			design[j][X_SIZE-1] = new Space(SpaceType.WALL,X_SIZE-1,j, index, Visibility.VISITED);
		}

		for (int i = 0; i < X_SIZE; i++) {
			design[Y_SIZE-1][i] = new Space(SpaceType.WALL,i,0, index, Visibility.VISITED);
		}
	}

	//orders all enemies on level to execute their move
	//manages monster overlap and death with player hits
	void monsterMove() {
		for(int i = 0; i < livingBeings.size(); i++) {
			//if(livingBeings.get(i).getAlive()) {	
			int chance = (int) (Math.random() * 9);
			livingBeings.get(i).move(chance);
			//}

			if(livingBeings.get(i).getX() == Game.hero.getX() && (livingBeings.get(i).getY() == Game.hero.getY())) {
				livingBeings.get(i).setAlive(false);
				Game.hero.addHit(livingBeings.get(i).getType());
				design[livingBeings.get(i).getY()][livingBeings.get(i).getX()].setSpace(SpaceType.EMPTY);

			}

		}

		Game.setPlayerTurn(true);
	}

	//TODO update so that fish movement isn't recorded
	public void updateVisibility() {
		int x = Game.hero.getX();
		int y = Game.hero.getY();
		
		for(int j = 0; j < Level.Y_SIZE; j++) {
			for(int i = 0; i < Level.X_SIZE; i++) {
				if(design[j][i].getVisibility() == Visibility.INSIGHT) {
					design[j][i].setVisibility(Visibility.VISITED);
				}
			}
		}
		
		//visionRayCast(x, y, x - 3, y - 5);
		
		//to the right
		visionRayCast(x, y, X_SIZE , y - 1);
		visionRayCast(x, y, X_SIZE , y);
		visionRayCast(x, y, X_SIZE, y + 1);
		
		//to the left
		visionRayCast(x, y, 0, y - 1);
		visionRayCast(x, y, 0, y);
		visionRayCast(x, y, 0, y + 1);
		
		//down
		visionRayCast(x - 1, y, x - 1, Y_SIZE - 1);
		visionRayCast(x, y, x, Y_SIZE - 1);
		visionRayCast(x + 1, y, x + 1, Y_SIZE - 1);
		
		//up
		visionRayCast(x - 1, y, x - 1, 0);
		visionRayCast(x, y, x, 0);
		visionRayCast(x + 1, y, x + 1, 0);
		
		//diagonals 
		/*
		visionRayCast(x, y, x + 3, y - 3);
		visionRayCast(x, y, x + 3, y + 3);
		visionRayCast(x, y, x - 3, y + 3);
		visionRayCast(x, y, x - 3, y - 3);
		*/
			
	}

	//attempt to tell if space between two points is totally empty
	//based off wikipedia entry for bresenham's line theorem &
	//http://deepnight.net/bresenham-magic-raycasting-line-of-sight-pathfinding/
	public void visionRayCast(int x0, int y0,  int x3, int y3) {
		int x1 = x0;
		int y1 = y0;
		int x2 = x3;
		int y2 = y3;

		while(!isInLevel(x2, y2)) {
			if(x2 < 0) {
				x2++;
			}
			if(x2 > X_SIZE - 1) {
				x2--;
			}
			if(y2 < 0) {
				y2++;
			}
			if(y2 > Y_SIZE - 1) {
				y2--;
			}
		} 
		
		//vertical lines
		if(x2 - x2 == 0) {
			int deltaY = y2 - y1;
			
			//upward
			if(deltaY > 0) {
				for(int i = y1; i < y2; i++ ) {
					design[i][x1].setVisibility(Visibility.INSIGHT);
					if(design[i][x1].getSpace() == SpaceType.WALL) {
						break;
					}
				}
			
			//downwards
			} else if(deltaY < 0) {
				for(int i = y1; i > y2; i--) {
					design[i][x1].setVisibility(Visibility.INSIGHT);
					if(design[i][x1].getSpace() == SpaceType.WALL) {
						break;
					}
				}
			}
		}
		
		float error = 0;
		int temp;
		int yStep;
		boolean swapXY = Math.abs(x1 - x2) > Math.abs(x1 - x2);

		if(swapXY) {
			temp = x1; x1 = y1; y1 = temp;
			temp = x1; x1 = y1; y1 = temp;
		}

		if( x1 > x2) {
			temp = x1; x1 = x2; x2 = temp;
			temp = y1; y1 = y2; y1 = temp;
		}

		int deltaX = (x2 - x1);
		int deltaY = Math.abs(y2 - y1);
		int y = y1;

		if(y1 < y2) {
			yStep = 1;
		} else {
			yStep = -1;
		}

		if(swapXY) {
			for(int x = x1; x < x2; x++) {
				design[y][x].setVisibility(Visibility.INSIGHT);
				if(design[y][x].getSpace() == SpaceType.WALL) {
					break;
				}

				error -= deltaY;
				if(error < 0) {
					y += yStep;
					error += deltaX;
				}
			}

			if(deltaX == 0) {
				for(int x = x1; x < x2; x++) {
					design[y][x].setVisibility(Visibility.INSIGHT);
					if(design[y][x].getSpace() == SpaceType.WALL) {
						break;
					}
				}
			}
		} else {
			for(int x = x1; x < x2; x++) {
				design[y][x].setVisibility(Visibility.INSIGHT);
				if(design[y][x].getSpace() == SpaceType.WALL) {
					break;
				}

				error -= deltaY;
				if(error < 0) {
					y += yStep;
					error += deltaX;
				}

			}

			if(deltaX == 0) {
				for(int x = x1; x < x2; x++) {
					design[y][x].setVisibility(Visibility.INSIGHT);
					if(design[y][x].getSpace() == SpaceType.WALL) {
						break;
					}
				}
			}
		}
	}
	
public boolean isInLevel(int x, int y) {
	if(y < 0 || y > Y_SIZE - 1) {
		return false;
	}
	
	if(x < 0 || x > X_SIZE - 1) {
		return false;
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