package crach.stage.game.entity.zone;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;

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
	public void defineEntity(float x, float y, float angle, float width, float height) {
        bodyDef(x,y,angle);
        createFixtureBox(width, height,true);
	}

    @Override
    public short getCategoryBits() {
        return CrachGame.ZONE_BIT;
    }

    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.StaticBody;
    }

    @Override
    public short getMaskBits() {
        return maskBit;
    }

    @Override
	public void draw(Batch batch, Body body) {
	}

    @Override
    public String toString() {
        return "Zone{" +
                '}';
    }
}
