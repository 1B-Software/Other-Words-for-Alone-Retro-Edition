package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.Interior.BedTile;
import robatortas.code.files.project.archive.tileArchive.Interior.ChairTile;
import robatortas.code.files.project.archive.tileArchive.Interior.DoorTile;
import robatortas.code.files.project.archive.tileArchive.Interior.NightStand;
import robatortas.code.files.project.archive.tileArchive.Interior.RoomWall;
import robatortas.code.files.project.archive.tileArchive.Interior.TableTile;
import robatortas.code.files.project.archive.tileArchive.Nature.BushTile;
import robatortas.code.files.project.archive.tileArchive.Nature.CobblePathTile;
import robatortas.code.files.project.archive.tileArchive.Nature.DirtTile;
import robatortas.code.files.project.archive.tileArchive.Nature.FlowerTile;
import robatortas.code.files.project.archive.tileArchive.Nature.GrassBladeTile;
import robatortas.code.files.project.archive.tileArchive.Nature.GrassTile;
import robatortas.code.files.project.archive.tileArchive.Nature.PebbleTile;
import robatortas.code.files.project.archive.tileArchive.Nature.PoppyTile;
import robatortas.code.files.project.archive.tileArchive.Nature.TreeTile;
import robatortas.code.files.project.archive.tileArchive.Nature.WaterTile;
import robatortas.code.files.project.archive.tileArchive.Solids.BricksTile;
import robatortas.code.files.project.archive.tileArchive.Solids.CobblestoneTile;
import robatortas.code.files.project.archive.tileArchive.Solids.SolidRockTile;
import robatortas.code.files.project.archive.tileArchive.Solids.StoneBricksTile;
import robatortas.code.files.project.archive.tileArchive.Solids.TVTile;
import robatortas.code.files.project.archive.tileArchive.Solids.WoodFloorTile;
import robatortas.code.files.project.archive.tileArchive.Solids.WoodWallFrontTile;
import robatortas.code.files.project.archive.tileArchive.Solids.WoodWallTile;

public class TileArchive {
	
	// Nature
	public static TileManager voidTile = new VoidTile(SpriteArchive.voidSprite, 0);
	public static TileManager grass = new GrassTile(SpriteArchive.grass, SpriteArchive.col_grass);
	public static TileManager tree = new TreeTile(SpriteArchive.tree, SpriteArchive.col_oakTree);
	public static TileManager water = new WaterTile(SpriteArchive.voidSprite, SpriteArchive.col_water);
	public static TileManager rock = new PebbleTile(SpriteArchive.pebble, SpriteArchive.col_solidRock);
	public static TileManager yellowDahlia = new FlowerTile(SpriteArchive.yellowDahlia, SpriteArchive.col_yellowDahlia);
	public static TileManager flowerRed = new PoppyTile(SpriteArchive.poppy, SpriteArchive.col_flowerRed);
	public static TileManager woodFloor = new WoodFloorTile(SpriteArchive.woodFloor, SpriteArchive.col_wood_floor);
	public static TileManager dirtTile = new DirtTile(SpriteArchive.dirt, SpriteArchive.col_dirt);
	public static TileManager bushTile = new BushTile(SpriteArchive.bush, SpriteArchive.col_bush);
	public static TileManager cobblePath = new CobblePathTile(SpriteArchive.cobblePath, SpriteArchive.col_cobblePath);
	
	// Solids
	public static TileManager cobblestone = new CobblestoneTile(SpriteArchive.cobblestone, SpriteArchive.col_cobblestone);
	public static TileManager stoneBricks = new StoneBricksTile(SpriteArchive.stoneBrick, SpriteArchive.col_stoneBricks);
	public static TileManager bricksTile = new BricksTile(SpriteArchive.bricks, SpriteArchive.col_bricks);
	public static TileManager woodWall = new WoodWallTile(SpriteArchive.woodWall, SpriteArchive.col_wood_wall_front);
	public static TileManager solidRock = new SolidRockTile(null, 21);
	
	// Interior
	public static TileManager table = new TableTile(SpriteArchive.table, 22);
	public static TileManager chair = new ChairTile(SpriteArchive.chair, 23);
	public static TileManager bed = new BedTile(SpriteArchive.bed, SpriteArchive.col_bed);
	public static TileManager sand = new GrassTile(SpriteArchive.sand, 25);
	public static TileManager grassBlades = new GrassBladeTile(SpriteArchive.grass, 26);
	public static TileManager woodWallFront = new WoodWallFrontTile(SpriteArchive.woodWall_Front, 27);
	public static TileManager TvTile = new TVTile(SpriteArchive.tv, SpriteArchive.col_tv);
	public static TileManager nightStand = new NightStand(SpriteArchive.nightStand, SpriteArchive.col_nightStand);
	public static TileManager roomWall = new RoomWall(SpriteArchive.roomWall, SpriteArchive.col_room_walls);
	public static TileManager placeHolderTile = new PlaceHolderTile(SpriteArchive.voidSprite, SpriteArchive.col_placeHolder);
	public static TileManager doorTile = new DoorTile(SpriteArchive.door, SpriteArchive.col_door);
}