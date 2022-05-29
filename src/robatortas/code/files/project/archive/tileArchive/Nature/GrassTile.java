/* This software and it's files are property of Prolusio Interactive
 * Any unauthorized use of this software is a violation against the rights of this work.
 * It is prohibited the use of code, textures, audio files or any kind of file related to this software outside of this game's use.
 * Changing the game's code and/or adding modifications is permitted.
 * It is strictly prohibited to resell or plagiarize this software and any of it's files.
 * Contact us at prolusiointeractive@gmail.com for more information on the legal rights
 * about this software.
 * WARNING:
 * DO NOT CHANGE THESE FILES IF YOU DON'T KNOW WHAT YOU'RE DOING
 * IT MAY RESULT IN THE UNINTENDED MALFUNCTION OF THIS SOFTWARE 
 * OR MAY DAMAGE PERFORMANCE.
 * 
 * MADE WITH THE PROLUSIO 2D ENGINE
 * 
 * -Robatortas & the Prolusio Team.
 */

package robatortas.code.files.project.archive.tileArchive.Nature;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;
import robatortas.code.files.project.archive.SpriteArchive;

public class GrassTile extends TileManager {
	
	public GrassTile(SpriteManager sprite, int id) {
		super(sprite, id);
		
		super.seamsToRock = true;
	}
	
	// Efficient connected textures rendering!
	// Connects when for example down = there is water one tile down the grass tile!!!
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		// << equals multiply because its a binary operation
		
		boolean up = level.getLevel(x, y - 1).seamsToGrass;
		boolean down = level.getLevel(x, y + 1).seamsToGrass;
		boolean left = level.getLevel(x - 1, y).seamsToGrass;
		boolean right = level.getLevel(x + 1, y).seamsToGrass;
		
		boolean ur = level.getLevel(x + 1, y - 1).seamsToGrass;
		boolean dr = level.getLevel(x + 1, y + 1).seamsToGrass;
		boolean ul = level.getLevel(x - 1, y - 1).seamsToGrass;
		boolean dl = level.getLevel(x - 1, y + 1).seamsToGrass;
		
		// Only renders when necessary
		// TODO: THIS BULSHITT!!!
		if(up || down || left || right) {
			
			if(up) screen.renderSprite(x << 4, y << 4, upSprite, 0);
			
			if(down) screen.renderSprite(x << 4, y << 4, downSprite, 0);
			if(left) screen.renderSprite(x << 4, y << 4, leftSprite, 0);
			if(right) screen.renderSprite(x << 4, y << 4, rightSprite, 0);
			
			if(down && left) {
				screen.renderSprite(x << 4, y << 4, downSprite, 0);
				if(dl) screen.renderSprite(x << 4, y << 4, dlSprite, 0);
			}
			
		}
		else {
			screen.renderTile(x << 4, y << 4, this);
		}
	}
	
	SpriteSheetManager ground = new SpriteSheetManager("/textures/spritesheet/nature/ground.png", 208, 96);
	
	private SpriteManager upSprite = new SpriteManager(16, 1, 0, ground);
	private SpriteManager downSprite = new SpriteManager(16, 1, 2, ground);
	private SpriteManager leftSprite = new SpriteManager(16, 0, 1, ground);
	private SpriteManager rightSprite = new SpriteManager(16, 2, 1, ground);

	private SpriteManager urSprite = new SpriteManager(16, 2, 2, ground);
	private SpriteManager dlSprite = new SpriteManager(16, 0, 2, ground);
}