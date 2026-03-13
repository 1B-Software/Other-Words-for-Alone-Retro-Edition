#version 330 core
layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoord;
layout(location = 2) in vec4 aColor;

out vec2 vTexCoord;
out vec4 vColor;
out vec2 vWorldPos;

uniform mat4 uProjection;
uniform vec4 rColor;
uniform float uWind;

void main()
{
    vec2 pos = aPos;
    // Shift top vertices by wind — top has texCoord.y=0, bottom has texCoord.y=1
    // So (1.0 - aTexCoord.y) is 1 at top, 0 at bottom — top leans with wind
    pos.x += (uWind*2) * (1.0 - aTexCoord.y);

    vTexCoord = aTexCoord;
    vColor = vec4(rColor.rgb, aColor.a);
    vWorldPos = pos;
    gl_Position = uProjection * vec4(pos, 0.0, 1.0);
}
