package robatortas.code.files.core.level.tiles;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class ConnectTile {

	public enum SeamType { GRASS, WALL, ROCK }
	public enum Layer { LEVEL, FRONT }

	private SpriteManager upSprite, downSprite, leftSprite, rightSprite;
	private SpriteManager ulSprite, urSprite, dlSprite, drSprite;

	public SpriteManager below;
	public SpriteManager base;
	public SeamType seamType = SeamType.GRASS;
	public Layer layer = Layer.LEVEL;

	public boolean up, down, left, right;
	public boolean ur, ul, dr, dl;

	private RenderManager screen;
	private LevelManager level;

	private int x, y;

	public ConnectTile(RenderManager screen, LevelManager level, int x, int y) {
		this.screen = screen;
		this.level = level;
		this.x = x;
		this.y = y;
	}

	private TileManager getTile(int tx, int ty) {
		switch (layer) {
			case FRONT: return level.getFront(tx, ty);
			default:    return level.getLevel(tx, ty);
		}
	}
 
	private boolean checkSeam(TileManager tile) {
		switch (seamType) {
			case GRASS: return tile.seamsToGrass;
			case WALL:  return tile.seamsToWall;
			case ROCK:  return tile.seamsToRock;
		}
		return false;
	}

	public int mask;

	public void init() {
		int xt = x << 4;
		int yt = y << 4;

		up = checkSeam(getTile(x, y - 1));
		down = checkSeam(getTile(x, y + 1));
		left = checkSeam(getTile(x - 1, y));
		right = checkSeam(getTile(x + 1, y));
 
		ur = checkSeam(getTile(x + 1, y - 1));
		dr = checkSeam(getTile(x + 1, y + 1));
		ul = checkSeam(getTile(x - 1, y - 1));
		dl = checkSeam(getTile(x - 1, y + 1));

		mask = 0;
		if (up) mask |= 1;
		if (right) mask |= 2;
		if (down) mask |= 4;
		if (left) mask |= 8;

		if (!connects()) return;

		// Render below tile (e.g. water) under connections
		if (below != null) screen.renderSprite(xt, yt, below, 1, 0);

		int half = 8;

		// Top-left quadrant — depends on up + left
		SpriteManager tl = base;
		if (up && left) tl = ulSprite;
		else if (up) tl = upSprite;
		else if (left) tl = leftSprite;
		screen.renderSpriteRegion(xt, yt, tl, 0, 0, half, half, 1);

		// Top-right quadrant — depends on up + right
		SpriteManager tr = base;
		if (up && right) tr = urSprite;
		else if (up) tr = upSprite;
		else if (right) tr = rightSprite;
		screen.renderSpriteRegion(xt + half, yt, tr, half, 0, half, half, 1);

		if (up){
			if(left) {
				screen.renderSpriteRegion(xt, yt+half, tl, half, half, half, half, 1);
				screen.renderSpriteRegion(xt+half, yt+half, tl, half, half, half, half, 1);
			}
			if(right) {
				screen.renderSpriteRegion(xt, yt+half, tr, 0, half, half, half, 1);
				screen.renderSpriteRegion(xt+half, yt+half, tr, half, half, half, half, 1);
			}
		}
		// Bottom-left quadrant — depends on down + left
		SpriteManager bl = base;
		if (down && left) bl = dlSprite;
		else if (down) bl = downSprite;
		else if (left) bl = leftSprite;
		screen.renderSpriteRegion(xt, yt + half, bl, 0, half, half, half, 1);

		// Bottom-right quadrant — depends on down + right
		SpriteManager br = base;
		if (down && right) br = drSprite;
		else if (down) br = downSprite;
		else if (right) br = rightSprite;
		screen.renderSpriteRegion(xt + half, yt + half, br, half, half, half, half, 1);
		
		if(down && (!left && !right)) {
			screen.renderSpriteRegion(xt, yt, downSprite, 0, 0, half, half, 1);
			screen.renderSpriteRegion(xt+half, yt, downSprite, 0, 0, half, half, 1);
		}
		
		if(up && (!left && !right)) {
			screen.renderSpriteRegion(xt+half, yt+half, upSprite, half*2, half, half, half, 1);
			screen.renderSpriteRegion(xt, yt+half, upSprite, half*2, half, half, half, 1);
		} 
		
		if(down) {
			if(left) {
				screen.renderSpriteRegion(xt, yt, bl, 0, 0, half, half, 1);
				screen.renderSpriteRegion(xt+half, yt, bl, half, 0, half, half, 1);
			}
			if(right) {
				screen.renderSpriteRegion(xt, yt, br, 0, 0, half, half, 1);
				screen.renderSpriteRegion(xt+half, yt, br, half, 0, half, half, 1);
			}
		}
	}
	
	public void full(SpriteManager up, SpriteManager down, SpriteManager left, SpriteManager right) {
		this.upSprite = up;
		this.downSprite = down;
		this.leftSprite = left;
		this.rightSprite = right;
	}
	
	public void sides(SpriteManager ul, SpriteManager ur, SpriteManager dl, SpriteManager dr) {
		this.ulSprite = ul;
		this.urSprite = ur;
		this.dlSprite = dl;
		this.drSprite = dr;
	}
	
	public boolean below() {
		if(below != null) return true;
		else return false;
	}
	
	public boolean connects() {
		return mask != 0 || ur || ul || dr || dl;
	}
}
