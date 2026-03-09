package robatortas.code.files.project.archive.tileArchive.Interior;

import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.CollisionShape;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.SpriteManager;

public class WallType extends TileManager {

	
	public WallType(SpriteManager sprite, int id) {
		super(sprite, id);
	}

	protected int getMask(LevelManager level, int x, int y) {

		boolean u = level.getFront(x, y - 1) instanceof WallType;
	    boolean d = level.getFront(x, y + 1) instanceof WallType;
	    boolean l = level.getFront(x - 1, y) instanceof WallType;
	    boolean r = level.getFront(x + 1, y) instanceof WallType;

	    int mask = 0;

	    if(u) mask |= 1;
	    if(r) mask |= 2;
	    if(d) mask |= 4;
	    if(l) mask |= 8;

	    return mask;
	}
	
	protected CollisionShape getShape(LevelManager level, int x, int y) {

	    int mask = getMask(level, x, y);

	    switch(mask) {
	    	case 1: // up
	    		return CollisionShape.TOP;
	        case 5:  // up + down
	            return CollisionShape.VERTICAL;
	        case 10: // left + right
	            return CollisionShape.HORIZONTAL;
	        default:
	            return CollisionShape.FULL;
	    }
	}
}