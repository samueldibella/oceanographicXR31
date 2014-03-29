package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;

public class Barricuda extends Animus{
	int chance;
	String aspect;
	boolean isAlive;
	boolean playerSighted;
	int currentLevel;
	float lethargy;
	
	public Barricuda(int initX, int initY) {
		//currentLevel = Game.hero.getCurrentLevel();
		x = initX;
		y = initY;
		aspect = "B";
		lethargy = 1;
		isAlive = true;
		type = SpaceType.BARRICUDA;
		currentLevel = Game.hero.getCurrentLevel();
		playerSighted = false;
	}

	@Override
	public void move(int direction) {
		seek();

	}
	
}

