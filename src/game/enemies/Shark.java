package game.enemies;

import game.Animus;
import game.Game;
import game.enums.SpaceType;
import game.enums.Visibility;

public class Shark extends Animus {
	int chance;
	int x;
	int y;
	String aspect;
	boolean playerSighted;
	int currentLevel;
	
	public Shark(int initX, int initY) {
		//currentLevel = Game.hero.getCurrentLevel();
		x = initX;
		y = initY;
		aspect = "B";
		playerSighted = false;
		currentLevel = Game.hero.getCurrentLevel();
	}
	
	public void move(int direction) {
		//	Space[] empty = Dungeon.getLevels()[currentLevel].design.checkNeigbors(space);
		//probs way too inefficient
		//
		

		//System.out.println(direction);
		if(Game.getLevel(currentLevel).getDesign()[y][x].getVisibility() == Visibility.INSIGHT)	{
			//playerSighted = true;
		} 
		
		if(playerSighted) {
			seek();
		} else {
			wander();
		}
	}
	
	public void seek() {
		
	}
	
	public void wander() {
		chance = (int) Math.random() * 9;
		
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
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 2:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 3:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x - 1].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 4:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 5:
			break;
		case 6:
			if(Game.getLevel(currentLevel).getDesign()[y][x + 1].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == (x + 1) && Game.hero.getY() == y)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 7:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x - 1].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x - 1 && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				x--;
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			}
			break;
		case 8:
			if(Game.getLevel(currentLevel).getDesign()[y - 1][x].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x && Game.hero.getY() == y - 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y--;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
		case 9:
			if(Game.getLevel(currentLevel).getDesign()[y + 1][x - 1].getSpace() == SpaceType.EMPTY || 
					(Game.hero.getX() == x - 1 && Game.hero.getY() == y + 1)) {
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.EMPTY);
				y++;
				x++;
				Game.getLevel(currentLevel).getDesign()[y][x].setSpace(SpaceType.BARRICUDA);
			} 
			break;
	}
	}
	
	public String toString() {
		return aspect;
	}
}
