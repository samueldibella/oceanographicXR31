package game.enums;

public enum ItemType {
	DRILL("Mining drill"),
	ECHO("Echolocative Map"),
	HARPOON("Harpoon"),
	RELAY("Communication Relay"),
	OXYGEN("Oxygen Tank");
	
	private String script;
	
	private ItemType(String type) {
		script = type;
	}
	
	public String toString() {
		return script;
	}
}
