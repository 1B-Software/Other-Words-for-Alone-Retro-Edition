package robatortas.code.files.project.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class LevelRenderManager {

	private RenderManager screen;
	private LevelManager level;
	private Random random = new Random();

	public LevelRenderManager(LevelManager level, RenderManager screen) {
		this.level = level;
		this.screen = screen;
	}

	// pinPoint settings
	public int x0,y0,x1,y1;
	public void pinPoints(float xScroll, float yScroll) {
		screen.setOffset(xScroll, yScroll);
		x0 = (Math.round(xScroll) - 6) >> 4;
		x1 = (Math.round(xScroll)+ screen.width + 16) >> 4;
		y0 = (Math.round(yScroll)- 6) >> 4;
		y1 = (Math.round(yScroll) + screen.height + 32) >> 4;
	}
	
	public List<TileManager> getTile(int id) {
		List<TileManager> lT = new ArrayList<>();
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				TileManager lt = level.getLevel(x, y);
				TileManager pt = level.getPost(x, y);
				TileManager ft = level.getFront(x, y);
				if(ft != TileArchive.voidTile || pt != TileArchive.voidTile || lt != TileArchive.voidTile ) {
					lT.add(lt);
					lT.add(pt);
					lT.add(ft);
				}
			}
		}
		List<TileManager> resultList = new ArrayList<>();
		for(int i = 0; i < lT.size(); i++) {
			if(lT.get(i).id == id) resultList.add(lT.get(i));
		}
		return resultList;
	}

	// Holds a front tile's grid position and tile reference for Y-sorted rendering
	private static class FrontTileEntry {
		final int x, y;
		final TileManager tile;
		FrontTileEntry(int x, int y, TileManager tile) {
			this.x = x; this.y = y; this.tile = tile;
		}
	}

	// Level Rendering goes here!
	public void render(LevelManager level) {
		/* These act like layers, stack em up, the lower layers are the ones rendered last (meaning they are on top)
		 * and the top layer is the one rendering on the bottom of the game.
		 * It's just following basic programming logic.
		 */
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= level.width|| y >= level.height) continue;
				level.getLevel(x, y).render(x, y, level, screen);
			}
		}

		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= level.width|| y >= level.height) continue;
				level.getPost(x, y).render(x, y, level, screen);
			}
		}

		// Collect all front tiles in the viewport
		List<FrontTileEntry> frontTiles = new ArrayList<FrontTileEntry>();
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= level.width || y >= level.height) continue;
				TileManager ft = level.getFront(x, y);
				if(ft != TileArchive.voidTile) {
					frontTiles.add(new FrontTileEntry(x, y, ft));
				}
			}
		}

		// Collect all entities in the viewport (each entity lives in exactly one tile cell)
		List<EntityManager> allEntities = new ArrayList<EntityManager>();
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= level.width || y >= level.height) continue;
				allEntities.addAll(level.entitiesInTiles[x + y * level.width]);
			}
		}

		Collections.sort(allEntities, comparator);
		Collections.sort(frontTiles, frontTileComparator);

		// Render entities and front tiles by Y so they depth-sort correctly
		int ei = 0, fi = 0;
		while(ei < allEntities.size() || fi < frontTiles.size()) {
			int entityY = (int) ((ei < allEntities.size()) ? allEntities.get(ei).y           : Integer.MAX_VALUE);
			int tileY   = (fi < frontTiles.size())  ? (frontTiles.get(fi).y + 1) * 16 : Integer.MAX_VALUE;
			
			if(entityY <= tileY) {
				allEntities.get(ei++).render(screen);
			} else {
				FrontTileEntry ft = frontTiles.get(fi++);
				ft.tile.render(ft.x, ft.y, level, screen);
			}
		}
	}

	private Comparator<EntityManager> comparator = new Comparator<EntityManager>() {
		public int compare(EntityManager e0, EntityManager e1) {
			if(e0.y > e1.y) return +1;
			if(e0.y < e1.y) return -1;
			return 0;
		}
	};

	private Comparator<FrontTileEntry> frontTileComparator = new Comparator<FrontTileEntry>() {
		public int compare(FrontTileEntry a, FrontTileEntry b) {
			int ay = (a.y + 1) * 16;
			int by = (b.y + 1) * 16;
			if(ay > by) return +1;
			if(ay < by) return -1;
			return 0;
		}
	};

	public void sortAndRender(RenderManager screen, List<EntityManager> list) {
		Collections.sort(list, comparator);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).render(screen);
		}
	}
}
