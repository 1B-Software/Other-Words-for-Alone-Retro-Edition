package robatortas.code.files.project.level.levels;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.Level;

public class OutsideLevel extends Level {
	
	public OutsideLevel(String name, LevelManager level) {
		super(name, level);
	}

	public void init() {
//		levelManager.unload();
		environmentLight = 1f;
		LevelManager.player = new Player(3<<4, 5<<4, levelManager.input);
		levelManager.add(LevelManager.player);
	}
}
