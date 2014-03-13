package game.enemies;

import game.Animus;
import game.Game;
import game.Space;
import game.enums.SpaceType;

public class Eel extends Animus {
	int chance;
	String aspect;
	boolean isAlive;
	int currentLevel;
	float lethargy;
	
	public Eel(int initX, int initY) {
		x = initX;
		y = initY;
		lethargy = (float) 1.5;
		aspect = "u";
		isAlive = true;
		type = SpaceType.EEL;
		currentLevel = Game.hero.getCurrentLevel();
	}

	public void move(int direction) {
		//chance = (int) Math.random() * 2;
		normalize();
		
		wander(type, lethargy);
		
		shock();	
	}
	
	public static void normalize(int k, int l) {
		Space[][] map = Game.dungeon[Game.playerLevel].getDesign();

		for(int j = -2; j < 2; j++) {
			for(int i = -2; i < 2; i++) {
				if(Game.dungeon[Game.playerLevel].isInLevel(k + i, l + j)) {
					if(map[l + j][k + i].getSpace() == SpaceType.SHOCK) {

						map[l + j][k + i].setSpace(SpaceType.EMPTY);
					}
				}
				
			}
		}
	}
	
	void normalize() {
		Space[][] map = Game.dungeon[Game.playerLevel].getDesign();

		for(int j = -2; j < 2; j++) {
			for(int i = -2; i < 2; i++) {
				if(Game.dungeon[Game.playerLevel].isInLevel(x + i, y + j)) {
					if(map[y + j][x + i].getSpace() == SpaceType.SHOCK) {

						map[y + j][x + i].setSpace(SpaceType.EMPTY);
					}
				}
				
			}
		}
	}
	
	void shock() {
		Space[][] map = Game.dungeon[Game.playerLevel].getDesign();
		
		for(int j = -1; j < 2; j++) {
			for(int i = -1; i < 2; i++) {
				if(Game.dungeon[Game.playerLevel].isInLevel(x + i, y + j) && map[y + j][x + i].getSpace() == SpaceType.EMPTY) {
						map[y + j][x + i].setSpace(SpaceType.SHOCK);

				}
			}
		}
	}
}
