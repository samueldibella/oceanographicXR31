package game.player;

import game.enums.ItemType;

//18 item max
public class Item {

	char referent;
	ItemType type;
	
	public Item(Item reference) {
		referent = ' ';
		type = reference.getType();
	}
	
	public Item(Item reference, char r) {
		referent = r;
		type = reference.getType();
	}
	
	public Item(ItemType choice, char r) {
		referent = r;
		type = choice;
	}
	
	public Item(ItemType choice) {
		referent = ' ';
		type = choice;
	}
	
	public char getReferent() {
		return referent;
	}
	
	public ItemType getType() {
		return type;
	}
	
	public void setReferent(char refer) {
		referent = refer;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}

	public char getDescription() {
		// TODO Auto-generated method stub
		return 0;
	}
}
