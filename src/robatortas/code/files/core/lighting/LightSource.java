package robatortas.code.files.core.lighting;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.RenderMethod;
import robatortas.code.files.project.settings.Globals;

public class LightSource {
	
	RenderManager screen;
	
	public LightSource(RenderManager screen) {
		this.screen = screen;
	}
	
	public void add(float x, float y, int radius, float intensity, int color, float fallOff) {
		float r = ((color >> 16) & 0xFF) / 255f;
		float g = ((color >> 8) & 0xFF) / 255f;
		float b = (color & 0xFF) / 255f;
		screen.queueLight(x, y, (float) radius, intensity, r, g, b, fallOff);
	}
	
}