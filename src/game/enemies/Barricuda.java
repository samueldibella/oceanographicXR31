package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;
import game.enums.Visibility;

public class Barricuda extends Animus{
	int chance;
	String aspect;
	boolean isAlive;
	boolean playerSighted;
	int currentLevel;

	public Barricuda(int initX, int initY) {
		//currentLevel = Game.hero.getCurrentLevel();
		x = initX;
		y = initY;
		aspect = "B";
		isAlive = true;
		type = SpaceType.BARRICUDA;
		currentLevel = Game.hero.getCurrentLevel();
		playerSighted = false;
	}

	public void move(int direction) {
		//System.out.println(direction);
		if(Game.getLevel(currentLevel).getDesign()[y][x].getVisibility() == Visibility.INSIGHT)	{
			//playerSighted = true;
		} else {
			playerSighted = false;
		}

		if(playerSighted) {
			seek();
		} else {
			wander(type);
		}

	}

	public void seek() {

	}

	
}

