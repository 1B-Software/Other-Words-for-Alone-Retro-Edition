package robatortas.code.files.core.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.input.MouseManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.utils.CrashHandler;
import robatortas.code.files.core.utils.CrashHandler.ErrorType;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.GameManager.PLAYSTATE;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.archive.tileArchive.Interior.DoorTile;
import robatortas.code.files.project.archive.tileArchive.Nature.WaterTile;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.level.Level;
import robatortas.code.files.project.level.LevelAddons;
import robatortas.code.files.project.level.LevelRenderManager;
import robatortas.code.files.project.settings.Globals;

public class LevelManager {

	public static int width;

	public static int height;
	
	public RenderManager screen;
	
	public int[] tiles;
	public int[] postTiles;
	public int[] doorTiles;
	public int[] tileData; // per-cell state (example: damage taken)
	
	public Level currentLevel;
	
	// Lists
	public List<EntityManager> entities = new LinkedList<EntityManager>();
	public List<EntityManager>[] entitiesInTiles;
	
	public List<TileManager> allTiles = new LinkedList<TileManager>();
	
	public static LevelManager level = new GameLevel();
	
	// Classes
	public LevelAddons addons;
	
	// Mobs
	public static Player player;
	
	public LevelManager() {
		load(Level.currentLevelName);
		addons = new LevelAddons(this);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				allTiles.add(getLevel(x,y));
				allTiles.add(getPost(x,y));
				allTiles.add(getFront(x,y));
			}
		}
	}
	
	// Folder name of the level (e.g. player_room)
	public void load(String folderName) {
		String initDir = "res/textures/level/";
		String finalDir = String.join("", initDir, folderName, "/");
		Console.log("Loading Level "  + folderName + ": "+ finalDir);
		File file = new File(finalDir);
		File[] allFilesInDir = file.listFiles();
		for(File f : allFilesInDir) {
			if(f.isFile()) {
				String name = f.getName();

		        if(name.equals("level.png")) Globals.levelPath = finalDir.substring(3) + name;
		        if(name.equals("level_doors.png")) Globals.levelPathDoors = finalDir.substring(3) + name;
		        if(name.equals("level_post.png")) Globals.levelPathPost = finalDir.substring(3) + name;
		        if(name.equals("level_doors.txt")) Globals.levelDoorsTxt = finalDir.substring(3) + name;
			}
		}
		
		loadLevel();

		if (!Globals.levelDoorsTxt.isEmpty()) {
			doorParser("res" + Globals.levelDoorsTxt);
		}
	}
	
	public void unload() {}
	
	public void doorParser(String path) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
	
			while((line = reader.readLine()) != null) {
	
			    String[] parts = line.split("->");
	
			    String[] doorPos = parts[0].trim().split(",");
			    String[] target = parts[1].trim().split(",");
	
			    int x = Integer.parseInt(doorPos[0]);
			    int y = Integer.parseInt(doorPos[1]);
	
			    String level = target[0];
			    int spawnX = Integer.parseInt(target[1]);
			    int spawnY = Integer.parseInt(target[2]);
	
			    DoorTile door = findDoorAt(x,y);
			    if (door == null) {
			        Console.logError("Warning: no door tile found at " + x + "," + y);
			        continue;
			    }
			    Level.currentLevelName = level;
			    door.setTargetLevelPath(level);
			    door.setSpawn(spawnX, spawnY);
			}
		} catch (FileNotFoundException e) {
			CrashHandler crash = new CrashHandler();
			crash.handle(e, e.getMessage(), ErrorType.SERIOUS);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

    
	public static DoorTile pendingDoor = null;

	public static void enterDoor(DoorTile door) {
		if (!GameManager.fadingOut && !GameManager.fadingIn) {
			pendingDoor = door;
			GameManager.fadingOut = true;
			GameManager.fadeAlpha = 0;
		}
	}
	
	public DoorTile findDoorAt(int x, int y) {
		if (doorTiles == null) return null;
		TileManager tile = getDoors(x, y);

	    if(tile instanceof DoorTile) {
	        return (DoorTile) tile;
	    }
		return null;
	}
	
	public class TileState {
	    public int health;
	}
	public TileState[] tileStates;
	
	
	public void loadLevel() {
	}
		
	// Input Declarations
	public InputManager input = new InputManager();
	public MouseManager mouse = new MouseManager();
	
	public void update() {
		WaterTile.tick();
		input.update();
		
		mouse.update();
		

		currentLevel.levelSelector.getCurrentLevel().update();
		
		for (int i = 0; i < entities.size(); i++) {
			EntityManager e = entities.get(i);
			int xto = (int)e.x >> 4;
			int yto = (int)e.y >> 4;
			
			e.update();
			
			//this removes entities
			if (e.removed) {
				entities.remove(i--);
				this.removeEntity(xto, yto, e);
			} else {
				int xt = (int)e.x >> 4;
				int yt = (int)e.y >> 4;
				
				//if the x != x or y != or x = x or y = y
				if (xto != xt || yto != yt || xto == xt || yto == yt) {
					this.removeEntity(xto, yto, e);
					this.insertEntity(xt, yt, e);
				}
			}
		}
	}
	
	// MY MIND IS NOT THINKING CLEARLY! Because it's FUCKING 2 AM
	public void render(float xScroll, float yScroll, RenderManager screen) {
		this.screen = screen;
		
		LevelRenderManager levelRender = new LevelRenderManager(this, screen);
		
		levelRender.pinPoints(xScroll, yScroll);
				
		levelRender.render(this);
		
//		System.out.println(currentLevel.currentLevelName);
		currentLevel.levelSelector.getCurrentLevel().render(player.x, player.y, screen);
	}
	
	public void add(EntityManager e) {
		e.init(this);
		e.removed = false;
		entities.add(e);
		
		int xp = (int)e.x >> 4;
		int yp = (int)e.y >> 4;
		
		insertEntity(xp, yp, e);
	}
	
	public void remove(EntityManager e) {
		e.removed = true;
		e.remove();
		entities.remove(e);
		
		int xp = (int)e.x >> 4;
		int yp = (int)e.y >> 4;
		
		removeEntity(xp, yp, e);
	}
	
	public TileManager getTile(TileManager query) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(getLevel(x, y) == query || getFront(x, y) == query) {
					return query;
				}
			}
		}
		return TileArchive.voidTile;
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
	
	public List<EntityManager> getEntities(float f, float g, float h, float i) {
		return addons.getEntities(f, g, h, i);
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
		if(tiles[x + y * width] == SpriteArchive.col_woodWall) return TileArchive.woodWall;
		if(tiles[x + y * width] == SpriteArchive.col_chair) return TileArchive.woodFloor;
		if(tiles[x + y * width] == SpriteArchive.col_table) return TileArchive.woodFloor;
		if(tiles[x + y * width] == SpriteArchive.col_sand) return TileArchive.sand;
		
		if(tiles[x + y * width] == SpriteArchive.col_wood_floor) return TileArchive.woodFloor;
		if(tiles[x + y * width] == SpriteArchive.col_wood_wall_front) return TileArchive.woodWallFront;
		
		
		// Nature
		if(tiles[x + y * width] == SpriteArchive.col_bush) return TileArchive.grass;
		
		// Trees
		if(tiles[x + y * width] == SpriteArchive.col_oakTree) return TileArchive.grass;
		if(tiles[x + y * width] == SpriteArchive.col_birchTree) return TileArchive.grass;
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
		if(tiles[x + y * width] == SpriteArchive.col_woodFloor) return TileArchive.woodFloor;
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
		if(x < 0 || y < 0 || x >= width || y >= height) return TileArchive.voidTile;
		if(postTiles[x + y * width] == SpriteArchive.col_placeHolder) return TileArchive.placeHolderTile;
		if(postTiles[x + y * width] == SpriteArchive.col_oakTree) return TileArchive.tree;
		if(postTiles[x + y * width] == SpriteArchive.col_bed) return TileArchive.bed;
		if(postTiles[x + y * width] == SpriteArchive.col_tv) return TileArchive.TvTile;
		if(postTiles[x + y * width] == SpriteArchive.col_room_walls) return TileArchive.roomWall;
		if(postTiles[x + y * width] == SpriteArchive.col_nightStand) return TileArchive.nightStand;
		if(postTiles[x + y * width] == SpriteArchive.col_flowerRed) return TileArchive.flowerRed;
		if(postTiles[x + y * width] == SpriteArchive.col_yellowDahlia) return TileArchive.yellowDahlia;
		if(postTiles[x + y * width] == SpriteArchive.col_bush) return TileArchive.bushTile;
		return TileArchive.voidTile;
	}
	
	public TileManager getDoors(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return TileArchive.voidTile;
		if(doorTiles[x + y * width] == SpriteArchive.col_door) return TileArchive.doorTile;
		return TileArchive.voidTile;
	}
}