package robatortas.code.files.project.entities.mobs.mobArchive;

import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.interaction.DialogueBox;
import robatortas.code.files.core.interaction.DialogueSystem;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.lighting.LightSource;
import robatortas.code.files.core.render.Animate;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.sound.SoundEngine;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.archive.Animations;
import robatortas.code.files.project.archive.SheetArchive;
import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;
import robatortas.code.files.project.archive.tileArchive.Interior.BedTile;
import robatortas.code.files.project.archive.tileArchive.Interior.DoorTile;
import robatortas.code.files.project.archive.tileArchive.Interior.NightStand;
import robatortas.code.files.project.entities.Particle;
import robatortas.code.files.project.entities.mobs.MobAddons;
import robatortas.code.files.project.inventory.Inventory;
import robatortas.code.files.project.save_system.FileSystem;

public class Player extends MobAddons {
	
	public String name = "";
	
	public InputManager input;
	
	public boolean punch = true;
	public int attackTime, attackDir;
	
	private Particle particle;
	
	private SpriteManager punchSprite;
	
	public Inventory inventory;
	
	private LightSource light;
	
	public int stamina = 10;
	

	// If the player is interacting with something
	private boolean interacts = false;
	
	FileSystem file;
	
	public Player(float x, float y, InputManager input) {
		this.x = x;
		this.y = y;
		this.input = input;
		file = new FileSystem("res/save/player.json");
		this.name = (String) file.getKeyValue("name", name);
		this.sprite = new SpriteManager(16, 0, 0, SheetArchive.player);
		super.dir = 2;
		this.inventory = new Inventory();
		alpha = 0xFF;
	}
	
	private static float velX = 1;
	private static float velY = 1;	
	public float xa, ya;
	
	public int tickTime;
	int doorTime = 0;
	
	public boolean isInInteractibleRange = false;
	
	float speed = 0.5f;
	
