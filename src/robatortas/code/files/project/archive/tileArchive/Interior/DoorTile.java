package robatortas.code.files.project.archive.tileArchive.Interior;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.GameLevel;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.CollisionShape;
import robatortas.code.files.core.level.tiles.ConnectTile;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class DoorTile extends WallType {
	
	GameLevel targetLevel;
	String targetLevelPath;
    int spawnX;
    int spawnY;
	
	public DoorTile(SpriteManager sprite, int id) {
		super(sprite, id);
		super.color = SpriteArchive.col_door;
	}
	
	ConnectTile connect;
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		this.x = x;
		this.y = y;
		this.level = level;

//		screen.renderSprite(x << 4, y << 4, downSprite, 1, 0);
		if(getOrientation().equals("horizontal")) {
			screen.renderSprite(x << 4, y << 4, downSprite, 1, 0);
		} else {
			screen.renderSprite(x << 4, y << 4, leftSprite, 1, 0);
		}
//		System.out.println(doorType());
	}

	public boolean solid(LevelManager level, int x, int y, EntityManager e) {
		return true;
	}

	public boolean solidAt(LevelManager level, int xt, int yt, float px, float py, EntityManager e) {
		if(!solid(level, xt, yt, e)) return false;

		if(!solid(level, xt, yt, e)) return false;

	    CollisionShape s = super.getShape(level, xt, yt);

	    float left = (xt << 4) + s.x;
	    float top  = (yt << 4) + s.y+3;
		return px >= left && px < left + s.w && py >= top && py < top + s.h;
	}
	
	public void setTargetLevelPath(String level) {
		this.targetLevelPath = level;
	}

	public void setSpawn(int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}

	public String getTargetLevelPath() { return targetLevelPath; }
	public GameLevel getTargetLevel() {return targetLevel;}
	public int getSpawnX() { return spawnX; }
	public int getSpawnY() { return spawnY; }

	public String getOrientation() {
//		TileManager u = level.getFront(x, y - 1);
//		TileManager d = level.getFront(x, y + 1);
		TileManager l = level.getFront(x - 1, y);
		TileManager r = level.getFront(x + 1, y);
		
		if(l == doorType() && r == doorType()) {
			return "horizontal";
		} else return "vertical";
	}

	// Gives the current Tile where the door is, so that it can know what type of door to be.
	public TileManager doorType() {
		return getTile(x, y, level.postTiles);
	}
	
	SpriteSheetManager ground = SheetArchive.interior_walls;

	private SpriteManager upSprite = new SpriteManager(16, 4, 0, ground);
	private SpriteManager downSprite = new SpriteManager(16, 4, 2, ground);
	private SpriteManager leftSprite = new SpriteManager(16, 0, 1, ground);
	private SpriteManager rightSprite = new SpriteManager(16, 2, 1, ground);
}
