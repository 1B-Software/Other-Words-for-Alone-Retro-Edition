#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D uScene;
uniform sampler2D uLightmap;

void main() {
    vec4 scene = texture(uScene, vTexCoord);
    vec4 light = texture(uLightmap, vTexCoord);
    fragColor = vec4(scene.rgb * light.rgb, scene.a);
}
