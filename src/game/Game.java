package game;
import processing.core.PApplet;
import processing.core.PFont;

public class Game extends PApplet {
	//TODO implement visibility (including walls), level generation
	//TODO monster generation, basic combat, item drops, inventory
	PFont f = createFont("VeraMono", 14);
	static Level[] dungeon = new Level[10];
	int result;
	int initX;
	int initY;
	public Player hero;
	boolean playerTurn;

	
	
	public void setup() {
		frameRate(20);
		size(1400, 700);
		background(0);
		playerTurn = true;
		result = 0;
		initX = (int) (Math.random() * Level.X_SIZE - 2) + 1;
		initY = (int) (Math.random() * Level.Y_SIZE - 2) + 1;
		dungeon[0] = new Level(10, initX, initY, 0);
		hero = new Player(dungeon[0].getHeroX(), dungeon[0].getHeroY());
	}

	public void draw() {
		stroke(255);
		background(0);
		//System.out.print(hero.getAlive());
		//while(hero.getAlive() == true) {
			if(playerTurn) {
				hero.move(result);
			} else {
				dungeon[hero.getCurrentLevel()].monsterMove();
			}
			pDisplay(dungeon[hero.getCurrentLevel()]);
			result = 0;
	//	}
		
		
	}

	public void pDisplay(Level level) {
		Space[][] display = level.getDesign();
		textFont(f);
		fill(255);
		int baseX = 150;
		int baseY = 50;
			
		text(hero.vitals(), 10, 20);
		
		for (int j = 0; j < Level.Y_SIZE; j++) {
			for (int i = 0; i < Level.X_SIZE; i++) {
				if(j == hero.getY() && i == hero.getX()) {
					//compensate for font @
					text(hero.toString(), baseX - 4, baseY + 2);
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
			break;
		case('6'): case('d'): case('D'): 
			result = 2;
			break;
		case('2'): case('s'): case('S'): 
			result = 3;
			break;
		case('4'): case('a'): case('A'): 
			result = 4;
			break;
		}
		
		//dungeon level
		if(key == '>') {
			result = 5;
		}
	
		//arrow player movement
		if(key == CODED) {
			switch(keyCode) {
			case(UP):
				result = 1;
				break;
			case(RIGHT):
				result = 2;
				break;
			case(DOWN):
				result = 3;
				break;
			case(LEFT):
				result = 4;
				break;
			case(ESC):
				System.exit(0);
			}
		}
	}

	/*public void keyReleased() {  
		switch(key) {
		default: result = 0;
		}
	}
*/	
	public static Level[] getDungeon() {
		return dungeon;
	}

}
