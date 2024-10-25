package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.GameManager;

public class Animations {
	// Player
	
	public Animations() {
		
	}
	
	public static SpriteSheetManager playerUp = new SpriteSheetManager(SheetArchive.player, 3, 0, 1, 3, 32);
	public static SpriteSheetManager playerRight = new SpriteSheetManager(SheetArchive.player, 2, 0, 1, 3, 32);
	public static SpriteSheetManager playerDown = new SpriteSheetManager(SheetArchive.player, 0, 0, 1, 3, 32);
	public static SpriteSheetManager playerLeft = new SpriteSheetManager(SheetArchive.player, 1, 0, 1, 3, 32);
	
	public static SpriteSheetManager playerPunchUp = new SpriteSheetManager(SheetArchive.player, 3, 3, 1, 2, 32);
	public static SpriteSheetManager playerPunchDown = new SpriteSheetManager(SheetArchive.player, 0, 3, 1, 2, 32);
	public static SpriteSheetManager playerPunchRight = new SpriteSheetManager(SheetArchive.player, 2, 3, 1, 2, 32);
	public static SpriteSheetManager playerPunchLeft = new SpriteSheetManager(SheetArchive.player, 1, 3, 1, 2, 32);
	
	public static SpriteSheetManager playerUpSwim = new SpriteSheetManager(SheetArchive.playerSwim, 3, 0, 1, 3, 32);
	public static SpriteSheetManager playerRightSwim = new SpriteSheetManager(SheetArchive.playerSwim, 2, 0, 1, 3, 32);
	public static SpriteSheetManager playerDownSwim = new SpriteSheetManager(SheetArchive.playerSwim, 0, 0, 1, 3, 32);
	public static SpriteSheetManager playerLeftSwim = new SpriteSheetManager(SheetArchive.playerSwim, 1, 0, 1, 3, 32);
	
	// Chicken
	public static SpriteSheetManager chickenLeft = new SpriteSheetManager(SheetArchive.chicken, 0, 0, 1, 3, 16);
	public static SpriteSheetManager chickenRight = new SpriteSheetManager(SheetArchive.chicken, 0, 0, 1, 3, 16);
	
	// Cow
	public static SpriteSheetManager cowLeft = new SpriteSheetManager(SheetArchive.cow, 1, 0, 1, 3, 32);
	public static SpriteSheetManager cowRight = new SpriteSheetManager(SheetArchive.cow, 0, 0, 1, 3, 32);
	
	// Sheep
	public static SpriteSheetManager sheepLeft = new SpriteSheetManager(SheetArchive.sheep, 1, 0, 1, 3, 32);
	public static SpriteSheetManager sheepRight = new SpriteSheetManager(SheetArchive.sheep, 0, 0, 1, 3, 32);
	
}
