package game.enemies;

import game.Animus;
import game.Game;
import game.Space;
import game.enums.SpaceType;
import game.enums.Visibility;

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

	@Override
	public void move(int direction) {
		int dx = Game.hero.getX() - x;
		int dy = Game.hero.getY() - y;
		float distanceFromPlayer = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
		
		normalize();
		
		if(Game.dungeon[currentLevel].getDesign()[y][x].getVisibility() == Visibility.INSIGHT && distanceFromPlayer < 4) {
			flee();
		} else {
			wander(type, lethargy);
		}
		
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
