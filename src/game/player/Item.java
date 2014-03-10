package game.player;

import game.enums.ItemType;

public class Item {

	char referent;
	ItemType type;
	
	public Item(ItemType choice, char generate) {
		referent = generate;
		type = choice;
	}
	
	public String toString() {
		return type.toString();
	}
}
