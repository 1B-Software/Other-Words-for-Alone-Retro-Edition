package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.ConnectTile;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class SandTile extends TileManager {

	public SandTile(SpriteManager sprite, int id) {
		super(sprite, id);
		this.seamsToGrass = true;
		this.seamsToWater = true;
	}
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		
		
		screen.renderTile(x << 4, y << 4, this);
	}
}
