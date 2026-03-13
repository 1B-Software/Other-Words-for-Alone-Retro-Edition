#version 330 core

in vec2 vWorldPos;

out vec4 fragColor;

uniform vec2 uLightPos;
uniform float uRadius;
uniform float uIntensity;
uniform vec3 uLightColor;
uniform float uFallOff;
uniform vec2 uResolution;

void main() {
    float dist = length(vWorldPos - uLightPos);
    if (dist >= uRadius) discard;
    float b = pow(1.0 - (dist / uRadius), uFallOff);
    fragColor = vec4(uLightColor * b * uIntensity, 1.0);
}
