package robatortas.code.files.core.level;

import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.LevelAddons;
import robatortas.code.files.project.level.LevelRenderManager;
import robatortas.code.files.project.settings.Constants;
import robatortas.code.files.project.tileArchive.TileArchive;

public class LevelManager {

	public int width, height;
	
	public int[] tiles;
	
	public List<EntityManager> entities = new LinkedList<EntityManager>();
	public List<EntityManager>[] entitiesInTiles;
	
	public static LevelManager level = new GameLevel(Constants.levelPath);
	
	public LevelAddons addons = new LevelAddons(this);
	
	public Player player;
	
	public LevelManager(String path) {
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
	}
	
	// Input Declarations
	public InputManager input = new InputManager();
		
	public void update() {
		addons.update();
		input.update();
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
		
		e.init(this);
		e.removed = false;
		entities.add(e);
	}
	
	//Inserts Entities in entitiesInTile list (To know where entities are in tiles)
	public void insertEntity(int x, int y, EntityManager e) {
		addons.insertEntity(x, y, e);
	}
	
	//remove entities from the entitiesInTiles list (When mobs are killed, it calls this method to remove the entity from the list.)
	public void removeEntity(int x, int y, EntityManager e) {
		addons.removeEntity(x, y, e);
	}
	
	public List<EntityManager> getEntities(int x0, int y0, int x1, int y1) {
		return addons.getEntities(x0, y0, x1, y1);
	}
	
	public TileManager getLevel(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x+y*width] == 0x00BC0F) return TileArchive.grass;
		return TileArchive.voidTile;
	}
}
