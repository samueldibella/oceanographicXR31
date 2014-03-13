package game.player;

import game.enums.ItemType;

public class Item {

	char referent;
	ItemType type;
	
	public Item(ItemType choice) {
		referent = ' ';
		type = choice;
	}
	
	public ItemType getType() {
		return type;
	}
	
	public void setReferent(char refer) {
		referent = refer;
	}
	
	public String toString() {
		return type.toString();
	}
}
