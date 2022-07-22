package robatortas.code.files.project.inventory;

import java.util.ArrayList;
import java.util.List;

import robatortas.code.files.core.console.Console;

// Class to get all items that exist in the game and stuff related
public class Items {
	
	public List<Item> items = new ArrayList<Item>();
	
	public Items() {
	}
	
	public static Item getItem(String itemName) {
		ArrayList<Item> items = new ResourceItem(itemName).getInstances();
		for(int i = 0; i < items.size(); i++) {
			Item gottenItem = items.get(i);
			if(gottenItem.getName() == itemName) return gottenItem;
		}
		Console.writeErr("Item not found exception on getItem method.");
		return null;
	}
	
	public void add(Item item) {
		items.add(item);
	}
}