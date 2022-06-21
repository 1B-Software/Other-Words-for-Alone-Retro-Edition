package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;

public class TreeTile extends TileManager {
	
	public TreeTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	int randPos = (random.nextInt(10) - x);
	
	public SpriteManager treeDL = new SpriteManager(16, 0, 1, SheetArchive.foliage);
	public SpriteManager treeDR = new SpriteManager(16, 1, 1, SheetArchive.foliage);
	public SpriteManager treeUL = new SpriteManager(16, 0, 0, SheetArchive.foliage);
	public SpriteManager treeUR = new SpriteManager(16, 1, 0, SheetArchive.foliage);
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
//		screen.renderSprite(x << 4, y << 4, treeDL, 0);
//		screen.renderSprite((x + 1) << 4 , (y << 4) - 10, treeDR, 0);
		
		screen.renderTile(((x - 1) << 4) + 7, ((y - 1) << 4), this);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}
}