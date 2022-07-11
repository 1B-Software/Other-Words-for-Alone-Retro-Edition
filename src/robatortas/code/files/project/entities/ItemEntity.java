package robatortas.code.files.project.entities;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.physics.PhysicsEngine;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.entities.mobs.mobArchive.Player;
import robatortas.code.files.project.inventory.Item;
import robatortas.code.files.project.inventory.ResourceItem;

public class ItemEntity extends EntityManager {
	
	private int lifeTime = 60*8;
	private int tickTime = 0;
	
	public Item item;
	public PhysicsEngine physicsEngine;
	
	public ItemEntity(int x, int y, Item item) {
		this.x = x;
		this.y = y;
		this.item = item;
		SoundEngine.drop.play();
		physicsEngine = new PhysicsEngine(this.x, this.y);
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
	
	public void takeItem(Player player) {
		if(physicsEngine.calculations.z0 == 0 && tickTime > 60) {
			player.inventory.add((ResourceItem) item);
			SoundEngine.take.play();
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
		int yy = 0;
		if(physicsEngine.calculations.z0 == 0 && tickTime > 60) {
			if((tickTime / 20) % 2 == 0) yy=1;
			else yy=0;
		}
		
		int shade = 0;
		shade += ((int)physicsEngine.calculations.z0 * 2) - 30;
		if(shade <= 1) screen.renderColorRelativeToLocation(x - 10, (y + 1) - 10, item.getSprite(), -shade, 0, level);
		screen.renderSprite(x - 10, ((y + yy) - (int) physicsEngine.calculations.z0) - 10, item.getSprite(), 0);

	}
}