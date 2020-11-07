#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 uv_map;

uniform mat4 mvp;

void main() {
    uv_map = textures;
    gl_Position = mvp * vec4(vertices, 1);
}