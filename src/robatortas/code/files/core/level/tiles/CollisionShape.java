package robatortas.code.files.core.level.tiles;

public class CollisionShape {

    public int x, y, w, h;

    public CollisionShape(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public static final CollisionShape FULL =
        new CollisionShape(0,0,16,16);

    public static final CollisionShape VERTICAL =
        new CollisionShape(6,1,4,16);

    public static final CollisionShape TOP =
            new CollisionShape(0,0,16,16);

    public static final CollisionShape HORIZONTAL =
        new CollisionShape(0,6,16,4);
}