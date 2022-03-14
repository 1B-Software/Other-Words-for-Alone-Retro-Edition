package robatortas.code.files.project.level;

import robatortas.code.files.core.level.GameLevel;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;

public class GameLevelAddons extends GameLevel {

	public GameLevelAddons(String path) {
		super(path);
	}
	
	public void spawn() {
		level.add(new Player(10, 10));
	}
}
