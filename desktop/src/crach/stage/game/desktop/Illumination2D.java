package crach.stage.game.desktop;


import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Simple illumination model with shaders in LibGDX.
 * @author davedes
 */
public class Illumination2D implements ApplicationListener {	

	Texture texture, texture_n;
	
	boolean flipY;
	Texture normalBase;
	Viewport viewport;
	OrthographicCamera cam;
	SpriteBatch fxBatch, batch;
	
	Matrix4 transform = new Matrix4();

	Random rnd = new Random();

	// position of our light
	final Vector3 DEFAULT_LIGHT_POS = new Vector3(0f, 0f, 0.07f);
	// the color of our light
	final Vector3 DEFAULT_LIGHT_COLOR = new Vector3(1f, 0.7f, 0.6f);
	// the ambient color (color to use when unlit)
	final Vector3 DEFAULT_AMBIENT_COLOR = new Vector3(0.3f, 0.3f, 1f);
	// the attenuation factor: x=constant, y=linear, z=quadratic
	final Vector3 DEFAULT_ATTENUATION = new Vector3(0.4f, 3f, 20f);
	// the ambient intensity (brightness to use when unlit)
	final float DEFAULT_AMBIENT_INTENSITY = 0.2f;
	final float DEFAULT_STRENGTH = 1f;
	
	final Color NORMAL_VCOLOR = new Color(1f,1f,1f,DEFAULT_STRENGTH);
	
	// the position of our light in 3D space
	Vector3 lightPos = new Vector3(DEFAULT_LIGHT_POS);
	// the resolution of our game/graphics
	Vector2 resolution = new Vector2();
	// the current attenuation
	Vector3 attenuation = new Vector3(DEFAULT_ATTENUATION);
	// the current ambient intensity
	float ambientIntensity = DEFAULT_AMBIENT_INTENSITY;
	float strength = DEFAULT_STRENGTH;
	
	// whether to use attenuation/shadows
	boolean useShadow = true;

	// whether to use lambert shading (with our normal map)
	boolean useNormals = true;
	
	DecimalFormat DEC_FMT = new DecimalFormat("0.00000");

	ShaderProgram program;

	BitmapFont font;
	
	private int texWidth, texHeight;
	
	final String TEXT = "Use number keys to adjust parameters:\n" +
			"1: Randomize Ambient Color\n" +
			"2: Randomize Ambient Intensity {0}\n" +
			"3: Randomize Light Color\n" +
			"4/5: Increase/decrease constant attenuation: {1}\n" +
			"6/7: Increase/decrease linear attenuation: {2}\n" +
			"8/9: Increase/decrease quadratic attenuation: {3}\n" +
			"0: Reset parameters\n" +
			"RIGHT/LEFT: Increase/decrease normal map intensity: {4}\n" +
			"UP/DOWN: Increase/decrease lightDir.z: {5}\n\n" +
			"S toggles attenuation, N toggles normal shading\n" +
			"T to toggle textures";
	
	private Texture rock, rock_n, teapot, teapot_n;
	
