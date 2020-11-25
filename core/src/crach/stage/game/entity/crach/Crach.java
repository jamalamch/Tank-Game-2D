package crach.stage.game.entity.crach;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.shell.Shell;
import crach.stage.game.utils.SpriteAccessor;

public abstract class Crach extends Entity {

	public float force;
	public float rotation ;
	public int Vitass ;
	public float fire ;
	public Shell.TypeShell typeShell = Shell.TypeShell.Medium_Shell;

	protected TextureRegion track1, track2, gun, gunPoss, track;
    private final static Animation<TextureRegion> animation = Assets.spriteEffectsExhaust00;
    protected long soundMoved ;
    
    protected  float DstBetTr ;
    protected  float HieghtTr ,WidthTr;
    protected  float HieghtGun ,WidthGun;
    protected  float HieghtPos ,WidthPos;
    protected  float heightBody ,widthBody;
    protected float scaleT = 0.5f;
    
    protected float actTire  = 0;
    private float dtSmoke;
    
    protected boolean smokeFire;

	protected float dtTime;

	public Crach(MapObject object) {
        setTexture();
		defineEntity(object);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tweenFadeInt();
		soundMoved = Assets.movement.loop(0);
	}
	public Crach(float x,float y,float r) {
        setTexture();  
		defineEntity(x,y,r);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		tweenFadeInt();
		soundMoved = Assets.movement.loop(0);
	}
  
