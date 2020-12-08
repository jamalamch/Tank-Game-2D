package crach.stage.game.entity.door;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.crach.CrachPlayer;
import crach.stage.game.entity.enimy.Enimy;

public abstract class Door extends Entity {
	protected float height = 15 / CrachGame.PPM;
	protected float width;

	
	protected float X,Y;
	protected boolean toOpen, toClose;
	protected float rotat90 = 0;
	private float Dt_time = 0;
	

    public Derection DerType;
    
    
	public Door(MapObject object, int DerType) {
    	this.DerType = Door.Derection.values()[DerType];
		Rectangle rect = ((RectangleMapObject) object).getRectangle();
		X = (rect.getX() + rect.getWidth() / 2) / CrachGame.PPM;
		Y = (rect.getY() + rect.getHeight() / 2) / CrachGame.PPM;
		
		switch (this.DerType) {
		case Top:
			this.rotat90 = -90;
			this.width = rect.getHeight() / 2 / CrachGame.PPM;
			break;	
		case Left:
			this.width = rect.getWidth() / 2 / CrachGame.PPM;
			break;	
		case Bottom:
			this.rotat90 = 90;
			this.width = rect.getHeight() / 2 / CrachGame.PPM;
			break;
		case Right:
			this.rotat90 = 180;
			this.width = rect.getWidth() / 2 / CrachGame.PPM;
			break;
		}
		this.setTexture();
		setZIndex(Zindex.ZindexDoor);
	}
	@Override
	public void defineEntity(float x, float y, float angle) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set(x, y);
		bdef.angle = MathUtils.degreesToRadians * rotat90;
		b2body = world.createBody(bdef);
		shape.setAsBox(width, height);
		fdef.shape = shape;
		fdef.filter.categoryBits = CrachGame.DOOR_BIT;
		b2body.createFixture(fdef);
		b2body.setUserData(this);
		shape.dispose();
		
}


	public void addtoDourder(float X, float Y, float Width, float Height) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(X, Y);
		bdef.angle = MathUtils.degreesToRadians * rotat90;
		Body body = world.createBody(bdef);
		shape.setAsBox(Width, Height);
		fdef.shape = shape;
		fdef.filter.categoryBits = CrachGame.OBJECT_BIT;
		body.createFixture(fdef);
		shape.dispose();
	}

	@Override
	public void update(float dt) {
		if (toOpen) {
			if(Dt_time==0) {
				final float dst1 = 2 * width;
			if (b2body.getPosition().dst(X, Y) > dst1) {
				System.out.println(" To Close Door ");
				b2body.setLinearVelocity(0, 0);
				Dt_time = 8;
		    }
		    }else { Dt_time -=  dt;  if(Dt_time< 0) closeDoor();}
			}
		if (toClose) {
				if (b2body.getPosition().dst(X, Y) <= 0.5f) {
					StopMove();
				}
			}
	}

	@Override
	public void onContactStart(Entity otherEntity) {
		if(otherEntity instanceof CrachPlayer |otherEntity instanceof Enimy)
			if (!toOpen)
				openDoor();
	}

	protected void openDoor() {
		Assets.soundDoor.play();
		toOpen = true;
       switch (DerType) {
   	case Top:
		b2body.setLinearVelocity(0, 10);
		break;
	case Left:
		b2body.setLinearVelocity(-10, 0);
		break;
	case Bottom:
		b2body.setLinearVelocity(0, -10);
		break;
	case Right:
		b2body.setLinearVelocity(10, 0);
		break;
	}
	}
    
	public void closeDoor() {
		toClose = true;
		toOpen = false;
		Dt_time=0;
	       switch (DerType) {
	   	case Top:
	   		b2body.setLinearVelocity(0, -3);
	   		break;
	   	case Left:
	   		b2body.setLinearVelocity(3, 0);
	   		break;	   	
	   	case Bottom:
	   		b2body.setLinearVelocity(0, 3);
	   		break;
	   	case Right:
	   		b2body.setLinearVelocity(-3, 0);
	   		break;
	   	}
	}
	public boolean Open() {
		if(toOpen || toClose)
			return true;
		return false;		
	}
	protected void StopMove() {
		b2body.setLinearVelocity(0, 0);
		toOpen = false;
		toClose = false;
	}

	@Override
	public String toString() {
		return "Door{" +
				", toOpen=" + toOpen +
				", toClose=" + toClose +
				", rotat90=" + rotat90 +
				", Dt_time=" + Dt_time +
				", DerType=" + DerType +
				'}';
	}
}
