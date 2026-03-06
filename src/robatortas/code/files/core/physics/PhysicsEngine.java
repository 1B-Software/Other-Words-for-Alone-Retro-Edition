package robatortas.code.files.core.physics;

import robatortas.code.files.core.physics.maths.Calculations;

public class PhysicsEngine {

	private float x, y;
	
	public Calculations calculations;
	
	public PhysicsEngine(float x, float y) {
		this.x = x;
		this.y = y;
		
		calculations = new Calculations(x, y);
	}
}
