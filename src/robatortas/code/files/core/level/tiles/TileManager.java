package robatortas.code.files.core.level.tiles;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class TileManager {
	
	public SpriteManager sprite;
	public int id;
	
	public TileManager(SpriteManager sprite ,int id) {
		this.sprite = sprite;
		this.id = id;
	}
	
	public void render(int x, int y, RenderManager screen) {
		
	}
}
