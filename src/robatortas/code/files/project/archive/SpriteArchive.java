package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;

public class SpriteArchive {

	public static SpriteManager voidSprite = new SpriteManager(16, 0, 0, SheetArchive.nature);
	public static SpriteManager grassSprite = new SpriteManager(16, 0xffffffff);
	
	// Mobs
	public static SpriteManager swingFx = new SpriteManager(16, 4, 0, SheetArchive.fx);
	public static SpriteManager swingFx_Sides = new SpriteManager(16, 3, 0, SheetArchive.fx);
}
