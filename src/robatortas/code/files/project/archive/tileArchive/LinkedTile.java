package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;

// This just allows for tiles that are bigger than 16x16 to be able to have extra collision and interactability outside
// the 16x space. One such case is the bed tile (16x32)
public class LinkedTile extends TileManager {

    public TileManager parent;

    public LinkedTile(TileManager parent, int id) {
        super(null, id);
        this.parent = parent;
    }

    public void render(int x, int y, LevelManager level, RenderManager screen) {}

    public boolean solid(LevelManager level, int x, int y, EntityManager e) {
        return parent.solid(level, x, y, e);
    }

    public boolean solidAt(LevelManager level, int xt, int yt, float px, float py, EntityManager e) {
        return parent.solidAt(level, xt, yt, px, py, e);
    }

    public boolean isInteractable() {
        return parent.isInteractable();
    }
}