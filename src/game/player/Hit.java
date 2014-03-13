package game.player;

import game.Game;
import game.enums.SpaceType;

public class Hit {
	float maxRadius;
	float currentRadius;
	int x;
	int y;
	SpaceType type;
	float deviation;
	float deviationY;
	float deviationY2;
	float deviationX;
	float deviationX2;
	
	Hit(SpaceType type2) {
		x = Game.screenX;
		y = Game.screenY;
		
		switch(type2) {
		case JELLYFISH:
			maxRadius = (int) (Math.random() * 40);
			currentRadius = 1;
			deviationX = (int) (Math.random() * 1399);
			deviationY = (int) (Math.random() * 699);
			deviationX2 = (int) (Math.random() * 1399);
			deviationY2 = (int) (Math.random() * 699);
			break;
		case EEL:
			maxRadius = (float) (Math.random() * 2 * (Math.PI));
			currentRadius = (float) (Math.random() * 2 * (Math.PI));
			
			//eclipse coordinates
			deviationX = (int) (Math.random() * 1399);
			deviationY = (int) (Math.random() * 699);
			
			//eclipse size
			deviationX2 = (int) (Math.random() * 80);
			deviationY2 = (int) (Math.random() * 80);
			break;
			
		case SHARK:
			maxRadius = 100;
			currentRadius = 1;
			break;
			
		case BARRICUDA:
			maxRadius = (int) (Math.random());
			currentRadius = 1;
			
			//base delta y for crack growing
			deviationY = 1;
			
			//x deviation of crack
			deviation = (int) (((Math.random() * 200)));
			
			if(maxRadius == 0) {
				deviation += -1;
			}
			break;
		}
		
		type = type2;
	}
	
	//jellyfish only
	public float getDeviationX() {
		return deviationX;
	}
	
	//jellyfish only
	public float getDeviationY() {
		return deviationY;
	}
	
	public float getDeviationX2() {
		return deviationX2;
	}
	
	//jellyfish only
	public float getDeviationY2() {
		return deviationY2;
	}
	
	public float getDeviation() {
		return deviation;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getMax() {
		return maxRadius;
	}
	
	public void incrementCrack(double d) {
		deviationY += d;
	}
	
	public void incrementCurrentRadius(double d) {
		currentRadius += d;
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
