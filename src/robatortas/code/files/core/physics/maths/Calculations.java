package robatortas.code.files.core.physics.maths;

import java.util.Random;

public class Calculations {
	
	public double x0, y0, z0;
	public double x1, y1, z1;
	
	public double gravityForce, elasticity, frictionX, frictionY;
	
	private Random random = new Random();
	
	public Calculations(int x, int y) {
		x0 = x;
		y0 = y;
		settings();
	}
	
	public void physics() {
		this.x0 += x1;
		this.y0 += y1;
		this.z0 += z1;
		
		if(z0 < 0) {
			z0 = 0;
			x1 *= frictionX;
			y1 *= frictionY;
			z1 *= -elasticity;
		}
		
		z1 -= gravityForce;
	}
	
	public void settings() {
		x1 = random.nextGaussian() * 0.3; // Defualt Value = 0.3
		y1 = random.nextGaussian() * 0.2; // Defualt Value = 0.2
		z1 = random.nextFloat() * 0.7 + 1; // Defualt Value = 0.7 + 1
		
		gravityForce = 0.09; // Defualt Value = 0.14
		elasticity = 0.5; // Defualt Value = 0.7
		
		frictionX = 0.4; // Default Value = 0.4
		frictionY = 0.6; // Default Value = 0.7
	}
}
