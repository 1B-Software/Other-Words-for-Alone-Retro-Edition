package robatortas.code.files.core.level.tiles;

import java.util.Random;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class TileManager {
	
	public SpriteManager sprite;
	public int x, y;
	public int id;
	public LevelManager level;
	
	// Collision box within the 16x16 tile (pixel offsets from tile origin)
	public int colX = 0, colY = 0, colW = 16, colH = 16;
	
	protected Random random = new Random();
	
	public static TileManager[] tiles = new TileManager[256];
	
	public TileManager(SpriteManager sprite, int id) {
		this.sprite = sprite;
		this.id = id;
		if(tiles[id] != null) System.err.println("Duplicate Tile ID's");
	}
	
	public TileManager(SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	public TileManager() {}
	
	// What I mean with SEAMS is that they react to each other when touching.
	public boolean seamsToWater = false;
	public boolean seamsToGrass = false;
	public boolean seamsToRock = false;
	public boolean seamsToWall = false;
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		this.level = level;
	}
	
	public void hurt(LevelManager level, Mob mob, int x, int y, int damage, int dir) {
	}

	public boolean solid(LevelManager level, int xt, int yt, EntityManager e) {
		return false;
	}

	public boolean solidAt(LevelManager level, int xt, int yt, float px, float py, EntityManager e) {
		if(!solid(level, xt, yt, e)) return false;
		float left = (xt << 4) + colX;
		float top = (yt << 4) + colY;
		return px >= left && px < left + colW && py >= top && py < top + colH;
	}
}