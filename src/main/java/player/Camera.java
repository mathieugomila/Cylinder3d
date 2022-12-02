package player;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import util.Matrix4f;
import util.Vector3f;

public class Camera {
	private float FOV = 90; // Angle in degree
	private float minDistance = 0.001f;
	private float maxDistance = 10000;

	private Vector3f position;
	private Vector3f rotation;

	private Matrix4f finalMatrix;
	private Matrix4f rotationMatrix;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public void update(Vector3f newPosition, Vector3f newOrientation, long window) {
		position = newPosition;
		rotation = newOrientation;

		// View matrix
		Matrix4f viewMatrix = Matrix4f.view(position, rotation);

		// Projection matrix
		Matrix4f projectionMatrix = Matrix4f.perspective(FOV, getAspectRatio(window), minDistance, maxDistance);

		// MatrixProduct
		finalMatrix = projectionMatrix.multiply(viewMatrix);
		rotationMatrix = projectionMatrix.multiply(Matrix4f.view(new Vector3f(0.0f, 0.0f, 0.0f), rotation));
	}

	public float getAspectRatio(long window) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer windowWidth = stack.mallocInt(1);
			IntBuffer windowHeight = stack.mallocInt(1);
			GLFW.glfwGetWindowSize(window, windowWidth, windowHeight);
			return (float) windowWidth.get(0) / (float) windowHeight.get(0);
		} catch (Exception e) {
			return 1;
		}
	}

	public Matrix4f getMVPMatrix() {
		return finalMatrix;
	}

	public Matrix4f getRotationMatrix() {
		return rotationMatrix;
	}

	public Vector3f getPosition() {
		return position;
	}

}
