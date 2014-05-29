package game.player;

import processing.core.PImage;
import game.Game;
import game.enums.SpaceType;

public class Hit {
	float maxRadius;
	public float currentRadius;
	int x;
	int y;
	SpaceType type;
	
	float deviation;
	
	float deviationY;
	float deviationX;
	
	float deviationY2;
	float deviationX2;
	
	PImage img;
	
	public static int numOfEelHits = 0;
	
	@SuppressWarnings("incomplete-switch")
	Hit(SpaceType type2) {
		x = Game.screenX;
		y = Game.screenY;
		
		switch(type2) {
		case JELLYFISH:
			maxRadius = (int) (Math.random() * 40);
			currentRadius = 1;
			
			//size of first circle
			deviationX = (int) (Math.random() * 1399);
			deviationY = (int) (Math.random() * 699);
			
			//size of second circle
			deviationX2 = (int) (Math.random() * 1399);
			deviationY2 = (int) (Math.random() * 699);
			break;
		case EEL:
			numOfEelHits++;
			
			//reference (<= 3 eels = random pixel shifts, > 3 = large chunks
			deviationX = (int) (Math.random() * 1000) + 500;

			break;
			
		case SHARK:
			maxRadius = 100;
			currentRadius = 1;
			break;
			
		case BARRICUDA:
			maxRadius = (int) (Math.random());
			currentRadius = 0;
			
			deviation = (float) (Math.random() * 40) - 20;
			
			//first spot == x, y
			
			//second
			deviationX = x + (int) ((Math.random() * 30)) * 2;
			deviationY = y + (int) ((Math.random() * 30)) * 2;
			
			//third
			deviationX2 = x - (int) (Math.random() * 30);
			deviationY2 = y - (int) (Math.random() * 30);

		}
		
		type = type2;
	}
	
	public void increment() {
		switch(type) {
		case SHARK:
			currentRadius++;
			break;
		case BARRICUDA:
			currentRadius++;
			break;
		default:
			currentRadius++;
			break;
		}
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
	
	public float getCurrentRadius() {
		return currentRadius;
	}
	
	public SpaceType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
