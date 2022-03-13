package robatortas.code.files.project.tileArchive;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.tileArchive.nature.GrassTile;

public class TileArchive {
	
	public static TileManager voidTile = new VoidTile(SpriteArchive.voidSprite, 0);
	public static TileManager grass = new GrassTile(SpriteArchive.grassSprite, 1);
}
