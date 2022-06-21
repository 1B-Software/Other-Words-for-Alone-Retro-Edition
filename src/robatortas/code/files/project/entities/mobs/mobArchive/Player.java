package robatortas.code.files.project.entities.mobs.mobArchive;

import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.entities.Particle;
import robatortas.code.files.project.entities.mobs.MobAddons;

public class Player extends MobAddons {
	
	private InputManager input;
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	private Particle particle;
	
	public Player(int x, int y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
		super.dir = 2;
	}
	
	private static int velX = 1;
	private static int velY = 1;	
	private int xs, ys;
	
	public int tickTime;
	
	public void update() {
		super.update();
		this.xs = 0;
		this.ys = 0;
		
		int speed = 1;
		
		tickTime++;
		
		// Controls
		controls();
		
		particleEffects();
		
		if(isSwimming) {
			swimTime++;
			speed = tickTime & 1;
		}
		else swimTime = 0;
		
		// Reset Animations (AVOIDS CRASHING!)
		animSprite.resetAnimation(animSprite, walking);
		
		if(xs != 0 || ys != 0) {
			move(xs * speed, ys * speed);
			walking = true;
		} else walking = false;
	}
	
	private void attack() {
		attackDir = dir;
		attackTime = 5;
		
		int xRange = 10;
		int yRange = 20;
		// coordinate parameters: -- ++
		if (dir == 0) hurt(x + 5, y, x - 5, y - yRange + 4);
		if (dir == 1) hurt(x + xRange + 5, y + 10, x, y - 10);
		if (dir == 3) hurt(x, y, x - xRange - 5, y - 10);
		if (dir == 2) hurt(x, y + yRange - 2, x - 5, y);
	}
	
	private void hurt(int x0, int y0, int x1, int y1) {
		List<EntityManager> entities = level.getEntities(x0, y0, x1, y1);
		for(int i = 0; i < entities.size(); i++) {
			EntityManager e = entities.get(i);
			if(e != this) {
				e.hurt(this, 5, attackDir);
//				System.out.println("INTERSECTS");
			}	
		}
	}
	
	public void render(RenderManager screen) {
		int renderAxysConstX = 18;
		int renderAxysConstY = 23;
		
		////////////
		// GROUND //
		////////////
		if(dir == 0) animSprite = up;
		if(dir == 1) animSprite = right;
		if(dir == 2) animSprite = down;
		if(dir == 3) animSprite = left;
		
		if(!walking) {
			if(attackTime > 0) {
				if(dir == 0) animSprite = punchUp;
				if(dir == 1) animSprite = punchRight;
				if(dir == 2) animSprite = punchDown;
				
				if(dir == 3) animSprite = punchLeft;
				
				if((tickTime / 8) % 2 == 0) {
					animSprite.setFrame(1);
				}
			}
		}
		
		
		//////////////
		// SWIMMING //
		//////////////
		isSwimming = false;
		if(level.getLevel(x >> 4, y >> 4) == TileArchive.water) {
			if(swimTime == 1) {
				SoundEngine.splash.play();
				level.add(particle = new Particle(x, y));
				particle.setColor(0xff40AEE5);
				particle.life = 20 + random.nextInt(20);
			}
			
			if(dir == 0) animSprite = upSwim;
			if(dir == 1) animSprite = rightSwim;
			if(dir == 2) animSprite = downSwim;
			if(dir == 3) animSprite = leftSwim;
			
			// Swimming sillhouete
			if((tickTime / 32) % 2 == 0) screen.renderSprite(x - renderAxysConstX + 8, y - renderAxysConstY/3, new SpriteManager(16, 1, 10, SheetArchive.player), 0);
			else screen.renderSprite(x - renderAxysConstX + 8, y - renderAxysConstY/3, new SpriteManager(16, 2, 10, SheetArchive.player), 0);
			
			if(walking) {
				if(tickTime % 17 == 0) {
					SoundEngine.swim.play();
				}
			}
			
			isSwimming = true;
		}
		if(isSwimming) renderAxysConstY = 15;
		
		// Render Mob
		sprite = animSprite.getSprite();
		
		beforeLayer(screen);
		screen.renderMob(x - renderAxysConstX, y - renderAxysConstY, this, sprite, 0);
		afterLayer(screen);
	}
	
	private void beforeLayer(RenderManager screen) {
		// Finally using switch case huh?
			switch(attackDir) {
			case 0: 
				if(attackTime > 0) screen.renderSprite(x-10, y-26, SpriteArchive.swingFx, 0);
				break;
			case 1:
				if(attackTime > 0) screen.renderSprite(x+1, y-16, SpriteArchive.swingFx_Sides, 1);
				break;
			case 3:
				if(attackTime > 0) screen.renderSprite(x-20, y-16, SpriteArchive.swingFx_Sides, 0);
				break;
			}
	}
	
	private void afterLayer(RenderManager screen) {
		switch(attackDir) {
		case 2:
			if(attackTime > 0) screen.renderSprite(x-8, y-1, SpriteArchive.swingFx, 2);
			break;
		}
	}
	
	private void particleEffects() {
		if(walking && isSwimming) {
			if(tickTime % 17 == 0) {
				for(int i = 0; i < 3; i++) {
					level.add(particle = new Particle(x, y));
					particle.setColor(0xff40AEE5);
					particle.physicsEngine.calculations.elasticity = 0;
					particle.physicsEngine.calculations.gravityForce = 0.2;
					particle.physicsEngine.calculations.frictionX = 0.96;
					particle.physicsEngine.calculations.frictionY = 0.96;
					particle.life = 20 + random.nextInt(20);
				}
			}
		}
	}
	
	
	private void controls() {
		// Controls
		if(input.up) ys -= velY;
		if(input.down) ys += velY;
		if(input.left) xs -= velX;
		if(input.right) xs += velX;
		
		if(input.f) {;
			if(punch == false) {
				punch = true;
				attack();
				level.insertTile((x >> 4) + 1, y >> 4, SpriteArchive.col_bricks);
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
		if(GameManager.DEV_MODE) {
			if(input.shift) {
				velX = 2;
				velY = 2;
			} else {
				velX = 1;
				velY = 1;
			}
		}
	}
	
	// PLAYER CAN SWIM!!! (The other entities can't now, so fuck 'em)
	public boolean canSwim() {
		return true;
	}
	
	// Animations
	public Animate up = new Animate(Animations.playerUp, 1, 3, 3);
	public Animate right = new Animate(Animations.playerRight, 1, 3, 3);
	public Animate down = new Animate(Animations.playerDown, 1, 3, 3);
	public Animate left = new Animate(Animations.playerLeft, 1, 3, 3);
	
	public Animate upSwim = new Animate(Animations.playerUpSwim, 1, 3, 3);
	public Animate rightSwim = new Animate(Animations.playerRightSwim, 1, 3, 3);
	public Animate downSwim = new Animate(Animations.playerDownSwim, 1, 3, 3);
	public Animate leftSwim = new Animate(Animations.playerLeftSwim, 1, 3, 3);
	
	public Animate punchDown = new Animate(Animations.playerPunchDown, 1, 2, 2);
	public Animate punchUp = new Animate(Animations.playerPunchUp, 1, 2, 2);
	
	public Animate punchRight = new Animate(Animations.playerPunchRight, 1, 2, 2);
	public Animate punchLeft = new Animate(Animations.playerPunchLeft, 1, 2, 2);
	
	private Animate animSprite = down;
}