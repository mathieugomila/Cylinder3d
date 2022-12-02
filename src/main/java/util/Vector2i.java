package util;

import java.lang.Math;

public class Vector2i {
	public int x, y, z;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i(Vector2i vector) {
		this(vector.x, vector.y);
	}

	public Vector2i add(Vector2i other) {
		return new Vector2i(other.x + x, other.y + y);
	}

	public Vector2i sub(Vector2i other) {
		return new Vector2i(x - other.x, y - other.y);
	}

	public Vector2i multiply(int value) {
		Vector2i result = new Vector2i(x * value, y * value);
		return result;
	}

	public int getNorm() {
		return (int) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		int norm = getNorm();
		if (norm == 0) {
			return;
		}
		x /= norm;
		y /= norm;
	}

	public static int crossProduct(Vector2i a, Vector2i b) {
		return a.x * b.x + a.y * b.y;
	}

	public Vector2f toVector2f() {
		return new Vector2f((float) this.x, (float) this.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
