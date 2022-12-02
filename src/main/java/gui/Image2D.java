package gui;

import org.lwjgl.opengl.GL33;

import shaders.Shader;
import util.Matrix4f;
import util.Vector3f;

public class Image2D {
	private Shader shader;
	protected int vaoID;
	protected int[] vboID = new int[1];

	public Image2D(String shaderName) {
		shader = new Shader(shaderName);

		// Create VAO
		vaoID = GL33.glGenVertexArrays();
		bindVAO();

		// Create VBO
		GL33.glGenBuffers(vboID);

		generateBuffers();
	}

	public void generateBuffers() {
		float[] vertices = new float[] {
				-1.0f, -1.0f,
				-1.0f, 1.0f,
				1.0f, -1.0f,
				1.0f, -1.0f,
				-1.0f, 1.0f,
				1.0f, 1.0f,
		};

		// bind VAO
		bindVAO();

		// Fill VBO n�1
		bindVBO(0);
		GL33.glEnableVertexAttribArray(0);
		GL33.glBufferData(GL33.GL_ARRAY_BUFFER, vertices, GL33.GL_STATIC_DRAW);
		GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 0, 0);
		unbindVBO();

		// Unbind everything
		unbindVAO();
	}

	public void draw(Vector3f position, Matrix4f rotationMatrix) {
		shader.start();
		shader.setUniformFloat3Value("cameraPosition", position);
		shader.setUniformMatrix4fValue("rotationMatrix", rotationMatrix);
		GL33.glEnable(GL33.GL_BLEND);
		GL33.glBlendFunc(GL33.GL_SRC_ALPHA, GL33.GL_ONE_MINUS_SRC_ALPHA);
		bindVAO();
		GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6);
		unbindVAO();
		GL33.glDisable(GL33.GL_BLEND);
		shader.stop();
	}

	public void bindVAO() {
		GL33.glBindVertexArray(vaoID);
	}

	public void unbindVAO() {
		GL33.glBindVertexArray(0);
	}

	public void bindVBO(int index) {
		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboID[index]);
	}

	public void unbindVBO() {
		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
	}
}
