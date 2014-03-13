package game;
import game.enums.SpaceType;
import game.enums.Visibility;
import game.player.Hit;
import game.player.Player;
import processing.core.PApplet;
import processing.core.PFont;

public class Game extends PApplet {
	//TODO implement visibility (including walls), level generation
	//TODO monster generation, basic combat, item drops, inventory
	PFont f = createFont("VeraMono", 14);
	public static Level[] dungeon = new Level[10];
	int result;
	int initX;
	int initY;
	boolean moveEntered; 
	static boolean playerTurn;
	public static Player hero;
	public static int playerLevel;
	
	public void setup() {
		frameRate(20);
		size(1400, 650);
		background(0);
		playerTurn = true;
		moveEntered = false;
		result = 0;
		initX = (int) (Math.random() * Level.X_SIZE - 2) + 1;
		initY = (int) (Math.random() * Level.Y_SIZE - 2) + 1;
		hero = new Player(initX, initY);
		dungeon[0] = new Level(10, initX, initY, 0);
		dungeon[0].updateVisibility();
		
		/*raycast test
		dungeon[0].getDesign()[10][6].setSpace(SpaceType.AIM);
		dungeon[0].getDesign()[16][5].setSpace(SpaceType.AIM);
		dungeon[0].getDesign()[3][1].setSpace(SpaceType.AIM);
		dungeon[0].getDesign()[1][3].setSpace(SpaceType.AIM);
		dungeon[0].getDesign()[21][5].setSpace(SpaceType.AIM);
		dungeon[0].getDesign()[0][0].setSpace(SpaceType.AIM);
		System.out.println(dungeon[0].rayCast(0, 0, 10, 6));
		System.out.println(dungeon[0].rayCast(16, 5, 1, 3));
		System.out.println(dungeon[0].rayCast(3, 1, 21, 5));
		System.out.println(dungeon[0].rayCast(10, 6, 0, 0));*/
	}

	public void draw() {
		stroke(255);
		background(0);
		//System.out.print(hero.getAlive());
		//while(hero.getAlive() == true) {
			if(playerTurn == true && moveEntered == true) {
				hero.move(result);
				dungeon[hero.getCurrentLevel()].updateVisibility();
				moveEntered = false;
			} else if(playerTurn == false){
			//	System.out.print("trigger");
				dungeon[hero.getCurrentLevel()].monsterMove();
				
			}
			
			pDisplay(dungeon[hero.getCurrentLevel()]);
			//hitDisplay();
			result = 0;
	//	}
		
		
	}

	private void hitDisplay() {
		// TODO Auto-generated method stub
		Hit[] hitList = hero.getHits();
		int index = hero.getHitsIndex();
		
		for(int i = 0; i <= index; i++) {
			switch(hitList[i].getType()) {
			case SHARK:
				fill(255,0,0);
				ellipse(hitList[i].getX(), hitList[i].getY(), hitList[i].getRadius(), hitList[i].getRadius());
				break;
			case EEL:
				break;
			case JELLYFISH:
				fill(32,178,170);
				ellipse(hitList[i].getDeviationX(), hitList[i].getDeviationY(), hitList[i].getRadius(), hitList[i].getRadius());
				ellipse(hitList[i].getX(), hitList[i].getY(), hitList[i].getRadius(), hitList[i].getRadius());
				break;
			case BARRICUDA:
				break;
			default:
				break;
			
			}
		}
	}

	
	
	
	private void pDisplay(Level level) {
		Space[][] display = level.getDesign();
		textFont(f);
		fill(255);
		int baseX = 150;
		int baseY = 50;
		int opacity = 255;
		
		//basic text information
		text(hero.vitals(), 10, 20);
		text(hero.inventory(), 10, 150);
		//text(hero.textBuffer(), 1250, 10);
		text("Oceanographic Expedition XR31", 575, 20);
		
		//display all level tiles
		for (int j = 0; j < Level.Y_SIZE; j++) {
			for (int i = 0; i < Level.X_SIZE; i++) {
				fill(255);
				
				//change whether space can be seen based on visibility
				switch(display[j][i].getVisibility()) {
				case UNVISITED:
					opacity = 0;
					break;
				case INSIGHT:
					opacity = 255;
					break;
				case VISITED:
					opacity = 127;
					break;
					
				}
				
				//colors for different spaces
				switch(display[j][i].getSpace()) {
				case EXIT:
					fill(255,215,0, opacity);
					break;
				case EMPTY:
					fill(255, opacity);
					break;
				case BARRICUDA:
					fill(176,100,65, opacity);
					break;
				case JELLYFISH:
					fill(176,196,222, opacity);
					break;
				case WALL:
					fill(0,139,139, opacity);
					break;
				default:
					fill(255, opacity);
				}
				
				//hero display
				if(j == hero.getY() && i == hero.getX()) {
					fill(255,193,37);
					text(hero.toString(), baseX, baseY);
				} else if(display[j][i].getItem() != null){
					fill(255,0,0,opacity);
					text(display[j][i].toString(), baseX, baseY);	
				} else {
					text(display[j][i].toString(), baseX, baseY);	
				}
				
				baseX += 15;
			}

			baseY += 15;
			baseX = 150;
		}
	}

	public void keyPressed() {
		
		//wasd and numpad player movement
		switch(key) {
		case('8'): case('w'): case('W'):
			result = 1; 
			moveEntered = true;
			break;
		case('6'): case('d'): case('D'): 
			result = 2;
			moveEntered = true;
			break;
		case('2'): case('s'): case('S'): 
			result = 3;
			moveEntered = true;
			break;
		case('4'): case('a'): case('A'): 
			result = 4;
			moveEntered = true;
			break;
		}
		
		//dungeon level increment
		if(key == '>') {
			result = 5;
			moveEntered = true;
		}
	
		//arrow player movement
		if(key == CODED) {
			switch(keyCode) {
			case(UP):
				result = 1;
				moveEntered = true;
				break;
			case(RIGHT):
				result = 2;
				moveEntered = true;
				break;
			case(DOWN):
				result = 3;
				moveEntered = true;
				break;
			case(LEFT):
				result = 4;
				moveEntered = true;
				break;
			case(ESC):
				System.exit(0);
			}
		}
	}

	public static void setPlayerTurn(boolean input) {
		playerTurn = input;
	}
	
	public static Level getLevel(int i) {
		return dungeon[i];
	}
	
	public static Level[] getDungeon() {
		return dungeon;
	}

}
