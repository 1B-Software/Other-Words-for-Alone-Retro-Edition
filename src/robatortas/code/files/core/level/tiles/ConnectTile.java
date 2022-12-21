package robatortas.code.files.core.level.tiles;

import java.util.LinkedList;
import java.util.List;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class ConnectTile {

	private SpriteManager upSprite, downSprite, leftSprite, rightSprite;
	private SpriteManager ulSprite, urSprite, dlSprite, drSprite;
	
	public SpriteManager below;
	
	public List<TileManager> seamedTiles = new LinkedList<TileManager>();
	
	public boolean up, down, left, right;
	public boolean ur, ul, dr, dl;
	
	private boolean diagonal;
	
	private RenderManager screen;
	private LevelManager level;
	
	private int x, y;
	
	public ConnectTile(RenderManager screen, LevelManager level, int x, int y, List<TileManager> seamedTiles) {
		this.screen = screen;
		this.level = level;
		this.x = x;
		this.y = y;
		this.seamedTiles = seamedTiles;
		for(int i = 0; i < seamedTiles.size(); i++) {
			System.out.println(seamedTiles.get(i));
		}
	}
	
	public void init() {
		int xt = x << 4;
		int yt = y << 4;

		for(int i = 0; i < seamedTiles.size(); i++) {
			if(level.getLevel(x, y-1) == seamedTiles.get(i)) up = true;
//			System.out.println(seamedTiles.get(i));
//			System.out.println(i);
		}

//		System.out.println(seamedTiles.get(1));
		
		down = level.getLevel(x, y + 1).seamsToGrass;
		left = level.getLevel(x - 1, y).seamsToGrass;
		right = level.getLevel(x + 1, y).seamsToGrass;
		
		ur = level.getLevel(x + 1, y - 1).seamsToGrass;
		dr = level.getLevel(x + 1, y + 1).seamsToGrass;
		ul = level.getLevel(x - 1, y - 1).seamsToGrass;
		dl = level.getLevel(x - 1, y + 1).seamsToGrass;
		
		// ALMOST DONE!!
		// just need to make it actually efficient

		if(below() && connects()) screen.renderSprite(x << 4, y << 4, below, 16, 0);
		
		if(down && right || down && left || up && right || up && left) {
			if(up && right) screen.renderSprite(xt, yt, urSprite, 16, 0); 
			else if(up && left) screen.renderSprite(xt, yt, ulSprite, 16, 0);
		
			if(down && right) screen.renderSprite(xt, yt, drSprite, 16, 0);
			else if(down && left) screen.renderSprite(xt, yt, dlSprite, 16, 0);
		} else if(up || down || left || right){
			if(up) screen.renderSprite(xt, yt, upSprite, 16, 0);
			if(down) screen.renderSprite(xt, yt, downSprite, 16, 0);
			if(left) screen.renderSprite(xt, yt, leftSprite, 16, 0);
			if(right) screen.renderSprite(xt, yt, rightSprite, 16, 0);
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
		if(up || down || left || right || down && right || down && left || up && right || up && left) return true;
		else return false;
	}
}
