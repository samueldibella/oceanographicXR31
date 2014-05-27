package game;
import game.enemies.Barricuda;
import game.enemies.Eel;
import game.enemies.Jellyfish;
import game.enemies.Shark;
import game.enums.InputMode;
import game.enums.ItemType;
import game.enums.SpaceType;
import game.enums.Visibility;

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
@SuppressWarnings("serial")
public class Level extends PApplet{
	public static final int X_SIZE = 70;
	public static final int Y_SIZE = 40;
	static ArrayList<Space> stinky; 
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
		FloorWalker floor2 = new FloorWalker(heroX, heroY);

		for(int i = 0; i < 200; i++) {
			//System.out.println(floor.getY() + ", " + floor.getX());
			design[floor.getY()][floor.getX()].setSpace(SpaceType.EMPTY);
			floor.move();
			
			design[floor2.getY()][floor2.getX()].setSpace(SpaceType.EMPTY);
			floor2.move();
			if(floor.getIsFar()) {
				//	design[floor.getY()][floor.getX()].setSpace(SpaceType.EXIT);
			}
		}

		design[floor.getY()][floor.getX()].setSpace(SpaceType.EXIT);
		design[floor2.getY()][floor2.getX()].setSpace(SpaceType.EXIT);

		assertWalls();

		for(int j = 0; j < Y_SIZE; j++) {
			for(int i = 0; i < X_SIZE; i++) {
				if(design[j][i].getSpace() == SpaceType.EMPTY) {
					int randomGen = (int) (Math.random() * 1000) - difficulty;

					if(randomGen < 50) {
						livingBeings.add(new Jellyfish(i, j));
						design[j][i].setSpace(SpaceType.JELLYFISH);
					} else if(randomGen < 60) {
						livingBeings.add(new Eel(i, j));
						design[j][i].setSpace(SpaceType.EEL);
					} else if(randomGen < 65) {
						livingBeings.add(new Barricuda(i,j));
						design[j][i].setSpace(SpaceType.BARRICUDA);
					} else if(randomGen < 70) {
						livingBeings.add(new Shark(i,j));
						design[j][i].setSpace(SpaceType.SHARK);
					} else if(randomGen > (999 - difficulty)) {
						design[j][i].setItem(ItemType.DRILL);
					} else if(randomGen > (998 - difficulty)) {
						design[j][i].setItem(ItemType.ECHO);
					} else if(randomGen > (997 - difficulty)) {
						design[j][i].setItem(ItemType.ADRL);
					} else if(randomGen > (996 - difficulty)) {
						design[j][i].setItem(ItemType.OXYGEN);
					} else if(randomGen > (995 - difficulty)) {
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
		updateScent();

		for(int i = 0; i < livingBeings.size(); i++) {
			int chance = (int) (Math.random() * 9);
			Animus corpus = livingBeings.get(i);
			
			monsterCollision(corpus, i);
			
			if(corpus.isAlive) {
				corpus.move(chance);
			}
			
			monsterCollision(corpus, i);
		}

		Game.setPlayerTurn(true);
	}

	/**Refreshes scent map for AI seek function
	 * A space's scent will be a function of the distance from the player,
	 * and some relation to it's overall position, to insure that there are no
	 * tiles with the same scent value.
	 */
	void updateScent() {
		int x = Game.hero.getX();
		int y = Game.hero.getY();
		int scentRate = 100000;
		
		for(int j = 0; j < Y_SIZE - 1; j++) {
			for(int i = 0; i < X_SIZE - 1; i++) {
				if(design[j][i].getSpace() == SpaceType.WALL) {
					design[j][i].setScent(0);
				} else {
					int dx = i - x;
					int dy = j - y;
					float totalDelta = (float) (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));			
					
					design[j][i].setScent(((scentRate - totalDelta)) - ((j + i) * .1f));
				}
			}
		}
	}

	/**Removes creatures that intersect with player, and adds their
	 * hit effect to general player state
	 * 
	 * @param corpus
	 * @param livingIndex
	 */
	private void monsterCollision(Animus corpus, int livingIndex) {
		if(corpus.getX() == Game.hero.getX() && (corpus.getY() == Game.hero.getY())) {
			switch (corpus.getType()) {
			case JELLYFISH:
				Game.addBuffer("Your visor smears with jelly.");
				break;
			case EEL:
				Game.addBuffer("You crush the eel.");
				Eel.normalize(corpus.getX(), corpus.getY());
				break;
			case BARRICUDA:
				Game.addBuffer("Teeth trying to tear the suit.");
				break;
			case SHARK:
				Game.addBuffer("A brief near-death struggle.");
				break;
			default:
				break;
			}
			
			if(livingBeings.get(livingIndex).isAlive) {
				livingBeings.get(livingIndex).setAlive(false);
				livingBeings.remove(livingIndex);
				Game.hero.getBody().addHit(corpus.getType());
				design[corpus.getY()][corpus.getX()].setSpace(SpaceType.EMPTY);
			}
		}
	}

	/**Cuts a hole in the level in the cardinal directions
	 * 
	 * @param direction
	 */
	public void drill(int direction) {
		int x = Game.hero.getX();
		int y = Game.hero.getY();
		
		switch(direction) {
		case 0:
			break;
		//up
		case 1:
			for(int i = y - 1; i > 0; i--) {
				if(design[i][x].getSpace() != SpaceType.EXIT){
					design[i][x].setSpace(SpaceType.EMPTY);
				}
				
				design[i][x].setVisibility(Visibility.INSIGHT);
			}
			
			Game.addBuffer("The drill pierces the earth.");
			assertWalls();
			Game.inputMode = InputMode.NORMAL;
		//right
		case 2:
			for(int i = x + 1; i < X_SIZE - 1; i++) {
				if(design[y][i].getSpace() != SpaceType.EXIT){
					design[y][i].setSpace(SpaceType.EMPTY);
				}
				
				design[y][i].setVisibility(Visibility.INSIGHT);
			}
			
			Game.addBuffer("The drill pierces the earth.");
			assertWalls();
			Game.inputMode = InputMode.NORMAL;
		//down
		case 3:
			for(int i = y + 1; i < Y_SIZE - 1; i++) {
				if(design[i][x].getSpace() != SpaceType.EXIT){
					design[i][x].setSpace(SpaceType.EMPTY);
				}
				
				design[i][x].setVisibility(Visibility.INSIGHT);
			}
			
			Game.addBuffer("The drill pierces the earth.");
			assertWalls();
			Game.inputMode = InputMode.NORMAL;
		//left
		case 4:
			for(int i = x - 1; i > 0; i--) {
				if(design[y][i].getSpace() != SpaceType.EXIT){
					design[y][i].setSpace(SpaceType.EMPTY);
				}
				
				design[y][i].setVisibility(Visibility.INSIGHT);
			}
			
			Game.addBuffer("The drill pierces the earth.");
			assertWalls();
			Game.inputMode = InputMode.NORMAL;
		default:
			Game.addBuffer("You don't want to put that there.");
			break;
		}
	}
	
	/**
	 * In effect reveals the whole level. Used for map item.
	 */
	public void echo() {
		for(int j = 1; j < Y_SIZE; j++) {
			for(int i = 1; i < X_SIZE; i++) {
				design[j][i].setVisibility(Visibility.VISITED);
			}
		}
	}

	/**
	 * Between each player turn, refreshes the visibility of tiles
	 */
	public void updateVisibility() {
		int x = Game.hero.getX();
		int y = Game.hero.getY();
		int visionRange = 10;
		
		for(int j = 0; j < Level.Y_SIZE; j++) {
			for(int i = 0; i < Level.X_SIZE; i++) {
				if(design[j][i].getVisibility() == Visibility.INSIGHT) {
					design[j][i].setVisibility(Visibility.VISITED);
				}
			}
		}
		
		//only vision with lights on
		if(Game.hero.getLights() == true) {
			for(int j = -5 -(visionRange / 2); j <= (visionRange / 2) + 5; j++) {
				//left and right side
				visionRayCast(x, y, x - visionRange, y + j);
				visionRayCast(x, y, x + visionRange, y + j);
				
				//up and down
				visionRayCast(x, y, x + j, y - visionRange);
				visionRayCast(x, y, x + j, y + visionRange);
			}
			
			visionRayCast(x, y, x + 3, y + 3);
			visionRayCast(x, y, x + 3, y - 3);
			visionRayCast(x, y, x - 3, y + 3);
			visionRayCast(x, y, x - 3, y - 3);
		}

	}

	/**Used http://tech-algorithm.com/articles/drawing-line-using-bresenham-algorithm/
	 * primitive raycast for player vision
	 * @param x0 start x coordinate
	 * @param y0 start y coordinate
	 * @param x3 end 
	 * @param y3 end
	 */
	public void visionRayCast(int x0, int y0,  int x3, int y3) {
		int w = x3 - x0;
		int h = y3 - y0;
		
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		
		//octant selector
		if(w < 0) {
			dx1 = -1;
		} else if( w > 0) {
			dx1 = 1;
		}
		
		if(h < 0) {
			dy1 = -1;
		} else if (h > 0) {
			dy1 = 1;
		}
		
		if(w < 0) {
			dx2 = -1;
		} else if(w > 0) {
			dx2 = 1;
		}
		
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		
		if(!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			
			if(h < 0) {
				dy2 = -1;
			} else if (h > 0) {
				dy2 = 1;
			}
			
			dx2 = 0;
		}
		
		int numerator = longest >> 1;
		
		for(int i = 0; i <= longest; i++) {
			if(isInLevel(x0, y0) && design[y0][x0].getSpace() != SpaceType.WALL) {
				design[y0][x0].setVisibility(Visibility.INSIGHT);
				numerator += shortest;
				
				if(!(numerator < longest)) {
					numerator -= longest;
					x0 += dx1;
					y0 += dy1;
				} else {
					x0 += dx2;
					y0 += dy2;
				}
			} else if( design[y0][x0].getSpace() == SpaceType.WALL){
				design[y0][x0].setVisibility(Visibility.INSIGHT);
				break;
			} else {
				break;
			}
		}
	}

	//basic test to insure no null reference passes
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
	//all eight
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
	 * and returns a list of them. Only cardinal directions
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

		// check left space
		if (design[p.getY()][p.getX() - 1].getSpace() == SpaceType.EMPTY ||
				design[p.getY()][p.getX() - 1].getSpace() == SpaceType.EXIT) {
			emptyPoints[3] = design[p.getY()][p.getX() - 1];

		}
		
		// check bottom space
		if (design[p.getY() + 1][p.getX()].getSpace() == SpaceType.EMPTY ||
				design[p.getY() + 1][p.getX()].getSpace() == SpaceType.EXIT) {
			emptyPoints[2] = design[p.getY() + 1][p.getX()];
		}

		// check right space
		if (design[p.getY()][p.getX() + 1].getSpace() == SpaceType.EMPTY ||
				design[p.getY()][p.getX() + 1].getSpace() == SpaceType.EXIT) {
			emptyPoints[1] = design[p.getY()][p.getX() + 1];
		}

		return emptyPoints;
	}

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