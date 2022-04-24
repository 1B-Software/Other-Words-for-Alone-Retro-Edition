package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteSheetManager;

public class Animations {

	private static SheetArchive sheet;
	
	// Player
	public static SpriteSheetManager playerUp = new SpriteSheetManager(SheetArchive.player, 3, 0, 1, 3, 32);
	public static SpriteSheetManager playerRight = new SpriteSheetManager(SheetArchive.player, 2, 0, 1, 3, 32);
	public static SpriteSheetManager playerDown = new SpriteSheetManager(SheetArchive.player, 0, 0, 1, 3, 32);
	public static SpriteSheetManager playerLeft = new SpriteSheetManager(SheetArchive.player, 1, 0, 1, 3, 32);
	
	public static SpriteSheetManager playerPunchUp = new SpriteSheetManager(SheetArchive.player, 3, 3, 1, 2, 32);
	public static SpriteSheetManager playerPunchDown = new SpriteSheetManager(SheetArchive.player, 0, 3, 1, 2, 32);
}
