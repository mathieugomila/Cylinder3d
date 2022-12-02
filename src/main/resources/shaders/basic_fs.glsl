#version 330 core

in vec2 pass_position;

uniform mat4 rotationMatrix;
uniform vec3 cameraPosition;

out vec4 finalColor;

float sdf_sphere(vec3 p, vec3 center, float radius){
    return length(p - center) - radius;
}


float sdf_cylinder(vec3 position, vec3 center, float radius, float halfHeight, float cornerRadius) {
   vec2 d = vec2(length(position.xz - center.xz), abs(position.y - center.y)) - vec2(radius, halfHeight) + cornerRadius; 
   return length(max(d, 0.0)) + min(max(d.x, d.y), 0.0) - cornerRadius;
}


void main()
{
    // Find origin position and direction of ray
    // Direction is computed using MVP matrix
    vec3 currentPosition = cameraPosition;

    vec3 relative_position_on_near_plane = vec4(inverse(rotationMatrix) * vec4(pass_position, 0.001, 1.0)).xyz;
    vec3 ray_forward = normalize(relative_position_on_near_plane);

    for(int i = 0; i < 200; i++){
        float distance_to_cylinder = sdf_cylinder(currentPosition, vec3(-5.0, 0.0, 0.0), 1.0, 3.0, 0.5);
        if (distance_to_cylinder < 0.001){
            finalColor = vec4((0.5 * (vec3(1.0) + (currentPosition - vec3(-5.0, 0.0, 0.0)))), 1.0);
            return;
        }
        else {
            currentPosition += distance_to_cylinder * ray_forward;
        }
    }

    finalColor = vec4(0.0, 0.0, 0.0, 1.0);
}
