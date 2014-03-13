package game;

import game.enums.SpaceType;
import game.enums.Visibility;

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
		
	}
	
	public void slide(int direction) {
		Space[][] map = Game.getDungeon()[currentLevel].getDesign();
		
		switch(direction) {
			case 0:
				break;
			//move up
			case 1:
				if(map[y - 1][x].isEmpty() || (Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
					map[y][x].setSpace(SpaceType.EMPTY);
					y--;
					map[y][x].setSpace(type);
				}
				break;
			//move right
			case 2:
				if(map[y][x + 1].isEmpty() || (Game.hero.getX() == x + 1 && Game.hero.getY() == y)) {
					map[y][x].setSpace(SpaceType.EMPTY);
					x++;
					map[y][x].setSpace(type);
				}
				break;
			//move down
			case 3:
				if(map[y + 1][x].isEmpty() || (Game.hero.getX() == x && Game.hero.getY() == y + 1)) {
					map[y][x].setSpace(SpaceType.EMPTY);
					y++;
					map[y][x].setSpace(type);
				}
				break;
			//move left
			case 4:
				if(map[y][x - 1].isEmpty() || (Game.hero.getX() == x - 1 && Game.hero.getY() == y)) {
					map[y][x].setSpace(SpaceType.EMPTY);
					x--;
					map[y][x].setSpace(type);
				}
				break;
		}
	}
	
	public void seek() {
		Level level = Game.dungeon[currentLevel];
		
		if(level.getDesign()[y][x].getVisibility() == Visibility.INSIGHT) {
			
			if(x > Game.hero.getX()) {
				slide(4);
			} else if (x < Game.hero.getX()) {
				slide(2);
			}
			
			if(y > Game.hero.getY()) {
				slide(1);
			} else if (y < Game.hero.getX()) {
				slide(3);
			}
		} else if(y != Game.hero.getY() && x != Game.hero.getX()){
			wander(type, lethargy);
		}
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
