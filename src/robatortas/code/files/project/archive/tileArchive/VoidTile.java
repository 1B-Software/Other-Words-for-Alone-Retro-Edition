package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class VoidTile extends TileManager {

	public VoidTile(SpriteManager sprite, int id) {
		super(sprite, id);
		super.seamsToWall = false;
	}

	public void render(int x, int y, RenderManager screen) {
		screen.renderTile(x << 4, y << 4, 1, this);
	}
}