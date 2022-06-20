package robatortas.code.files.core.physics;

import robatortas.code.files.core.physics.maths.Calculations;

public class PhysicsEngine {

	private int x, y;
	
	public Calculations calculations;
	
	public PhysicsEngine(int x, int y) {
		this.x = x;
		this.y = y;
		
		calculations = new Calculations(x, y);
	}
}
