package game;

import game.enums.SpaceType;

public abstract class Animus {
	protected int currentLevel;
	protected int hp;
	protected int speed;
	protected int dmg;
	protected int x;
	protected int y;
	protected SpaceType aspect;
	
	public Animus() {
	}
	
	public void wander(SpaceType type) {
		
	}
	
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
