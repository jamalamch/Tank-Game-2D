package crach.stage.game.entites.Zone;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.CrachGame;
import crach.stage.game.entites.Entity;

public abstract class Zone extends Entity{
	
	protected short maskBit;
	
	public Zone(MapObject object,short maskBit) {
		this.maskBit=maskBit;
		defineEntity(object);
		setZIndex(Zindex.Zindexfire);
	}
	@Override
	public void setTexture() {
	}
	@Override
	public void defineEntity(float X, float Y, float R, float Width, float Height) {
		
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(X,Y);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setAsBox( Width, Height);
        fdef.shape = shape;
        fdef.isSensor=true;
        fdef.filter.categoryBits = CrachGame.ZONE_BIT;
        fdef.filter.maskBits = maskBit;
        b2body.createFixture(fdef);
        b2body.setUserData(this);
        shape.dispose();
	}
	@Override
	public void draw(Batch batch, Body body) {
	}
}
