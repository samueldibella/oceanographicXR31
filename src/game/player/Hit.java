package game.player;

import game.Game;
import game.enums.HitType;

public class Hit {
	int radius;
	int x;
	int y;
	HitType type;
	int deviation;
	int deviationY;
	int deviationX;
	
	Hit(HitType enemy) {
		switch(enemy) {
		case JELLYFISH:
			radius = 4;
			deviationX = x - ((int) Math.random() * 6) - 3;
			deviationY = y - ((int) Math.random() * 6) - 3;
			break;
		case EEL:
			radius = 5;
			break;
		case SHARK:
			radius = 6;
			break;
		case BARRICUDA:
			deviation = (int) (Game.hero.getX() + ((Math.random() * 20) - 10));
			break;
		}
		
		type = enemy;
	}
	
	//jellyfish only
	public int getDeviationX() {
		return deviationX;
	}
	
	//jellyfish only
	public int getDeviationY() {
		return deviationY;
	}
	
	public int getDeviation() {
		return deviation;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public HitType getType() {
		return type;
	}
}