	public void setTexture() {
		int nGunA=CrachGame.getGunASelect() 
				,nGun_B=CrachGame.getGunBSelect() 
				,nHull = CrachGame.getHullSelect() 
				,nTranck = CrachGame.getTrackSelect()
				,ncolor = CrachGame.getColorSelect();
		track1 = new TextureRegion(Assets.textureTrackAs.get(nTranck));
		track2 =  new TextureRegion(Assets.textureTrackBs.get(nTranck));
		gun = new TextureRegion(Assets.textureGunBs.get(ncolor).get(nGun_B));
		gunPoss = new TextureRegion(Assets.textureGunAs.get(nGunA));
		track = track1;
		
        setRegion(new TextureRegion(Assets.textureHulls.get(ncolor).get(nHull)));
        setRotation(90);
        setZIndex(Zindex.ZindexCrach);
        
	    HieghtTr = (track1.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthTr=(track1.getRegionWidth()*scaleT)/CrachGame.PPM;
	    heightBody = (this.getRegionHeight()*scaleT)/CrachGame.PPM ;widthBody =(this.getRegionWidth()*scaleT)/CrachGame.PPM;
	    if(heightBody < (90*scaleT*2-HieghtTr)/CrachGame.PPM) {
	    	widthBody = (widthBody*((90*scaleT*2-HieghtTr)/heightBody))/CrachGame.PPM;
	    	heightBody=((90*scaleT*2-HieghtTr))/CrachGame.PPM;
	    	DstBetTr=(85*scaleT)/CrachGame.PPM;
	    }
	    else {
	    	DstBetTr=(90*scaleT)/CrachGame.PPM;
	    }
	    HieghtPos = (gunPoss.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthPos=(gunPoss.getRegionWidth()*scaleT)/CrachGame.PPM;
	    HieghtGun = (gun.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthGun=(gun.getRegionWidth()*scaleT)/CrachGame.PPM;

	}
	
	public void update(float dt) {
		 loopTrack(dt);
	     actTire = (actTire <= 0) ? 0 : actTire - 1.5f*dt;	
     }
	@Override
	public void onContactStart(Entity otherEntity) {
    	if(otherEntity instanceof Explosion) {
    		Explosion E = (Explosion) otherEntity;
    		if(E.isExplose() && !destroy)
    			hit(E);
	}
	}
	
    public void hit(Explosion explosion) {
    	tweenHitAnim();
    }	
    	
	public void actGunPoss(int act,int max) {
        actTire +=(actTire > max/CrachGame.PPM)? 0:act/CrachGame.PPM;  
	}

	public void addSmokeFire() {
        smokeFire =true;
	}

	public void loopTrack(float dt) {
		dtTime +=dt;
		float LinearVelocity = MathUtils.map(0, 60, 0, 1,getBody().getLinearVelocity().len());		
		if(dtTime *LinearVelocity>0.1) {
		    track = (track == track1)? track2 : track1;
		    dtTime =0;
		}
		Assets.movement.setVolume(soundMoved, LinearVelocity);
		Assets.movement.setPitch(soundMoved, LinearVelocity+0.6f);
		if(smokeFire) {
			dtSmoke += dt;
		}
	}
	@Override
	public void draw(Batch batch, Body body) {
		final float rotation = b2body.getAngle()*MathUtils.radiansToDegrees ;
		final float Px =  b2body.getPosition().x ,Py = b2body.getPosition().y;
		//drawEffictation(batch,Gdx.graphics.getDeltaTime());
		batch.setColor(getColor());
		
		batch.draw(track
				, Px  - WidthTr/2
				, Py  -DstBetTr - HieghtTr/2
				, WidthTr/2
				, HieghtTr/2 +DstBetTr  
				,WidthTr, HieghtTr, getScaleX(), getScaleY(), rotation);
		batch.draw(track
				, Px  - WidthTr/2
				, Py  +DstBetTr - HieghtTr/2
				, WidthTr/2
				,  -DstBetTr + HieghtTr/2
				,WidthTr, HieghtTr,getScaleX(), getScaleY(), rotation);
		batch.draw(this
				, Px - widthBody/2
				, Py- heightBody/2
				, widthBody/2
				, heightBody/2
				, widthBody, heightBody, getScaleX(), getScaleY(), rotation);
		batch.draw(gun, Px - WidthGun/2 - actTire, Py -HieghtGun/2 , WidthGun/2 + actTire, HieghtGun/2, WidthGun, HieghtGun, getScaleX(), getScaleY(), rotation);
		batch.draw(gunPoss, Px + WidthGun/2 - actTire, Py -HieghtPos/2 , -WidthGun/2 + actTire, HieghtPos/2, WidthPos, HieghtPos, getScaleX(), getScaleY(), rotation);
		if(smokeFire) {
				TextureRegion AnimationF = animation.getKeyFrame(dtSmoke);
				float WidthS = (AnimationF.getRegionWidth()*0.7f)/CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
				batch.draw(AnimationF, Px + WidthGun*2 - actTire -WidthS/2, Py -HiegthS/2, -WidthGun*2 + actTire +WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), rotation);
				if(animation.isAnimationFinished(dtSmoke)) {
					smokeFire = false;
					dtSmoke =0;
				}
			}
		batch.setColor(Color.WHITE);
		}
	
	public void tweenHitAnim() {
		Assets.soundPlayerHit.play();
		int flashAmount = 3;
		tweenFlash(this,flashAmount, invincibleTimer, new Color(1, 0.1f, 0.2f, 0.2f))
		.end().start(creator.TWEEN_MANAGER);
	}

	private float invincibleTimer = 1f;
	private float deathDuration =0.7f;
	
	public void tweenDeathAnim() {
		Assets.soundExplosionPlayer.play();
		creator.TWEEN_MANAGER.killTarget(this);
		tweenFadeOut(this,deathDuration, new Color(1, 0.2f, 0.2f, 0))		
		.push(Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				creator.deletePlayer();
			}
		}))
		.end().start(creator.TWEEN_MANAGER);;
	}

	public void tweenFadeInt() {
		tweenFadeInt(this,deathDuration, new Color(0,0,0.5f,0.4f))
		.push(Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				destroy =false;
			}
		}))
		.end().start(creator.TWEEN_MANAGER);
	}

    public int getCodeCrach() {
		return 0;
    }
    
	@Override
	public void deathEntity() {
		tweenDeathAnim();
	}

}
