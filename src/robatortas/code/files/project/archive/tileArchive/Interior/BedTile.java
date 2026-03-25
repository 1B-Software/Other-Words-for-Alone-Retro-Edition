package robatortas.code.files.project.archive.tileArchive.Interior;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.CollisionShape;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class BedTile extends TileManager {
	
	public BedTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}

	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderSprite(x << 4, y << 4, SpriteArchive.bed, 1, 0);
		screen.renderSprite(x << 4, ((y+1) << 4), SpriteArchive.bed2, 1, 0);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}
	
	public boolean solidAt(LevelManager level, int xt, int yt, float px, float py, EntityManager e) {
		if(!solid(level, xt, yt, e)) return false;

		if(!solid(level, xt, yt, e)) return false;

	    CollisionShape s = new CollisionShape(0, 0, 16, 32);

	    float left = (xt << 4) + s.x;
	    float top  = (yt << 4) + s.y;
		return px >= left && px < left + s.w && py >= top && py < top + s.h;
	}
	
	public boolean isInteractable() {
		return true;
	}
}
