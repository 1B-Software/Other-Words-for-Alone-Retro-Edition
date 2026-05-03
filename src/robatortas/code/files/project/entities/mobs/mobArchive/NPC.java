package robatortas.code.files.project.entities.mobs.mobArchive;

import java.io.File;

import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class NPC extends MobAddons {

	private String name;
	private File quirks; // A quirks file is the one which states the several unique modifiers of each individual npc.
	private File saveFile; // The file where the npc info is saved;
	
	public boolean isInteractible = true; // true by default, unless stated otherwise
	
	public NPC(float x, float y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.sprite = new SpriteManager(32, 0, 0, SheetArchive.cow);
	}
	
	private int xa = 0; 
	private int ya = 0;
	
	int ticks = 0;

	float speed = 0.5f;
	public void update() {
		super.update();
		ticks++;
		
		animSprite.resetAnimation(animSprite, walking);
		
		if(ticks % (random.nextInt(30) + 30) == 0) {
			xa = random.nextInt(3)-1;
			ya = random.nextInt(3)-1;
			// Chance of staying static
			if(random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		if(xa == 1) dir = 1;
		if(xa == -1) dir = 3;
		if(ya == 1) dir = 2;
		if(ya == -1) dir = 0;
		
		if(xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else walking = false;
	}
	
	public void render(RenderManager screen) {
		int spriteFlip = 0;
		
		if(dir == 0) animSprite = left;
		if(dir == 1) animSprite = right;
		if(dir == 2) animSprite = right;
		if(dir == 3) animSprite = left;
		
		if(xa >= 1 && ya < 1) animSprite = right;
		if(xa < 1 && ya >= 1) spriteFlip = 1;
		
		sprite = animSprite.getSprite();
		
		screen.renderMob(x - (10*2), y - (12*2), this, sprite, spriteFlip);
	}
	
	private Animate right = new Animate(Animations.cowRight, 1, 3, 3);
	private Animate left = new Animate(Animations.cowLeft, 1, 3, 3);

	private Animate animSprite = right;
	
	// GETTERS
	
	public String getName() {
		return name;
	}
	
	public File getQuirks() {
		return this.quirks;
	}
	
	public File getSaveFile() {
		return this.saveFile;
	}
}