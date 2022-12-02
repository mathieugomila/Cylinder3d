#version 330 core
layout (location = 0) in vec2 position;

uniform mat4 rotationMatrix;
uniform vec3 cameraPosition;

out vec2 pass_position;


void main() {
	gl_Position = vec4(position, 0.0, 1.0);
	pass_position = position;
}
