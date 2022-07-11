package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.physics.PhysicsEngine;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.inventory.Item;
import robatortas.code.files.project.inventory.ResourceItem;

public class ItemEntity extends EntityManager {
	
	private int lifeTime = 60*8;
	private int tickTime = 0;
	
	public Item item;
	private PhysicsEngine physicsEngine;
	
	public ItemEntity(int x, int y, Item item) {
		this.x = x;
		this.y = y;
		this.item = item;
		SoundEngine.drop.play();
		physicsEngine = new PhysicsEngine(this.x, this.y);
		physicsEngine.calculations.settings();
	}
	
	public void update() {
		tickTime++;
		
		if(tickTime >= lifeTime) {
			this.remove();
			return;
		}
		
		physicsEngine.calculations.physics();
		
		move2((int) physicsEngine.calculations.x0 - x, (int) physicsEngine.calculations.y0 - y);
		
	}
	
	public boolean canSwim() {
		return true;
	}
	
	public void grabbedBy(Player player) {
		player.inventory.add((ResourceItem) item);
		System.out.println("GRABBED");
		this.remove();
	}
	
	public void takeItem(Player player) {
		if(physicsEngine.calculations.z0 == 0 && tickTime > 60/2) {
			player.inventory.add((ResourceItem) item);
			SoundEngine.take.play();
			System.out.println("TAKEN!");
			this.remove();
		}
	}
	
	public ItemEntity getItem(ItemEntity iE) {
		return iE = this;
	}
	
	public void render(RenderManager screen) {
		// Blinking when death is close
		if(tickTime >= (lifeTime - 120)) {
			if((tickTime / 10) % 2 == 0) return;
		}
		
		screen.renderSprite(x, y - (int) physicsEngine.calculations.z0, item.getSprite(), 0);
	}
}