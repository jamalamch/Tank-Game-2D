package crach.stage.game.entity.Crach;

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
import crach.stage.game.entity.Shell.Shell;
import crach.stage.game.utils.SpriteAccessor;

public abstract class Crach extends Entity {
	 
	protected TextureRegion Track_1,Track_2,Gun,GunPoss,Track;
//  protected TextureRegion Tire_Track;
    private final static Animation<TextureRegion> animation = Assets.spriteEffectsExhaust00;
    protected long soundMoved ;
    
    protected  float DstBetTr ;
    protected  float HieghtTr ,WidthTr;
    protected  float HieghtGun ,WidthGun;
    protected  float HieghtPos ,WidthPos;
    protected  float heightBody ,widthBody;
    protected float scaleT = 0.5f;
    
    protected float actTire  = 0;
    private float Dt_smoke;
    
    protected boolean SmokeFire;
    
    public float Force ;
    public float rotation ;
    public int Vitass ;
    public float fire ;
    public Shell.TypeShell typeShell = Shell.TypeShell.Medium_Shell;
    
    int derivative = 30;
     
    private ParticleEffect Effictation;
      
    float Dt_time;
	private float invincibleTimer = 1f;
	private float deathDuration =0.7f;



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
		Track_1 = new TextureRegion(Assets.textureTrackAs.get(nTranck));
		Track_2 =  new TextureRegion(Assets.textureTrackBs.get(nTranck));
		Gun = new TextureRegion(Assets.textureGunBs.get(ncolor).get(nGun_B));
		GunPoss = new TextureRegion(Assets.textureGunAs.get(nGunA));
		Track = Track_1;
		
        setRegion(new TextureRegion(Assets.textureHulls.get(ncolor).get(nHull)));
        setRotation(90);
        setZIndex(Zindex.ZindexCrach);
        
	    HieghtTr = (Track_1.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthTr=(Track_1.getRegionWidth()*scaleT)/CrachGame.PPM;
	    heightBody = (this.getRegionHeight()*scaleT)/CrachGame.PPM ;widthBody =(this.getRegionWidth()*scaleT)/CrachGame.PPM;
	    if(heightBody < (90*scaleT*2-HieghtTr)/CrachGame.PPM) {
	    	widthBody = (widthBody*((90*scaleT*2-HieghtTr)/heightBody))/CrachGame.PPM;
	    	heightBody=((90*scaleT*2-HieghtTr))/CrachGame.PPM;
	    	DstBetTr=(85*scaleT)/CrachGame.PPM;
	    }
	    else {
	    	DstBetTr=(90*scaleT)/CrachGame.PPM;
	    }
	    HieghtPos = (GunPoss.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthPos=(GunPoss.getRegionWidth()*scaleT)/CrachGame.PPM;
	    HieghtGun = (Gun.getRegionHeight()*scaleT)/CrachGame.PPM ;WidthGun=(Gun.getRegionWidth()*scaleT)/CrachGame.PPM;

	}
	
	public void update(float dt) {
		 loopTrack(dt);
	     actTire = (actTire <= 0) ? 0 : actTire - 1.5f*dt;	
     }
	@Override
	public void onContactStart(Entity otherEntity) {
    	if(otherEntity instanceof Explosion) {
    		Explosion E = (Explosion) otherEntity;
    		if(E.isExplose() && !destory)
    			Hit(E);
	}
	}
	
    public void Hit(Explosion explosion) {
    	tweenHitAnim();
    }	
    	
	public void actGunPoss(int act,int max) {
        actTire +=(actTire > max/CrachGame.PPM)? 0:act/CrachGame.PPM;  
	}
	public void addSmokeFire() {
        SmokeFire =true;
	}
	public void addEffictation(Vector2 position , boolean move) {
		Vector2 P = new Vector2(40,40);
		P.setAngleRad(b2body.getAngle());
		position.sub(P);
		Effictation.setPosition(position.x,position.y);
		if(Effictation.isComplete() && move)
			Effictation.reset(move);
	}
	public void loopTrack(float dt) {
		Dt_time +=dt;
		float LinearVelocity = MathUtils.map(0, 60, 0, 1,getBody().getLinearVelocity().len());		
		if(Dt_time*LinearVelocity>0.1) {
		    Track = (Track == Track_1)?Track_2 : Track_1;
		    Dt_time=0;
		}
		Assets.movement.setVolume(soundMoved, LinearVelocity);
		Assets.movement.setPitch(soundMoved, LinearVelocity+0.6f);
		if(SmokeFire) {
			Dt_smoke += dt;
		}
	}
	@Override
	public void draw(Batch batch, Body body) {
		final float rotation = b2body.getAngle()*MathUtils.radiansToDegrees ;
		final float Px =  b2body.getPosition().x ,Py = b2body.getPosition().y;
		float Dv = getBody().getLinearVelocity().len()/50;
		Dv = (Dv > 1)?1:Dv;
		//drawEffictation(batch,Gdx.graphics.getDeltaTime());
		batch.setColor(getColor());
		
		batch.draw(Track
				, Px  - WidthTr/2
				, Py  -DstBetTr - HieghtTr/2
				, WidthTr/2
				, HieghtTr/2 +DstBetTr  
				,WidthTr, HieghtTr, getScaleX(), getScaleY(), rotation);
		batch.draw(Track
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
		batch.draw(Gun    , Px - WidthGun/2 - actTire, Py -HieghtGun/2 , WidthGun/2 + actTire, HieghtGun/2, WidthGun, HieghtGun, getScaleX(), getScaleY(), rotation);
		batch.draw(GunPoss, Px + WidthGun/2 - actTire, Py -HieghtPos/2 , -WidthGun/2 + actTire, HieghtPos/2, WidthPos, HieghtPos, getScaleX(), getScaleY(), rotation);
		if(SmokeFire) {
				TextureRegion AnimationF = animation.getKeyFrame(Dt_smoke);
				float WidthS = (AnimationF.getRegionWidth()*0.7f)/CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
				batch.draw(AnimationF, Px + WidthGun*2 - actTire -WidthS/2, Py -HiegthS/2, -WidthGun*2 + actTire +WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), rotation);
				if(animation.isAnimationFinished(Dt_smoke)) {
					SmokeFire = false;
					Dt_smoke=0;
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
				destory =false;
			}
		}))
		.end().start(creator.TWEEN_MANAGER);
	}
	public void drawEffictation(Batch spriteBatch,float delta) {
		Effictation.draw(spriteBatch, delta);
	}
	
    public int getCodeCrach() {
		return 0;
    }
    
	@Override
	public void DeathEntity() {
		tweenDeathAnim();
	}

}
