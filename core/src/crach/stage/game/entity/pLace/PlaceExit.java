package crach.stage.game.entity.pLace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.crach.CrachPlayer;

public class PlaceExit extends Place{
	private boolean  Active = true;
	private int pose;
	private int Place = 0;
	private float dt_time;
	private TextureRegion animation1 ,animation2,region;
    public PlaceExit(MapObject object) {
             super(object);
    }

	@Override
	public void defineEntity(float x, float y, float angle) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(x, y);

        b2body = world.createBody(bdef);
        
        shape.setAsBox( hieght/CrachGame.PPM, hieght/CrachGame.PPM);
        fdef.filter.categoryBits = CrachGame.NOTHING_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef);        
        shape.dispose();
        
        fdef = new FixtureDef();
        shape = new PolygonShape();
        shape.setAsBox( 5/CrachGame.PPM, 5/CrachGame.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = CrachGame.PLACE_BIT;
        fdef.filter.maskBits=CrachGame.CRACH_BIT ;
        b2body.createFixture(fdef);        
        shape.dispose();        
        b2body.setUserData(this);
	}
	public void setTexture() {
        animation1 = Assets.texturePlatformActive0;
        animation2 = Assets.texturePlatformActive1;
		region = Assets.texturePlatform;
		setRegion(region);
        setOriginCenter();
        setZIndex(Zindex.ZindexPlace);
	}
	
	@Override
	public void onContactStart(Entity otherEntity) {

		if (otherEntity instanceof CrachPlayer && Active) {
			if (interaction != Interaction.CrachPlyer) {
				setInteraction(Interaction.CrachPlyer, otherEntity);
			}
		}

	}
	@Override
	public void update(float dt) {
		if(Active) {
			dt_time+= dt;
		if(dt_time > 0.5f) {
			region = (region == animation1) ? animation2 : animation1;
			setRegion(region);
			dt_time= 0;
		}
		}
	}
	public void setInteraction(Interaction interaction, Entity interactEntity) {
		this.interactEntity = (CrachPlayer) interactEntity;
		this.interaction = interaction;
		creator.victorGame();
	}
		
	public void setActive(boolean active) {
		Active = active;
	}

	public int getPose() {
		return pose;
	}

	public int getPlace() {
		return Place;
	}

	public void setPose(int pose) {
		this.pose = pose;
	}
}
