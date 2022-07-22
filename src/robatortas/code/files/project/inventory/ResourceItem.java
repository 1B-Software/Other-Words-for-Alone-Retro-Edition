package robatortas.code.files.project.inventory;

import java.util.ArrayList;

import robatortas.code.files.core.render.SpriteManager;

public class ResourceItem extends Item {

	public Resource resource;
	
	// This class makes it able to compare and use Resources with items!
	public ResourceItem(String resourceName) {
		Resource resource = null;
		for(int i = 0; i < Resource.getInstances().size(); i++) {
			resource = Resource.getInstances().get(i);
			if(resource.name == resourceName) this.resource = resource;
		}
	}
	
	public ArrayList<Item> getInstances() {
		super.getInstances();
		for(int i = 0; i < Resource.getInstances().size(); i++) {
			Resource r = Resource.getInstances().get(i);
			items.add(resourceToItem(r.name));
			System.out.println(r);
		}
		return super.items;
	}
	
	public ResourceItem resourceToItem(String resourceName) {
		return new ResourceItem(resourceName);
	}
	
	public SpriteManager getSprite() {
		return resource.sprite;
	}
	
	public String getName() {
		return resource.name;
	}
}