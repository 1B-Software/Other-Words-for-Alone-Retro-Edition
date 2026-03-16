package robatortas.code.files.project.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;

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
				if(x < 0 || y < 0 || x >= level.width || y >= level.height) continue;
				List<EntityManager> entities = level.entitiesInTiles[x + y * level.width];
				for(int i = 0; i < entities.size(); i++) {
					EntityManager e = entities.get(i);
					if(e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
}