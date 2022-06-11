package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class TreeTile extends TileManager {

	public TreeTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	public SpriteManager treeDL = new SpriteManager(16, 0, 1, SheetArchive.foliage);
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}