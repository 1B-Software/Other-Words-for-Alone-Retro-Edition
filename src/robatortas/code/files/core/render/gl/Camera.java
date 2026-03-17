package robatortas.code.files.core.render.gl;

public class Camera {
	
	private float x, y;
	private static float[] projectionMatrix;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setCoords(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public static float[] getProjectionMatrix() {return projectionMatrix;}
	
	public void setProjection(float width, float height) {
		// Orthographic: left=0, right=width, top=0, bottom=height (Y-down like AWT)
		projectionMatrix = new float[] {
			2f / width,  0,            0, 0,
			0,          -2f / height,  0, 0,
			0,           0,           -1, 0,
			-1,          1,            0, 1
		};
	}
}