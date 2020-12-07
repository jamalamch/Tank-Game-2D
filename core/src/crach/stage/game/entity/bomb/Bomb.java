package crach.stage.game.entity.bomb;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;
import crach.stage.game.utils.SpriteAccessor;


public  abstract class Bomb extends Entity implements Explosion{
	
	protected  Animation<TextureRegion> animationTriggering;
	protected  Animation<TextureRegion> animationExplosion ;
	protected  Animation<TextureRegion> animation;
	
	protected boolean Explosion;
	
	protected float dt_time = MathUtils.random(0.5f);
	protected float lifeTime;
		
	protected float zonDeath ;
	protected float Domage = 110;
	
	public Bomb() {}
	public Bomb(MapObject object) {
		defineEntity(object);
 		setTexture();
	}
	public Bomb(MapObject object,float r) {
		defineEntity(object,r);
 		setTexture();
	}
	public Bomb(float x,float y) {
		this(x, y, (float)(Math.random()*1.7));
	}
	public Bomb(float x,float y,float r) {
		setTexture();
		defineEntity(x,y,r);
	}
	public void setTexture(Animation<TextureRegion> animationIdle,Animation<TextureRegion> animationTrigge,Animation<TextureRegion> animationExpl) {
		this.animation = animationIdle;
		this.animationTriggering = animationTrigge;
		this.animationExplosion = animationExpl;
 	    setRegion(animation.getKeyFrame(dt_time));
        setOriginCenter();
        setZIndex(Zindex.ZindexBomb);
	}
	@Override
	public void update(float dt) {
		dt_time +=dt;
		setRegion(animation.getKeyFrame(dt_time,true));
}
	@Override	
	public void defineEntity(float X, float Y, float R,float radius,float zonDeath) {
//		this.defineEntity(X, Y, R, 4, 2, true, 0.1f, 0.7f, radius, zonDeath);
		bodyDef(X,Y,R);
		createFixtureCircle(radius/CrachGame.PPM,true);
		this.zonDeath=zonDeath;
	}
	public void defineEntity(float X, float Y, float R,float LinearDamp, float AngularDamp,boolean isSensor,float density,float restitution,float radius,float zonDeath) {
        BodyDef bdef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(X,Y);
        bdef.linearDamping=LinearDamp;
        bdef.angularDamping=AngularDamp;
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setRadius(radius /CrachGame.PPM);
        fdef.shape = shape;
        fdef.isSensor=isSensor;
        fdef.restitution=restitution;
        fdef.density = density;
        fdef.filter.categoryBits = CrachGame.BOMB_BIT;
        fdef.filter.maskBits     = CrachGame.CRACH_BIT|CrachGame.BOMB_BIT | CrachGame.ENIMY_BIT|
        						   CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT |CrachGame.EXPLOSION_BIT;
        b2body.createFixture(fdef);
        shape.dispose();	  
        b2body.setUserData(this);
		this.zonDeath=zonDeath;
	}

	@Override
	public void onContactStart(Entity otherEntity) {
		if(!destroy)
			deathEntity();
	}
	
	@Override
	public void deathEntity() {
    	destroy = true;
    	dt_time = 0;
		animation = animationExplosion;
    	tweenDeathAnim();
	}
	
	private void AddZoneOfDestor() {
		Assets.soundExplodeBomb.play(getVolume(0.4f),1,getPan());

        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        shape.setRadius(zonDeath /CrachGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = CrachGame.EXPLOSION_BIT;
        fdef.filter.maskBits =  CrachGame.CRACH_BIT|CrachGame.BOMB_BIT |CrachGame.FIRE_BIT| CrachGame.ENIMY_BIT|
        						CrachGame.OBJECT_BIT |CrachGame.DOOR_BIT|CrachGame.EXPLOSION_BIT;
        b2body.createFixture(fdef);

        shape.dispose();
	}
	
    public void draw(Batch batch, Body body) {  
    	if(!destroy) {
    		setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
			setPosition(b2body.getPosition().x , b2body.getPosition().y);
    	}

		final float WidthS = (this.getRegionWidth()*0.8f)/CrachGame.PPM, HiegthS = (this.getRegionHeight()*0.8f)/CrachGame.PPM;

		batch.draw(this
				, getX() - WidthS/2
				, getY()- HiegthS/2
				, WidthS/2
				, HiegthS/2
				, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
    }
    
	private void tweenDeathAnim() {
		creator.TWEEN_MANAGER.killTarget(this);		
		Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				AddZoneOfDestor();
			}
		 }).delay(0.5f).start(creator.TWEEN_MANAGER);
		
		 Timeline.createSequence().beginSequence()
		.push(Tween.to(this, SpriteAccessor.SCALE, 0.1f).target(1.1f,1.1f)).repeat(4, 0)
		.push(Tween.to(this, SpriteAccessor.SCALE, 0).target(1,1))
		.pushPause(1)
		.end().start(creator.TWEEN_MANAGER);		
	}
	@Override
	public boolean isDeath() {
		return destroy;
	}
	
	@Override
	public float getDanger() {
		return Domage;
	}
	@Override
	public boolean isExplose() {
		return destroy;
	}
	
	@Override
	public void destroy() {
		super.destroy();
    	creator.TWEEN_MANAGER.killTarget(this);
    	creator.SetDestoryEntity(this);
	}
	@Override
	protected float getVolume() {
		return super.getVolume() +0.2f;
	}
	public static Bomb CreateBomb(MapObject object){
		Bomb B = null;
		int codetype = object.getProperties().get("code", MathUtils.random(2), Integer.class);
		switch(codetype) {
			case 0:
				B = new Bomb1(object);
				break;
			case 1:
				B = new Bomb2(object);
				break;
			case 2:
				B = new Bomb3(object);
				break;
		}
		return B;
	}

	@Override
	public float getLinearDamp() {
		return 4;
	}

	@Override
	public float getAngularDamp() {
		return 2;
	}

	@Override
	public float getDensity() {
		return 0.1f;
	}

	@Override
	public float getRestitution() {
		return 0.7f;
	}

	@Override
	public short getCategoryBits() {
		return CrachGame.BOMB_BIT;
	}

	@Override
	public short getMaskBits() {
		return CrachGame.CRACH_BIT|CrachGame.BOMB_BIT | CrachGame.ENIMY_BIT|
				CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT |CrachGame.EXPLOSION_BIT;
	}

	@Override
	public String toString() {
		return "Bomb{" +
				", lifeTime=" + lifeTime +
				", Domage=" + Domage +
				'}';
	}
}
