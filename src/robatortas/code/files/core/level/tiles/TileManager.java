package robatortas.code.files.core.level.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class TileManager {
	
	public SpriteManager sprite;
	public int x, y;
	public int id;
	public int color;
	public LevelManager level;
	
	// Collision box within the 16x16 tile (pixel offsets from tile origin)
	public int colX = 0, colY = 0, colW = 16, colH = 16;
	
	protected Random random = new Random();
	
	public static HashMap<Integer, TileManager> tiles = new HashMap<>();
	
	public TileManager(SpriteManager sprite, int id) {
		this.sprite = sprite;
		this.id = id;
		tiles.put(id, this);
//		if(tiles[id] != null) System.err.println("Duplicate Tile ID's");
	}
	
	public TileManager(SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	public TileManager() {}
	
	// Gets the tile from the chosen layer with x, y coordinates
	public static TileManager getTile(int x, int y, int[] layer) {
	    if (x < 0 || y < 0 || x >= LevelManager.width || y >= LevelManager.height) {
	        return TileArchive.voidTile;
	    }

	    int color = layer[x + y * LevelManager.width];
	    TileManager tile = tiles.get(color);

	    if (tile == null) return TileArchive.voidTile;
	    return tile;
	}
	
	// Gets a list of all the tiles that are of that ID
	public List<TileManager> getTile(int id) {
		List<TileManager> lT = new ArrayList<>();
		for(int y = 0; y < level.height; y++) {
			for(int x = 0; x < level.width; x++) {
				TileManager lt = level.getLevel(x, y);
				TileManager pt = level.getPost(x, y);
				TileManager ft = level.getFront(x, y);
				if(ft != TileArchive.voidTile || pt != TileArchive.voidTile || lt != TileArchive.voidTile ) {
					lT.add(lt);
					lT.add(pt);
					lT.add(ft);
				}
			}
		}
		List<TileManager> resultList = new ArrayList<>();
		for(int i = 0; i < lT.size(); i++) {
			if(lT.get(i).id == id) resultList.add(lT.get(i));
		}
		return resultList;
	}
	
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