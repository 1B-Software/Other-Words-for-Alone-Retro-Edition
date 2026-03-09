package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class PlaceHolderTile extends TileManager {

	public PlaceHolderTile(SpriteManager sprite, int id) {
		super(sprite, id);
		super.seamsToWall = true;
	}

	public void render(int x, int y, RenderManager screen) {
//		screen.renderTile(x << 4, y << 4, 1, this);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return false;
	}
}