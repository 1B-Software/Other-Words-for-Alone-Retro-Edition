package robatortas.code.files.core.level;

import robatortas.code.files.core.utils.IOUtils;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.GameLevelAddons;

public class GameLevel extends LevelManager {
	
	private GameLevelAddons addons = new GameLevelAddons();
	
	public GameLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path) {
		IOUtils io = new IOUtils();
		int[] data = io.createBufferedImage(width, height, path, tiles);
		this.tiles = data;
		System.out.println(tiles);
		
//		addons.spawn();
		
		player = new Player(10, 10, input);
		add(player);
		
	}
}