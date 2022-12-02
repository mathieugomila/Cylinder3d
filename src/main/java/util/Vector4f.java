package util;

import java.lang.Math;

public class Vector4f {
	public float x, y, z, w;

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4f(Vector4f vector) {
		this(vector.x, vector.y, vector.z, vector.w);
	}

	public Vector4f(Vector3f vector, float w) {
		this(vector.x, vector.y, vector.z, w);
	}

	public Vector4f add(Vector4f other) {
		return new Vector4f(other.x + x, other.y + y, other.z + z, other.w + w);
	}

	public Vector4f sub(Vector4f other) {
		return new Vector4f(x - other.x, y - other.y, z - other.z, other.w - w);
	}

	public Vector4f multiply(float value) {
		Vector4f result = new Vector4f(x * value, y * value, z * value, w * value);
		return result;
	}

	public Vector4f multiply(int value) {
		Vector4f result = new Vector4f(x * value, y * value, z * value, w * value);
		return result;
	}

	public float getNorm() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public void normalize() {
		float norm = getNorm();
		if (norm == 0) {
			return;
		}
		x /= norm;
		y /= norm;
		z /= norm;
		w /= norm;
	}

	public static float crossProduct(Vector4f a, Vector4f b) {
		return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
