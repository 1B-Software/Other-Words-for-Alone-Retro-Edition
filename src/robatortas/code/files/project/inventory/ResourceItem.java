package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.SpriteManager;

public class ResourceItem extends Item {

	public Resource resource;
	
	// This class makes it able to compare and use Resources with items!
	public ResourceItem(Resource resource) {
		this.resource = resource;
	}
	
	public SpriteManager getSprite() {
		return resource.sprite;
	}
	
	public String getName() {
		return resource.name;
	}
}