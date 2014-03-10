package game;

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
		this.seen = Visibility.INSIGHT;
	}

	public boolean isEmpty() {
		if((space == SpaceType.EMPTY || space == SpaceType.EXIT || space == SpaceType.JELLYFISH)) {
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
	public String toString() {	
		if (creature == true) {
			for(int i = 0; i < Game.getLevel(level).getBeings().size(); i++) {
				if(this.x == Game.getLevel(level).getBeings().get(i).x && 
				   this.y == Game.getLevel(level).getBeings().get(i).y) {
					return Game.getLevel(level).getBeings().get(i).toString();
				}
			}
		} else  if (item != null) {
			return item.toString();
		} 
		
		return getSpace().toString();
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
	public void setSpace(SpaceType space) {
		this.space = space;
	}

	public Visibility getVisibility() {
		// TODO Auto-generated method stub
		return seen;
	}
}
