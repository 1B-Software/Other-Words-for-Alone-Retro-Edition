#version 330 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoord;
layout(location = 2) in vec4 aColor;

out vec2 vTexCoord;
out vec4 vColor;

out vec2 vWorldPos;

uniform mat4 uProjection;
uniform vec2 uResidue;

uniform vec2 uCamera;

void main() {
    vTexCoord = aTexCoord;
    vColor = aColor;

    vec2 cameraPos = vec2(aPos.x-uCamera.x, aPos.y-uCamera.y);

//    float xResidue = xScroll - floor(xScroll);
//    float yResidue = yScroll - floor(yScroll);
//    vWorldPos -= vec2(xResidue, yResidue);

    gl_Position = uProjection * vec4(cameraPos, 0.0, 1.0);
}
