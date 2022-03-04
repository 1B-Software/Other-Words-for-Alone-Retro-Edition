package robatortas.code.files.core.render;

public class SpriteManager {
	
	public int x, y;
	public int SIZE;
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
	
	public void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x+y*SIZE] = sheet.pixels[(x+this.x) + (y+this.y) * sheet.SIZE];
			}
		}
	}
}
