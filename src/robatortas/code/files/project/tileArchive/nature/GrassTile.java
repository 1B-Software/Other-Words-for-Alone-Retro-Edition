package robatortas.code.files.project.tileArchive.nature;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class GrassTile extends TileManager {

	public GrassTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	public void render(int x, int y, RenderManager screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
