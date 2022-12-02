package util;

import java.nio.FloatBuffer;

public class Matrix4f {
	private float m00, m01, m02, m03;
	private float m10, m11, m12, m13;
	private float m20, m21, m22, m23;
	private float m30, m31, m32, m33;

	public Matrix4f() {
		setIdentity();
	}

	public void setIdentity() {
		m00 = 1;
		m11 = 1;
		m22 = 1;
		m33 = 1;

		m01 = 0;
		m02 = 0;
		m03 = 0;
		m10 = 0;
		m12 = 0;
		m13 = 0;
		m20 = 0;
		m21 = 0;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
	}

	public static Matrix4f transpose(Matrix4f mat) {
		Matrix4f result = new Matrix4f();
		result.m00 = mat.m00;
		result.m11 = mat.m11;
		result.m22 = mat.m22;
		result.m33 = mat.m33;

		result.m01 = mat.m10;
		result.m02 = mat.m20;
		result.m03 = mat.m30;
		result.m10 = mat.m01;
		result.m12 = mat.m21;
		result.m13 = mat.m31;
		result.m20 = mat.m02;
		result.m21 = mat.m12;
		result.m23 = mat.m32;
		result.m30 = mat.m03;
		result.m31 = mat.m13;
		result.m32 = mat.m23;
		return result;
	}

	public Matrix4f multiply(Matrix4f other) {
		Matrix4f result = new Matrix4f();

		result.m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
		result.m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
		result.m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
		result.m30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;

		result.m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
		result.m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
		result.m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
		result.m31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;

		result.m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
		result.m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
		result.m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
		result.m32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;

		result.m03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;
		result.m13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;
		result.m23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;
		result.m33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;

		return result;
	}

	public Vector3f multiply(Vector3f in) {
		Vector3f result = new Vector3f(0, 0, 0);

		result.x = this.m00 * in.x + this.m01 * in.y + this.m02 * in.z + this.m03 * 1;
		result.y = this.m10 * in.x + this.m11 * in.y + this.m12 * in.z + this.m13 * 1;
		result.z = this.m20 * in.x + this.m21 * in.y + this.m22 * in.z + this.m23 * 1;

		return result;
	}

	public Vector4f multiply(Vector4f in) {
		Vector4f result = new Vector4f(0, 0, 0, 0);

		result.x = this.m00 * in.x + this.m01 * in.y + this.m02 * in.z + this.m03 * in.w;
		result.y = this.m10 * in.x + this.m11 * in.y + this.m12 * in.z + this.m13 * in.w;
		result.z = this.m20 * in.x + this.m21 * in.y + this.m22 * in.z + this.m23 * in.w;
		result.w = this.m30 * in.x + this.m31 * in.y + this.m32 * in.z + this.m33 * in.w;

		return result;
	}

	public static Matrix4f translate(Vector3f position) {
		Matrix4f translation = new Matrix4f();

		translation.m03 = position.x;
		translation.m13 = position.y;
		translation.m23 = position.z;

		return translation;
	}

	public static Matrix4f rotate(float angle, Vector3f vec) {
		Matrix4f rotation = new Matrix4f();

		float c = (float) Math.cos(Math.toRadians(angle));
		float s = (float) Math.sin(Math.toRadians(angle));

		vec.normalize();

		rotation.m00 = vec.x * vec.x * (1f - c) + c;
		rotation.m10 = vec.y * vec.x * (1f - c) + vec.z * s;
		rotation.m20 = vec.x * vec.z * (1f - c) - vec.y * s;
		rotation.m01 = vec.x * vec.y * (1f - c) - vec.z * s;
		rotation.m11 = vec.y * vec.y * (1f - c) + c;
		rotation.m21 = vec.y * vec.z * (1f - c) + vec.x * s;
		rotation.m02 = vec.x * vec.z * (1f - c) + vec.y * s;
		rotation.m12 = vec.y * vec.z * (1f - c) - vec.x * s;
		rotation.m22 = vec.z * vec.z * (1f - c) + c;

		return rotation;
	}

	public static Matrix4f view(Vector3f position, Vector3f rotation) {
		Matrix4f result = new Matrix4f();

		Vector3f negativePosition = new Vector3f(-position.x, -position.y, -position.z);
		Matrix4f translationMatrix = Matrix4f.translate(negativePosition);

		Vector3f negativeOrientation = new Vector3f(-rotation.x, -rotation.y + 180, -rotation.z);
		Matrix4f rotXMatrix = Matrix4f.rotate(negativeOrientation.x, new Vector3f(1, 0, 0));
		Matrix4f rotYMatrix = Matrix4f.rotate(negativeOrientation.y, new Vector3f(0, 1, 0));
		Matrix4f rotZMatrix = Matrix4f.rotate(negativeOrientation.z, new Vector3f(0, 0, 1));

		Matrix4f rotationMatrix = rotZMatrix.multiply(rotXMatrix.multiply(rotYMatrix));

		result = rotationMatrix.multiply(translationMatrix);

		return result;
	}

	public static Matrix4f perspective(float fovy, float aspect, float near, float far) {
		Matrix4f perspective = new Matrix4f();

		float f = (float) (1f / Math.tan(Math.toRadians(fovy) / 2f));

		perspective.m00 = f / aspect;
		perspective.m11 = f;
		perspective.m22 = (far + near) / (near - far);
		perspective.m32 = -1f;
		perspective.m23 = (2f * far * near) / (near - far);
		perspective.m33 = 0f;

		return perspective;
	}

	public void toBuffer(FloatBuffer buffer) {
		buffer.put(m00).put(m10).put(m20).put(m30);
		buffer.put(m01).put(m11).put(m21).put(m31);
		buffer.put(m02).put(m12).put(m22).put(m32);
		buffer.put(m03).put(m13).put(m23).put(m33);
		buffer.flip();
	}

	@Override
	public String toString() {
		String matrixString;
		matrixString = m00 + " | " + m01 + " | " + m02 + " | " + m03 + "\n"
				+ m10 + " | " + m11 + " | " + m12 + " | " + m13 + "\n"
				+ m20 + " | " + m21 + " | " + m22 + " | " + m23 + "\n"
				+ m30 + " | " + m31 + " | " + m32 + " | " + m33;
		return matrixString;
	}
}
