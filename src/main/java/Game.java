
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import devTools.ProgramTime;
import gui.Image2D;
import player.Player;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Game {

	// The window handle
	private long window;

	// The window parameters
	private int WINDOW_WIDTH = 500;
	private int WINDOW_HEIGHT = 500;
	private String WINDOW_NAME = "Cylinder3D";
	private boolean isFullScreen = false;

	// Graphical options
	private boolean vSyncEnable = false;

	private Player player;
	private ProgramTime programTime;

	private Image2D image2d;

	public void initialize() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will not be resizable

		// Using OpenGL 3.3
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

		// Create the window
		if (!isFullScreen) {
			window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_NAME, NULL, NULL);
			if (window == NULL)
				throw new RuntimeException("Failed to create the GLFW window");
		} else {
			var primary = GLFW.glfwGetPrimaryMonitor();
			var videoMode = GLFW.glfwGetVideoMode(primary);
			WINDOW_WIDTH = videoMode.width();
			WINDOW_HEIGHT = videoMode.height();
			window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_NAME, primary, NULL);
			if (window == NULL)
				throw new RuntimeException("Failed to create the GLFW window");
		}

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		// Enable v-sync ?
		if (vSyncEnable) {
			glfwSwapInterval(1);
		} else {
			glfwSwapInterval(0);
		}

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loadContent() {

		programTime = new ProgramTime();
		player = new Player();

		image2d = new Image2D("basic");

		return;
	}

	private boolean update() {
		GL.createCapabilities();
		glfwPollEvents();

		// Time update
		float timeSinceLastUpdate = programTime.update();

		// Update player (position, head rotation, etc)
		player.update(window, timeSinceLastUpdate);

		// Check if window has to be closed
		if (glfwWindowShouldClose(window) || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
			return false;
		}

		return true;
	}

	private void draw() {
		// Set the clear color
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glFrontFace(GL_CW);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		image2d.draw(player.getCamera().getPosition(), player.getCamera().getRotationMatrix());

		glfwSwapBuffers(window); // swap the color buffers
	}

	public void terminate() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		boolean gameShouldContinue = true;
		Game game = new Game();

		// Initializing program and loading some contents
		game.initialize();
		game.loadContent();

		// Updating game and drawing elements
		while (gameShouldContinue) {
			// Updating and drawing game
			gameShouldContinue = game.update();
			game.draw();

			// If game has to stop : break the while loop
			if (!gameShouldContinue)
				break;
		}
		game.terminate();
	}
}
