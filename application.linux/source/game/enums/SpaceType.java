package game.enums;

/**Enum for maze spaces.
 * 
 * @author Samuel
 * @see Maze
 */
public enum SpaceType {
	EMPTY('.'),
	HERO('@'),
	WALL('#'),
	EXIT('>'),
	SHOCK('+'),
	JELLYFISH('u'),
	BARRICUDA('B'),
	EEL('e'),
	SHARK('S'),
	AIM('*');
	
	private char type;
	
	/**Enum constructor.
	 * @param c desired character representation
	 */
	private SpaceType(char c) {
		type = c;
	}
	
	/**
	 * @return String associated character as a string
	 */
	@Override
	public String toString() {
		return Character.toString(type);
	}
}
