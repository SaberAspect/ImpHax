package mod.imphack.module;

public enum Category {
	COMBAT("Combat"), RENDER("Render"), MOVEMENT("Movement"), PLAYER("Player"), UTILITIES("Utilities"), HUD("Hud"),
	CLIENT("Client");

	public final String name;

	Category(String name) {
		this.name = name;
	}
}
