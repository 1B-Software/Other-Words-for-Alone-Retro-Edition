package robatortas.code.files.project.archive;

import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;

public class SpriteArchive {

	private static SpriteSheetManager nature = SheetArchive.nature;
	private static SpriteSheetManager groundNature = SheetArchive.groundNature;
	private static SpriteSheetManager foliage = SheetArchive.foliage;
	private static SpriteSheetManager solids = SheetArchive.solids;
	private static SpriteSheetManager interior = SheetArchive.interior;
	
	public static SpriteManager voidSprite = new SpriteManager(16, 0, 0, new SpriteSheetManager("/textures/spritesheet/NULL_TEXTURE.png", 16));
	
	//////////////
	// NATURE!! //
	//////////////
	
	public static SpriteManager grass = new SpriteManager(16, 1, 1, groundNature);
	public static SpriteManager tree = new SpriteManager(32, 0, 0, foliage);
	public static SpriteManager tree2 = new SpriteManager(16, 2, 3, nature);
	public static SpriteManager pebble = new SpriteManager(16, 2, 0, nature);
	public static SpriteManager dirt = new SpriteManager(16, 5, 0, nature);
	public static SpriteManager bush = new SpriteManager(16, 6, 1, foliage);
	
	// Exteriors
	public static SpriteManager cobblePath = new SpriteManager(16, 2, 1, nature);
	
	// Flowers
	public static SpriteManager yellowDahlia = new SpriteManager(16, 1, 0, nature);
	public static SpriteManager poppy = new SpriteManager(16, 4, 0, nature);
	
	//////////////////
	// NATURE END!! //
	//////////////////
	
	////////////
	// Solids //
	////////////
	
	// Wood
	public static SpriteManager woodFloor = new SpriteManager(16, 0, 0, solids);
	public static SpriteManager woodWall = new SpriteManager(16, 0, 1, solids);
	
	// Stone
	public static SpriteManager cobblestone = new SpriteManager(16, 1, 0, solids);
	public static SpriteManager stoneBrick = new SpriteManager(16, 2, 0, solids);
	
	// Brick
	public static SpriteManager bricks = new SpriteManager(16, 3, 0, solids);
	
	////////////////
	// Solids End //
	////////////////
	
	///////////////
	// Furniture //
	///////////////
	
	public static SpriteManager table = new SpriteManager(16, 1, 0, interior);
	public static SpriteManager chair = new SpriteManager(16, 2, 0, interior);
	public static SpriteManager bed = new SpriteManager(16, 0, 1, interior);
	public static SpriteManager bed2 = new SpriteManager(16, 0, 2, interior);
	
	///////////////////
	// Furniture End //
	///////////////////
	
	// Mobs
	public static SpriteManager swingFx = new SpriteManager(16, 4, 0, SheetArchive.fx);
	public static SpriteManager swingFx_Sides = new SpriteManager(16, 3, 0, SheetArchive.fx);
	
	// GUI
	public static SpriteManager cori = new SpriteManager(16, 8, 8, SheetArchive.gui);
	public static SpriteManager stamina = new SpriteManager(16, 7, 8, SheetArchive.gui);
	
	////////////
	// Colors //
	////////////
	
	//Tile Values in hex
	public static final int col_grass = 0xff089F00;
	public static final int col_yellowDahlia = 0xffE2B500;
	public static final int col_flowerRed = 0xffff000f;
	public static final int col_flowersRed = 0xff71000f;
	public static final int col_solidRock = 0xffA0A0A0;
	public static final int col_woodFloor = 0xffBC894F;
	public static final int col_dirt = 0xff724227;
	public static final int col_cobblestone = 0xff808080;
	public static final int col_cobblePath = 0xff61705F;
	public static final int col_stoneBricks = 0xff8D8D8D;
	public static final int col_bricks = 0xff9B6666;
	public static final int col_flowersRedDouble = 0xffff6666;
	public static final int col_woodWall = 0xff3D2D19;
	public static final int col_dirtGrass = 0xff72692f;
	public static final int col_table = 0xff683A14;
	public static final int col_void = 0xff000000;
	public static final int col_chair = 0xffa05920;
	
	// Nature
	public static final int col_bush = 0xff286d1f;
	
	// TREE
	public static final int col_oakTree = 0xff003D00;
	public static final int col_birchTree = 0xffA4CC82;
	
	public static final int col_bed = 0xff00e5ff;
	public static final int col_water = 0xff000080;
}
