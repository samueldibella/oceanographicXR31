package game;

import game.enums.SpaceType;

public abstract class Animus {
	protected int currentLevel;
	protected int hp;
	protected int speed;
	protected int dmg;
	protected int x;
	protected int y;
	boolean isAlive;
	protected SpaceType type;
	protected SpaceType aspect;
	
	public Animus() {
		isAlive = true;
	}
	
	public void wander(SpaceType type) {
		int chance = (int) Math.random() * 9;

		switch(chance) {
		case 0:
			break;
			//move right
		case 1:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x - 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 2:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 3:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x - 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 4:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 5:
			break;
		case 6:
			if(Game.getLevel(currentLevel).getDesign()[y][x + 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == (x + 1) && Game.hero.getY() == y)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 7:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x - 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				x--;
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			}
			break;
		case 8:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		case 9:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x - 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(type);
			} 
			break;
		}
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
	
	public void seek() {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public SpaceType getType() {
		return type;
	}
	
	public void setAlive(boolean living) {
		isAlive = living;
	}
	
	public boolean getAlive() {
		return isAlive;
	}
	
	public String toString() {
		return aspect.toString();
	}
}
