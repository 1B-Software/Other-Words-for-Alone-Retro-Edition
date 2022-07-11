package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.entities.Mob;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.entities.ItemEntity;
import robatortas.code.files.project.entities.Particle;
import robatortas.code.files.project.inventory.Resource;
import robatortas.code.files.project.inventory.ResourceItem;

public class TreeTile extends TileManager {
	
	private int health = 10;
	
	private ItemEntity wood;
	
	public TreeTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}
	
	int randPos = (random.nextInt(10) - x);
	
	public SpriteManager treeDL = new SpriteManager(16, 0, 1, SheetArchive.foliage);
	public SpriteManager treeDR = new SpriteManager(16, 1, 1, SheetArchive.foliage);
	public SpriteManager treeUL = new SpriteManager(16, 0, 0, SheetArchive.foliage);
	public SpriteManager treeUR = new SpriteManager(16, 1, 0, SheetArchive.foliage);
	
	public void hurt(LevelManager level, Mob mob, int x, int y, int damage, int dir) {
		hurt(level, x, y);
	}
	
	private Particle particle;
	
	public void hurt(LevelManager level, int x, int y) {
		health-=1;
		
		for(int i = 0; i < 5; i++) {
			level.add(particle = new Particle((x << 4) + 8, (y << 4) + 10));
			particle.setColor(0xff633A00);
		}
		for(int i = 0; i < 3; i++) {
			level.add(particle = new Particle((x << 4) + 8, (y << 4) + 10));
			particle.setColor(0xff5E5140);
		}
		
		if(health <= 0) {
			level.insertTile(x, y, SpriteArchive.col_grass);
			for(int i = 0; i < random.nextInt(1)+2; i++) level.add(wood = new ItemEntity((x << 4) + 8, (y << 4) + 10, new ResourceItem(Resource.wood)));
			
			health = 10;
		}
	}
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderTile(((x - 1) << 4) + 7, ((y - 1) << 4), this);
	}
	
	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		if(e instanceof Particle) return false;
		else return true;
	}
}