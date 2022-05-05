package robatortas.code.files.core.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IOUtils {
	
	private static String errorMsg = "Unable to load level file parameters.";
	
	public IOUtils() {
		
	}
	
	public void createBufferedImage(int width, int height, String path, int[] data) {
		try {
			BufferedImage image = ImageIO.read(IOUtils.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			data = new int[w*h];
			image.getRGB(0, 0, w, h, data, 0, w);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println(errorMsg);
		}
	}
}
