package crach.stage.game.entity.Object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;

public abstract class  Box extends Object{	


	public Box(String id,float x,float y) {
		super(x, y);
		this.Id=id;
	}
	public Box(String id,float x,float y,float r) {
		super(x, y, r);
         this.Id=id;
	}
	public Box(MapObject object) {
		super(object);
	}
	public void setTexture(TextureRegion texture) {
 	    setRegion(texture);
        setOriginCenter();
        setZIndex(Zindex.ZindexBox);
	}

	public void defineEntity(float X, float Y, float R, float Hieght ,float Width, float LinearDamp, float AngularDamp,float density) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=LinearDamp;
        bdef.angularDamping=AngularDamp;
        bdef.position.set(X,Y);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setAsBox( Width/CrachGame.PPM, Hieght/CrachGame.PPM);
        fdef.shape = shape;
        fdef.density=density;
        fdef.filter.categoryBits = CrachGame.OBJECT_BIT;
        b2body.createFixture(fdef);
        b2body.setUserData(this);
        shape.dispose();
	}


	public static class A extends Box{

		public A(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureBox.get(0));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 50, 50, 1.5f, 1.9f,0.065f);
		}
	}
	public static class B extends Box{

		public B(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureBox.get(0));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 50, 50, 2, 2,0.07f);
		}
	}
	public static class C extends Box{

		public C(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureBox.get(0));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 50, 50, 2.7f, 2.6f,0.06f);
		}
	}
	public static Box CreateBox(MapObject object){
		Box B = null;
		int codetype = object.getProperties().get("code", MathUtils.random(3), Integer.class);
		switch(codetype) {
			case 0:
				B = new A(object);
				break;
			case 1:
				B = new B(object);
				break;
			case 2:
				B = new C(object);
				break;
			case 3:
				B = new BoxInte(object);
			break;
		}
		return B;
	}
}
