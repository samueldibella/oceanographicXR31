package game;

import game.enums.SpaceType;

public abstract class Animus {
	protected int currentLevel;
	public int hp;
	protected float lethargy;
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
	
	public void wander(SpaceType type, float lethargy) {
		int chance = (int) (Math.random() * 9 * lethargy);
		Space[][] map = Game.getLevel(currentLevel).getDesign();
		
		switch(chance) {
		
		case 1:
			if(map[y + 1][x - 1].getSpace() == SpaceType.EMPTY) {
				
				map[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x--;
				map[y][x].setSpace(type);
			} 
			break;
		case 2:
			if(map[y + 1][x].getSpace() == SpaceType.EMPTY) {
				map[y][x].setSpace(SpaceType.EMPTY);
				y++;
				map[y][x].setSpace(type);
			} 
			break;
		case 3:
			if(map[y + 1][x + 1].getSpace() == SpaceType.EMPTY || (Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x++;
				map[y][x].setSpace(type);
			} 
			break;
		case 4:
			if(map[y - 1][x].getSpace() == SpaceType.EMPTY || (Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				y--;
				map[y][x].setSpace(type);
			} 
			break;
		case 6:
			if(map[y][x + 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == (x + 1) && Game.hero.getY() == y)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				x++;
				map[y][x].setSpace(type);
			} 
			break;
		case 7:
			if(map[y - 1][x - 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y - 1)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				x--;
				y--;
				map[y][x].setSpace(type);
			}
			break;
		case 8:
			if(map[y - 1][x].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				y--;
				map[y][x].setSpace(type);
			} 
			break;
		case 9:
			if(map[y - 1][x + 1].getSpace() == SpaceType.EMPTY || 
			(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				map[y][x].setSpace(SpaceType.EMPTY);
				y--;
				x++;
				map[y][x].setSpace(type);
			} 
			break;
		default:
			
			break;
		}
	}
	
	public void move(int direction) {
	//	Space[] empty = Dungeon.getLevels()[currentLevel].design.checkNeigbors(space);
	//probs way too inefficient
		switch(direction) {
			case 0:
				break;
			//move up
			case 1:
				if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].isEmpty()) {
					y--;
				}
				break;
			//move right
			case 2:
				if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].isEmpty()) {
					x++;
				}
				break;
			//move down
			case 3:
				if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].isEmpty()) {
					y++;
				}
				break;
			//move left
			case 4:
				if(Game.getDungeon()[currentLevel].getDesign()[y][x - 1].isEmpty()) {
					x--;
				}
				break;
		}
	}
	
	public void seek() {
		Level level = Game.dungeon[currentLevel];
		int xFinal = 0;
		int yFinal = 0;
		int scentFinal = 0;
		
		
		for(int j = -1; j < 2; j++) {
			for(int i = -1; i < 2; i++) {
				if(level.isInLevel(y + j, x + i) && level.getDesign()[y + j][x + i].getScent() > scentFinal) {
					xFinal = x + i;
					yFinal = y + j;
				}
			}
		}
		
		level.getDesign()[y][x].setSpace(SpaceType.EMPTY);
		if(xFinal > x) {
			x++;
		} else if (xFinal < x) {
			x--;
		}
		
		if(yFinal > y) {
			y++;
		} else if(yFinal < y) {
			y--;
		}
		
		for(int i = 0; i < level.getBeings().size(); i++) {
			if(level.getBeings().get(i).x == x && level.getBeings().get(i).y == y) {
				level.getBeings().get(i).isAlive = false;
				Game.addBuffer("A slight tremor in the water...");
			}
		}
		
		level.getDesign()[y][x].setSpace(type);
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
