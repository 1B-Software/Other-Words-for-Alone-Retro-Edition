package robatortas.code.files.project.level.levels;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Bee;
import robatortas.code.files.project.entities.mobs.mobArchive.Butterfly;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.Level;

public class PlayerRoomLevel extends Level {
	
	public PlayerRoomLevel(String name, LevelManager level) {
		super(name, level);
	}

	int tick = 0;
	public void update() {
		tick++;
	}
	
	public void init() {
		LevelManager.player = new Player(3<<4, 5<<4, levelManager.input);
		add(LevelManager.player);
		
		super.environmentLight = 0.1f;
		
//		level.add(new Chicken(100<<4, 100<<4));
//		level.add(new Cow(4 << 4, 4 << 4));
//		level.add(new Sheep(4 << 4, 4 << 4));

		for(int i = 0; i < 10; i++) add(new Bee(7 << 4, 5 << 4));
		for(int i = 0; i < 10; i++) add(new Butterfly(7 << 4, 5 << 4));
	}
}