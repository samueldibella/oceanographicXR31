package game;

import java.awt.Color;

import game.enums.InputMode;
import game.enums.Mode;
import game.enums.SpaceType;
import game.enums.Visibility;
import game.player.Hit;
import game.player.Player;
import game.player.Wounds;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

@SuppressWarnings("serial")
public class Game extends PApplet {
	//TODO implement visibility (including walls), level generation
	//TODO monster generation, basic combat, item drops, inventory

	PFont fixed; 
	PFont f; 

	public static Level[] dungeon = new Level[25];
	int result;
	int initX;
	int initY;
	public static int druggedTurns;
	public static int screenX;
	public static int screenY;
	Mode overall;
	public static InputMode inputMode;
	static Queue textBuffer = new Queue();
	boolean moveEntered; 
	static boolean playerWin;
	static boolean playerTurn;
	public static Player hero;
	public static int playerLevel;
	
	@Override
	public void setup() {

		String[] fontList = PFont.list();
		
		//System.out.println(fontList[0]);
		frameRate(20);
		size(1400, 650);
		background(0);
		stroke(0);
		playerTurn = true;
		moveEntered = false;
		playerWin = false;
		overall = Mode.GAME;
		inputMode = InputMode.NORMAL;
		result = 0;
		initX = (int) (Math.random() * (Level.X_SIZE - 2)) + 2;
		initY = (int) (Math.random() * ( Level.Y_SIZE - 2)) + 2;

		hero = new Player(initX, initY);
		dungeon[0] = new Level(10, initX, initY, 0);
		dungeon[0].updateVisibility();
		addBuffer("It's dark down here...");
	}

	@Override
	public void draw() {
		background(0);
		noStroke();
		fixed = createFont("secrcode.tff", 14);
		f = createFont("VeraMono", 14);
		
		switch(overall) {
		case GAME:
			pDisplay(dungeon[hero.getCurrentLevel()]);
			hitDisplay();
			
			switch(inputMode) {
			case NORMAL:
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

				break;
			case DRILL:
				dungeon[playerLevel].drill(result);
				break;
			}

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
		fill(255,192,203);
		text("OCEANOGRAPHIC EXPEDITION XR31", 700, 200 );
		fill(70, 111, 65);
		String output = "The Letter came several years ago, with its strange drawings and arcane coordinates.\n";
		output += "It promised an escape, not just from the humdrum rhythm of normal life, but from world.\n";
		output += "You settled your affairs, sold all your assets, bought a single hydrosuit, and chartered\n";
		output += "a small boat to the location obliquely hinted at by the text.";
		text(output,700, 250);
		text("<Press Enter to Begin>", 700, 630);
		textAlign(LEFT);
	}

	public void winScreen() {
		textAlign(CENTER);
		fill(255,193,37);
		text('@', 700, 150);
		fill(255, 125, 125);
		String output = "The moment you descend to the final depth reading, you can sense something wrong.\n";
		output += "The fish and the colorful plants that surrounded you on your descent are not here.\n";
		output += "Carved grey stones litter the ocean floor, and you can make out a larger structure\n";
		output += "through the murk. It looks... like a city of some sort. But that can't be, there\n";
		output += "should be nothing here. You expected to find a treasure, another hint, but not this.\n";
		output += "You brush against a rock outcropping and notice that it is also engraved with dark runes.\n";
		output += "It seems be depicting a great crouching figure, protuberant and alien on the ocean floor.\n";
		output += "Like a dream, a slow fear creeps into your mind, as you realize what exactly it was that\n";
		output += "brought you here. The image, the idol, half-remembered and magnetic, waiting in the murk.";
		
		
		output += "\nYour boat, with all your notes, drifts to the mainland, guided by a gentle ocean current.\n";
		fill(0,139,139);
		output += "\n\n Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn";
		text(output, 700, 170);
	}

	public void loseScreen() {
		textAlign(CENTER);
		fill(255,193,37);
		text('@', 700, 150);
		fill(255, 125, 125);
		String output = "You have been acutely aware of your diminishing oxygen reserves of some time.\n";
		output += "But as the gauge crawls toward red you are nowhere near your destination.\n";
		output += "It's not too far too go back, but something pulls you onward, even as your breathe\n";
		output += "grows ragged. You can barely see through the gunk and mechanical malfunction,\n";
		output += "as you start to lose consciousness, still going forward. Soon even blind determination\n";
		output += "is no match for your oxygen deprived brain, and you fall to the sea floor, among the sand.\n";
		output += "You think you can feel the presence of something larger \nthan any fish in these caves\n";
		output += "and then you black out \n and you don't \n get up.";
		text(output, 700, 170);
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

				line(hit.getX(), hit.getY(), hit.getDeviationX(), hit.getDeviationY());
				line(hit.getX(), hit.getY(), hit.getDeviationX2() + hit.getDeviation(), hit.getDeviationY2() - hit.getDeviation());
				
				if(hit.currentRadius == 10) {
					line(hit.getDeviationX(), hit.getDeviationY(), 
							hit.getDeviationX() + (hit.getDeviation() * 4), hit.getDeviationY() + (hit.getDeviation() * 4) );
					line(hit.getDeviationX2(), hit.getDeviationY2(),
							hit.getDeviationX() + (hit.getDeviation() * 3), hit.getDeviationY() + (hit.getDeviation() * 4) );
				}
				
				noStroke();
				break;	
				
			default:
				break;

			}
		}

