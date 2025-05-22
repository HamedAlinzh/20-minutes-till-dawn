#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform vec2 u_direction; // (1.0, 0.0) for horizontal, (0.0, 1.0) for vertical

varying vec2 v_texCoords;

void main() {
    vec4 sum = vec4(0.0);
    vec2 texel = u_direction / u_resolution;

    sum += texture2D(u_texture, v_texCoords - 4.0 * texel) * 0.05;
    sum += texture2D(u_texture, v_texCoords - 3.0 * texel) * 0.09;
    sum += texture2D(u_texture, v_texCoords - 2.0 * texel) * 0.12;
    sum += texture2D(u_texture, v_texCoords - 1.0 * texel) * 0.15;
    sum += texture2D(u_texture, v_texCoords) * 0.18;
    sum += texture2D(u_texture, v_texCoords + 1.0 * texel) * 0.15;
    sum += texture2D(u_texture, v_texCoords + 2.0 * texel) * 0.12;
    sum += texture2D(u_texture, v_texCoords + 3.0 * texel) * 0.09;
    sum += texture2D(u_texture, v_texCoords + 4.0 * texel) * 0.05;

    gl_FragColor = sum;
}
