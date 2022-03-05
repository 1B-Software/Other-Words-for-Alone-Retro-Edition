package robatortas.code.files.project.tileArchive.nature;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;

public class GrassTile extends TileManager {

	public GrassTile(int id) {
		super(id);
	}
	
	public void render(int x, int y, RenderManager screen) {
		screen.renderTile(x<<4, y<<4, this);
	}
}
