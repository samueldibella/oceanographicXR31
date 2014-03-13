package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;
import game.enums.Visibility;

public class Shark extends Animus {
	int chance;
	String aspect;
	boolean isAlive;
	boolean playerSighted;
	int currentLevel;
	
	public Shark(int initX, int initY) {
		//currentLevel = Game.hero.getCurrentLevel();
		x = initX;
		y = initY;
		aspect = "B";
		isAlive = true;
		lethargy = 1;
		type = SpaceType.SHARK;
		playerSighted = false;
		currentLevel = Game.hero.getCurrentLevel();
	}

	public void move(int direction) {
		seek();
	}
}
