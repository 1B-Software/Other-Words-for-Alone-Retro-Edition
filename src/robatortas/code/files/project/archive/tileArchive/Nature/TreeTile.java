package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class TreeTile extends TileManager {
	
	private int health = 10;
	
	public TreeTile(SpriteManager sprite, int id) {
		super(sprite, id);
//		for(TreeTile tt: ) {
//			
//		}
		
	}
	
	int randPos = (random.nextInt(10) - x);
	
	public SpriteManager treeDL = new SpriteManager(16, 0, 1, SheetArchive.foliage);
	public SpriteManager treeDR = new SpriteManager(16, 1, 1, SheetArchive.foliage);
	public SpriteManager treeUL = new SpriteManager(16, 0, 0, SheetArchive.foliage);
	public SpriteManager treeUR = new SpriteManager(16, 1, 0, SheetArchive.foliage);
	
	public void hurt(LevelManager level, Mob mob, int x, int y, int damage, int dir) {
		hurt(level, x, y);
		System.out.println("HURT!");
	}
	
	public void hurt(LevelManager level, int x, int y) {
		health-=1;
		System.out.println(x);
		if(health <= 0) {
			level.insertTile(x, y, SpriteArchive.col_cobblestone);
			health = 10;
		}
	}
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderTile(((x - 1) << 4) + 7, ((y - 1) << 4), this);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}
}