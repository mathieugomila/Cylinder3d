package devTools;

import org.lwjgl.glfw.GLFW;

public class ProgramTime {
	private float timeSinceBeginning = 0;
	private float timeSinceLastUpdate = 0;

	private float currentTime;

	public ProgramTime() {
		currentTime = (float) GLFW.glfwGetTime();
	}

	public float update() {
		float lastTime = currentTime;
		currentTime = (float) GLFW.glfwGetTime();

		timeSinceLastUpdate = currentTime - lastTime;
		timeSinceBeginning += timeSinceLastUpdate;

		return timeSinceLastUpdate;
	}

	public float getTimeSinceBeginning() {
		return timeSinceBeginning;
	}

	public float getTimeSinceLastUpdate() {
		return timeSinceLastUpdate;
	}
}
