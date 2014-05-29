package game.enums;

public enum ItemType {
	DRILL("^","Mining drill"),
	ECHO("%","Echo. Map"),
	ADRL("|","Battery"),
	RELAY("&","Comm. Relay"),
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
	
	@Override
	public String toString() {
		return script;
	}
}
