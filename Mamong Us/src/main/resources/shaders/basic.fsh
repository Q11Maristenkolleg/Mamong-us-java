#version 120

uniform sampler2D sampler;

varying vec2 uv_map;

void main() {
    vec4 color = texture2D(sampler, uv_map);

    gl_FragColor = color;
}
