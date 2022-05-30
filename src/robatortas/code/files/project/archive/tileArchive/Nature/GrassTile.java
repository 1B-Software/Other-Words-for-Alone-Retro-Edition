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
import robatortas.code.files.core.level.tiles.ConnectTile;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.SpriteManager;
import robatortas.code.files.core.render.SpriteSheetManager;

public class GrassTile extends TileManager {
	
	public GrassTile(SpriteManager sprite, int id) {
		super(sprite, id);
		
		super.seamsToRock = true;
	}
	
	ConnectTile connect;
	
	// Efficient connected textures rendering!
	// Connects when for example down = there is water one tile down the grass tile!!!
	public void render(int x, int y, LevelManager level, RenderManager screen) {
		// << equals multiply because its a binary operation
		
		int xt = x << 4;
		int yt = y << 4;
		
		connect = new ConnectTile(screen, level, xt, yt);
		
		// Only renders when necessary
		// TODO: THIS BULSHITT!!! (I can't think)
		connect.full(upSprite, downSprite, leftSprite, rightSprite);
		connect.sides(ulSprite, urSprite, drSprite, dlSprite);
		
//		screen.renderTile(x << 4, y << 4, this);
	}
	
	SpriteSheetManager ground = new SpriteSheetManager("/textures/spritesheet/nature/ground.png", 208, 96);
	
	private SpriteManager upSprite = new SpriteManager(16, 1, 0, ground);
	private SpriteManager downSprite = new SpriteManager(16, 1, 2, ground);
	private SpriteManager leftSprite = new SpriteManager(16, 0, 1, ground);
	private SpriteManager rightSprite = new SpriteManager(16, 2, 1, ground);

	private SpriteManager urSprite = new SpriteManager(16, 2, 2, ground);
	private SpriteManager ulSprite = new SpriteManager(16, 2, 2, ground);
	private SpriteManager drSprite = new SpriteManager(16, 2, 2, ground);
	private SpriteManager dlSprite = new SpriteManager(16, 0, 2, ground);
}