	public void create() {
		
		
		// load our textures
		rock = new Texture(Gdx.files.internal("data/teapot.png"));
		rock_n = new Texture(Gdx.files.internal("data/teapot_n.png"));
		teapot = new Texture(Gdx.files.internal("data/rock.png"));
		teapot_n = new Texture(Gdx.files.internal("data/rock_n.png"));
		
		texture = teapot;
		texture_n = teapot_n;
		flipY = texture==rock;
		
		//we only use this to show what the strength-adjusted normal map looks like on screen
		Pixmap pix = new Pixmap(1, 1, Format.RGB565); 
		pix.setColor(0.5f, 0.5f, 1.0f, 1.0f); 
		pix.fill();
		normalBase = new Texture(pix);
		
		texWidth = texture.getWidth();
		texHeight = texture.getHeight();
		
		// a simple 2D orthographic camera
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ExtendViewport(500,850, cam);
		cam.setToOrtho(false);

		// create our shader program...
		program = createShader();

		// now we create our sprite batch for our shader
		fxBatch = new SpriteBatch(100, program);
		// setShader is needed; perhaps this is a LibGDX bug?
		fxBatch.setShader(program);
		fxBatch.setProjectionMatrix(cam.combined);
		fxBatch.setTransformMatrix(transform);

		// usually we would just use a single batch for our application,
		// but for demonstration let's also show the un-affected image
		batch = new SpriteBatch(100);
		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(transform);

		// quick little input for debugging -- press S to toggle shadows, N to
		// toggle normals
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean keyDown(int key) {
				if (key == Keys.S) {
					useShadow = !useShadow;
					return true;
				} else if (key == Keys.N) {
					useNormals = !useNormals;
					return true;
				} else if (key == Keys.NUM_1) {
					program.begin();
					program.setUniformf("ambientColor", rndColor());
					program.end();
					return true;
				} else if (key == Keys.NUM_2) {
					ambientIntensity = rnd.nextFloat();
					return true;
				} else if (key == Keys.NUM_3) {
					program.begin();
					program.setUniformf("lightColor", rndColor());
					program.end();
					return true;
				} else if (key == Keys.NUM_0) {
					attenuation.set(DEFAULT_ATTENUATION);
					ambientIntensity = DEFAULT_AMBIENT_INTENSITY;
					lightPos.set(DEFAULT_LIGHT_POS);
					strength = DEFAULT_STRENGTH;
					program.begin();
					program.setUniformf("lightColor", DEFAULT_LIGHT_COLOR);
					program.setUniformf("ambientColor", DEFAULT_AMBIENT_COLOR);
					program.setUniformf("ambientIntensity", ambientIntensity);
					program.setUniformf("attenuation", attenuation);
					program.setUniformf("lightPos", lightPos);
					program.setUniformf("strength", strength);
					program.end();
				} else if (key == Keys.T) {
					texture = texture==teapot ? rock : teapot;
					texture_n = texture_n==teapot_n ? rock_n : teapot_n;
					flipY = texture==rock;
					texWidth = texture.getWidth();
					texHeight = texture.getHeight();
					program.begin();
					program.setUniformi("yInvert", flipY ? 1 : 0);
					program.end();
				}
				return false;
			}
		});
		
		font = new BitmapFont();
	}
	
	private Vector3 rndColor() {
		return new Vector3(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
	}

	private ShaderProgram createShader() {
		// see the code here: http://pastebin.com/7fkh1ax8
		// simple illumination model using ambient, diffuse (lambert) and attenuation
		// see here: http://nccastaff.bournemouth.ac.uk/jmacey/CGF/slides/IlluminationModels4up.pdf
		String vert = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "uniform mat4 u_proj;\n" //
				+ "uniform mat4 u_trans;\n" //
				+ "uniform mat4 u_projTrans;\n" //
				+ "varying vec4 v_color;\n" //
				+ "varying vec2 v_texCoords;\n" //
				+ "\n" //
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
				+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
				+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
				+ "}\n";
		
		String frag = "#ifdef GL_ES\n" +
				"precision mediump float;\n" +
				"#endif\n" +
				"varying vec4 v_color;\n" +
				"varying vec2 v_texCoords;\n" +
				
				"uniform sampler2D u_texture;\n" +
				"uniform sampler2D u_normals;\n" +
				"uniform vec3 light;\n" + 
				"uniform vec3 ambientColor;\n" + 
				"uniform float ambientIntensity; \n" + 
				"uniform vec2 resolution;\n" + 
				"uniform vec3 lightColor;\n" + 
				"uniform bool useNormals;\n" + 
				"uniform bool useShadow;\n" + 
				"uniform vec3 attenuation;\n" + 
				"uniform float strength;\n" +
				"uniform bool yInvert;\n"+ 
				"\n" + 
				"void main() {\n" +
				"	//sample color & normals from our textures\n" +
				"	vec4 color = texture2D(u_texture, v_texCoords.st);\n" +
				"	vec3 nColor = texture2D(u_normals, v_texCoords.st).rgb;\n\n" +
				"	//some bump map programs will need the Y value flipped..\n" +
				"	nColor.g = yInvert ? 1.0 - nColor.g : nColor.g;\n\n" +
				"	//this is for debugging purposes, allowing us to lower the intensity of our bump map\n" +
				"	vec3 nBase = vec3(0.5, 0.5, 1.0);\n" +
				"	nColor = mix(nBase, nColor, strength);\n\n" +
				"	//normals need to be converted to [-1.0, 1.0] range and normalized\n" +
				"	vec3 normal = normalize(nColor * 2.0 - 1.0);\n\n" +
				"	//here we do a simple distance calculation\n" +
				"	vec3 deltaPos = vec3( (light.xy - gl_FragCoord.xy) / resolution.xy, light.z );\n\n" +
				"	vec3 lightDir = normalize(deltaPos);\n" + 
				"	float lambert = useNormals ? clamp(dot(normal, lightDir), 0.0, 1.0) : 1.0;\n" + 
				"	\n" + 
				"	//now let's get a nice little falloff\n" + 
				"	float d = sqrt(dot(deltaPos, deltaPos));"+ 
				"	\n" + 
				"	float att = useShadow ? 1.0 / ( attenuation.x + (attenuation.y*d) + (attenuation.z*d*d) ) : 1.0;\n" + 
				"	\n" + 
				"	vec3 result = (ambientColor * ambientIntensity) + (lightColor.rgb * lambert) * att;\n" + 
				"	result *= color.rgb;\n" + 
				"	\n" + 
				"	gl_FragColor = v_color * vec4(result, color.a);\n" + 
				"}";
		System.out.println("VERTEX PROGRAM:\n------------\n\n"+vert);
		System.out.println("FRAGMENT PROGRAM:\n------------\n\n"+frag);
		ShaderProgram program = new ShaderProgram(vert, frag);
		// u_proj and u_trans will not be active but SpriteBatch will still try to set them...
		program.pedantic = false;
		if (program.isCompiled() == false)
			throw new IllegalArgumentException("couldn't compile shader: "
					+ program.getLog());

		// set resolution vector
		resolution.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// we are only using this many uniforms for testing purposes...!!
		program.begin();
		program.setUniformi("u_texture", 0);
		program.setUniformi("u_normals", 1);
		program.setUniformf("light", lightPos);
		program.setUniformf("strength", strength);
		program.setUniformf("ambientIntensity", ambientIntensity);
		program.setUniformf("ambientColor", DEFAULT_AMBIENT_COLOR);
		program.setUniformf("resolution", resolution);
		program.setUniformf("lightColor", DEFAULT_LIGHT_COLOR);
		program.setUniformf("attenuation", attenuation);
		program.setUniformi("useShadow", useShadow ? 1 : 0);
		program.setUniformi("useNormals", useNormals ? 1 : 0);
		program.setUniformi("yInvert", flipY ? 1 : 0);
		program.end();

		return program;
	}

	public void dispose() {
		fxBatch.dispose();
		batch.dispose();
		texture.dispose();
		texture_n.dispose();
	}
	
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		// draw our sprites without any effects
		batch.begin();
		
		final int IMG_Y = texHeight/2; 
		
		//let's first simulate our resulting normal map by blending a blue square atop it
		//we also could have achieved this with glTexEnv in the fixed function pipeline
		NORMAL_VCOLOR.a = 1.0f - strength;
		batch.draw(texture_n, texWidth + 10, IMG_Y);
		batch.setColor(NORMAL_VCOLOR);
		batch.draw(normalBase, texWidth + 10, IMG_Y, texWidth, texHeight);
		batch.setColor(Color.WHITE);
		batch.draw(texture, 0, IMG_Y);
		//now let's simulate how our normal map will be sampled using strength
		//we can do this simply by blending a blue fill overtop
		
		String str = MessageFormat.format(TEXT, ambientIntensity,
				attenuation.x, attenuation.y, DEC_FMT.format(attenuation.z),
				strength, lightPos.z);
		font.draw(batch, str, 10, Gdx.graphics.getHeight()-10, 200, 200, false);
		font.draw(batch, "Diffuse Color", 10, IMG_Y+texHeight + 30);
		font.draw(batch, "Normal Map", texWidth+20, IMG_Y+texHeight + 30);
		font.draw(batch, "Final Color", texWidth*2+30, IMG_Y+texHeight + 30);
		batch.end();
		
		// start our FX batch, which will bind our shader program
		fxBatch.begin();
		
		// get y-down light position based on mouse/touch
		lightPos.x = Gdx.input.getX();
		lightPos.y = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		// handle attenuation input
		if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			attenuation.x += 0.025f;
		} else if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			attenuation.x -= 0.025f;
			if (attenuation.x < 0)
				attenuation.x = 0;
		} else if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
			attenuation.y += 0.25f;
		} else if (Gdx.input.isKeyPressed(Keys.NUM_7)) {
			attenuation.y -= 0.25f;
			if (attenuation.y < 0)
				attenuation.y = 0;
		} else if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
			attenuation.z += 0.25f;
		} else if (Gdx.input.isKeyPressed(Keys.NUM_9)) {
			attenuation.z -= 0.25f;
			if (attenuation.z < 0)
				attenuation.z = 0;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			strength += 0.025f;
			if (strength > 1f)
				strength = 1f;
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			strength -= 0.025f;
			if (strength < 0)
				strength = 0;
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
			lightPos.z += 0.0025f;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			lightPos.z -= 0.0025f;
		}
		
		// update our uniforms
		program.setUniformf("ambientIntensity", ambientIntensity);
		program.setUniformf("attenuation", attenuation);
		program.setUniformf("light", lightPos);
		program.setUniformi("useNormals", useNormals ? 1 : 0);
		program.setUniformi("useShadow", useShadow ? 1 : 0);
		program.setUniformf("strength", strength);
		
		// bind the normal first at texture1
		texture_n.bind(1);
		
		// bind the actual texture at texture0
		texture.bind(0);
		
		// we bind texture0 second since draw(texture) will end up binding it at
		// texture0...
		fxBatch.draw(texture, texWidth*2 + 20, IMG_Y);
		fxBatch.end();
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(cam.combined);
		fxBatch.setProjectionMatrix(cam.combined);
		resolution.set(width, height);
		program.setUniformf("resolution", resolution);
	}

	public void pause() {
	}

	public void resume() {
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Lighting Test";
		cfg.width = 1024;
		cfg.height = 768;

		new LwjglApplication(new Illumination2D(), cfg);
	}
}