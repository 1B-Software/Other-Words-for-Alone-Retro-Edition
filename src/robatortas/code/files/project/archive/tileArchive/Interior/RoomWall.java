package robatortas.code.files.project.archive.tileArchive.Interior;

import java.util.HashMap;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.CollisionShape;
import robatortas.code.files.core.level.tiles.ConnectTile;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class RoomWall extends WallType {

	public RoomWall(SpriteManager sprite, int id) {
		super(sprite, id);
		super.color = SpriteArchive.col_room_walls;
	}

	ConnectTile connect;
	
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		connect = new ConnectTile(screen, level, x, y);
		connect.seamType = ConnectTile.SeamType.WALL;
		connect.layer = ConnectTile.Layer.FRONT;
		connect.full(upSprite, downSprite, leftSprite, rightSprite);
		connect.sides(ulSprite, urSprite, dlSprite, drSprite);
		connect.base = this.sprite;
		connect.init();
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

	SpriteSheetManager ground = SheetArchive.interior_walls;

	private SpriteManager upSprite = new SpriteManager(16, 1, 0, ground);
	private SpriteManager downSprite = new SpriteManager(16, 1, 2, ground);
	private SpriteManager leftSprite = new SpriteManager(16, 0, 1, ground);
	private SpriteManager rightSprite = new SpriteManager(16, 2, 1, ground);

	private SpriteManager urSprite = new SpriteManager(16, 2, 0, ground);
	private SpriteManager ulSprite = new SpriteManager(16, 0, 0, ground);
	private SpriteManager drSprite = new SpriteManager(16, 2, 2, ground);
	private SpriteManager dlSprite = new SpriteManager(16, 0, 2, ground);
}
