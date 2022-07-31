package robatortas.code.files.project.inventory;

import java.util.ArrayList;

import robatortas.code.files.core.render.SpriteManager;

public class ResourceItem extends Item {

	public Resource resource;
	
	// This class makes it able to compare and use Resources with items!
	public ResourceItem(String resourceName) {
		Resource resource = null;
		for(int i = 0; i < Resource.getInstances().size(); i++) {
//			System.out.println(resource.name);
			resource = Resource.getInstances().get(i);
			if(resource.name.equals(resourceName)) this.resource = resource;
		}
	}
	
	public static ArrayList<Item> getInstances() {
		for(int i = 0; i < Resource.getInstances().size(); i++) {
			Resource r = Resource.getInstances().get(i);
			items.add(resourceToItem(r.name));
		}
		return items;
	}
	
	public static ResourceItem resourceToItem(String resourceName) {
		return new ResourceItem(resourceName);
	}
	
	public SpriteManager getSprite() {
		return resource.sprite;
	}
	
	public String getName() {
		return resource.name;
	}
}