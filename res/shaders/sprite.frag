#version 330 core

in vec2 vTexCoord;
in vec4 vColor;

out vec4 fragColor;

uniform int uFontMode;

uniform sampler2D uTexture;

void main() {
	// vTexCoord = current position of pixel, exactly like pixels[x+y*w]
    vec4 texColor = texture(uTexture, vTexCoord);
    if(uFontMode == 1) {
    	fragColor = vec4(vColor.rgb, texColor.a * vColor.a);
    } else {
    	fragColor = texColor * vColor;
    }
}
