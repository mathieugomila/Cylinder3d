package util;

import java.lang.Math;

public class Vector3i {
	public int x, y, z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3i vector) {
		this(vector.x, vector.y, vector.z);
	}

	public Vector3i add(Vector3i other) {
		return new Vector3i(other.x + x, other.y + y, other.z + z);
	}

	public Vector3i sub(Vector3i other) {
		return new Vector3i(x - other.x, y - other.y, z - other.z);
	}

	public Vector3i multiply(int value) {
		Vector3i result = new Vector3i(x * value, y * value, z * value);
		return result;
	}

	public int getNorm() {
		return (int) Math.sqrt(x * x + y * y + z * z);
	}

	public void normalize() {
		int norm = getNorm();
		if (norm == 0) {
			return;
		}
		x /= norm;
		y /= norm;
		z /= norm;
	}

	public static int crossProduct(Vector3i a, Vector3i b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
