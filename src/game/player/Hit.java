package game.player;

import game.Game;
import game.enums.SpaceType;

public class Hit {
	float maxRadius;
	float currentRadius;
	int x;
	int y;
	SpaceType type;
	int deviation;
	int deviationY;
	int deviationX;
	
	Hit(SpaceType type2) {
		x = Game.playerLevel;
		y = 
		
		switch(type2) {
		case JELLYFISH:
			maxRadius = 40;
			currentRadius = 1;
			deviationX = x - ((int) Math.random() * 20) - 10;
			deviationY = y - ((int) Math.random() * 20) - 10;
			break;
		case EEL:
			maxRadius = 40;
			currentRadius = 1;
			break;
		case SHARK:
			maxRadius = 70;
			currentRadius = 1;
			break;
		case BARRICUDA:
			maxRadius = 10;
			currentRadius = 1;
			deviation = (int) (Game.hero.getX() + ((Math.random() * 20) - 10));
			break;
		}
		
		type = type2;
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
	
	public float getCurrentRadius() {
		return currentRadius;
	}
	
	public SpaceType getType() {
		return type;
	}
	
	public String toString() {
		return type.toString();
	}
}
