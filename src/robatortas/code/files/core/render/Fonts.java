package robatortas.code.files.core.render;

public class Fonts {

	public static int color = 0x000000;
	
	@SuppressWarnings("unused")
	private int SIZE = 0;
	private int spacing = 11;
	private int row = 0;
	
	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "1234567890"
			+ ".,!?'\"-+=:/\\%()<>";
	
	public static SpriteSheetManager font = new SpriteSheetManager("/textures/font/fontsheet.png", 864);
	
	public SpriteManager characters;
	
	// draws font to screen
	public void draw(String msg, int x, int y, boolean scroll, RenderManager screen) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int ix = chars.indexOf(msg.charAt(i));
			if (ix >= 0) {
				characters = new SpriteManager(16, ix, row, font);
				if(!scroll) screen.renderFont((RenderMethod.xScroll + x) + i * spacing, (RenderMethod.yScroll + y), characters, 16, color(color), 0);
				else screen.renderFont(x + i * spacing, y, characters, 16, color(color), 0);
			}
		}
	}
	
	public int color(int color) {
		Fonts.color = color;
		return color;
	}
	
	// fontSize 1 = small font
	// fontSize 7 = big font
	public void fontSize(int SIZE) {
		this.SIZE = SIZE;
		if(SIZE == 0) {
			spacing = 5;
			row = 1;
		}
		// Not the most effective way, I know...
		switch(SIZE) {
		case 1:
			spacing = 6;
			row = 0;
		break;
		case 2:
			spacing = 6;
			row = 1;
		break;
		case 3:
			spacing = 6;
			row = 2;
		break;
		case 4:
			spacing = 6;
			row = 3;
		break;
		case 5:
			spacing = 6;
			row = 4;
		break;
		case 6:
			spacing = 6;
			row = 5;
		break;
		case 7:
			spacing = 6;
			row = 6;
		break;
		}
	}
}