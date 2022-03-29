package robatortas.code.files.core.render;

public class Animate extends SpriteManager {
	
	private int time = 0;
	private SpriteManager sprite;
	private int lenght;
	private int rate = 10;
	public int frame;
	
	public Animate(SpriteSheetManager sheet, int width, int height, int animLenght) {
		super(sheet, width, height);
		this.lenght = animLenght;
		sprite = sheet.getSprites()[0];
		//getSprites is where the sprites are stored
		if(lenght > sheet.getSprites().length) System.err.println("Animate: animLenght value is longer than the number of stored frames");
	}
	
	public void tick() {
		time++;
		if(time % rate == 0) {
			if(frame >= lenght - 1) frame = 1;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}
	
	public void setFrameRate(int frames) {
		rate = frames;
	}
	
	public void loop(int tick, int time, Animate animation) {
		if(tick % time == 0) {
			sprite = animation;
		}
		System.err.println("ERROR: Animation value returns null");
	} 
	
	public void setFrame(int i) {
		if(i > sheet.getSprites().length - 1) {
			System.err.println("setFrame: Index out of bounds in " + this);
			return;
		}
		sprite = sheet.getSprites()[i];
	}
	
	public void resetAnimation(Animate animatedSprite, boolean walking) {
		// Sets animation frame back to 0 if not walking
		if(walking) animatedSprite.tick();
			else if(!walking){
				animatedSprite.setFrame(0);
		}
	}
	
	public SpriteManager getSprite() {
		return sprite;
	}
}