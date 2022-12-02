package util;

import java.lang.Math;

public class Vector3f {
	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(Vector3f vector) {
		this(vector.x, vector.y, vector.z);
	}

	public Vector3f add(Vector3f other) {
		return new Vector3f(other.x + x, other.y + y, other.z + z);
	}

	public Vector3f sub(Vector3f other) {
		return new Vector3f(x - other.x, y - other.y, z - other.z);
	}

	public Vector3f multiply(float value) {
		Vector3f result = new Vector3f(x * value, y * value, z * value);
		return result;
	}

	public Vector3f multiply(int value) {
		Vector3f result = new Vector3f(x * value, y * value, z * value);
		return result;
	}

	public float getNorm() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public void normalize() {
		float norm = getNorm();
		if (norm == 0) {
			return;
		}
		x /= norm;
		y /= norm;
		z /= norm;
	}

	public static float crossProduct(Vector3f a, Vector3f b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
