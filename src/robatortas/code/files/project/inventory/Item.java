package robatortas.code.files.project.inventory;

import java.util.ArrayList;
import java.util.List;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;

public class Item extends ItemHandler {
	
	public ResourceItem resourceItem;
	
	protected ArrayList<Item> items = new ArrayList<Item>();
	
	public Item() {
//		for(int i = 0; i < getInstances().size(); i++) {
//			System.out.println("ITEMS: " + getInstances().get(i).getName());
//		}
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
	
	// Might use this code later.
	public Item getItem(String itemName) {
//		Item result = null;
//		for(int i = 0; i < getInstances().size(); i++) {
//			Item unfoundItem = getInstances().get(i);
//			System.out.println(unfoundItem.getName());
//			if(unfoundItem.getName() == itemName) {
//				result = unfoundItem;
//				return result;
//			}
//		}
		return null;
	}
	
	public void render(RenderManager screen) {
		
	}
}