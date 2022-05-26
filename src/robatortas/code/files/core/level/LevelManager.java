package robatortas.code.files.core.level;

import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.archive.tileArchive.Nature.WaterTile;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.LevelAddons;
import robatortas.code.files.project.level.LevelRenderManager;
import robatortas.code.files.project.settings.Constants;

public class LevelManager {

	public int width, height;
	
	public int[] tiles;
	
	// Lists
	public List<EntityManager> entities = new LinkedList<EntityManager>();
	public List<EntityManager>[] entitiesInTiles;
	
	public static LevelManager level = new GameLevel(Constants.levelPath);
	
	// Classes
	public LevelAddons addons;
	
	// Mobs
	public static Player player;
	
	public LevelManager(String path) {
		loadLevel(path);
		addons = new LevelAddons(this);
	}
	
	public void loadLevel(String path) {
	}
		
	// Input Declarations
	public InputManager input = new InputManager();
	
	public void update() {
		addons.update();
		WaterTile.tick();
		input.update();
	}
	
	// MY MIND IS NOT THINKING CLEARLY! Because it's FUCKING 2 AM
	public void render(int xScroll, int yScroll, RenderManager screen) {
		
		LevelRenderManager levelRender = new LevelRenderManager(this ,screen);
		
		levelRender.pinPoints(xScroll, yScroll);
				
		levelRender.render(this);
		levelRender.renderEntities(xScroll, yScroll);
	}
	
	public void add(EntityManager e) {
		e.init(this);
		e.removed = false;
		entities.add(e);
		
		int xp = e.x >> 4;
		int yp = e.y >> 4;
		
		insertEntity(xp, yp, e);
	}
	
	public void remove(EntityManager e) {
		e.removed = true;
		e.remove();
		entities.remove(e);
		
		int xp = e.x >> 4;
		int yp = e.y >> 4;
		
		removeEntity(xp, yp, e);
	}
	
	//Inserts Entities in entitiesInTile list (To know where entities are in tiles)
	public void insertEntity(int x, int y, EntityManager e) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		entitiesInTiles[x+y*width].add(e);
	}
	
	//remove entities from the entitiesInTiles list (When mobs are killed, it calls this method to remove the entity from the list.)
	public void removeEntity(int x, int y, EntityManager e) {
		addons.removeEntity(x, y, e);
	}
	
	public List<EntityManager> getEntities(int x0, int y0, int x1, int y1) {
		return addons.getEntities(x0, y0, x1, y1);
	}
	
	public void insertTile(int x, int y, int color) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		tiles[x+y*width] = color;
	}
	
	public TileManager getLevel(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x + y * width] == SpriteArchive.col_grass) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_water) return TileArchive.water;
		if(tiles[x + y * width] == SpriteArchive.col_yellowDahlia) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_flowerRed) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_solidRock) return TileArchive.solidRock;
		if(tiles[x + y * width] == SpriteArchive.col_woodFloor) return TileArchive.woodFloor;
		if(tiles[x + y * width] == SpriteArchive.col_dirt) return TileArchive.dirtTile;
		if(tiles[x + y * width] == SpriteArchive.col_cobblestone) return TileArchive.cobblestone;
		if(tiles[x + y * width] == SpriteArchive.col_cobblePath) return TileArchive.cobblePath;
		if(tiles[x + y * width] == SpriteArchive.col_stoneBricks) return TileArchive.stoneBricks;
		if(tiles[x + y * width] == SpriteArchive.col_bricks) return TileArchive.bricksTile;
		if(tiles[x + y * width] == SpriteArchive.col_void) return TileArchive.voidTile;
		if(tiles[x + y * width] == SpriteArchive.col_woodWall) return TileArchive.woodWall;
		if(tiles[x + y * width] == SpriteArchive.col_chair) return TileArchive.woodFloor;
		if(tiles[x + y * width] == SpriteArchive.col_table) return TileArchive.woodFloor;
		
		// Nature
		if(tiles[x + y * width] == SpriteArchive.col_bush) return TileArchive.grass;
		
		// Trees
		if(tiles[x + y * width] == SpriteArchive.col_oakTree) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_birchTree) return TileArchive.grass;
		
		if(tiles[x + y * width] == SpriteArchive.col_bed) return TileArchive.woodFloor;
		return TileArchive.voidTile;
	}
	
	public TileManager getFront(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x + y * width] == SpriteArchive.col_flowerRed) return TileArchive.flowerRed;
		if(tiles[x + y * width] == SpriteArchive.col_yellowDahlia) return TileArchive.yellowDahlia;
		if(tiles[x + y * width] == SpriteArchive.col_bush) return TileArchive.bushTile;
		return TileArchive.voidTile;
	}
}
