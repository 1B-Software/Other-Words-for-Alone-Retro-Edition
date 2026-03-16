package robatortas.code.files.core.render.gl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import robatortas.code.files.project.settings.Globals;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLWindow {

	private long window;
	private int windowWidth, windowHeight;
	
	public static boolean[] keys = new boolean[512];
	public static boolean released = false;

	public static int mouseX, mouseY;
	public static boolean mouseLeft, mouseRight, mouseMiddle;
	public static int mouseButton = 0;

	public GLWindow() {
		windowWidth = Globals.WIDTH * Globals.SCALE;
		windowHeight = Globals.HEIGHT * Globals.SCALE;
	}

	public void create() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		window = glfwCreateWindow(windowWidth, windowHeight, Globals.TITLE, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Center window
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (vidmode != null) {
			glfwSetWindowPos(window,
				(vidmode.width() - windowWidth) / 2,
				(vidmode.height() - windowHeight) / 2);
		}

		// Keyboard callback
		glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
			if (key < 0 || key >= keys.length) return;
			if (action == GLFW_PRESS) {
				keys[key] = true;
				released = false;
			} else if (action == GLFW_RELEASE) {
				keys[key] = false;
				released = true;
			}
		});

		// Mouse position callback
		glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
			mouseX = (int) xpos;
			mouseY = (int) ypos;
		});

		// Mouse button callback
		glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
			mouseButton = button;
			if (action == GLFW_PRESS) {
				if (button == GLFW_MOUSE_BUTTON_LEFT) mouseLeft = true;
				if (button == GLFW_MOUSE_BUTTON_RIGHT) mouseRight = true;
				if (button == GLFW_MOUSE_BUTTON_MIDDLE) mouseMiddle = true;
			} else if (action == GLFW_RELEASE) {
				if (button == GLFW_MOUSE_BUTTON_LEFT) mouseLeft = false;
				if (button == GLFW_MOUSE_BUTTON_RIGHT) mouseRight = false;
				if (button == GLFW_MOUSE_BUTTON_MIDDLE) mouseMiddle = false;
			}
		});

		// Framebuffer resize callback
		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			windowWidth = w;
			windowHeight = h;
			updateViewport();
		});

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); // vsync
		glfwShowWindow(window);

		GL.createCapabilities();

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		updateViewport();
	}

	public void updateViewport() {
		float targetAspect = (float) Globals.WIDTH / Globals.HEIGHT;
		float windowAspect = (float) windowWidth / windowHeight;

		int vpW, vpH, vpX, vpY;
		if (windowAspect > targetAspect) {
			vpH = windowHeight;
			vpW = (int) (vpH * targetAspect);
			vpX = (windowWidth - vpW) / 2;
			vpY = 0;
		} else {
			vpW = windowWidth;
			vpH = (int) (vpW / targetAspect);
			vpX = 0;
			vpY = (windowHeight - vpH) / 2;
		}

		glViewport(vpX, vpY, vpW, vpH);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	public void swapBuffers() {
		glfwSwapBuffers(window);
	}

	public void pollEvents() {
		glfwPollEvents();
	}

	public void destroy() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		System.exit(0);
		GLFWErrorCallback ec = glfwSetErrorCallback(null);
		if (ec != null) ec.free();
	}

	public long getHandle() {
		return window;
	}

	public int getWidth() {
		return windowWidth;
	}

	public int getHeight() {
		return windowHeight;
	}
}
