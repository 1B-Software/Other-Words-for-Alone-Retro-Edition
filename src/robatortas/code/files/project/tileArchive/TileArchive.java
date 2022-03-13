package robatortas.code.files.project.tileArchive;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.tileArchive.nature.GrassTile;

public class TileArchive {
	
	public static TileManager grass = new GrassTile(SpriteArchive.grass, 1);
}
