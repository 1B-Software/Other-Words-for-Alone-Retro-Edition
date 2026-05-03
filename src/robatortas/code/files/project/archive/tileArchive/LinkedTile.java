package robatortas.code.files.project.archive.tileArchive;

import robatortas.code.files.core.entities.EntityManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.core.level.tiles.TileManager;
import robatortas.code.files.core.render.RenderManager;

public class LinkedTile extends TileManager {

    public TileManager parent;
    public boolean visible;

    public LinkedTile(TileManager parent, boolean visible) {
        super(null);
        this.parent = parent;
        this.visible = visible;
    }

    // Invisible by default
    public LinkedTile(TileManager parent) {
        this(parent, false);
    }

    public void render(int x, int y, LevelManager level, RenderManager screen) {
        if (visible) parent.render(x, y, level, screen);
    }

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
