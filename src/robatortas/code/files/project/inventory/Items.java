package robatortas.code.files.project.inventory;

import java.util.ArrayList;
import java.util.List;

// Class to get all items that exist in the game and stuff related
public class Items {
	
	public List<Item> items = new ArrayList<Item>();
	
	public void add(Item item) {
		items.add(item);
	}
	
	public List<Item> getList() {
		return items;
	}
	
	static {
		
	}
}