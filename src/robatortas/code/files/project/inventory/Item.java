package robatortas.code.files.project.inventory;

import java.util.ArrayList;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.render.SpriteManager;

public class Item extends ItemHandler {
	
	public ResourceItem resourceItem;
	
	protected static ArrayList<Item> items = new ArrayList<Item>();
	
	public Item() {
		
	}
	
	public SpriteManager getSprite() {
		return resourceItem.getSprite();
	}
	
	public String getName() {
		return resourceItem.getName();
	}
	
	public static ArrayList<Item> getInstances() {
		return items;
	}
	
	// Gets each instance of the items one by one in a for loop.
	public static Item getLoneInstance(){
		Item result = null;
		for(int i = 0; i < ResourceItem.getInstances().size(); i++) {
			result = getInstances().get(i);
		}
		return result;
	}

	// To get any items from the game
	public Item getItem(String itemName) {
		ArrayList<Item> items = ResourceItem.getInstances();
		for(int i = 0; i < items.size(); i++) {
			Item gottenItem = items.get(i);
			if(gottenItem.getName().equals(itemName)) {
				return gottenItem;
			}
		}
		return null;
	}
}