package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.physics.PhysicsEngine;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Particle extends EntityManager {
	
	private int life = 60 + random.nextInt(60);
	private int time = 0;
	
	private PhysicsEngine physicsEngine;
	
	private int color;
	
	public Particle(int x, int y) {
		this.x = x;
		this.y = y;
		this.physicsEngine = new PhysicsEngine(x, y);
	}
	
	public void update() {
		time++;
		
		physicsEngine.calculations.physics();
		move((int)physicsEngine.calculations.x0 - x, (int)physicsEngine.calculations.y0 - y);
		
		life();
	}
	
	public void setColor(int color) {
		this.color = color;
		
		if(color == 0) {
			color = 0xffff00ff;
		}
	}
	
	public void render(RenderManager screen) {
		screen.renderBox(x, y - (int) physicsEngine.calculations.z0, 2, 2, color);
	}
	
	public void life() {
		if(time >= life) {
			remove();
			return;
		}
	}
	
	public boolean canSwim() {
		return true;
	}
}