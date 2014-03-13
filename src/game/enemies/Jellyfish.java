package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;

public class Jellyfish extends Animus{
	int chance;
	String aspect;
	boolean isAlive;
	float lethargy;
	
	public Jellyfish(int initX, int initY) {
		x = initX;
		y = initY;
		aspect = "u";
		lethargy = 2;
		isAlive = true;
		type = SpaceType.JELLYFISH;
		currentLevel = Game.hero.getCurrentLevel();
	}

	public void move(int direction) {
			wander(type, lethargy);
		}
	}

