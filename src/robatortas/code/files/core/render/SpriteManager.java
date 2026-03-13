package robatortas.code.files.core.render;

public class SpriteManager {

	public int x, y;
	public int SIZE;
	public int width, height;
	public int alpha = 0xff;
	public SpriteSheetManager sheet;
	public int[] pixels;

	// UV coordinates within the parent spritesheet (0..1 range)
	public float u0, v0, u1, v1;
	
	public SpriteManager(int size, int x, int y, SpriteSheetManager sheet) {
		this.SIZE = size;
		this.x = x * size;
		this.y = y * size;
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		pixels = new int[size*size];
		load();
		computeUVs();
	}
	
	public SpriteManager(int size, int color, int alpha) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.alpha = alpha;
		pixels = new int[size*size];
		
		for(int i = 0; i < size*size; i++) {
			pixels[i] = color;
		}
	}
	
	public SpriteManager(int size, int x, int y, SpriteManager sprite) {
		this.SIZE = size;
		this.x = x * size;
		this.y = y * size;
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		pixels = new int[size*size];
	}
	
	
	public SpriteManager( int width, int height, SpriteSheetManager sheet) {
		SIZE = (width == height) ? width: -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		pixels = new int[width*height];
		load();
		computeUVs();
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
				pixels[x+y*width] = sheet.pixels[(x+this.x) + (y+this.y) * sheet.WIDTH];
			}
		}
	}

	/**
	 * Tells the UV coordinates where the sprite starts and where it ends.
	 */
	public void computeUVs() {
		if (sheet != null && sheet.WIDTH > 0 && sheet.HEIGHT > 0) {
			u0 = (float) this.x / sheet.WIDTH;
			v0 = (float) this.y / sheet.HEIGHT;
			u1 = (float) (this.x + width) / sheet.WIDTH;
			v1 = (float) (this.y + height) / sheet.HEIGHT;
		} else {
			// Fallback: full texture
			u0 = 0; v0 = 0; u1 = 1; v1 = 1;
		}
	}

	/**
	 * Get the GPU texture ID from the parent spritesheet.
	 * Returns 0 if no sheet or not uploaded yet.
	 */
	public int getTextureId() {
		if (sheet != null) return sheet.getGPUTextureId();
		return 0;
	}
}
