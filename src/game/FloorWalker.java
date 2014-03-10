package game;

public class FloorWalker {
	int direction;
	int chance;
	int startX;
	int startY;
	int x;
	int y;
	boolean minDistance;
	
	public FloorWalker(int xStart, int yStart) {
		direction = (int) (Math.random() * 3);
		x = xStart;
		y = yStart;
		startX = x;
		startY = y;
		minDistance = false;
	}
	
	void move() {
		//System.out.println(x + ", " + y + " Direction: " + direction);
		chance = (int) (Math.random() * 9);
		
		switch(direction) {
		//up
		case(0):
			if(y > 0) {
				y--;
			}
			break;
			
		//right
		case(1):
			if(x < Level.X_SIZE - 1) {
				x++;
			}
			break;
			
		//down
		case(2):
			if(y < Level.Y_SIZE - 1) {
				y++;
			}
			break;
			
		//left
		case(3):
			if(x > 0) {
				x--;
			}
			break;
		}
		
		if(chance < 7) {
			
		} else if(chance < 8) {
			if(direction != 3) {
				direction++;
			} else {
				direction = 0;
			}
		} else if(chance < 9) {
			if(direction != 0) {
				direction--;
			} else {
				direction = 3;
			}
		} else {
			if(direction < 2) {
				direction++;
			} else {
				direction--;
			}
		}
		
		if(Math.abs(x - startX) > 30 && Math.abs(y - startY) > 30) {
			minDistance = true;
		} else {
			minDistance = false;
		}
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	boolean getIsFar() {
		return minDistance;
	}
}
