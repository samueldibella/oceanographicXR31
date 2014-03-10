package game;

public abstract class Animus {
	int currentLevel;
	int hp;
	int speed;
	int dmg;
	int x;
	int y;
	SpaceType aspect;
	
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
				}
				break;
			case 2:
				if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].isEmpty()) {
					x++;
				}
				break;
			case 3:
				if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].isEmpty()) {
					y++;
				}
				break;
			case 4:
				if(Game.getDungeon()[currentLevel].getDesign()[y][x - 1].isEmpty()) {
					x--;
				}
				break;
		}
	}
	
	public String toString() {
		return aspect.toString();
	}
}
