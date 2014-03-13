package game.player;

import game.Game;
import game.enums.ItemType;

public class Inventory {
	int inventoryIndex;
	Item[] inventory; 

	public Inventory() {
		inventoryIndex = 0;
		inventory = new Item[22];

		//frequency
		addItem(ItemType.HARPOON);
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

		for(int i = 1; i < inventoryIndex; i++) {
			if(inventory[i].getReferent() == key) {

				switch(inventory[i].getType()) {
				case DRILL:
					System.out.println("DRILL");
					break;
				case HARPOON:
					System.out.println("HARPOON");
					break;
				case ECHO:
					Game.dungeon[Game.playerLevel].echo();
					break;
				case RELAY:
					int index = Game.hero.getBody().getHitsIndex();
					Game.hero.getBody().setHits(index, null);
					Game.hero.getBody().setHitsIndex(index--);
					break;
				case OXYGEN:
					Game.hero.hp += 50;
					break;
				}
				for(int j = i; j < inventoryIndex; j++) {
					inventory[j] = inventory[j+1];
				}

				isThere = true;
			}
		}

		if(!isThere) {
			//outputbuffer;
		}
	}

	public String toString() {
		String output = "\tInventory\n";
		for(int i = 0; i < inventoryIndex; i++) {
			output += inventory[i].referent + ". " + inventory[i].getType();
			output += " " + inventory[i].getType().getDescription() + "\n";
		}
		return output;
	}
}

