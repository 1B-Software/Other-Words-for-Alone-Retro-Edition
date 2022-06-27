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
	
	public void update() {
		entityUpdates();
	}
	
	public void entityUpdates() {
		//entity ticks
		for (int i = 0; i < entities.size(); i++) {
			EntityManager e = entities.get(i);
			int xto = e.x >> 4;
			int yto = e.y >> 4;
			
			e.update();
			
			//this removes entities
			if (e.removed) {
				entities.remove(i--);
				level.removeEntity(xto, yto, e);
			} else {
				int xt = e.x >> 4;
				int yt = e.y >> 4;
				
				//if the x != x or y != or x = x or y = y
				if (xto != xt || yto != yt || xto == xt || yto == yt) {
					level.removeEntity(xto, yto, e);
					level.insertEntity(xt, yt, e);
				}
			}
		}
	}
	
	public List<EntityManager> getEntities(int x0, int y0, int x1, int y1) {
		List<EntityManager> result = new ArrayList<EntityManager>();
		int xt0 = (x0 >> 4) - 1;
		int yt0 = (y0 >> 4) - 1;
		int xt1 = (x1 >> 4) + 1;
		int yt1 = (y1 >> 4) + 1;
		
		for(int y = yt0; y <= yt1; y++) {
			for(int x = xt0; x <= xt1; x++) {
				if(x < 0 || y < 0 || x >= width || y >= height) continue;
				List<EntityManager> entities = entitiesInTiles[x + y * this.width];
				for(int i = 0; i < entities.size(); i++) {
					EntityManager e = entities.get(i);
					if(e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
}