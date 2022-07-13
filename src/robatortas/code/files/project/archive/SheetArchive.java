package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteSheetManager;

public class SheetArchive {
	
	public static SpriteSheetManager nature = new SpriteSheetManager("/textures/spritesheet/nature/nature.png", 144);
	public static SpriteSheetManager groundNature = new SpriteSheetManager("/textures/spritesheet/nature/ground.png", 208, 96);
	public static SpriteSheetManager foliage = new SpriteSheetManager("/textures/spritesheet/nature/foliage.png", 144, 32);
	
	public static SpriteSheetManager interior = new SpriteSheetManager("/textures/spritesheet/interior/interior.png", 144);

	public static SpriteSheetManager solids = new SpriteSheetManager("/textures/spritesheet/solid/solid.png", 144);
	
	public static SpriteSheetManager gui = new SpriteSheetManager("/textures/spritesheet/gui/gui.png", 144);
	
	public static SpriteSheetManager items = new SpriteSheetManager("/textures/spritesheet/item/item.png", 96);
	public static SpriteSheetManager food = new SpriteSheetManager("/textures/spritesheet/item/food.png", 64, 56);
	
	//////////
	// Mobs //
	//////////
	
	// PLAYER
	public static SpriteSheetManager player = new SpriteSheetManager("/textures/spritesheet/mob/player.png", 128, 176);
	public static SpriteSheetManager playerSwim = new SpriteSheetManager("/textures/spritesheet/mob/player_swim.png", 128, 96);
	
	// ANIMALS
	public static SpriteSheetManager chicken = new SpriteSheetManager("/textures/spritesheet/mob/chicken.png", 16, 48);
	public static SpriteSheetManager cow = new SpriteSheetManager("/textures/spritesheet/mob/cow.png", 64, 96);
	
	
	public static SpriteSheetManager fx = new SpriteSheetManager("/textures/spritesheet/fx/fx.png", 128);
}