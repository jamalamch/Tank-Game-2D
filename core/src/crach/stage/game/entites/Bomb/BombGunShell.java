package crach.stage.game.entites.Bomb;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.entites.Entity;
import net.dermetfan.gdx.physics.box2d.RotationController;

public abstract class BombGunShell extends Bomb{
	
    public RotationController.P toRoration ;
    private Vector2 vitass = new Vector2(30, 30);
    private Body target;
    protected TextureRegion adlTexture;
    protected long soundMotor;
    
	public BombGunShell(MapObject object,Body target) {
		this.target = target;
		this.lifeTime = 10;
		this.Domage = 60;
	    toRoration = new RotationController.P(3, 0);
	    Rectangle rect = ((RectangleMapObject) object).getRectangle();
	    Vector2 postion =  rect.getPosition(new Vector2());
	    postion.scl(1/CrachGame.PPM);
		defineEntity(postion.x,postion.y,getAngelToPosion(postion, target.getPosition()));
 		setTexture();
	}
	public BombGunShell(Vector2 posiVector,Body target,float lifeTime,float domage,float vitass,float rotation) {
		this.target = target;
		this.lifeTime = lifeTime;
		this.Domage = domage;
		this.vitass =  new Vector2(vitass, vitass);
	    toRoration = new RotationController.P(rotation, 0);
		defineEntity(posiVector.x,posiVector.y,getAngelToPosion(posiVector, target.getPosition()));
 		setTexture();
	}

	public void setTexture(TextureRegion teRegion,Animation<TextureRegion> animationIdle,Animation<TextureRegion> animationExpl) {
		setTexture(animationIdle, null,animationExpl);
		this.adlTexture = teRegion;
		setZIndex(Zindex.Zindexfire);
	}
	
	public void defineEntity(float X, float Y, float R,float raduis,float radiZon) {
        BodyDef bdef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.linearDamping=2f;
        bdef.angularDamping=1.2f;
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setRadius(raduis /CrachGame.PPM);
        fdef.shape = shape;
        fdef.density = 0.015f;
        fdef.filter.categoryBits = CrachGame.BOMB_BIT;
        fdef.filter.maskBits = CrachGame.CRACH_BIT |CrachGame.FIRE_BIT| CrachGame.ENIMY_BIT| CrachGame.EXPLOSION_BIT|
        					   CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT | CrachGame.FIRE_BIT| CrachGame.NOTHING_BIT;
        b2body.createFixture(fdef);
        shape.dispose();	  
        b2body.setUserData(this);
		this.zonDeath=radiZon;		
	}

	@Override
	public void update(float dt) {
		dt_time +=dt;
		lifeTime -=dt;
		
		if(!Explosion)
			setRegion(animation.getKeyFrame(dt_time,true));

		
		if(Destore) {
			if(animation.isAnimationFinished(dt_time)) {
		    	destroy();
			}
		}else if(Explosion) {
			if(lifeTime < -1)
				DeathEntity();
		}else { 
			UpdateToDeplace(target.getPosition());
			toRoration.applyTorque(getBody(), true);
			b2body.applyForceToCenter(vitass.setAngleRad(b2body.getAngle()), true);
			if(lifeTime < 0) {
				Explosion = true;
				setRegion(adlTexture);
			}
		}
}
    @Override
    public void UpdateToDeplace(Vector2 toPosion, float toAngle) {
    	toRoration.setAngle(toAngle);
    }
    
	@Override
	public void onContactStart(Entity otherEntity) {
		if(!Destore) {
			DeathEntity();
		}
	}
	@Override
	public void DeathEntity() {
		super.DeathEntity();
		Assest.Gun_Motor.stop(soundMotor);
		
		Explosion = false;
		b2body.setLinearVelocity(0, 0);
	}
	@Override
	public void draw(Batch batch, Body body) {
		final float WidthS = (this.getRegionWidth()*1.2f)/CrachGame.PPM, HiegthS = (this.getRegionHeight()*1.2f)/CrachGame.PPM;
    	if(!Destore && !Explosion) {
    		setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
			setPosition(b2body.getPosition().x , b2body.getPosition().y);
			batch.draw(this
					, getX() - WidthS
					, getY() -HiegthS/2
					, WidthS
					, HiegthS/2
					, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
    	}
    	else {
			batch.draw(this
					, getX() - WidthS/2
					, getY() -HiegthS/2
					, WidthS/2
					, HiegthS/2
					, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
    	}



	}
	
    public void UpdateToDeplace(Vector2 toPosion) {
    		UpdateToDeplace(toPosion, getAngelToPosion(b2body.getWorldCenter(),toPosion));
    		Assest.Gun_Motor.setPan(soundMotor,getPan() ,getVolume(0.2f));
    }
    public float getAngelToPosion(Vector2 InPosiotion,Vector2 toPosion) {
    	Vector2 chemain = new Vector2(toPosion);
        chemain.sub(InPosiotion);
        float rad = chemain.angleRad();
		return rad;
    }
    @Override
    public boolean isExplose() {
    	return true;
    }
    public static class A extends BombGunShell{

		public A(Vector2 posiVector, Body target) {
			super(posiVector, target,30,60,30,3);
	 		soundMotor = Assest.Gun_Motor.loop(getVolume(0.2f));
		}
		@Override
		public void defineEntity(float X, float Y, float R) {
			super.defineEntity(X, Y, R,27,120);
		}

		@Override
		public void setTexture() {		
			setTexture(Assest.comet_a, Assest.comet_animation_a, Assest.impact_asteroid_c);
		}
    	
    }
    public static class B extends BombGunShell{

		public B(Vector2 posiVector, Body target) {
			super(posiVector, target,20,60,30,1);
	 		soundMotor = Assest.Gun_Motor.loop(getVolume(0.2f));
		}
		@Override
		public void defineEntity(float X, float Y, float R) {
			super.defineEntity(X, Y, R,18,60);
		}

		@Override
		public void setTexture() {		
			setTexture(Assest.comet_b, Assest.comet_animation_b, Assest.impact_asteroid_b);
		}
    	
    }
}
