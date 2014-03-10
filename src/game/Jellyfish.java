package game;

public class Jellyfish extends Animus{
	int chance;
	int x;
	int y;
	String aspect;
	
	Jellyfish(int initX, int initY) {
		x = initX;
		y = initY;
		aspect = "u";
	}
	
	public void move(int direction) {
		//	Space[] empty = Dungeon.getLevels()[currentLevel].design.checkNeigbors(space);
		//probs way too inefficient
		//chance = (int) Math.random() * 9;
		
			switch(direction) {
				case 0:
					break;
				//move right
				case 1:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x - 1].isEmpty()) {
						y++;
						x--;
						Game.getDungeon()[currentLevel].getDesign()[y + 1][x - 1].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 2:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].isEmpty()) {
						y++;
						Game.getDungeon()[currentLevel].getDesign()[y + 1][x].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 3:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x + 1].isEmpty()) {
						y++;
						x++;
						Game.getDungeon()[currentLevel].getDesign()[y + 1][x + 1].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 4:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].isEmpty()) {
						y--;
						Game.getDungeon()[currentLevel].getDesign()[y - 1][x].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 5:
					break;
				case 6:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].isEmpty()) {
						x++;
						Game.getDungeon()[currentLevel].getDesign()[y][x + 1].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 7:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x - 1].isEmpty()) {
						x--;
						y--;
						Game.getDungeon()[currentLevel].getDesign()[y - 1][x - 1].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 8:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].isEmpty()) {
						y--;
						Game.getDungeon()[currentLevel].getDesign()[y - 1][x].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
				case 9:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x + 1].isEmpty()) {
						y++;
						x++;
						Game.getDungeon()[currentLevel].getDesign()[y - 1][x + 1].setCreature(false);
						Game.getDungeon()[currentLevel].getDesign()[y][x].setCreature(true);
					}
					break;
			}
		}
	
	
	public String toString() {
		return aspect;
	}
}
