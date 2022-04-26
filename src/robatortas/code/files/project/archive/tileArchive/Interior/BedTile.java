package robatortas.code.files.project.archive.tileArchive.Interior;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class BedTile extends TileManager {
	
	public BedTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}

	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderSprite((this.x) << 4, (this.y) << 4, SpriteArchive.bed, 0);
		screen.renderSprite((this.x) << 4, (this.y + 1) << 4, SpriteArchive.bed2, 0);
	}
	
	public boolean inter() {
		return true;
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}
}
