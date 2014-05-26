package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;

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

	@Override
	public void move(int direction) {
		int dx = Game.hero.getX() - x;
		int dy = Game.hero.getY() - y;
		float distanceFromPlayer = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
		
		if(distanceFromPlayer < 15) {
			seek();
		} else {
			wander(type, lethargy);
		}

	}
}