	public void update() {
		super.update();
		this.xa = 0;
		this.ya = 0;
		
		tickTime++;
		this.isInInteractibleRange = false;
		

		if(!isSwimming && speed != 0) speed = 0.5f;
		
		// Controls
		controls();
		
		stamina();
		
		particleEffects();
		if(isSwimming) {
			swimTime++;
			speed = 0.4f;
		}
		else swimTime = 0;
		
		if((doorTime % 2 == 0) && GameManager.fadeAlpha == 0) {
			if(LevelManager.level.getDoors((int)x >> 4, (int)y >> 4) == TileArchive.doorTile) {
				DoorTile door = LevelManager.level.findDoorAt((int)x >> 4, (int)y >> 4);
				doorTime++;
				if (door != null) {
					SoundEngine.drop.play();
					LevelManager.enterDoor(door);
					speed = 0;
				}
			}
		}
		
		if(tickTime % 17 == 0 && !isSwimming && walking) {
			SoundEngine.hard_step.play();
		}
		
//		if(canInteractWith(TileArchive.dirtTile)) {
//			System.out.println("TOUCHING BED");
//		}
		
		// TILE INTERACTION
		for (TileManager t : level.getNeighborTiles((int)x, (int)y)) {
		    if (t instanceof BedTile) {
		    	
		    }
		    if(t instanceof NightStand) {
		    	// TODO: if interacts with, then itll toggle the light off.
		    	if(t.isInteractable() && interacts) {
		    		
		    		if(((NightStand) t).lightIntensity == 1) ((NightStand) t).lightIntensity = 0;
		    		else ((NightStand) t).lightIntensity = 1;
		    	}
		    }
		}
		
		// ENTITY INTERACTION
		for (EntityManager e : level.getEntities(x-1, y-1, x+1, y+1)) {
		    if(e instanceof NPC && ((NPC)e).isInteractible) {
		    	this.isInInteractibleRange = true;
		    	if(GameManager.dialogueSystem.inDialogue) {
		    		new DialogueBox("Hey !", ((NPC) e).getName(), GameManager.dialogueSystem);
		    		System.out.println("Interacts");
		    	}
		    }
		}
		
		// Reset Animations (AVOIDS CRASHING!)
		animSprite.resetAnimation(animSprite, walking);
		
		if(xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else walking = false;
	}
	
	// For bed implementation
	// Returns true if its neigbouring a tile.
//	public boolean canInteractWith(TileManager tile) {
//		if(intersects(x, y, x+16, y+16)) return true;
//		return false;
//	}
	
	private void controls() {
		// Controls
		if(input.up) ya -= velY;
		if(input.down) ya += velY;
		if(input.left) xa -= velX;
		if(input.right) xa += velX;
		
		if(input.space) {
			if(punch == false && stamina > 0) {
				punch = true;
				attack();
			}
		} else punch = false;
		if(attackTime > 0) attackTime--;
		
		
		if(GameManager.DEV_MODE) {
			if(input.shift) {
				speed = 0.75f;
				speed = 0.75f;
			}
		}
	}
	
	private void stamina() {
		if(stamina <= 0) {
			if(isSwimming && swimTime % 60 == 0) hurt(this, 1, dir);
		}
		
		recoverStamina();
		
		payStamina();
	}
	private void recoverStamina() {
		if(!isSwimming && stamina < 10) {
			if(tickTime % 20 == 0) stamina++;
		}
	}
	private void payStamina() {
		if(attackTime == 1) stamina--;
		
		if(isSwimming) {
			if(swimTime % 60 == 0) {
				stamina--;
			}
		}
	}
	
	private void attack() {
		attackDir = dir;
		attackTime = 5;
		
		int iX = (int)x;
		int iY = (int)y;
		
		int xRange = 10;
		int yRange = 20;
		// coordinate parameters: -- ++
		if (dir == 0) hurt(x + 5, y, x - 5, y - yRange + 4);
		if (dir == 1) hurt(x + xRange + 5, y + 10, x, y - 10);
		if (dir == 3) hurt(x, y, x - xRange - 5, y - 10);
		if (dir == 2) hurt(x, y + yRange - 2, x - 5, y);
		
		int xt = iX >> 4;
		int yt = iY >> 4;
		if (attackDir == 0) yt = (iY - 16) >> 4;
		if (attackDir == 1) xt = (iX + 16) >> 4;
		if (attackDir == 3) xt = (iX - 16) >> 4;
		if (attackDir == 2) yt = (iY + 16) >> 4;
		
		if (xt >= 0 && yt >= 0 && xt < level.width && yt < level.height) {
			level.getFront(xt, yt).hurt(level, this, xt, yt, 2, attackDir); //Tile tile, int x, int y, int damage
		}
	}
	
	private void hurt(float f, float y, float g, float h) {
		List<EntityManager> entities = level.getEntities(f, y, g, h);
		for(int i = 0; i < entities.size(); i++) {
			EntityManager e = entities.get(i);
			if(e != this && !e.isInvincible()) {
				e.hurt(this, 2, attackDir);
			}	
		}
	}
	
	public void touched(EntityManager entity) {
//		if(iE == null) return;
		entity.getItem(iE).takeItem(this);
	}
	
	// PLAYER CAN SWIM!!! (The other entities can't now, so fuck 'em)
	public boolean canSwim() {
		return true;
	}
	
	public void die() {
		super.die();
		if(health <= 0) SoundEngine.dead.play();
	}
	
	public void render(RenderManager screen) {
		int renderAxysConstX = 18;
		int renderAxysConstY = 23;
		// Pixel coordinates instead of world coordinates.
//		float px = (x - RenderMethod.xScroll) * Globals.RENDER_SCALE;
//		float py = (y - RenderMethod.yScroll) * Globals.RENDER_SCALE;
		light = new LightSource(screen);
		light.add(x-2, y-8, 20, 0.5f, 0xFFFFFFFF, 3f);
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
		if(isSwimming()) {
			if(swimTime == 1) {
				SoundEngine.splash.play();
			}
			
			// Swimming sillhouete
			if((tickTime / 32) % 2 == 0) screen.renderSprite(x - renderAxysConstX + 8, y - renderAxysConstY/3, new SpriteManager(16, 1, 10, SheetArchive.player), 1, 0);
			else screen.renderSprite(x - renderAxysConstX + 8, y - renderAxysConstY/3, new SpriteManager(16, 2, 10, SheetArchive.player), 1, 0);
			
			if(dir == 0) animSprite = upSwim;
			if(dir == 1) animSprite = rightSwim;
			if(dir == 2) animSprite = downSwim;
			if(dir == 3) animSprite = leftSwim;
			
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
		float xs = x - (float) Math.floor(x);
		float ys = y - (float) Math.floor(x);
		screen.renderMob((int)x - renderAxysConstX, (int)y - renderAxysConstY, this, sprite, 0);
		afterLayer(screen);
//		screen.renderSprite(x, y, SpriteArchive.bed, 1, 1);
	}
	
	private int punchY = 0;
	
	private void beforeLayer(RenderManager screen) {
		switch(attackDir) {
		case 0: 
			if(attackTime > 0) screen.renderSprite(x-10, y-26 + punchY, SpriteArchive.swingFx, 1, 0);
			break;
		case 1:
			if(attackTime > 0) screen.renderSprite(x+1, y-16 + punchY, SpriteArchive.swingFx_Sides, 1, 1);
			break;
		case 3:
			if(attackTime > 0) screen.renderSprite(x-20, y-16 + punchY, SpriteArchive.swingFx_Sides, 1, 0);
			break;
		}
		
	}
	
	private void afterLayer(RenderManager screen) {
		switch(attackDir) {
		case 2:
			if(attackTime > 0) screen.renderSprite(x-10, y-8 + punchY, SpriteArchive.swingFx, 1, 2);
			break;
		}
	}
	
	// Particle Effects for all type of states from the player
	private void particleEffects() {
		int iX = (int)x;
		int iY = (int)y;
		
		// When moving
		if(walking) {
			
			if(tickTime % 17 == 0 && !isSwimming) {
				for(int i = 0; i < 3; i++) {
					level.add(particle = new Particle(iX, iY));
					
					// particle colors depend on what the player is standing on.
					for(int yy = 0; yy < level.getLevel(iX >> 4, iY >> 4).sprite.height; yy++) {
						for(int xx = 0; xx < level.getLevel(iX >> 4, iY >> 4).sprite.width; xx++) {
							int color = level.getLevel(iX >> 4, iY >> 4).sprite.pixels[xx+yy*level.getLevel(iX >> 4, iY >> 4).sprite.width];
							
							/* 
							 * Each hex number = 4 bits.
							 * It is moved to the right since we don't want the zeros interfering with the data value.
							 * So now it's just 0x0000ff instead of 0xff0000. It's like having 110000 converted to just 000011
							 */
							int r = (color & 0xff0000) >> 16;
							int g = (color & 0xff00) >> 8;
							int b = (color & 0xff);
							
							int shade = (random.nextInt(5 + 30));
							
							// Reorganizes data to corresponding places.
							int shadedColor = 0xff << 24 | (r - shade) << 16 | (g - shade) << 8 | (b - shade);
							
							particle.setColor(shadedColor);
						}
					}
					
					particle.physicsEngine.calculations.gravityForce = 0.23;
					particle.life = 20 + random.nextInt(20);
				}
			}
			
			// Swimming
			if(isSwimming) {
				if(swimTime == 1) {
					for(int i = 0; i < 50; i++) {
						level.add(particle = new Particle(iX, iY));
						particle.setColor(0xff40AEE5);
						particle.physicsEngine.calculations.frictionX = 0.96;
						particle.physicsEngine.calculations.frictionY = 0.96;
						particle.life = 20 + random.nextInt(20);
					}
				}
				
				if(tickTime % 17 == 0) {
					for(int i = 0; i < 3; i++) {
						level.add(particle = new Particle(iX, iY));
						particle.setColor(0xff40AEE5);
						particle.physicsEngine.calculations.elasticity = 0;
						particle.physicsEngine.calculations.gravityForce = 0.2;
						particle.physicsEngine.calculations.frictionX = 0.96;
						particle.physicsEngine.calculations.frictionY = 0.96;
						particle.life = 10 + random.nextInt(10);
					}
				}
			}
			
		}
		
		// Hurting
		if(hurtTime > 0) {
			if(tickTime % 60 == 0) {
				for(int i = 0; i < 5; i++) {
					level.add(particle = new Particle(iX-4, iY - 1 , new SpriteManager(8, 0, 0, SheetArchive.smallFx)));
					
					particle.physicsEngine.calculations.gravityForce = 0.06;
					particle.life = 10 + random.nextInt(20);
				}
			}
		}
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