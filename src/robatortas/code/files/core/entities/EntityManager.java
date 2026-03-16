package robatortas.code.files.core.entities;

import java.util.List;
import java.util.Random;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.entities.ItemEntity;
import robatortas.code.files.project.settings.Globals;

public class EntityManager {
	
	public float x, y;
	float velX, velY;
	public SpriteManager sprite;
	public LevelManager level;
	
	protected Random random = new Random();
	public int alpha = 0xff;
	
	public boolean removed;
	
	protected ItemEntity iE;
	
	public EntityManager() {
		
	}
	
	public EntityManager(float x, float y, SpriteManager sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(RenderManager screen) {
		
	}
	
	public void update() {
		
	}
	
	public void move(float xa, float ya) {
		x+=xa;
		y+=ya;
	}
	
	public void move2(float xa, float ya) {
		List<EntityManager> in = level.getEntities(x + xp, y + yp, x - xp, y - yp);
		for(int i = 0; i < in.size(); i++) {
			EntityManager e = in.get(i);
			if(e == this) continue;
			e.touched(this);
		}
		x+=xa;
		y+=ya;
	}
	
	public void touched(EntityManager entity) {
		
	}
	
	public void hurt(Mob mob, int damage, int attackDir) {
		
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void remove() {
		removed = true;
	}

	public boolean canSwim() {
		return false;
	}
	
	public boolean isInvincible() {
		return false;
	}
	
	protected int xp = 8;
	protected int yp = 8;
	public boolean intersects(float x0, float y0, float x1, float y1) {
		return (x + xp < x0 || y + yp < y0 || x - xp > x1 || y - yp > y1);
	}
	
	public SpriteManager getSprite() {
		return this.sprite;
	}
	
	public void takeItem(EntityManager entity) {
		
	}
	
	public ItemEntity getItem(ItemEntity iE) {
		return this.iE = iE;
	}
	
	public void init(LevelManager level) {
		this.level = level;
	}
}