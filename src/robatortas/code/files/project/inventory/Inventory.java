package robatortas.code.files.project.inventory;

import java.util.LinkedList;
import java.util.List;

public class Inventory {
	
	public List<Item> items = new LinkedList<Item>();
	
	public Item item;
	
	public Inventory() {
		
	}
	
	public void add(ResourceItem resource) {
		items.add(resource);
	}
	
	public void remove(ResourceItem resource) {
		items.remove(resource);
	}
	
	// Finds specified item from inventory
	public Item find(ResourceItem resource) {
		for(int i = 0; i < items.size(); i++) {
			ResourceItem ri = (ResourceItem) items.get(i);
			if(ri instanceof ResourceItem) {
				return resource;
			}
		}
		return null;
	}
	
	// Entity gets item
	public void get(ResourceItem item) {
		
	}
	
}
