package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;

public class Eel extends Animus {
	int chance;
	String aspect;
	boolean isAlive;
	int currentLevel;
	
	public Eel(int initX, int initY) {
		x = initX;
		y = initY;
		aspect = "u";
		isAlive = true;
		type = SpaceType.EEL;
		currentLevel = Game.hero.getCurrentLevel();
	}

	public void move(int direction) {
		wander(type);
	}
}
