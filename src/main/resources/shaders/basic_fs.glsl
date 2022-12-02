#version 330 core

in vec2 pass_position;

uniform mat4 rotationMatrix;
uniform vec3 cameraPosition;

out vec4 finalColor;

float sdf_sphere(vec3 p, vec3 center, float radius){
    return length(p - center) - radius;
}


void main()
{
    // Find origin position and direction of ray
    // Direction is computed using MVP matrix
    vec3 currentPosition = cameraPosition;

    vec3 relative_position_on_near_plane = vec4(inverse(rotationMatrix) * vec4(pass_position, 0.001, 1.0)).xyz;
    vec3 ray_forward = normalize(relative_position_on_near_plane);

    for(int i = 0; i < 200; i++){
        float distance_to_sphere = sdf_sphere(currentPosition, vec3(5.0, 0.0, 0.0), 1.0);
        if (distance_to_sphere < 0.01){
            float normal_dot = abs(dot(ray_forward, currentPosition - vec3(5.0, 0.0, 0.0)));
            finalColor = vec4(pow(normal_dot, 0.6) * vec3(1.0), 1.0);
            return;
        }
        else {
            currentPosition += distance_to_sphere * ray_forward;
        }
    }

    finalColor = vec4(0.0, 0.0, 0.0, 1.0);
}
