package crach.stage.game.screen.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.pickups.Crystal;
import net.dermetfan.gdx.math.MathUtils;

public class WordMapRender extends Image implements Disposable {

	private ShapeRenderer renderer;
	private boolean drawBodies;

	private static Vector2 t = new Vector2();
	private static Vector2 axis = new Vector2();
	private final Vector2 f = new Vector2();
	private final Vector2 v = new Vector2();
	private final Vector2 lv = new Vector2();

	public final Color SHAPE_CADER = new Color(1,1,1, 1);
	
	public final Color SHAPE_ENIMY = new Color(0.8f, 0.5f, 0.3f, 1);
	public final Color SHAPE_OBJECT_BIT = new Color(0, 0.9f, 0.3f, 1);
	public final Color SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, 1);
	public final Color SHAPE_BICKUP = new Color(0.6f, 0.8f, 0.6f, 1);
	public final Color SHAPE_CRACH = new Color(0.9f, 0.7f, 0.7f, 1);
	public final Color SHAPE_BOMB = new Color(0.3f, 0.7f, 0.2f, 1);

	private final Vector2[] vertices = new Vector2[1000];
	private final Array<Fixture> fixtArray = new Array<Fixture>();

	private float widthMap, heightMap , widthMapView,  heightMapView , PosX ,PosY;

	private boolean Active;
	private float timeView = 10f;

	public WordMapRender(float widthMap, float heightMap) {
		super(Assets.skinStyle, "table");
		this.drawBodies = true;
		this.widthMap = widthMap;
		this.heightMap = heightMap;
		renderer = new ShapeRenderer();
		// initialize vertices array
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = new Vector2();

		this.setSize( 40, 40);
		this.setColor(getColor().r, getColor().g,getColor().b, 0.6f);
		
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				active(!Active);
			}
		});
	}
	
	public void active(boolean active) {
		this.Active = active;
		this.timeView = 10;
		if(active) {
			this.setSize(widthMap + 10, heightMap + 10);
			this.setPosition(PosX - widthMap-5, PosY -heightMap- 5);
		}else {
			this.setSize(40,40);
			this.setPosition(PosX -45, PosY -45);
		}
	}
	
	public void render(float dt,float x,float y,Batch batch, World world) {
		if(Active) {
			renderer.begin(ShapeType.Line);
		addFixtureOfWord(world);
		renderFixture();
		drawCader(x,y);
		renderer.end();
		if((timeView -= dt) < 0)
			active(false);
		}
	}
	
	private void drawCader(float x,float y) {
		
		x -=widthMapView/2;y -=heightMapView/2;
		renderer.setColor(SHAPE_CADER);
		renderer.line(x				,y					, x+widthMapView,				y);
		renderer.line(x+widthMapView,					y,x+widthMapView, y+heightMapView);
		renderer.line(x	+widthMapView, y+heightMapView,x				, y+heightMapView);
		renderer.line(x					,y+heightMapView, 				x, 				y);
	}
	public void addFixtureOfWord(World world) {
		Array<Fixture> fixtworld = new Array<Fixture>();
		world.getFixtures(fixtworld);
		fixtArray.clear();
		for (Fixture fix : fixtworld)
			switch (fix.getFilterData().categoryBits) {
			case CrachGame.OBJECT_BIT:
			case CrachGame.FRENDS_BIT:
			case CrachGame.DOOR_BIT:
			case CrachGame.CRACH_BIT:
			case CrachGame.ENIMY_BIT:
			case CrachGame.CRYSTAL_BIT:
			case CrachGame.BOMB_BIT:
			case CrachGame.PLACE_BIT:
				this.fixtArray.add(fix);
				break;
			}
	}

	
	private void renderFixture() {
		for (Fixture fixture : fixtArray)
			if (drawBodies) {
				switch (fixture.getFilterData().categoryBits) {
				case CrachGame.PLACE_BIT:
					drawShape(verticesExit, fixture.getBody().getTransform(), getColorFixture(fixture));
					break;
				case CrachGame.ENIMY_BIT:
					drawShape(verticesEnimy, fixture.getBody().getTransform(), getColorFixture(fixture));
					break;
				case CrachGame.BOMB_BIT:
					Object dataObject = fixture.getBody().getUserData();
//					if (dataObject instanceof BombGun)
//						drawShape(verticesGun, fixture.getBody().getTransform(), getColorFixture(fixture));
//					else if (dataObject instanceof BombGunShell)
//						drawShape(verticesShell, fixture.getBody().getTransform(), getColorFixture(fixture));
//					else if (dataObject instanceof TrapBomb)
//						drawShape(verticesDanger, fixture.getBody().getTransform(), getColorFixture(fixture));
//					else
						drawShape(verticesBomb, fixture.getBody().getTransform(), getColorFixture(fixture));
					break;
				case CrachGame.CRACH_BIT:
					drawShape(verticesPlayer, fixture.getBody().getTransform(), getColorFixture(fixture));
					break;
				case CrachGame.CRYSTAL_BIT:
					Object dataObject2 = fixture.getBody().getUserData();
					if(dataObject2 instanceof Crystal)
						drawShape(verticesCrystal, fixture.getBody().getTransform(), getColorFixture(fixture));
					break;
				default:
					drawShape(fixture, fixture.getBody().getTransform(), getColorFixture(fixture));
				}
			}
	}

	private void drawShape(float[] fixtureVertices, Transform transform, Color color) {
		int vertexCount = fixtureVertices.length / 2;
		for (int i = 0; i < vertexCount; i++) {
			vertices[i].set(fixtureVertices[2 * i], fixtureVertices[2 * i + 1]);
			transform.mul(vertices[i]);
		}
		drawSolidPolygon(vertices, vertexCount, color, true);
		return;
	}

	private void drawShape(Fixture fixture, Transform transform, Color color) {
		if (fixture.getType() == Type.Circle) {
			CircleShape circle = (CircleShape) fixture.getShape();
			t.set(circle.getPosition());
			transform.mul(t);
			drawSolidCircle(t, circle.getRadius(),
					axis.set(transform.vals[Transform.COS], transform.vals[Transform.SIN]), color);
			return;
		}

		if (fixture.getType() == Type.Edge) {
			EdgeShape edge = (EdgeShape) fixture.getShape();
			edge.getVertex1(vertices[0]);
			edge.getVertex2(vertices[1]);
			transform.mul(vertices[0]);
			transform.mul(vertices[1]);
			drawSolidPolygon(vertices, 2, color, true);
			return;
		}

		if (fixture.getType() == Type.Polygon) {
			PolygonShape chain = (PolygonShape) fixture.getShape();
			int vertexCount = chain.getVertexCount();
			for (int i = 0; i < vertexCount; i++) {
				chain.getVertex(i, vertices[i]);
				transform.mul(vertices[i]);
			}
			drawSolidPolygon(vertices, vertexCount, color, true);
			return;
		}

		if (fixture.getType() == Type.Chain) {
			ChainShape chain = (ChainShape) fixture.getShape();
			int vertexCount = chain.getVertexCount();
			for (int i = 0; i < vertexCount; i++) {
				chain.getVertex(i, vertices[i]);
				transform.mul(vertices[i]);
			}
			drawSolidPolygon(vertices, vertexCount, color, false);
		}
	}

	private void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
		float angle = 0;
		float angleInc = 2 * (float) Math.PI / 20;
		renderer.setColor(color.r, color.g, color.b, color.a);
		for (int i = 0; i < 20; i++, angle += angleInc) {
			v.set((float) Math.cos(angle) * radius + center.x, (float) Math.sin(angle) * radius + center.y);
			if (i == 0) {
				lv.set(v);
				f.set(v);
				continue;
			}
			renderer.line(lv.x, lv.y, v.x, v.y);
			lv.set(v);
		}
		renderer.line(f.x, f.y, lv.x, lv.y);
		renderer.line(center.x, center.y, 0, center.x + axis.x * radius, center.y + axis.y * radius, 0);
	}

	private void drawSolidPolygon(Vector2[] vertices, int vertexCount, Color color, boolean closed) {
		renderer.setColor(color.r, color.g, color.b, color.a);
		lv.set(vertices[0]);
		f.set(vertices[0]);
		for (int i = 1; i < vertexCount; i++) {
			Vector2 v = vertices[i];
			renderer.line(lv.x, lv.y, v.x, v.y);
			lv.set(v);
		}
		if (closed)
			renderer.line(f.x, f.y, lv.x, lv.y);
	}

	private Color getColorFixture(Fixture fixture) {
		switch (fixture.getFilterData().categoryBits) {
		case CrachGame.OBJECT_BIT:
			return SHAPE_OBJECT_BIT;
		case CrachGame.CRACH_BIT:
		case CrachGame.FRENDS_BIT:
		case CrachGame.LAMAS_BIT:
			return SHAPE_CRACH;
		case CrachGame.DOOR_BIT:
			return SHAPE_KINEMATIC;
		case CrachGame.ENIMY_BIT:
			return SHAPE_ENIMY;
		case CrachGame.CRYSTAL_BIT:
			return SHAPE_BICKUP;
		case CrachGame.BOMB_BIT:
			return SHAPE_BOMB;
		}
		return Color.WHITE;
	}

	public void translate(Matrix4 matrix, float widthViewPort, float heightViewPort) {
		renderer.setProjectionMatrix(matrix);
		PosX = widthViewPort  - 20; PosY = heightViewPort  - 100;
		renderer.getProjectionMatrix().translate(PosX- widthMap, PosY- heightMap, 0);
		this.setPosition(PosX - getWidth()-5, PosY -getHeight() -5);
	}
	public void setMapVeiw(float worldWidth, float worldHeight) {
		this.widthMapView  = worldWidth;
		this.heightMapView = worldHeight;
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	private float[] verticesEnimy = MathUtils.div(new float[] { 0f, 0f, -114.493f, -0.552795f, -114.493f, 17.1366f,
			-97.2687f, 17.6894f, -96.7621f, 70.205f, -113.987f, 69.6522f, -113.987f, 87.3416f, 0.506608f, 88.4472f, 0,
			70.7578f, -49.141f, 70.205f, -48.1278f, 52.5155f, -15.7048f, 52.5155f, -15.4515f, 35.6553f, -49.6476f,
			34.8261f, -49.6476f, 17.6894f, 0.506608f, 17.1366f, 0.337738f, 2.57971f }, CrachGame.PPM);

	private float[] verticesBomb = MathUtils.div(new float[] { 0f, 0f, -17.0225f, 6.78717f, -30.9499f, 18.8533f,
			-35.2055f, 36.9524f, -31.3368f, 54.6744f, -18.1831f, 65.9864f, 1.16062f, 72.7736f, 20.5043f, 66.7405f,
			33.658f, 54.6744f, 39.4611f, 36.1983f, 33.2712f, 18.0991f, 20.5043f, 5.27891f, 1.40242f, 0.235666f,
			3.43351f, -8.6725f, 10.1071f, -15.6011f, 9.962f, -24.2264f, 11.5579f, -17.1565f, 19.8273f, -19.7017f,
			12.7185f, -16.7323f, 14.8946f, -10.7935f, 11.5579f, -15.8839f, 3.28843f, -18.4291f }, CrachGame.PPM);

	private float[] verticesExit = MathUtils
			.div(new float[] { 0f, 0f, 44.0206f, -0.773585f, 44.0206f, 71.9434f, 1.4433f, 71.1698f, 1.4433f, 5.41509f,
					-25.2577f, -10.0566f, -25.9794f, 57.2453f, 0f, 70.3962f, -24.5361f, -8.50943f }, CrachGame.PPM);

	private float[] verticesShell = MathUtils.div(new float[] { 0f, 0f, 33.6699f, 15.9515f, 84.7917f, 15.3004f,
			95.5449f, 21.3229f, 101.01f, 30.9264f, 95.8974f, 41.5065f, 84.4391f, 46.8779f, 33.4936f, 46.8779f,
			-0.352564f, 62.6667f, 17.0994f, 47.2035f, -1.58654f, 49.4823f, 16.7468f, 36.2978f, -8.99038f, 31.4961f,
			16.7468f, 25.3108f, -1.85096f, 13.8355f, 16.9231f, 15.626f, -0.338792f, 0.0305195f }, CrachGame.PPM);

	private float[] verticesCrystal = MathUtils.div(new float[] { 0f, 0f, -25.7506f, 9.254f, 0f, 18.508f, 0f, 37.016f,
			-26.3104f, 9.44679f, 1.11959f, -19.472f, 1.53944f, -1.15675f, 27.1501f, 9.254f, 0.699746f, 37.3052f,
			0.699746f, 46.8484f, -27.43f, 8.96481f, 1.11959f, -28.3404f, 1.53944f, -19.5202f, 27.1501f, 8.96481f,
			0.699746f, 46.4146f, 1.11959f, 18.508f, 27.57f, 8.67562f, 1.95929f, -28.485f }, CrachGame.PPM);

	private float[] verticesPlayer = MathUtils.div(new float[] { 0f, 0f, -33.9053f, 0.493305f, -33.9053f, 40.9443f,
			1.58931f, 41.9309f, 1.58931f, 31.6899f, 52.034f, 31.6899f, 52.034f, 9.3728f, 0f, 9.3728f, 0f, -10.3594f,
			46.09f, -11.346f, 46.6197f, -32.0648f, -78.9357f, -30.0916f, -77.8762f, -10.3594f, -54.5663f, -10.8527f,
			-55.0961f, 51.3037f, -77.8762f, 52.2903f, -77.3464f, 73.9958f, 47.1495f, 72.5159f, 47.1495f, 51.3037f,
			-0.52977f, 51.797f, -50.8579f, 52.2903f, -50.3281f, -10.8527f, -4.76793f, -10.8527f, -5.2977f, 51.797f,
			0.52977f, 51.3037f, 1.05954f, 41.4376f }, CrachGame.PPM);

	private float[] verticesGun = MathUtils.div(new float[] { 0f, 0f, -12.58f, 5.54602f, -25.9462f, 15.4056f, -32.2362f,
			30.195f, -33.0225f, 49.2979f, -25.9462f, 62.8549f, -12.58f, 73.3307f, -0.78625f, 78.2605f, 13.3662f,
			72.0982f, 29.4844f, 54.6386f, 99.8537f, 54.2277f, 102.999f, 59.1575f, 108.109f, 49.0925f, 108.371f,
			21.9787f, 103.916f, 12.3245f, 100.64f, 16.4326f, 28.9602f, 16.8435f, 12.711f, 3.90275f, 0.393125f, 2.15679f,
			8.84531f, -8.01092f, 8.45219f, -14.4813f, 1.76906f, -20.9516f, -5.11062f, -20.0273f, -8.05906f, -11.4002f,
			-3.145f, -7.7028f, -0.78625f, -9.85959f }, CrachGame.PPM);
	private float[] verticesDanger = MathUtils.div(new float[] { 27.8036f, -1.91993f, -0.281413f, 1.06101f, -0.450261f,
			67.9049f, 23.4136f, 73.5636f, 23.4136f, 102.464f, 29.267f, 102.464f, 35.1204f, 89.3273f, 45.0261f, 89.7315f,
			48.1779f, 103.07f, 55.3821f, 102.666f, 57.1831f, 73.3615f, 87.1255f, 69.5217f, 85.9998f, 4.64825f, 62.5863f,
			-1.61678f, 62.8114f, 16.3699f, 70.691f, 16.572f, 69.7904f, 43.8552f, 52.0051f, 49.3119f, 53.1308f, 15.7636f,
			61.2355f, 16.1678f, 61.6858f, -1.41469f, 29.267f, -2.02098f, 28.3664f, 14.1469f, 17.335f, 14.551f, 17.1099f,
			42.8448f, 33.9947f, 47.2909f, 36.0209f, 14.551f, 28.8167f, 14.1469f }, CrachGame.PPM);


}
