package robatortas.code.files.core.level;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.utils.IOUtils;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.settings.Constants;
import robatortas.code.files.project.tileArchive.TileArchive;

public class LevelManager {
	
	protected int width, height;
	
	public int[] tiles;
	
	public static LevelManager level = new GameLevel(Constants.levelPath);
	
	public LevelManager(String path) {
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
	}
	
	public void tick() {
		
	}
	
	public void render(int xScroll, int yScroll, RenderManager screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= screen.width|| y >= screen.height) continue;
				
				getLevel(x, y).render(x, y, screen);
			}
		}
	}
	
	public TileManager getLevel(int x, int y) {
		if(x < 0 || x > width || y < 0 || y > height) return  TileArchive.grass;
		if(tiles[x+y*width] == 0x00BC0F) return TileArchive.grass;
		return TileArchive.grass;
	}
}
