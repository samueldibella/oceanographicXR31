package game.enums;

public enum ItemType {
	DRILL("^","Mining drill"),
	ECHO("%","Echolocative Map"),
	HARPOON("|","Harpoon"),
	RELAY("&","Communication Relay"),
	OXYGEN("=","Oxygen Tank");
	
	private String script;
	private String description;
	
	private ItemType(String type, String longform) {
		script = type;
		description = longform;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return script;
	}
}
