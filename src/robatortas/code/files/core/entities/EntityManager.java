package robatortas.code.files.core.entities;

import java.util.List;
import java.util.Random;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.project.entities.ItemEntity;

public class EntityManager {
	
	public int x, y;
	public SpriteManager sprite;
	public LevelManager level;
	
	protected Random random = new Random();
	
	public boolean removed;
	
	protected ItemEntity iE;
	
	public EntityManager() {
		
	}
	
	public EntityManager(int x, int y, SpriteManager sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(RenderManager screen) {
		
	}
	
	public void update() {
		
	}
	
	public void move(int xa, int ya) {
		x+=xa;
		y+=ya;
	}
	
	public void move2(int xa, int ya) {
		List<EntityManager> in = level.getEntities(x + xp*2, y + yp*2, x - xp, y - yp);
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
	public boolean intersects(int x0, int y0, int x1, int y1) {
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
