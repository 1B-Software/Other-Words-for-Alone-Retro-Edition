package robatortas.code.files.core.render;

public class Fonts {

	public int color = 0x000000;
	
	@SuppressWarnings("unused")
	public int x, y;
	private float size = 0;
	private int spacing = 7;
	private int row = 6;
	
	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "1234567890"
			+ ".,!?'\"-+=:/\\%()<>";
	
	public static SpriteSheetManager font = new SpriteSheetManager("/textures/font/fontsheet.png", 864);
	
	public SpriteManager characters;
	
	// draws font to screen
	public void draw(String msg, int x, int y, boolean scroll, RenderManager screen) {
		this.x = x;
		this.y = y;
		
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int ix = chars.indexOf(msg.charAt(i));
			if (ix >= 0) {
				characters = new SpriteManager(16, ix, row, font);
				if(!scroll) screen.renderFont((int) ((x + RenderMethod.xScroll) + i * (size*spacing)), (int) (RenderMethod.yScroll + y), characters, size, color, 0);
				else screen.renderFont(x + i * spacing, y, characters, size, color, 0);
			}
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}
	
	public void setSize(float f) {
		this.size = f;
	}
	
	public float getSize() {
		return this.size;
	}

	public int getTextWidth(String msg) {
		return (int) ((msg.length() - 1) * (size * spacing) + (16 * size));
	}
}