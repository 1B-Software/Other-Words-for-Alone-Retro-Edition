package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;

public class Resource {

	public SpriteManager sprite;
	
	public Resource(SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	private static SpriteManager sword = new SpriteManager(16, 0, 0, SheetArchive.items);
	
	public static Resource sample = new Resource(sword);
}