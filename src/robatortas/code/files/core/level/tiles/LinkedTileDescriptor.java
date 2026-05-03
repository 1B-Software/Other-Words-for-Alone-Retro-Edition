package robatortas.code.files.core.level.tiles;

public class LinkedTileDescriptor {
    public final int dx, dy;
    public final boolean visible;

    public LinkedTileDescriptor(int dx, int dy, boolean visible) {
        this.dx = dx;
        this.dy = dy;
        this.visible = visible;
    }

    // Invisible by default — most linked tiles just handle collision/interaction
    public LinkedTileDescriptor(int dx, int dy) {
        this(dx, dy, false);
    }
}
