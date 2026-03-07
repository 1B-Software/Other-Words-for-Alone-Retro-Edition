package robatortas.code.files.project.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;

public class LevelAddons {

	private LevelManager level;
	
	private int width, height;
	
	public List<EntityManager>[] entitiesInTiles;
	private List<EntityManager> entities = new LinkedList<EntityManager>();
	
	@SuppressWarnings("unchecked")
	public LevelAddons(LevelManager level) {
		this.level = level;
		this.width = level.width;
		this.height = level.height;
		this.entitiesInTiles = level.entitiesInTiles;
		this.entities = level.entities;
	}
	
	public List<EntityManager> getEntities(float f, float g, float h, float i2) {
		List<EntityManager> result = new ArrayList<EntityManager>();
		int xt0 = ((int)f >> 4) - 1;
		int yt0 = ((int)g >> 4) - 1;
		int xt1 = ((int)h >> 4) + 1;
		int yt1 = ((int)i2 >> 4) + 1;
		
		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= width || y >= height) continue;
				List<EntityManager> entities = entitiesInTiles[x + y * this.width];
				for(int i = 0; i < entities.size(); i++) {
					EntityManager e = entities.get(i);
					if(e.intersects(f, g, h, i2)) result.add(e);
				}
			}
		}
		return result;
	}
}