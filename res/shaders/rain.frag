#version 330 core

in vec2 vTexCoord;
in vec4 vColor;
in vec2 vWorldPos;

uniform sampler2D uTexture;
uniform float uTime;

out vec4 fragColor;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);

    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

void main()
{
    // Soft edges: fade at top and bottom of the raindrop streak
    float edge = smoothstep(0.0, 0.3, vTexCoord.y) * smoothstep(1.0, 0.7, vTexCoord.y);

    // Brightness shimmer from noise — each game pixel gets a different value
    float n = noise(floor(vWorldPos) + vec2(uTime * 2.0, uTime * 3.0));
    float brightness = 0.7 + 0.3 * n;

    fragColor = vec4(vColor.rgb * brightness, vColor.a * edge * 0.4);
}
