#ifdef GL_ES
#define LOWP lowp
	precision mediump float;
#else
	#define LOWP
#endif

varying vec2 v_texCoords;
varying vec4 v_color;

uniform sampler2D u_texture;

const float contrast = 1.4;
const float brightness = 0.01;

void main() {
    vec4 texColor = v_color *  texture2D(u_texture, v_texCoords);
    gl_FragColor = (texColor - 0.5 ) * contrast + 0.5 + brightness;
}

