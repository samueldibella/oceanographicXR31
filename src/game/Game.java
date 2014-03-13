package game;
import game.enums.Mode;
import game.enums.Visibility;
import game.player.Hit;
import game.player.Player;
import game.player.Wounds;
import processing.core.PApplet;
import processing.core.PFont;


public class Game extends PApplet {
	//TODO implement visibility (including walls), level generation
	//TODO monster generation, basic combat, item drops, inventory

	PFont fixed = createFont("secrcode.tff", 14);
	PFont f = createFont("VeraMono", 14);
	
	public static Level[] dungeon = new Level[25];
	int result;
	int initX;
	int initY;
	public static int screenX;
	public static int screenY;
	Mode overall;
	static Queue textBuffer = new Queue();
	boolean moveEntered; 
	static boolean playerWin;
	static boolean playerTurn;
	public static Player hero;
	public static int playerLevel;
	
	public void setup() {
		frameRate(20);
		size(1400, 650);
		background(0);
		stroke(0);
		playerTurn = true;
		moveEntered = false;
		playerWin = false;
		overall = Mode.TITLE;
		result = 0;
		initX = (int) (Math.random() * Level.X_SIZE - 2) + 2;
		initY = (int) (Math.random() * Level.Y_SIZE - 2) + 2;
		
		hero = new Player(initX, initY);
		dungeon[0] = new Level(10, initX, initY, 0);
		dungeon[0].updateVisibility();
		addBuffer("It's dark down here...");
		
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
		background(0);
		textFont(f);
		noStroke();
		
		switch(overall) {
		case GAME:
			pDisplay(dungeon[hero.getCurrentLevel()]);
			hitDisplay();
			if(playerTurn == true && moveEntered == true) {
				hero.move(result);
				dungeon[hero.getCurrentLevel()].updateVisibility();
				moveEntered = false;
			} else if(playerTurn == false){
				dungeon[hero.getCurrentLevel()].monsterMove();				
			}
			
			if(hero.getAlive() == false) {
				if(playerWin) {
					overall = Mode.WIN;
				} else {
					overall = Mode.LOSE;
				}
			}
			
			result = 0;
			break;
		case TITLE:
			titleScreen();
			break;
		case WIN:
			winScreen();
			break;
		case LOSE:
			loseScreen();
			break;
		}	
	}

	public void titleScreen() {
		textAlign(CENTER);
		fill(70, 111, 65);
		text("<Press Enter to Begin>", 700, 630);
		textAlign(LEFT);
	}
	
	public void winScreen() {
		
	}
	
	public void loseScreen() {
		textAlign(CENTER);
		fill(70, 111, 65);
		text("YOU LOSE", 700, 350);
		textAlign(LEFT);
	}
	
	private void hitDisplay() {
		//sub method for hit overlay
		Wounds body = hero.getBody();
		body.bleedOut();
		
		Hit[] hitList = body.getHits();
		int index = body.getHitsIndex();
		
		for(int i = 0; i < index; i++) {
			Hit hit = hitList[i];
			
			switch(hit.getType()) {
			case JELLYFISH:
				fill(176,196,222, 140);
				ellipse(hit.getDeviationX2(),hit.getDeviationY2(), hit.getCurrentRadius(), hit.getCurrentRadius());
				
				//not array coordinates, but rather screen coordinates
				ellipse(hit.getDeviationX(), hit.getDeviationY(), hit.getCurrentRadius(),hit.getCurrentRadius());
				break;

			case SHARK:
				fill(255,0,0, 200);
				ellipse(hit.getX(), hit.getY(), hit.getCurrentRadius(), hit.getCurrentRadius());
				break;
			
			case BARRICUDA:
				stroke(255);
				line(hit.getX() - hit.getDeviation(), 0, hit.getX() + hit.getDeviation(), 1400);
				noStroke();
				break;	
				
			case EEL:
				stroke(255);
				noFill();
				arc(hit.getDeviationX(), hit.getDeviationY(), hit.getDeviationX2(), hit.getDeviationY2(), 
						0, hit.getCurrentRadius());
				noStroke();
					break;
			default:
				break;
			
			}
		}
	}
	
	private void pDisplay(Level level) {
		Space[][] display = level.getDesign();
		
		textFont(f);
		fill(255, 125, 125);
		int baseX = 150;
		int baseY = 50;
		int opacity = 255;
		
		//basic text information
		text(hero.vitals(), 10, 20);
		text(hero.getInventory().toString(), 10, 155);
		text("Oceanographic Expedition X R31", 575, 20);
		text(buffer(), 1200, 20);
		
		textFont(fixed);
		text(localRadar(), 1200, 415);
		
		textFont(f);
		
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
				case EEL:
					fill(70, 111, 65);
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
					
				//	System.out.printf("ScreenX: %d ScreenY: %d\n" , screenX, screenY);
					//for use by hitDisplay()
					Game.screenX = baseX;
					Game.screenY = baseY;
				} else if (display[j][i].getItem() != null){
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

	private String buffer() {
		// TODO Auto-generated method stub
		String output = "     Environmental Factors\n";
		output += "--------------------------------------\n";
		output += textBuffer;
			
		return output;
	}
	
	public static void addBuffer(String text) {
		textBuffer.enqueue(text);
		
		if(textBuffer.getIndex() > 13) {
			textBuffer.dequeue();
		}
	}

	public void keyPressed() {
		
		if(key == ESC) {
			System.exit(0);
		}
		
		if(overall == Mode.TITLE && (key == ENTER || key == RETURN)) {
				overall = Mode.GAME;
		}
		
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
		case('g'):
			result = 6;
			moveEntered = true;
			break;
		default:
			hero.getInventory().itemUse(key);
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

	public String localRadar() {
		String radar = "    Local Sonar\n";
		radar += "X X X X X\n";
		Level level = Game.dungeon[playerLevel]; 
		
		for(int j = -3; j <= 3; j++) {
			radar += "X ";
			
			for(int i = -3; i <= 3; i++) {
				if(level.isInLevel(hero.getX() + i, hero.getY() + j)) {
					if(j == 0 && i == 0) {
						radar += "@ ";
					} else {
						radar += level.getDesign()[hero.getY() + j][hero.getX() + i].trueSee() + " ";
					}
				} else {
					radar += "X ";
				}
			}
			
			radar += "X\n";
		}
		
		radar += "X X X X X";
		
		return radar;
	}
	
	public static void setPlayerWin(boolean input) {
		playerWin = input;
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
