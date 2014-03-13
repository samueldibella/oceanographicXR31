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
		inventory = new Item[18];
		inventoryIndex = 0;
		aspect = SpaceType.HERO;
		x = xCo;
		y = yCo;
		isAlive = true;
		hits = new Hit[20];
		hitIndex = 0;
		
		//frequency
		addItem(ItemType.HARPOON);
		addItem(ItemType.DRILL);
		addItem(ItemType.OXYGEN);
		addItem(ItemType.ECHO);
		addItem(ItemType.RELAY);
		
	}
	
	@Override
	public void move(int direction) {
		int chance = (int) (Math.random() * 9);
		
			switch(direction) {
				case 0:
					break;
				//move right
				case 1:
					if(Game.getDungeon()[currentLevel].getDesign()[y - 1][x].getSpace() != SpaceType.WALL) {
						y--;
						if(chance < 6) {
							hp--;
						}
						Game.setPlayerTurn(false); 
					}
					break;
				case 2:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x + 1].getSpace() != SpaceType.WALL) {
						x++;
						if(chance < 6) {
							hp--;
						}
						Game.setPlayerTurn(false); 
					}
					break;
				case 3:
					if(Game.getDungeon()[currentLevel].getDesign()[y + 1][x].getSpace() != SpaceType.WALL) {
						y++;
						if(chance < 4) {
							hp--;
						}
						Game.setPlayerTurn(false); 
					}
					break;
				case 4:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x - 1].getSpace() != SpaceType.WALL) {
						x--;
						if(chance < 6) {
							hp--;
						}
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
					break;
				case 6:
					if(Game.getDungeon()[currentLevel].getDesign()[y][x].getItem() != null) {
						addItem(Game.getLevel(currentLevel).getDesign()[y][x].getItem().getType());
						Game.dungeon[currentLevel].getDesign()[y][x].setItem(null);
					}
					
			}
			
			
			
			if(hp == 0) {
				isAlive = false;
			}
		}
	
	public String inventory() {
		String output = "\tInventory\n";
		for(int i = 0; i < inventoryIndex; i++) {
			output += inventory[i].referent + ". " + inventory[i].getType();
			output += " " + inventory[i].getType().getDescription() + "\n";
		}
		return output;
	}
	
	public String vitals() {
		String output = "Player Vitals\n";
		output += "--------------------------\n";
		output += "Level: " + (currentLevel + 1) + "\n";
		output += "Oxygen: " + hp + "%\n";
		output += "--------------------------\n";
		
		return output;
	}
	
	public String textBuffer() {
		String output = "     Environmental Factors\n";
		output += "--------------------------------------\n";
		output += "It's dark down here";
		
		return output;
	}
	
	public void itemUse(char key) {
		for(int i = 0; i < inventoryIndex; i++) {
			if(key == inventory[i].getReferent()) {
				
				switch(inventory[i].getType()) {
				case DRILL:
					break;
				case HARPOON:
					break;
				case ECHO:
					Game.dungeon[currentLevel].echo();
					break;
				case RELAY:
					hits[hitIndex] = null;
					hitIndex--;
					break;
				case OXYGEN:
					hp += 50;
					break;
				}
				for(int j = i; j < inventoryIndex; j++) {
					inventory[j] = inventory[j+1];
				}
				
			}
		}
	}
		
	public Item referentFetch(char fetch) {
		for(int i = 0; i < inventoryIndex; i++) {
			if(inventory[inventoryIndex].referent == fetch) {
				return inventory[inventoryIndex];
			}
		}
		
		return null;
	}
	
	public void addItem(ItemType harpoon) {
		char toBe = (char) ((Math.random() * 25) + 97);
		boolean referenceClear;
		
		//checks to make sure that 
		if(inventoryIndex < 18) {
			inventory[inventoryIndex] = new Item(harpoon);
			referenceClear = true;
			toBe = (char) ((Math.random() * 25) + 97);
			
			do {
				if(referentFetch(toBe) != null || toBe == 'w' || toBe == 'a' 
											   || toBe == 's' || toBe == 'd' || toBe == 'g') {
					referenceClear = false;
					toBe = (char) ((Math.random() * 25) + 97);
				} else {
					referenceClear = true;
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
	
	public boolean getAlive() {
		return isAlive;
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
