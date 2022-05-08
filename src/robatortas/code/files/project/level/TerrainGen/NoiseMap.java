package robatortas.code.files.project.level.TerrainGen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import robatortas.code.files.project.archive.SpriteArchive;
import robatortas.code.files.project.archive.tileArchive.TileArchive;

public class NoiseMap {
	
	private static final Random random = new Random();
	public double[] values;
	private int w, h;
	
	/*
	 * ALL ARE SAMPLE CHANNELS
	 * A
	 * B
	 * C
	 * D
	 * E
	 * F
	 */
	
	public NoiseMap(int w, int h, int sampleSize) {
		this.w = w;
		this.h = h;

		values = new double[w * h];

		for (int y = 0; y < w; y += sampleSize) {
			for (int x = 0; x < w; x += sampleSize) {
				setSample(x, y, random.nextFloat() * 2 - 1);
			}
		}

		int stepSize = sampleSize;
		double scale = 0.009;
		double scaleMod = 1;
		
		while (stepSize > 1) {
			int halfStep = stepSize/2;
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (random.nextFloat()) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
			scale *= (scaleMod + 0.8);
			scaleMod *= 0.3;
			System.out.println(stepSize);
		}
	}
	
	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * width];
	}
	
	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * width] = value;
	}
	
	public static byte[][] createMap(int w, int h) {
		NoiseMap mnoise1 = new NoiseMap(w, h, 16);
		NoiseMap mnoise2 = new NoiseMap(w, h, 16);
		NoiseMap mnoise3 = new NoiseMap(w, h, 16);

		NoiseMap noise1 = new NoiseMap(w, h, 16);
		NoiseMap noise2 = new NoiseMap(w, h, 64);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;
				
				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				// if xd >= yd is true, it'll return xd. else yd
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 10;

				if (val < -0.2) {
					map[i] = (byte) TileArchive.water.id;
				} else if (val > 1.9 && mval < -1.0) {
					map[i] = (byte) TileArchive.solidRock.id;
				}else {
					map[i] = (byte) TileArchive.grass.id;
				}
			}
		}
		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			for (int j = 0; j < 100; j++) {
				int xx = x + random.nextInt(20) - random.nextInt(15);
				int yy = y + random.nextInt(10) - random.nextInt(15);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] == TileArchive.grass.id) {
//						map[xx + yy * w] = TileArchive.treeTile.id;
					}
				}
			}
		}
		
		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			for (int j = 0; j < 60; j++) {
				int xx = x + random.nextInt(25) - random.nextInt(30);
				int yy = y + random.nextInt(10) - random.nextInt(30);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] == TileArchive.grass.id) {
						map[xx + yy * w] = (byte) TileArchive.flowerRed.id;
					}
				}
			}
		}
		return new byte[][] {map,data};
	}
	
	public static int width=1024*1;
	public static int height=1024*1;
	public static int[] pixels = new int[width*height];
	
	public static void main(Class<String[]> class1) { //Class<String[]> class1
		System.out.println("Generating Noise Map...");
		
		byte[] map = createMap(width, height)[0];
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int i = x + y * width;
				if (map[i] == TileArchive.water.id) pixels[i] = SpriteArchive.col_water;
				if (map[i] == TileArchive.grass.id) pixels[i] = SpriteArchive.col_grass;
				if (map[i] == TileArchive.solidRock.id) pixels[i] = SpriteArchive.col_solidRock;
//				if (map[i] == TileArchive.treeTile.id) pixels[i] = SpriteArchive.col_tree;
				if (map[i] == TileArchive.flowerRed.id) pixels[i] = SpriteArchive.col_flowerRed;
//				System.out.println(map);
			}
		}
		
		try {
			image.setRGB(0, 0, width, height, pixels, 0, width);
			JOptionPane.showMessageDialog(null, null, "Generate", JOptionPane.YES_NO_OPTION, new ImageIcon(image.getScaledInstance(width/2, height/2, Image.SCALE_AREA_AVERAGING)));
		} catch (Exception e) {
			System.err.println("Noise Map unsuccessful :(");
		}
		
		String path = "res/textures/level/level/level.png";
		String dir = "res/textures/level/level";
		
		try {
			File dirPath = new File(dir);
			File outputFile = new File(path);
			boolean exist = outputFile.exists();
			if(!exist) {
				dirPath.mkdir();
				outputFile.createNewFile();
			}
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}