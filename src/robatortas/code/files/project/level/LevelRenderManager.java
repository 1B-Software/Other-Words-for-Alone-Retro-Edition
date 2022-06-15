package robatortas.code.files.project.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.render.RenderManager;

public class LevelRenderManager {
	
	private RenderManager screen;
	private LevelManager level;
	
	public LevelRenderManager(LevelManager level, RenderManager screen) {
		this.level = level;
		this.screen = screen;
	}
	
	// pinPoint settings
	public int x0,y0,x1,y1; 
	public void pinPoints(int xScroll, int yScroll) {
		screen.setOffset(xScroll, yScroll);
		x0 = (xScroll - 6) >> 4;
		x1 = (xScroll + screen.width + 16) >> 4;
		y0 = (yScroll - 6) >> 4;
		y1 = (yScroll + screen.height + 16) >> 4;
	}
	
	// Level Rendering goes here!
	public void render(LevelManager level) {
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
		
		renderEntities();
		
		for(int yy = y0; yy < y1; yy++) {
			for(int xx = x0; xx < x1; xx++) {
				if(xx   < 0 || yy < 0 || xx >= level.width|| yy >= level.height) continue;
				level.getFront(xx, yy).render(xx, yy, level, screen);
			}
		}
	}

	
	private List<EntityManager> entityRowSprites = new ArrayList<EntityManager>();
	public void renderEntities() {
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				if(x < 0 || y < 0 || x >= level.width|| y >= level.height) continue;
				entityRowSprites.addAll(level.entitiesInTiles[x+y*level.width]);
				if(entityRowSprites.size() > 0) {
					sortAndRender(screen, entityRowSprites);
				}
				entityRowSprites.clear();
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
	
	public void sortAndRender(RenderManager screen, List<EntityManager> list) {
		Collections.sort(list, comparator);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).render(screen);
		}
	}
}