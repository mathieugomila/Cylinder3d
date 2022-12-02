package util;

import java.lang.Math;

public class Vector2f {
	public float x, y, z;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(Vector2f vector) {
		this(vector.x, vector.y);
	}

	public Vector2f add(Vector2f other) {
		return new Vector2f(other.x + x, other.y + y);
	}

	public Vector2f sub(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y);
	}

	public Vector2f multiply(float value) {
		Vector2f result = new Vector2f(x * value, y * value);
		return result;
	}

	public float getNorm() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		float norm = getNorm();
		if (norm == 0) {
			return;
		}
		x /= norm;
		y /= norm;
	}

	public static float crossProduct(Vector2f a, Vector2f b) {
		return a.x * b.x + a.y * b.y;
	}

	public Vector2i toVector2i() {
		return new Vector2i((int) this.x, (int) this.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
