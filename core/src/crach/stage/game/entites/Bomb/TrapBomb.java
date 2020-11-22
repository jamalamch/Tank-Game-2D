package crach.stage.game.entites.Bomb;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.CrachGame;
import crach.stage.game.entites.Entity;
import crach.stage.game.entites.Explosion;
import crach.stage.game.entites.InZone;
import crach.stage.game.entites.Zone.ZonObject;

public abstract class TrapBomb extends Entity implements Explosion,InZone{
	
	protected  Animation<TextureRegion> animationIdle;
	protected  Animation<TextureRegion> animationExplosion ;
	protected  Animation<TextureRegion> animation;
	
	protected Fixture fireTrap;
	
	protected boolean attack,pause;
	protected float  angel;
	
	protected float dt_time,timeAttack;

	protected float Domage = 20;
	
	protected float Width,  Height, TrapWidth,TrapHeigh;
	
	public TrapBomb(MapObject object,float Width,float Height,float TrapWidth,float TrapHeigh) {
		
		this(((RectangleMapObject) object).getRectangle().getPosition(new Vector2()), object.getProperties().get("derect",0, Integer.class),
				Width,  Height, TrapWidth,TrapHeigh);
		ZonObject.addToZone( this, object.getProperties().get("zone", String.class));
	}
	public TrapBomb(Vector2 posiVector2,int derection,float Width,float  Height,float TrapWidth,float TrapHeigh) {
		this.Width=Width;
		this.Height=Height;
		this.TrapWidth=TrapWidth;
		this.TrapHeigh=TrapHeigh;
		this.angel = getAngle(derection);
		posiVector2.scl(1/CrachGame.PPM);
		defineEntity(posiVector2.x, posiVector2.y, this.angel*MathUtils.degreesToRadians);
		setTexture();
		setPosition(posiVector2.x, posiVector2.y);
	}

	public void setTexture(Animation<TextureRegion> animationIdle,Animation<TextureRegion> animationExpl) {
		 this.animation =this.animationIdle= animationIdle;
		this.animationExplosion = animationExpl;
 	    setRegion(animation.getKeyFrame(dt_time));
        setZIndex(Zindex.Zindexfire);
	}
	
	@Override
	public void defineEntity(float X, float Y, float R) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(X,Y);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape.setAsBox( Width, Height);
        fdef.shape = shape;
        fdef.isSensor=true;
        fdef.filter.categoryBits = CrachGame.BOMB_BIT;
        fdef.filter.maskBits     = CrachGame.CRACH_BIT|CrachGame.BOMB_BIT | CrachGame.ENIMY_BIT|
        						   CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT |CrachGame.EXPLOSION_BIT;
        b2body.createFixture(fdef);
        b2body.setUserData(this);
        shape.dispose();
	}
	
	protected abstract void starAttack() ;
	protected abstract void pauseAttack() ;
	
	protected void tweenToAttack(){
		creator.TWEEN_MANAGER.killTarget(this);		
		Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				AddZoneOfDestor();
			}
		 }).start(creator.TWEEN_MANAGER);
	}
	protected void tweenToPauser(){
		creator.TWEEN_MANAGER.killTarget(this);		
		Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				b2body.destroyFixture(fireTrap);
			}
		 }).start(creator.TWEEN_MANAGER);
	}
	
	protected void AddZoneOfDestor() {		
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Vector2 Zon = new Vector2(TrapWidth - Width,  0);
        shape.setAsBox(TrapWidth,TrapHeigh,Zon,0);
        fdef.shape = shape;
        fdef.isSensor=true;
        fdef.filter.categoryBits = CrachGame.EXPLOSION_BIT;	  
        fdef.filter.maskBits =  CrachGame.CRACH_BIT|CrachGame.BOMB_BIT |CrachGame.FIRE_BIT| CrachGame.ENIMY_BIT|
        						CrachGame.OBJECT_BIT |CrachGame.DOOR_BIT|CrachGame.EXPLOSION_BIT;
        fireTrap = b2body.createFixture(fdef);
        shape.dispose();
	}
	
	@Override
	public void onContactStart(Entity otherEntity) {
			AntyReaction(this, otherEntity, (attack)?5:1);
	}
	
	public void setStop(boolean stop) {
		this.Destore=stop;
	}
	
	@Override
	public float getDanger() {
		return Domage;
	}

	@Override
	public boolean isExplose() {
		return true;
	}
	
	@Override
	public void draw(Batch batch, Body body) {
		setWidth(this.getRegionWidth()/CrachGame.PPM);
		setHeight(this.getRegionHeight()/CrachGame.PPM);
		setOrigin(0, getHeight()/2);
		batch.draw(this
				, getX()
				, getY() - getOriginY()
				, getOriginX()
				, getOriginY()
				, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	@Override
	public void Getting(Entity otherEntity) {
		this.setStop(false);
		if(!attack) this.starAttack();
	}
	@Override
	public void Drifting(Entity otherEntity) {
		this.setStop(true);
	}
}
