package robatortas.code.files.core.level.tiles;

import java.util.LinkedList;
import java.util.List;
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
	
	protected ConnectTile connect;
	
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
	
	// What I mean with SEAMS is that they react to each other when touching.
	// The exact same terminology as sewing two parts of cloth together, that is seaming.
	public boolean seamsToWater = false;
	public boolean seamsToGrass = false;
	public boolean seamsToRock = false;
	public boolean seamsToSand = false;
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		this.level = level;
	}
	
	public void hurt(LevelManager level, Mob mob, int x, int y, int damage, int dir) {
	}

	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return false;
	}
}