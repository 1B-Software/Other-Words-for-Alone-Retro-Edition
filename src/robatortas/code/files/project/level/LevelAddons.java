package robatortas.code.files.project.level;

import java.util.ArrayList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class LevelAddons {

	private LevelManager level;
	
	@SuppressWarnings("unchecked")
	public LevelAddons(LevelManager level) {
		this.level = level;
	}

	public List<EntityManager> getEntities(float x0, float y0, float x1, float y1) {
		List<EntityManager> result = new ArrayList<EntityManager>();
		int xt0 = ((int)x0 >> 4) - 1;
		int yt0 = ((int)y0 >> 4) - 1;
		int xt1 = ((int)x1 >> 4) + 1;
		int yt1 = ((int)y1 >> 4) + 1;

		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= LevelManager.width || y >= LevelManager.height) continue;
				List<EntityManager> entities = level.entitiesInTiles[x + y * LevelManager.width];
				for(int i = 0; i < entities.size(); i++) {
					EntityManager e = entities.get(i);
					if(e.intersects(xt0, yt0, xt1, yt1)) result.add(e);
				}
			}
		}
		return result;
	}
	
	// Gets the neighbouring tiles in relation to the player coordinates.
	public List<TileManager> getNeighborTiles(float px, float py) {
	    List<TileManager> result = new ArrayList<>();
	    int tx = (int)px >> 4;
	    int ty = (int)py >> 4;
		
		switch(LevelManager.player.dir) {
			case 0: result.add(level.getFront(tx, ty - 1));
			break;
			case 1: result.add(level.getFront(tx + 1, ty));
			break;
			case 2: result.add(level.getFront(tx, ty + 1));
			break;
			case 3: result.add(level.getFront(tx - 1, ty));
			break;
		}
	    return result;
	}
}