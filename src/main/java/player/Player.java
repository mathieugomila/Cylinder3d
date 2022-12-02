package player;

import util.Matrix4f;
import util.Vector3f;

import org.lwjgl.glfw.GLFW;

public class Player {
	// Body transform
	private Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
	private Vector3f forward = new Vector3f(0, 0, 0);

	// Head
	private Camera camera;
	private float heightEyes = 1.6f;
	private Vector3f cameraRotation = new Vector3f(0, 0, 0); // in degree, (0, 0, 0) == facing +z
	private Vector3f headForward;

	// Movement
	private float normalSpeed = 20;
	private float sprintSpeed = 80;
	private float mouseSensibility = 0.1f;

	public Player() {
		camera = new Camera(calculEyesPosition(), cameraRotation);
	}

	public void update(long window, float timeSinceLastUpdate) {
		// Update camera
		camera.update(calculEyesPosition(), cameraRotation, window);
		calculForwardVectors();

		// Update player body and head position using Mouse/Keyboards
		move(window, timeSinceLastUpdate);
		orientateHead(window);
	}

	public void move(long window, float timeSinceLastUpdate) {
		Vector3f effectiveForward = calculEffectiveForwardVector(window, timeSinceLastUpdate);

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
			position = position.add(effectiveForward);
		}
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
			position = position.add(new Vector3f(effectiveForward.z, 0, -effectiveForward.x));
		}
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
			position = position.add(effectiveForward.multiply(-1));
		}
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
			position = position.add(new Vector3f(-effectiveForward.z, 0, effectiveForward.x));
		}

		// Up and Down
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
			position = position.add(new Vector3f(0, calculEffectiveSpeed(window, timeSinceLastUpdate), 0));
		}
		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS) {
			position = position.add(new Vector3f(0, -calculEffectiveSpeed(window, timeSinceLastUpdate), 0));
		}
	}

	public void orientateHead(long window) {
		// get mouse position
		double[] xpos = new double[1];
		double[] ypos = new double[1];
		GLFW.glfwGetCursorPos(window, xpos, ypos);

		// mouving mouse
		GLFW.glfwSetCursorPos(window, 250, 250);

		// calculating mouse movement since last update
		cameraRotation.x += mouseSensibility * (250 - ypos[0]);
		cameraRotation.y += mouseSensibility * (250 - xpos[0]);

		if (cameraRotation.x > 90)
			cameraRotation.x = 90;
		if (cameraRotation.x < -90)
			cameraRotation.x = -90;
	}

	public void calculForwardVectors() {
		forward = Matrix4f.transpose(Matrix4f.view(position, cameraRotation)).multiply(new Vector3f(0, 0, 1));
		forward = forward.multiply(-1);
		forward.y = 0;
		forward.normalize();

		headForward = Matrix4f.transpose(Matrix4f.view(position, cameraRotation)).multiply(new Vector3f(0, 0, 1));
		headForward = headForward.multiply(-1);
		headForward.normalize();
	}

	public float calculEffectiveSpeed(long window, float timeSinceLastUpdate) {
		float effectiveSpeed;

		if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS) {
			effectiveSpeed = timeSinceLastUpdate * sprintSpeed;
		} else {
			effectiveSpeed = timeSinceLastUpdate * normalSpeed;
		}

		return effectiveSpeed;
	}

	public Vector3f calculEffectiveForwardVector(long window, float timeSinceLastUpdate) {
		float effectiveSpeed = calculEffectiveSpeed(window, timeSinceLastUpdate);
		Vector3f effectiveForward = new Vector3f(forward).multiply(effectiveSpeed);
		return effectiveForward;
	}

	public Vector3f calculEyesPosition() {
		return position.add(new Vector3f(0, heightEyes, 0));
	}

	public Camera getCamera() {
		return camera;
	}
}
