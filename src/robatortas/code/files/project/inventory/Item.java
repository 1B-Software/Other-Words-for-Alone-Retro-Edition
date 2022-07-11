package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class Item extends ItemHandler {
	
	public ResourceItem resourceItem;
	
	public Item() {
		
	}
	
	public SpriteManager getSprite() {
		return resourceItem.getSprite();
	}
	
	public String getName() {
		return resourceItem.getName();
	}
	
	public void render(RenderManager screen) {
		screen.renderSprite(100, 100, SpriteArchive.chair, 0);
	}
}
