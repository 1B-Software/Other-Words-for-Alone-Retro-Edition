package robatortas.code.files.project.inventory;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class Resource {

	public SpriteManager sprite;
	
	public Resource(SpriteManager sprite) {
		this.sprite = sprite;
	}
	
	public static Resource sample = new Resource(SpriteArchive.poppy);
}
