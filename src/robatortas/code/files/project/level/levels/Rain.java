package robatortas.code.files.project.level.levels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import robatortas.code.files.core.render.RenderManager;
import robatortas.code.files.core.render.gl.ShaderProgram;
import robatortas.code.files.project.GameManager;
import robatortas.code.files.project.entities.Particle;
import robatortas.code.files.project.level.Level;
import robatortas.code.files.project.settings.Globals;

public class Rain {

	Random random;
	private Level level;
	private float wind = 0;
	
	ArrayList<Particle> particles = new ArrayList<>();
	
	int density = 0;
	
	public int rainColor = 0xFF9595ED;
	
	float r,g,b,a;
	
	public Rain(int density, float wind, Level level) {
		this.level = level;
		this.wind = wind;
		this.density = density;
		r = ((rainColor >> 16) & 0xFF) / 255f;
		g = ((rainColor >> 8) & 0xFF) / 255f;
		b = (rainColor & 0xFF) / 255f;
		a = ((rainColor >> 24) & 0xFF) / 255f;
	}
	
	public void update() {
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
	}
	
	private float spawnAccumulator = 0;
	private float spawnRate = 0.016f; // spawn every ~16ms (60 times per second)
	private float timeAccumulator = 0;
	private float zz0;
	
	public void render(float x, float y, RenderManager screen) {
		screen.getBatch().flush();
		screen.getBatch().beginWith(GameManager.rainShader);
		GameManager.rainShader.setUniform4f("rColor", r, g, b, a);
		GameManager.rainShader.setUniform1f("uWind", wind);
		timeAccumulator += GameManager.renderDt;
		GameManager.rainShader.setUniform1f("uTime", timeAccumulator);
		GameManager.rainShader.setUniform1f("uZ0", zz0);
		GameManager.rainShader.setUniform2f("uResolution", screen.width, screen.height);

		GameManager.rainShader.setUniform2f("uCamera", (float)Math.floor(GameManager.camera.getX() - Globals.WIDTH / 2f), (float)Math.floor(GameManager.camera.getY() - Globals.HEIGHT / 2f));

		spawnAccumulator += GameManager.renderDt;

		while (spawnAccumulator >= spawnRate) {
			for(int i = 0; i < this.density; i++) {
				random = new Random();
				Particle particle = new Particle((float)random.nextInt(screen.width+124), screen.yOffset+screen.height/3 + (float)random.nextInt(screen.height) - 32);
				particle.level = level.levelManager;
				particle.setColor(rainColor);
				particle.height = 8;
				particle.width = 1;
				particle.life = 60;
				particle.physicsEngine.calculations.gravityForce = 0.53;
				particle.physicsEngine.calculations.z0 = 400;
				zz0 = (float) particle.physicsEngine.calculations.z0;
				particle.physicsEngine.calculations.elasticity = 0.2;
				particle.physicsEngine.calculations.x1 = -wind;
				particles.add(particle);
			}
			
			for(int i = 0; i < this.density; i++) {
				random = new Random();
				Particle particle = new Particle(screen.xOffset+(float)random.nextInt(screen.width+124), screen.yOffset+screen.height/3 + (float)random.nextInt(screen.height) - 32);
				particle.level = level.levelManager;
				rainColor = 0xffffffff;
				particle.setColor(rainColor);
				particle.height = 1;
				particle.width = 1;
				particle.life = 60;
				particle.physicsEngine.calculations.gravityForce = 0.53;
				particle.physicsEngine.calculations.z0 = 10;
				zz0 = (float) particle.physicsEngine.calculations.z0;
				particle.physicsEngine.calculations.elasticity = 0.2;
				particle.physicsEngine.calculations.x1 = -wind;
				particles.add(particle);
			}
			spawnAccumulator -= spawnRate;
		}

		for(int i = particles.size() - 1; i >= 0; i--) {
			Particle particle = particles.get(i);
		    particle.render(screen);
		    if (particle.isRemoved()) particles.remove(i);
		}
		screen.getBatch().flush();
		screen.getBatch().beginWith(GameManager.spriteShader);
	}
}