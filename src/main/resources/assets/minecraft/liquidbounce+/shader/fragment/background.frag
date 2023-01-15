uniform float iTime;
uniform vec2 iResolution;

#define S sin
#define C cos
#define t iTime
#define X uv.x*32.
#define Y -uv.y*32.

void main( out vec4 outColor ) {

	outColor = vec4(0.);
	vec2 st = gl_FragCoord.xy / iResolution.y;
	
	vec2 b = vec2(0,.1), p;
	mat2 rotation;
	
	for (float i=.10; i<4.1111111111111 ;++i) {
			rotation = mat2(cos(i + vec4(0,33,11,0)));
			
			st = st * (i);
			
			st.y += iTime * (0.016);
			
			st = rotation * (fract((st)*rotation)-.5);
			
			outColor += 0.001/ length(clamp(st,-b,b)-st);
	}
	gl_FragColor(outColor,1.0);
}
