package game;

import game.enums.ItemType;
import game.enums.SpaceType;
import game.enums.Visibility;
import game.player.Item;

import java.util.ArrayList;

/**
 * Class to represent each space in the maze
 * 
 * @author Samuel
 * 
 */
public class Space {
	private int level;
	private SpaceType space;
	private int x;
	private int y;
	private Item item;
	private boolean creature;
	private Visibility seen;
	private float scent;

	/**
	 * Class constructor
	 * 
	 * @param s
	 *            space of Point
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 */
	Space(SpaceType s, int x, int y, int cL) {
		this.x = x;
		this.y = y;
		level = cL;
		setSpace(s);
		seen = Visibility.UNVISITED;
		scent = 0;
	}
	
	Space(SpaceType s, int x, int y, int cL, Visibility vision) {
		this.x = x;
		this.y = y;
		level = cL;
		setSpace(s);
		seen = vision;
		scent = 0;
	}
	
	public boolean isEmpty() {
		if((space == SpaceType.EMPTY || space == SpaceType.JELLYFISH)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCreature(boolean spirit) {
		creature = spirit;
	}
	
	/**
	 * Getter for SpaceType of Point
	 * 
	 * @return space
	 * @see SpaceType
	 */
	public SpaceType getSpace() {
		return space;
	}

	//TODO add visibility to method
	@Override
	public String toString() {	
		ArrayList<Animus> beings = Game.getLevel(level).getBeings();
		
		if (creature == true && seen == Visibility.INSIGHT) {
			for(int i = 0; i < beings.size(); i++) {
				if(this.x == beings.get(i).x && this.y == beings.get(i).y) {
					return space.toString();
				}
			}
		} else if( seen == Visibility.VISITED && space != SpaceType.WALL && space != SpaceType.EXIT) {
			return SpaceType.EMPTY.toString();
		}
		
		if (item != null) {
			return item.toString();
		} 
		
		return space.toString();

	}
	
	public String trueSee() {
		return space.toString();
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public Item getItem() {
		return item;
	}
	
	//implement better version
	public void setItem(ItemType type) {
		if(type != null) {
			item = new Item(type);
		} else {
			item = null;
		}
	}
	
	/**
	 * @param space
	 *            the space to set
	 */
	public void setSpace(SpaceType space) {
		this.space = space;
	}

	public Visibility getVisibility() {
		return seen;
	}

	public void setVisibility(Visibility visited) {
		seen = visited;
	}
	
	public float getScent() {
		return scent;
	}
	
	public void setScent(float f) {
		scent = f;
	}
	
}
