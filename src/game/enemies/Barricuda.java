package game.enemies;

import game.Animus;
import game.Game;
import game.Space;
import game.enums.SpaceType;
import game.enums.Visibility;

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

	public void move(int direction) {
		//System.out.println(direction);
		Space[][] map = Game.getLevel(currentLevel).getDesign();
		
		if(map[y][x].getVisibility() == Visibility.INSIGHT)	{
			playerSighted = true;
		} else {
			playerSighted = false;
		}

		if(playerSighted) {
			seek();
		} else {
			wander(type, lethargy);
		}

	}

	public void seek() {

	}

	
}

