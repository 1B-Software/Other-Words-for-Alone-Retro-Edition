package robatortas.code.files.core.level.tiles;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class TileManager {
	
	public SpriteManager sprite;
	public int x, y;
	public int id;
	
	public static TileManager[] tiles = new TileManager[256];
	
	public TileManager(SpriteManager sprite ,int id) {
		this.sprite = sprite;
		this.id = id;
		if(tiles[id] != null) System.err.println("Duplicate Tile ID's");
	}
	
	public boolean connectsToWater = true;
	public boolean connectsToGrass = true;
	public boolean connectsToRock = true;
	
	public void render(int x, int y, RenderManager screen) {
		
	}
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
	
	}

	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return false;
	}
}