package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class Resource {

	public SpriteManager sprite;
	public String name = "";
	
	public Resource(String name, SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	private static SpriteManager swordSprite = new SpriteManager(16, 0, 0, SheetArchive.items);
	private static SpriteManager woodSprite = new SpriteManager(16, 1, 0, SheetArchive.items);
	
	public static Resource sword = new Resource("Sword", swordSprite);
	public static Resource wood = new Resource("Wood", woodSprite);
}