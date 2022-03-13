package robatortas.code.files.core.level;

import robatortas.code.files.core.utils.IOUtils;

public class GameLevel extends LevelManager {
	
	public GameLevel(String path) {
		super(path);
	}
	
	public void loadLevel(String path) {
		IOUtils io = new IOUtils();
		int[] data = io.createBufferedImage(width, height, path, tiles);
		this.tiles = data;
		System.out.println(tiles);
	}
}