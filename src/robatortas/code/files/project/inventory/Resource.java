package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class Resource {

	public SpriteManager sprite;
	public String name = "";
	
	private int value = 0;
	
	public Resource(String name, SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	/*
	 * Value is for the amount of damage, healing and other stuff the item gives.
	 */
	public Resource(String name, int value, SpriteManager sprite) {
		this.sprite = sprite;
		this.value = value;
	}
	
	private static SpriteManager swordSprite = new SpriteManager(16, 0, 0, SheetArchive.items);
	private static SpriteManager woodSprite = new SpriteManager(16, 1, 0, SheetArchive.items);

	private static SpriteManager meatSprite = new SpriteManager(8, 0, 0, SheetArchive.food);
	private static SpriteManager chickenSprite = new SpriteManager(8, 0, 1, SheetArchive.food);
	
	public static Resource sword = new Resource("Sword", swordSprite);
	public static Resource wood = new Resource("Wood", woodSprite);
	public static Resource meat = new Resource("Meat", 1, meatSprite);
	public static Resource chicken = new Resource("Chicken", 1, chickenSprite);
}