package crach.stage.game.entity.Object;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Explosion;
import crach.stage.game.utils.SpriteAccessor;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

 public abstract  class Barell extends M_Circle implements Explosion{
	private static Animation<TextureRegion> destoryBarell =  Assest.fire_smoke;
	private static Animation<TextureRegion> ExplosionBarell = Assest.Sprite_Effects_Explosion;
	private Animation<TextureRegion> animationBarell = destoryBarell;
	private float RATA = 2;
	
	private boolean Explosion;
	
	private final static float deathDuration = destoryBarell.getAnimationDuration() - 0.4f;
	private float dt_time ;
	private float life = 40;
		
	public Barell(MapObject object) {
		super(object);
		setSize(80/CrachGame.PPM,80/CrachGame.PPM);
		setOriginCenter();
		this.Colorflash = new Color(0.5f, 0.6f, 0.4f,0.5f);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
 		if(destory) {
			dt_time +=dt;
			if(animationBarell.isAnimationFinished(dt_time)) {
				animationBarell = ExplosionBarell;
				dt_time = 0.5f;RATA = 0.85f;
				if(Explosion) {
					creator.SetDestoryEntity(this);
					creator.addExper(10);
					destroy();
				}else 
					AddZoneOfDestor();
		    }
		}
 		else
 			if(life < 0) DeathEntity();
	}
	
	@Override
	public void draw(Batch batch, Body body) {
	    setPosition(b2body.getPosition().x, b2body.getPosition().y);
	    setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
		
		batch.setColor(getColor());
		batch.draw(this,  getX() -getWidth()/2,  getY()-getHeight()/2,  getOriginX() ,  getOriginY() ,  getWidth(),  getHeight(), getScaleX(), getScaleY(),  getRotation());
		batch.setColor(Color.WHITE);
		if(destory) {
			TextureRegion AnimationF = animationBarell.getKeyFrame(dt_time);
			float WidthS = (AnimationF.getRegionWidth()*RATA)/CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*RATA)/CrachGame.PPM;
			batch.draw(AnimationF, getX()  -WidthS/2, getY() -HiegthS/2, WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
		}
	}
	@Override
	public void Hit(Explosion otherEntity) {
		Assest.explos_Barell_secret.play(getVolume(),1,getPan());
		life -= otherEntity.getDanger();
	}
	
	@Override
	public void DeathEntity() {
    	destory = true;
	    setPosition(b2body.getPosition().x, b2body.getPosition().y);
	    setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
	    setSize(Box2DUtils.size(getBody()).x,Box2DUtils.size(getBody()).y);
        setOriginCenter();
    	tweenDeathAnim();
	}
	
	private void tweenDeathAnim() {		

		creator.TWEEN_MANAGER.killTarget(this);
		Timeline.createSequence().beginSequence()
		.push(Timeline.createParallel().beginParallel()	
				 .push(Tween.to(this, SpriteAccessor.COLOR, deathDuration).target(0.4f, 0.4f, 0.4f))
				 .push(Tween.to(this, SpriteAccessor.ALPHA, deathDuration/2).target(0.7f)).end())
		.push(Tween.to(this, SpriteAccessor.SCALE, 0.4f).target(1.26f,1.26f))
		.push(Timeline.createParallel().beginParallel()	
				 .push(Tween.to(this, SpriteAccessor.SCALE, 0.12f).target(1.2f,1.2f))
				 .push(Tween.to(this, SpriteAccessor.ALPHA, 0.15f).target(0.2f)).repeat(3, 0)
				 .push(Tween.to(this, SpriteAccessor.ALPHA, 0).target(0))
				 .end())
		.end().start(creator.TWEEN_MANAGER);		
	}
	
	private void AddZoneOfDestor() {
		Assest.destroy_circular_blade.play(getVolume(0.5f),1,getPan());
		
		Explosion = true;
        CircleShape shape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        shape.setRadius(145 /CrachGame.PPM);
        fdef.shape = shape;
        fdef.friction =0.5f;
        fdef.density = 0.1f;
        fdef.restitution=0.7f;
        fdef.filter.categoryBits = CrachGame.EXPLOSION_BIT;	  
         b2body.createFixture(fdef);
        shape.dispose();
	}
	@Override
	public boolean isDeath() {
		return destory;
	}
	@Override
	public boolean isExplose() {
		return Explosion;
	}

public static class Barell1 extends Barell{

	public Barell1(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.Barell.get(0));
	}


	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 40, 2.2f, 2.6f, 0.03f,0.15f,0);
	}
	@Override
	public float getDanger() {
		return 130;
	}
}
public static class Barell2 extends Barell{

	public Barell2(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.Barell.get(1));
	}

	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 40, 2.5f, 2.8f, 0.026f,0.2f,0);
	}
	@Override
	public float getDanger() {
		return 160;
	}
}
public static class Barell3 extends Barell{

	public Barell3(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.Barell.get(2));
	}

	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 40, 3, 3.4f, 0.035f,0.1f,0);
	}
	@Override
	public float getDanger() {
		return 180;
	}
}
	public static Barell CreateBarell(MapObject object){
		Barell B = null;
		int codetype = object.getProperties().get("code", MathUtils.random(2), Integer.class);
		switch(codetype) {
			case 0:
				B = new Barell1(object);
				break;
			case 1:
				B = new Barell2(object);
				break;
			case 2:
				B = new Barell3(object);
				break;
		}
		return B;
	}
}
