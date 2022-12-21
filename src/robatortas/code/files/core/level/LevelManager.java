package robatortas.code.files.core.level;

import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.archive.tileArchive.Nature.WaterTile;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.LevelAddons;
import robatortas.code.files.project.level.LevelRenderManager;
import robatortas.code.files.project.settings.Globals;

public class LevelManager {

	public int width, height;
	
	public RenderManager screen;
	
	public int[] tiles;
	
	// Lists
	public List<EntityManager> entities = new LinkedList<EntityManager>();
	public List<EntityManager>[] entitiesInTiles;
	
	public static LevelManager level = new GameLevel(Globals.levelPath);
	
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
	public MouseManager mouse = new MouseManager();
	
	public void update() {
		WaterTile.tick();
		input.update();
		
		mouse.update();
		
		for (int i = 0; i < entities.size(); i++) {
			EntityManager e = entities.get(i);
			int xto = e.x >> 4;
			int yto = e.y >> 4;
			
			e.update();
			
			//this removes entities
			if (e.removed) {
				entities.remove(i--);
				this.removeEntity(xto, yto, e);
			} else {
				int xt = e.x >> 4;
				int yt = e.y >> 4;
				
				//if the x != x or y != or x = x or y = y
				if (xto != xt || yto != yt || xto == xt || yto == yt) {
					this.removeEntity(xto, yto, e);
					this.insertEntity(xt, yt, e);
				}
			}
		}
	}
	
	// MY MIND IS NOT THINKING CLEARLY! Because it's FUCKING 2 AM
	public void render(int xScroll, int yScroll, RenderManager screen) {
		this.screen = screen;
		
		LevelRenderManager levelRender = new LevelRenderManager(this ,screen);
		
		levelRender.pinPoints(xScroll, yScroll);
				
		levelRender.render(this);
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
	
	/**<NEWLINE>
	 * <b>insertEntity function on LevelManager class</b>
	 * <br><br>
	 * Inserts Entities in the entitiesInTile list (To know where entities are in tiles).
	 * <br>
	 * Inserts a tile exactly on the inputed x & y coordinates
	 * 
	 * @param x Coordinate on the x axis
	 * @param y Coordinate on the y axis
	 * @param e EntityManager class
	 */
	public void insertEntity(int x, int y, EntityManager e) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		entitiesInTiles[x+y*width].add(e);
	}
	
	//remove entities from the entitiesInTiles list (When mobs are killed, it calls this method to remove the entity from the list.)
	public void removeEntity(int x, int y, EntityManager e) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		entitiesInTiles[x+y*width].remove(e);
	}
	
	public List<EntityManager> getEntities(int x0, int y0, int x1, int y1) {
		return addons.getEntities(x0, y0, x1, y1);
	}
	
	public void insertTile(int x, int y, int color) {
		if(x < 0 || y < 0 || x >= width || y >= height) return;
		tiles[x+y*width] = color;
	}
	
	/**<NEWLINE>
	 * <b>getLevel function on LevelManager class</b>
	 * <br><br>
	 * Gets the tile located on the x & y value coordinates on the general level tiles!
	 * <br><br>
	 * This method is used A LOT for everything.
	 * 
	 * @param x Tile coordinate on the x axis
	 * 
	 * @param y Tile coordinate on the y axis
	 */
	public TileManager getLevel(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x + y * width] == SpriteArchive.col_grass) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_sand) return TileArchive.sand;
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
	
	/**<NEWLINE>
	 * <b>getLevel function on LevelManager class</b>
	 * <br><br>
	 * Gets the tile located on the x & y value coordinates in the BACKGROUND layer!
	 * <br><br>
	 * This method is used A LOT for everything.
	 * 
	 * @param x Tile coordinate on the x axis
	 * 
	 * @param y Tile coordinate on the y axis
	 */
	public TileManager getPost(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		if(tiles[x + y * width] == SpriteArchive.col_flowerRed) return TileArchive.flowerRed;
		if(tiles[x + y * width] == SpriteArchive.col_yellowDahlia) return TileArchive.yellowDahlia;
		if(tiles[x + y * width] == SpriteArchive.col_bush) return TileArchive.bushTile;
		if(tiles[x + y * width] == SpriteArchive.col_oakTree) return TileArchive.tree;
		return TileArchive.voidTile;
	}
	
	/**<NEWLINE>
	 * <b>getLevel function on LevelManager class</b>
	 * <br><br>
	 * Gets the tile located on the x & y value coordinates in the FRONT layer!
	 * <br><br>
	 * This method is used A LOT for everything.
	 * 
	 * @param x Tile coordinate on the x axis
	 * 
	 * @param y Tile coordinate on the y axis
	 */
	public TileManager getFront(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return  TileArchive.voidTile;
		
		return TileArchive.voidTile;
	}
}