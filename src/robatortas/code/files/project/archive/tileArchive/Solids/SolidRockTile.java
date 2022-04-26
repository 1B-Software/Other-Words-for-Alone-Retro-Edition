package robatortas.code.files.project.archive.tileArchive.Solids;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class SolidRockTile extends TileManager {

	private static int rockHealth = 0;
	public static int[] rockData;
	
	public SolidRockTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	//TODO: Fix perf issue!!! (Guess what, I never fixed it... Such a fucking troll ain't it).
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		// << equals multiply because its a binary operation
//		screen.renderSprite(x << 4, y << 4, SpriteArchive.rock, 0);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}
}
