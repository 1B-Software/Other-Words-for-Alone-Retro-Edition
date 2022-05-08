package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteSheetManager;

public class SheetArchive {
	
	public static SpriteSheetManager nature = new SpriteSheetManager("/textures/spritesheet/nature/nature.png", 144);
	public static SpriteSheetManager groundNature = new SpriteSheetManager("/textures/spritesheet/nature/ground.png", 208, 96);
	public static SpriteSheetManager foliage = new SpriteSheetManager("/textures/spritesheet/nature/foliage.png", 144, 32);
	
	public static SpriteSheetManager interior = new SpriteSheetManager("/textures/spritesheet/interior/interior.png", 144);

	public static SpriteSheetManager solids = new SpriteSheetManager("/textures/spritesheet/solid/solid.png", 144);
	
	public static SpriteSheetManager gui = new SpriteSheetManager("/textures/spritesheet/gui/gui.png", 144);
	
	// Mobs
	public static SpriteSheetManager player = new SpriteSheetManager("/textures/spritesheet/mob/mobtest.png", 256);
	public static SpriteSheetManager chicken = new SpriteSheetManager("/textures/spritesheet/mob/chicken.png", 48);
	
	
	public static SpriteSheetManager fx = new SpriteSheetManager("/textures/spritesheet/fx/fx.png", 128);
}