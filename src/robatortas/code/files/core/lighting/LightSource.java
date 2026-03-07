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
		x = (x - RenderMethod.xScroll) * Globals.RENDER_SCALE;
		y = (y - RenderMethod.yScroll) * Globals.RENDER_SCALE;
		
		for (int ly = -radius; ly <= radius; ly++) {
		    for (int lx = -radius; lx <= radius; lx++) {
		        float dist = (float) Math.sqrt(lx*lx + ly*ly);
		        if (dist >= radius) continue;
		        float b = (float) Math.pow(1.0f - (dist / radius), fallOff);
		        int px = (int) (x + lx), py = (int) (y + ly);
		        float cr = ((color >> 16) & 0xFF) / 255f;
		        float cg = ((color >> 8)  & 0xFF) / 255f;
		        float cb = ( color & 0xFF) / 255f;
		        if (px < 0 || py < 0 || px >= screen.pixelWidth || py >= screen.pixelHeight) continue;
		        screen.lightmapR[py * screen.pixelWidth + px] = Math.min(1f, screen.lightmapR[py * screen.pixelWidth + px] + b * intensity * cr);
		        screen.lightmapG[py * screen.pixelWidth + px] = Math.min(1f, screen.lightmapG[py * screen.pixelWidth + px] + b * intensity * cg);
		        screen.lightmapB[py * screen.pixelWidth + px] = Math.min(1f, screen.lightmapB[py * screen.pixelWidth + px] + b * intensity * cb);		        
//		        screen.lightmap[py * screen.pixelWidth + px] = color;
//		        screen.lightmap[py * screen.pixelWidth + px] = Math.min(1.0f, screen.lightmap[py * screen.pixelWidth + px] + b * intensity);
		    }
		}
	}
	
}