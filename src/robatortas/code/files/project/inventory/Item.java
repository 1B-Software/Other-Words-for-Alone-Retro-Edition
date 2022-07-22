package robatortas.code.files.project.inventory;

import java.util.ArrayList;

import robatortas.code.files.core.console.Console;
import robatortas.code.files.core.render.SpriteManager;

public class Item extends ItemHandler {
	
	public ResourceItem resourceItem;
	
	protected ArrayList<Item> items = new ArrayList<Item>();
	
	public Item() {
		
	}
	
	public SpriteManager getSprite() {
		return resourceItem.getSprite();
	}
	
	public String getName() {
		return resourceItem.getName();
	}
	
	public ArrayList<Item> getInstances() {
		return items;
	}

	// To get any items from the game.
	public Item getItem(String itemName) {
		ArrayList<Item> items = new ResourceItem(itemName).getInstances();
		for(int i = 0; i < items.size(); i++) {
			Item gottenItem = items.get(i);
			if(gottenItem.getName().equals(itemName)) {
				return gottenItem;
			} else if(itemName.equals(gottenItem.getName())) Console.writeErr("Item not found exception on getItem method.");
		}
		return null;
	}
}