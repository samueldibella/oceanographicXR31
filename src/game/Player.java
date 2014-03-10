package game;

public class Player extends Animus{
	Item[] inventory;
	boolean isAlive;
	
	Player(int xCo, int yCo) {
		hp = 100;
		speed = 50;
		dmg = 20;
		currentLevel = 0;
		inventory = new Item[5];
		aspect = SpaceType.HERO;
		x = xCo;
		y = yCo;
		isAlive = true;
	}
	
	@Override
	public void move(int direction) {
		//	Space[] empty = Dungeon.getLevels()[currentLevel].design.checkNeigbors(space);
		//probs way too inefficient
			switch(direction) {
				case 0:
					break;
				//move right
				case 1:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].isEmpty()) {
						y--;
						hp--;
					}
					break;
				case 2:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].isEmpty()) {
						x++;
						hp--;
					}
					break;
				case 3:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].isEmpty()) {
						y++;
						hp--;
					}
					break;
				case 4:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x - 1].isEmpty()) {
						x--;
						hp--;
					}
					break;
				case 5:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x].getSpace() == SpaceType.EXIT) {
						currentLevel++;
						Game.getDungeon()[currentLevel] = new Level(10, x, y, currentLevel);
						hp = 100;
					}
			}
			
			if(hp == 0) {
				isAlive = false;
			}
		}
	
	public String inventory() {
		String output = "Inventory\n";
		return output;
	}
	
	public String vitals() {
		String output = "Player Vitals\n";
		output += "Level: " + (currentLevel + 1) + "\n";
		output += "Oxygen: " + hp + "%\n";
		//output +=
		
		return output;
	}
	
	public boolean getAlive() {
		return isAlive;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
}
