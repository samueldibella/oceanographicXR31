package game.player;

import game.Game;
import game.enums.InputMode;
import game.enums.ItemType;

public class Inventory {
	int inventoryIndex;
	Item[] inventory; 

	public Inventory() {
		inventoryIndex = 0;
		inventory = new Item[22];

		//frequency
		addItem(ItemType.ADRL);
		addItem(ItemType.DRILL);
		addItem(ItemType.OXYGEN);
		addItem(ItemType.ECHO);
		addItem(ItemType.RELAY);
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
			inventory[inventoryIndex] = new Item(harpoon, toBe);
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

	public void itemUse(char key) {
		boolean isThere = false;

		for(int i = 0; i < inventoryIndex; i++) {
			if(inventory[i] != null && inventory[i].getReferent() == key) {
				
				switch(inventory[i].getType()) {
				case DRILL:
					Game.addBuffer("The label says 'point and click'.");
					Game.inputMode = InputMode.DRILL;
					break;
				case ADRL:
					Game.addBuffer("The world blurs, and you tense.");
					Game.inputMode = InputMode.ADRL;
					Game.druggedTurns = 5;
					break;
				case ECHO:
					Game.dungeon[Game.playerLevel].echo();
					Game.addBuffer("Water tremor data floods in.");
					break;
				case RELAY:
					int index = Game.hero.getBody().getHitsIndex();
					for(int l = 0; l < 3; l++) {
						Game.hero.getBody().setHits(index, null);
						Game.hero.getBody().setHitsIndex(index--);
					}
					
					Game.addBuffer("Your connection clears.");
					break;
				case OXYGEN:
					if(Game.hero.hp > 50) {
						Game.hero.hp = 100;
					} else if (Game.hero.hp > 0) {
						Game.hero.hp += 50;
					}
					
					Game.addBuffer("You hook up the old cannister.");
					break;
				}
				for(int j = i; j < inventoryIndex; j++) {
					inventory[j] = inventory[j+1];
				}
				
				inventoryIndex--;
				isThere = true;
			}
		}

		if(!isThere) {
			//outputbuffer;
		}
	}

	@Override
	public String toString() {
		String output = "\tInventory\n";
		for(int i = 0; i < inventoryIndex; i++) {
			output += inventory[i].referent + ". " + inventory[i].getType();
			output += " " + inventory[i].getType().getDescription() + "\n";
		}
		return output;
	}
}

