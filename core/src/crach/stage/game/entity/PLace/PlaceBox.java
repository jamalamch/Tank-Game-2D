package crach.stage.game.entity.PLace;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Object.Box;

public class PlaceBox extends Place {
	private Joint currentJoint;
	private int pose;
	private int Place = 1;
    public PlaceBox(MapObject object) {
             super(object);
    }
    
	@Override
	public void defineEntity(float X, float Y, float R) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(X,Y);

        b2body = world.createBody(bdef);
        
        shape.setAsBox( hieght/CrachGame.PPM, hieght/CrachGame.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef);        
        shape.dispose();
        
        fdef = new FixtureDef();
        shape = new PolygonShape();
        shape.setAsBox( 5/CrachGame.PPM, 5/CrachGame.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = CrachGame.PLACE_BOX;
        fdef.filter.maskBits=CrachGame.OBJECT_BIT;
        b2body.createFixture(fdef);        
        shape.dispose();        
        b2body.setUserData(this);
	}
	public void setTexture() {
		setRegion(Assets.texturePlatform);
        setOriginCenter();
        setZIndex(Zindex.ZindexPlace);
	}
		@Override
	public void update(float dt) {
		switch (interaction){  
		case CrachPlyer:
		case NONE :
			break;
		case BOX:
			    updateBoxInteraction() ;
		break;
		}
	}
	@Override
	public void onContactStart(Entity otherEntity) {

		if (otherEntity instanceof Box) {
			if (interaction != Interaction.BOX)
				setInteraction(Interaction.BOX, otherEntity);
			setRegion(Assets.texturePlatformActive0);
		}

	}
	@Override
	public void onContactEnd(Entity otherEntity) {

		if (otherEntity instanceof Box) {
			if (interaction == Interaction.BOX && currentJoint == null) {
				setInteraction(Interaction.NONE, null);
				setRegion(Assets.texturePlatform);
			}
		}

	}

	public void setInteraction(Interaction interaction, Entity interactEntity) {
		this.interactEntity = (Box) interactEntity;
		this.interaction = interaction;
	}

	private void updateBoxInteraction() {
		final Box box = (Box) this.interactEntity;
		if (box.isEnabled()) {
			startInteractionWithBox(box);
			box.setState(crach.stage.game.entity.Object.Box.State.PLACE);
		} else {
			endInteractionWithBox();
		}
	}

	
	public void endInteractionWithBox() {
		if (currentJoint == null)
			return;
		setInteraction(Interaction.NONE, null);

		world.destroyJoint(currentJoint);
		currentJoint = null;
	}

	private void startInteractionWithBox(Box box) {
		if (currentJoint != null)
			return;
		if (!box.isEnplace()) {

			PrismaticJointDef jointDef = new PrismaticJointDef();

			jointDef.initialize(getBody(), box.getBody(), getBody().getWorldCenter(), new Vector2(1, 1));
			jointDef.motorSpeed = 100.0f;
			jointDef.enableMotor = true;
			jointDef.lowerTranslation = 10f;
			jointDef.upperTranslation = 10f;
			jointDef.enableLimit = true;

			currentJoint = world.createJoint(jointDef);
			//Gdx.app.log(" connect  ", " box " + box.getLettercode() + "and placebox " + getPose() + " Place" + Place);
		}
	}

	public Box disposeInteract(Box box) {
		endInteractionWithBox();
		return box;
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
