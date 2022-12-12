package robatortas.code.files.project.level.world;

import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class Values {

	private double val, mval;
	private byte[] map;
	
	public Values(double val2, double mval2, byte[] map) {
		this.val = val2;
		this.mval = mval2;
		this.map = map;
	}
	
	// DEFINES THE WORLD BY THE TILE ID's!
	public void defineID(double val, int i) {
		if(val < -0.1) {
			// WATER
			map[i] = (byte) TileArchive.water.id;
		} else if(val > 1.9 && mval < -1.0) {
			// ROCK
			map[i] = (byte) TileArchive.cobblestone.id;
		} else if(val < 0.3) {
			map[i] = (byte) TileArchive.dirtTile.id;
		} else {
			// GRASS
			map[i] = (byte) TileArchive.grass.id;
		}
	}
	
	// DEFINES A COLORFUL IMAGE OF THE WORLD!
	public void defineColor(byte[] noiseMap, int[] pixels, int i) {
		if(noiseMap[i] == TileArchive.water.id) pixels[i] = 0xff000080;
		if(noiseMap[i] == TileArchive.grass.id) pixels[i] = 0xff089F00;
		if(noiseMap[i] == TileArchive.cobblestone.id) pixels[i] = 0xff808080;
		if(noiseMap[i] == TileArchive.dirtTile.id) pixels[i] = 0xffff00;
	}
	
	// DEFINES A BLACK AND WHITE IMAGE OF THE WORLD (NOT MY WORRY FOR NOW!)
	public void defineBW() {
		
	}
	
}