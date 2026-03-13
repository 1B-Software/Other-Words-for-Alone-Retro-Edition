package robatortas.code.files.project.archive.tileArchive.Interior;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.lighting.LightSource;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class NightStand extends TileManager {

	public NightStand(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderTile(x << 4, y << 4, 1, this);
		LightSource light = new LightSource(screen);
		light.add((x << 4) + 8, (y << 4) +15, 40, 1, 0xFFFFFFFF, 2f);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}

}
