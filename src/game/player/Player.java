package game.player;
import game.Animus;
import game.Game;
import game.Level;
import game.enums.ItemType;
import game.enums.SpaceType;

public class Player extends Animus{
	Item[] inventory;
	int inventoryIndex;
	Hit[] hits;
	int hitIndex;
	boolean isAlive;
	
	public Player(int xCo, int yCo) {
		hp = 100;
		speed = 50;
		dmg = 20;
		currentLevel = 0;
		inventory = new Item[26];
		inventoryIndex = 0;
		aspect = SpaceType.HERO;
		x = xCo;
		y = yCo;
		isAlive = true;
		hits = new Hit[20];
		hitIndex = 0;
		
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
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].getSpace() != SpaceType.WALL) {
						y--;
						hp--;
						Game.setPlayerTurn(false); 
					}
					break;
				case 2:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].getSpace() != SpaceType.WALL) {
						x++;
						hp--;
						Game.setPlayerTurn(false); 
					}
					break;
				case 3:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].getSpace() != SpaceType.WALL) {
						y++;
						hp--;
						Game.setPlayerTurn(false); 
					}
					break;
				case 4:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x - 1].getSpace() != SpaceType.WALL) {
						x--;
						hp--;
						Game.setPlayerTurn(false); 
					}
					break;
				case 5:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x].getSpace() == SpaceType.EXIT) {
						currentLevel++;
						Game.playerLevel++;
						Game.getDungeon()[currentLevel] = new Level(10, x, y, currentLevel);
						hp = 100;
						Game.setPlayerTurn(false); 
					}	
			}
			
			
			
			if(hp == 0) {
				isAlive = false;
			}
		}
	
	public String inventory() {
		String output = "\tInventory\n";
		for(int i = 0; i < inventoryIndex; i++) {
			output += inventory[i].referent + ". " + inventory[i].getType() + " " + inventory[i];
		}
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
	
	public Item referentFetch(char fetch) {
		for(int i = 0; i < inventoryIndex; i++) {
			if(inventory[inventoryIndex].referent == fetch) {
				return inventory[inventoryIndex];
			}
		}
		
		return null;
	}
	
	public void addItem(ItemType item) {
		char toBe = (char) ((Math.random() * 25) + 97);
		char[] referents = new char[inventoryIndex];
		boolean referenceClear;
		
		//checks to make sure that 
		if(inventoryIndex < 25) {
			inventory[inventoryIndex] = Game.getLevel(currentLevel).getDesign()[y][x].getItem();
			referenceClear = true;
			do {
				if(referentFetch(toBe) != null || toBe == 'w' || toBe == 'a' 
											   || toBe == 's' || toBe == 'd') {
					referenceClear = false;
					toBe = (char) ((Math.random() * 25) + 97);
				}
			} while(!referenceClear); 
			inventory[inventoryIndex].setReferent(toBe);
			inventoryIndex++;
		} else {
			//print to string buffer about inventory full
		}

	}
	
	public void addHit(SpaceType type) {
		Hit hit = new Hit(type);
		hits[hitIndex] = hit;
	}
	
	public Hit[] getHits() {
		return hits;
	}
	
	public int getHitsIndex() {
		return hitIndex;
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
