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

public class RockTile extends TileManager {

	public RockTile(SpriteManager sprite, int id) {
		super(sprite, id);
	}

	public void render(int x, int y, LevelManager level, RenderManager screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
