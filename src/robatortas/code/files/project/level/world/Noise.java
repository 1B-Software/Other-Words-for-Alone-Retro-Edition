package robatortas.code.files.project.level.world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import robatortas.code.files.core.utils.CrashHandler;

public class Noise {

	public double values[];
	
	public static int w,h;
	
	public static Random random = new Random();
	
	private static Values mapValues;
	
	public Noise(int w, int h, int sampleSize) {
		Noise.w=w;
		Noise.h=h;
		values = new double[w*h];
		// SAMPLE SIZE: Sets the gap between each pixel that represents a sample!
		for(int y=0;y<w;y+=sampleSize) {
			for(int x=0;x<w;x+=sampleSize) {
				setSample(x,y,random.nextFloat()*2-1);
			}
		}
		int stepSize = sampleSize;
		double scale = 0.01;
		while(stepSize>1) {
			int halfStep = stepSize/2;
			for(int y=0;y<w;y+=stepSize) {
				for(int x=0;x<w;x+=stepSize) {
					double a=sample(x,y);
					double b=sample(x+stepSize,y);
					double c=sample(x,y+stepSize);
					double d=sample(x+stepSize,y+stepSize);
					
					double e=(a+b+c+d)/4.0+(random.nextFloat())*stepSize*scale;
					setSample(x+halfStep,y+halfStep,e);
				}
			}
			for(int y=0;y<w;y+=stepSize) {
				for(int x=0;x<w;x+=stepSize) {
					double a=sample(x,y);
					double b=sample(x+stepSize,y);
					double c=sample(x,y+stepSize);
					double d=sample(x+halfStep,y+halfStep);
					double e=sample(x+halfStep,y-halfStep);
					double f=sample(x-halfStep,y+halfStep);
					
					// HORIZONTAL
					double H=(a+b+d+e)/4.0+(random.nextFloat()*2-1)*stepSize*scale*1.5;
					// VERTICAL
					double g=(a+c+d+f)/4.0+(random.nextFloat()*2-1)*stepSize*scale*1.5;
					setSample(x+halfStep,y,H);
					setSample(x,y+halfStep,g);
				}
			}
			stepSize /= 2;
		}
	}
	
	public double sample(int x, int y) {
		return values[(x&(w-1))+(y&(h-1))*w];
	}
	
	public void setSample(int x, int y, double value) {
		values[(x&(w-1))+(y&(h-1))*w] = value;
	}

	public static byte[][] createMap(int w, int h) {
		Noise mnoise1 = new Noise(w, h, 16);
		Noise mnoise2 = new Noise(w, h, 16);
		Noise mnoise3 = new Noise(w, h, 16);

		Noise noise1 = new Noise(w, h, 16);
		Noise noise2 = new Noise(w, h, 16);
		
		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int i = x + y * w;
				
				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;
				
				mapValues = new Values(val, mval, map, x, y);
				
				// DISTANCES
				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if(xd < 0) xd = -xd;
				if(yd < 0) yd = -yd;
				// if xd >= yd is true, it'll return xd. else yd
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 10;
				
				mapValues.defineID(val, i);
			}
		}
		return new byte[][] {map,data};
	}
	
	public static int width = 1024;
	public static int height = 1024;
	
	public static void main(String[] args) {
		byte[] noiseMap = createMap(width, height)[0];
			
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixels = new int[width*height];
		
		for(int i=0;i<width*height;i++) {
			int result = (int)(noiseMap[i]*120+128);
			int r = result << 16;
			int g = result << 8;
			int b = result;
//			pixels[i] = r | g | b;
			mapValues.defineColor(noiseMap, pixels ,i);
		}
		
		image.setRGB(0, 0, width, height, pixels, 0, width);
		JOptionPane.showMessageDialog(null,null,"Hot Chocolate",JOptionPane.YES_NO_OPTION,new ImageIcon(image.getScaledInstance(width, height,Image.SCALE_AREA_AVERAGING)));
		
		String dirPath = "res/textures/level";
		String path = dirPath + "/level1.png";
		
		File dirFile = new File(dirPath);
		File outFile = new File(path);
		boolean exist = outFile.exists();
		if(!exist) {
			dirFile.mkdir();
			try {
				outFile.createNewFile();
				ImageIO.write(image, "png", outFile);
			} catch (IOException e) {
				new CrashHandler().handle(e, "Unable to create Hot Chocolate File", CrashHandler.ErrorType.SERIOUS);
			}
		}
		
		if(dirFile.exists()) {
			try {
				outFile.createNewFile();
				ImageIO.write(image, "png", outFile);
			} catch (IOException e) {
				new CrashHandler().handle(e, "Unable to create Hot Chocolate File", CrashHandler.ErrorType.SERIOUS);
			}
		}
		
	}
}