		//distort screen proportional to eel presence
		int yGet;
		int xGet;
		int ySet;
		int xSet;
		
		loadPixels();
		
		int white = color(0);
		
		for(int i = 0; i < Hit.numOfEelHits; i++) {
			for(int j = 0; j < 10; j++) {
				yGet = (int) (Math.random() * 450) + 50;
				xGet = (int) (Math.random() * 1200) + 50;
				ySet = (int) (Math.random() * 450) + 50;
				xSet = (int) (Math.random() * 1200) + 50;
				PImage glitch = get(xGet, yGet, 300, 50);
				
				image(glitch, xSet, ySet);
			}
		} 
		
		updatePixels();
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

		text("       Local Sonar", 1200, 415);
		String radar = localRadar();
		int radarX = 1200;
		int radarY = 430;
		for(int j = 0; j < radar.length(); j++) {
			if(radar.charAt(j) == '\n') {
				radarX = 1200;
				radarY += 15;
			} else {
				text(radar.charAt(j), radarX, radarY);
				radarX += 15;
			}
		}

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
					//handled below
				case WALL:
					fill(0,139,139, opacity);
					break;
				default:
					fill(255, opacity);
				}
				
				if(display[j][i].getSpace() != SpaceType.WALL && display[j][i].getSpace() != SpaceType.EXIT 
						&& display[j][i].getVisibility() == Visibility.VISITED) {
					fill(255, opacity);
				}

				//hero display
				if(j == hero.getY() && i == hero.getX()) {
					fill(255,193,37);
					text(hero.toString(), baseX, baseY);
					
					//for use by hitDisplay()
					Game.screenX = baseX;
					Game.screenY = baseY;
				} else if (display[j][i].getItem() != null){
					fill(255,0,0,opacity);
					text(display[j][i].toString(), baseX, baseY);	
				} else if (display[j][i].getSpace() == SpaceType.JELLYFISH){
					//special case for jellyfish
					if(Game.hero.getLights()) {
						text(SpaceType.EMPTY.toString(), baseX, baseY);
					} else {
						fill(176,196,222, 240);
						text(SpaceType.JELLYFISH.toString(), baseX, baseY);
						
					}
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

	@Override
	public void keyPressed() {

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
		case('g'): case('G'):
			result = 6;
			moveEntered = true;
		break;
		
		//lights
		case('e'): case('E'):
			result = 7;
			moveEntered = true;
			
		//possible use of inventory item
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

	/**UI element for player
	 * 
	 * @return
	 */
	public String localRadar() {
		String radar = "XXXXXXXXX\n";
		Level level = Game.dungeon[playerLevel]; 

		for(int j = -3; j <= 3; j++) {
			radar += "X";

			for(int i = -3; i <= 3; i++) {
				if(level.isInLevel(hero.getX() + i, hero.getY() + j)) {
					if(j == 0 && i == 0) {
						radar += "@";
					} else {
						radar += level.getDesign()[hero.getY() + j][hero.getX() + i].trueSee();
					}
				} else {
					radar += "X";
				}
			}

			radar += "X\n";
		}

		radar += "XXXXXXXXX";

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
