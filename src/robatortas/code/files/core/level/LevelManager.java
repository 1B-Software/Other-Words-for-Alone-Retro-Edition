package robatortas.code.files.core.level;

import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.level.LevelRenderManager;
import robatortas.code.files.project.settings.Constants;
import robatortas.code.files.project.tileArchive.TileArchive;

public class LevelManager {
	
	protected int width, height;
	
	public int[] tiles;
	
	public List<EntityManager> entities = new LinkedList<EntityManager>();
	
	public static LevelManager level = new GameLevel(Constants.levelPath);
	
	public LevelManager(String path) {
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
	}
	
	public void tick() {
		
	}
	
	// MY MIND IS NOT THINKING CLEARLY! Because it's FUCKING 2 AM
	public void render(int xScroll, int yScroll, RenderManager screen) {
		
		LevelRenderManager levelRender = new LevelRenderManager(this ,screen);
		
		levelRender.pinPoints(xScroll, yScroll);
		
		for(int y = levelRender.y0; y < levelRender.y1; y++) {
			for(int x = levelRender.x0; x < levelRender.x1; x++) {
				if(x < 0 || y < 0 || x >= screen.width|| y >= screen.height) continue;
				
				levelRender.render(x, y, this);

				levelRender.renderEntities(x, y);
			}
		}
	}
	
	public void add(EntityManager e) {
		
		e.removed = false;
		entities.add(e);
		e.init(this);
	}
	
	public TileManager getLevel(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x+y*width] == 0x00BC0F) return TileArchive.grass;
		return TileArchive.voidTile;
	}
}
