package crach.stage.game.entity.object;

import com.badlogic.gdx.maps.MapObject;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import crach.stage.game.CrachGame;

public abstract class M_Circle extends Object{

	public M_Circle(MapObject object) {
		super(object);
	}
	public void defineEntity(float X, float Y, float R, float radius, float LinearDamp, float AngularDamp,float density,float friction,float restitution) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.linearDamping=LinearDamp;
        bdef.angularDamping=AngularDamp;
        bdef.angle = R;
        b2body = world.createBody(bdef);
        b2body.setUserData(this);

        bodyDef(X,Y,R,LinearDamp,AngularDamp);

        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        shape.setRadius(radius/CrachGame.PPM);
        fdef.shape = shape;
        fdef.friction=friction;
        fdef.restitution = restitution;
        fdef.density=density;
        fdef.filter.categoryBits = CrachGame.OBJECT_BIT;
        b2body.createFixture(fdef);
        shape.dispose();	  
	}

    @Override
    public String toString() {
        return "M_Circle";
    }
}
