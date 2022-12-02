package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import util.Matrix4f;
import util.Vector2f;
import util.Vector3f;
import util.Vector4f;

public class Shader {
	private int programID;

	public Shader(String shaderName) {
		int vertexShaderID;
		int fragmentShaderID;

		String vertexFile = shaderName + "_vs.glsl";
		String fragmentFile = shaderName + "_fs.glsl";

		// Reading vertex file
		System.out.println("Opening file " + vertexFile);
		vertexShaderID = loadShader(vertexFile, GL33.GL_VERTEX_SHADER);

		// Reading fragment file
		System.out.println("Opening file " + fragmentFile);
		fragmentShaderID = loadShader(fragmentFile, GL33.GL_FRAGMENT_SHADER);

		// Creating program that combines vertex and fragment programs
		programID = GL33.glCreateProgram();
		GL33.glAttachShader(programID, vertexShaderID);
		GL33.glAttachShader(programID, fragmentShaderID);
		GL33.glLinkProgram(programID);
		GL33.glValidateProgram(programID);

		System.out.println(
				"Successfully compiled " + shaderName + " vertex and fragment shader (ID = " + programID + ")");

		GL33.glDeleteShader(vertexShaderID);
		GL33.glDeleteShader(fragmentShaderID);
	}

	public int loadShader(String programFile, int typeShader) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			BufferedReader reader = new BufferedReader(
					new FileReader(classLoader.getResource("shaders/" + programFile).getFile()));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't open program file " + programFile);
			System.exit(-1);
		}
		System.out.println("Shader file " + programFile + " was successfully read");

		// Create program and set source string
		int shaderID = GL33.glCreateShader(typeShader);
		GL33.glShaderSource(shaderID, shaderSource);
		GL33.glCompileShader(shaderID);

		// Check if there is no error in the shader
		if (GL33.glGetShaderi(shaderID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
			System.out.println("Could not compile shader!");
			System.out.println(GL33.glGetShaderInfoLog(shaderID, 500));
			System.exit(-1);
		}
		// return ID
		return shaderID;
	}

	//////////
	public void setUniformMatrix4fValue(String name, Matrix4f value) {
		// get uniform ID
		int location = GL33.glGetUniformLocation(programID, name);

		// Send matrix value
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			value.toBuffer(buffer);
			GL33.glUniformMatrix4fv(location, false, buffer);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't send matrix " + name + " to shader program (ID = " + programID + ")");
			return;
		}
	}

	public void setUniformFloatValue(String name, float value) {
		// get uniform ID
		int location = GL33.glGetUniformLocation(programID, name);
		if (location == -1) {
			System.out.println("Can't send float " + name + " to shader program (ID = " + programID + ")");
			return;
		}
		// Send matrix value
		GL33.glUniform1f(location, value);
	}

	public void setUniformFloat2Value(String name, Vector2f value) {
		setUniformFloat2Value(name, value.x, value.y);
	}

	public void setUniformFloat2Value(String name, float f1, float f2) {
		// get uniform ID
		int location = GL33.glGetUniformLocation(programID, name);
		if (location == -1) {
			System.out.println("Can't send vec2 " + name + " to shader program (ID = " + programID + ")");
			return;
		}
		// Send matrix value
		GL33.glUniform2f(location, f1, f2);
	}

	public void setUniformFloat3Value(String name, Vector3f value) {
		setUniformFloat3Value(name, value.x, value.y, value.z);
	}

	public void setUniformFloat3Value(String name, float f1, float f2, float f3) {
		// get uniform ID
		int location = GL33.glGetUniformLocation(programID, name);
		if (location == -1) {
			System.out.println("Can't send vec3 " + name + " to shader program (ID = " + programID + ")");
			return;
		}
		// Send matrix value
		GL33.glUniform3f(location, f1, f2, f3);
	}

	public void setUniformFloat4Value(String name, Vector4f value) {
		setUniformFloat4Value(name, value.x, value.y, value.z, value.w);
	}

	public void setUniformFloat4Value(String name, float f1, float f2, float f3, float f4) {
		// get uniform ID
		int location = GL33.glGetUniformLocation(programID, name);
		if (location == -1) {
			System.out.println("Can't send vec4 " + name + " to shader program (ID = " + programID + ")");
			return;
		}
		// Send matrix value
		GL33.glUniform4f(location, f1, f2, f3, f4);
	}

	public void start() {
		GL33.glUseProgram(programID);
	}

	public void stop() {
		GL33.glUseProgram(0);
	}

	public int getID() {
		return programID;
	}

}