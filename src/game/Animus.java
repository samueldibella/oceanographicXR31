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
		Space[] neighbors = new Space[9];
		int buildIndex = 0;
		int maxScent = 0;
		
		for(int j = -1; j <= 1; j++) {
			for(int i = -1; i <= 1; i++) {
				if(level.isInLevel(y + j, x + i)) {
					neighbors[buildIndex++] = level.getDesign()[y + j][x + i];
				}	
			}
		}
		
		for(int i = 0; i < 8; i++) {
			if(neighbors[i] != null && neighbors[i].getScent() > neighbors[maxScent].getScent()) {
				maxScent = i;
			}
		}
		
		level.getDesign()[y][x].setSpace(SpaceType.EMPTY);
		
		x = neighbors[maxScent].getX();
		y = neighbors[maxScent].getY();
		
		neighbors[maxScent].setSpace(type);
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
	
	@Override
	public String toString() {
		return aspect.toString();
	}
}
