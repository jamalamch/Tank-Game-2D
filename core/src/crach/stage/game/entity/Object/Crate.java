package crach.stage.game.entity.Object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.Pickups.Crystal;
import crach.stage.game.utils.SpriteAccessor;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

public abstract class Crate extends Box{
	private static Animation<TextureRegion> destoryCrate =  Assets.animationImpactAsteroidA;
	private final static float deathDuration = 1;
	private float dt_time ;
	private float life = 35;
	private boolean Destore;
	public Crate(MapObject object) {
		super(object);
		b2body.setGravityScale(100);
		this.Colorflash = new Color(0.9f, 0.6f, 0.4f,0.5f);
	}
	

	@Override
	public void update(float dt) {
		super.update(dt);
 		if(Destore) {
			dt_time +=dt;
			if(destoryCrate.isAnimationFinished(dt_time)) {
		    	creator.SetDestoryEntity(this);
		    }
		}
 		else
 			if(life < 0) DeathEntity();
	}
	
	@Override
	public void draw(Batch batch, Body body) {
		if(Destore) {
			batch.setColor(getColor());
    		batch.draw(this,  getX() -getWidth()/2,  getY()-getHeight()/2,  getOriginX() ,  getOriginY() ,  getWidth(),  getHeight(), getScaleX(), getScaleY(),  getRotation());
			TextureRegion AnimationF = destoryCrate.getKeyFrame(dt_time);
			float WidthS = (AnimationF.getRegionWidth()*0.7f)/CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
			batch.setColor(Color.WHITE);
			batch.draw(AnimationF, getX()  -WidthS/2, getY() -HiegthS/2, WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
		}
		else
		super.draw(batch, body);
	}
	@Override
	public void Hit(Explosion otherEntity) {
		life -= otherEntity.getDanger();
		Assets.soundImpactObjectWall.play(getVolume(),1,getPan());
	}
	
	@Override
	public void DeathEntity() {
		Assets.soundHitCrateHeavy.play(getVolume(),1,getPan());
		creator.addExper(5);

    	Destore= true;
	    setPosition(b2body.getPosition().x, b2body.getPosition().y);
	    setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
	    setSize(Box2DUtils.size(getBody()).x,Box2DUtils.size(getBody()).y);
        setOriginCenter();
    	tweenDeathAnim();
	}
	
	private void tweenDeathAnim() {
		creator.TWEEN_MANAGER.killTarget(this);
		Timeline.createSequence().beginSequence()
		.push(Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				destroy();
			}
		}))
		.push(Timeline.createParallel().beginParallel()	
				 .push(Tween.to(this, SpriteAccessor.COLOR, deathDuration).target(0.4f, 0.4f, 0.4f))
				.push(Tween.to(this, SpriteAccessor.ALPHA, deathDuration/1.6f).target(0))
				.push(Tween.to(this, SpriteAccessor.SCALE, deathDuration).target(1.5f,1.55f)).end())
		.push(Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
		    	creator.addEntity(new Crystal.B(getX(), getY()));
			}
		}))
		.end().start(creator.TWEEN_MANAGER);		
	}

	
	public static class A extends Crate{

		public A(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureCrates.get(2));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 60, 60, 0.9f, 0.9f, 0.019f);
		}
		
	}
	public static class B extends Crate{

		public B(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureCrates.get(1));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 60, 60, 0.9f, 0.9f, 0.019f);
		}
		
	}	public static class C extends Crate{

		public C(MapObject object) {
			super(object);
		}

		@Override
		public void setTexture() {
			setTexture(Assets.textureCrates.get(0));
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 60, 120, 0.9f, 0.9f, 0.019f);
		}
	}
	public static Crate CreateCrate(MapObject object){
		Crate C = null;
		int codetype = object.getProperties().get("code", MathUtils.random(2), Integer.class);
		switch(codetype) {
			case 0:
				C = new A(object);
				break;
			case 1:
				C= new B(object);
				break;
			case 2:
				C = new C(object);
				break;
		}
		return C;
	}
}
