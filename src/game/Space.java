package game;

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
	private ArrayList<Animus> beings;

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
		this.level = cL;
		this.setSpace(s);
		this.beings = Dungeon.getLevels()[level].getBeings();		
	}

	public boolean isEmpty() {
		if((space == SpaceType.EMPTY || space == SpaceType.EXIT) && creature == false) {
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

	public String toString() {	
		if (creature == true) {
			for(int i = 0; i < beings.size(); i++) {
				if(this.x == beings.get(i).x && this.y == beings.get(i).y) {
					return beings.get(i).toString();
				}
			}
		} else  if (item != null) {
			return item.toString();
		} else {
			return getSpace().toString();
		}
		return null;
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

	/**
	 * @param space
	 *            the space to set
	 */
	void setSpace(SpaceType space) {
		this.space = space;
	}
}
