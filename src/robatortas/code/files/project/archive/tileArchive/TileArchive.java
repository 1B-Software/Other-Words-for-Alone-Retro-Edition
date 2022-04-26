package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.GUI.GUIRender;
import robatortas.code.files.project.archive.tileArchive.Interior.BedTile;
import robatortas.code.files.project.archive.tileArchive.Interior.ChairTile;
import robatortas.code.files.project.archive.tileArchive.Interior.TableTile;
import robatortas.code.files.project.archive.tileArchive.Nature.BushTile;
import robatortas.code.files.project.archive.tileArchive.Nature.CobblePathTile;
import robatortas.code.files.project.archive.tileArchive.Nature.DirtTile;
import robatortas.code.files.project.archive.tileArchive.Nature.FlowerTile;
import robatortas.code.files.project.archive.tileArchive.Nature.GrassTile;
import robatortas.code.files.project.archive.tileArchive.Nature.PebbleTile;
import robatortas.code.files.project.archive.tileArchive.Nature.PoppyTile;
import robatortas.code.files.project.archive.tileArchive.Nature.WaterTile;
import robatortas.code.files.project.archive.tileArchive.Solids.BricksTile;
import robatortas.code.files.project.archive.tileArchive.Solids.CobblestoneTile;
import robatortas.code.files.project.archive.tileArchive.Solids.SolidRockTile;
import robatortas.code.files.project.archive.tileArchive.Solids.StoneBricksTile;
import robatortas.code.files.project.archive.tileArchive.Solids.WoodFloorTile;
import robatortas.code.files.project.archive.tileArchive.Solids.WoodWallTile;

public class TileArchive {
	
	// Nature
	public static TileManager voidTile = new VoidTile(SpriteArchive.voidSprite, 0);
	public static TileManager grass = new GrassTile(SpriteArchive.grassSprite, 1);
	public static TileManager water = new WaterTile(SpriteArchive.voidSprite, 3);
	public static TileManager rock = new PebbleTile(SpriteArchive.pebble, 4);
	public static TileManager yellowDahlia = new FlowerTile(SpriteArchive.yellowDahlia, 7);
	public static TileManager flowerRed = new PoppyTile(SpriteArchive.poppy, 9);
	public static TileManager woodFloor = new WoodFloorTile(SpriteArchive.woodFloor, 12);
	public static TileManager dirtTile = new DirtTile(SpriteArchive.dirt, 14);
	public static TileManager bushTile = new BushTile(SpriteArchive.bush, 15);
	public static TileManager cobblePath = new CobblePathTile(SpriteArchive.cobblePath, 16);
	
	// Solids
	public static TileManager cobblestone = new CobblestoneTile(SpriteArchive.cobblestone, 17);
	public static TileManager stoneBricks = new StoneBricksTile(SpriteArchive.stoneBrick, 18);
	public static TileManager bricksTile = new BricksTile(SpriteArchive.bricks, 19);
	public static TileManager woodWall = new WoodWallTile(SpriteArchive.woodWall, 20);
	public static TileManager solidRock = new SolidRockTile(null, 21);
	
	// Interior
	public static TileManager table = new TableTile(SpriteArchive.table, 22);
	public static TileManager chair = new ChairTile(SpriteArchive.chair, 23);
	public static TileManager bed = new BedTile(SpriteArchive.bed, 24);
}
