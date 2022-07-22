package robatortas.code.files.project.inventory;

import java.util.ArrayList;
import java.util.List;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SheetArchive;

// This class is for handling resources (Resource being wood sticks and stuff like that).
public class Resource {

	public SpriteManager sprite;
	public String name = "";
	
	private int value = 0;
	
	public Resource(String name, SpriteManager sprite) {
		this.sprite = sprite;
		this.name = name;
	}
	
	
	public static ArrayList<Resource> getInstances() {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		
		// List of resources in the game.
		resources.add(new Resource("wood", 1, woodSprite));
		resources.add(new Resource("meat", 1, meatSprite));
		resources.add(new Resource("chicken", 1, chickenSprite));
		
		return resources;
	}
	
	// Value is for the amount of damage, healing and other stuff the item gives.
	public Resource(String name, int value, SpriteManager sprite) {
		this.sprite = sprite;
		this.name = name;
		this.value = value;
	}
	
	private static SpriteManager woodSprite = new SpriteManager(16, 1, 0, SheetArchive.items);
	public static SpriteManager meatSprite = new SpriteManager(8, 0, 0, SheetArchive.food);
	private static SpriteManager chickenSprite = new SpriteManager(8, 0, 1, SheetArchive.food);
}