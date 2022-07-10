package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.physics.PhysicsEngine;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.inventory.Item;

public class ItemEntity extends EntityManager {
	
	private int lifeTime = 60*8;
	private int tickTime = 0;
	
	public Item item;
	private PhysicsEngine physics;
	
	public ItemEntity(int x, int y, Item item) {
		this.x = x;
		this.y = y;
		
		this.item = item;
		
		physics = new PhysicsEngine(x, y);
	}
	
	public void update() {
		tickTime++;
		
		if(tickTime >= lifeTime) {
			this.remove();
			return;
		}
		
		if(tickTime == 1) SoundEngine.drop.play();
		
		physics.calculations.physics();
		physics.calculations.settings();
		
		move2((int) physics.calculations.x0 - x, (int) physics.calculations.y0 - y);
		
	}
	
	public void render(RenderManager screen) {
		// Blinking when death is close
		if(tickTime >= (lifeTime - 120)) {
			if((tickTime / 10) % 2 == 0) return;
		}
		
		screen.renderSprite(x - (int) physics.calculations.x1, y - (int) physics.calculations.z0, item.getSprite(), 0);
	}
}