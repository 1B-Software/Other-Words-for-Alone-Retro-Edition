package robatortas.code.files.core.render;

public class SpriteManager {
	
	public int x, y;
	public int SIZE;
	public int width, height;
	public SpriteSheetManager sheet;
	public int[] pixels;
	
	public SpriteManager(int size, int x, int y, SpriteSheetManager sheet) {
		this.SIZE = size;
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public SpriteManager(int size, int color) {
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
		
		for(int i = 0; i < SIZE*SIZE; i++) {
			pixels[i] = color;
		}
	}
	
	public SpriteManager(SpriteSheetManager sheet, int width, int height) {
		SIZE = (width == height) ? width: -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public SpriteManager(int[] pixels, int width, int height) {
		//this is the same as if(width == height) {size = width} else {size = -1;}
		//? = if true
		//: = else
		this.SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		// Copies pixels info to this.pixels info
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x+y*SIZE] = sheet.pixels[(x+this.x) + (y+this.y) * sheet.SIZE];
			}
		}
	}
}
