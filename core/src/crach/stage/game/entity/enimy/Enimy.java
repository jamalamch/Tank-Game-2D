package crach.stage.game.entity.enimy;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.control.ControuleEnimy;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.InZone;
import crach.stage.game.entity.shell.Shell;
import crach.stage.game.utils.SpriteAccessor;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public class Enimy extends Entity implements InZone{   
	private final static Animation<TextureRegion> destroyAnimation =  Assets.spriteEffectsExplosion;
    protected long soundMoved ;

	protected TextureRegion trank, trank1, trank2;
	
	private ControuleEnimy controule;

	private final static int radius  = 50;
	private final static float invincibleTimer = 0.5f;
	private final static float deathDuration =0.3f;	
	
    private int life = 100;
	private boolean destore;

    
    private float dt_time;
    
    public  int speed = 25;
    public  float force = 2;
    public  float rotation;
    public float fire;
    public Shell.TypeShell typeShell = Shell.TypeShell.Light_Shell;
     
    public PositionController.P toPosition ;
    public RotationController.P toRoration ;
	private int codeEnimy;

	private float timeTrak;
    
	private boolean active = true,move;
    
    public Enimy(String id,float X, float Y, float R) {
        this(X, Y, R);
        this.Id=id;
    } 
    public Enimy(String id,float X, float Y) {
           this(id, X, Y, (float)(Math.random()*1.7));
    }
    public Enimy(float X, float Y, float R) {
        defineEntity(X, Y, R);
        updateParameter();
		toPosition = new PositionController.P(speed,new Vector2(X, Y));
		toRoration = new RotationController.P( rotation *25,  R);
		controule = new ControuleEnimy(this, creator.getPlayer());
		setTexture();
    }
    public Enimy(MapObject object, float R,int code) {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        float X = (rect.getX() + rect.getWidth() / 2) /CrachGame.PPM;
        float Y = (rect.getY() + rect.getHeight() / 2) /CrachGame.PPM;
        this.codeEnimy = code;
		defineEntity(X, Y,R);
		updateParameter();
		toPosition = new PositionController.P(speed,new Vector2(X, Y));
		toRoration = new RotationController.P(rotation *50,  R);
		controule = new ControuleEnimy(this, creator.getPlayer());
        setTexture();
    }
	@Override
	public void defineEntity(float x, float y, float angle) {
		bodyDef(x, y, angle);
		createFixtureCircle(radius/CrachGame.PPM,false);
	}

	@Override
    public void setTexture() {
    	trank1 =trank= Assets.textureEnimyCrach.get(codeEnimy);
    	trank2 = Assets.textureEnimyCrach2.get(codeEnimy);
        setRegion(trank);
        setZIndex(Zindex.ZindexEnimy);
    }
	public void updateParameter() {
		
		this.speed = Assets.jsonDataEnimy.get(codeEnimy).getInt("vitass")-10;
		this.force = Assets.jsonDataEnimy.get(codeEnimy).getInt("force");
		this.rotation = Assets.jsonDataEnimy.get(codeEnimy).getFloat("rotation");
		this.fire = Assets.jsonDataEnimy.get(codeEnimy).getInt("refire");
		this.life = Assets.jsonDataEnimy.get(codeEnimy).getInt("life");
		
		soundMoved = Assets.movement.loop(0);
		Assets.movement.pause(soundMoved);
	}
    @Override
    public void update(float dt) {
    	if(active) {
    	if(destore) {
    		dt_time +=dt;
			if(destroyAnimation.isAnimationFinished(dt_time)) {
				creator.deleteEnimy(this);
			} 
    	}
    	else {
            controule.update(dt);
        	if(move) animationTrank(dt);
    	}
    }
    }
    @Override
    public void updateToDeplace(Vector2 toPosion, float toAngle) {
    	toPosition.setDestination(toPosion);
    	toRoration.setAngle(toAngle);
    }
    public void updateToDeplace(Vector2 toPosion , boolean toAngle) {
    	if(toAngle) {
        	Vector2 V= b2body.getWorldCenter();
        	Vector2 chemain = new Vector2(toPosion);
            chemain.sub(V);
    		updateToDeplace(toPosion, chemain.angleRad());
    	}else
        	toPosition.setDestination(toPosion);
    }
    
    
    @Override
    public void onContactStart(Entity otherEntity) {
         if(otherEntity instanceof Explosion) {
        	 Hit((Explosion) otherEntity);
          }
         else if(otherEntity instanceof Enimy) {
        	if(((Enimy) otherEntity).controule.Attack) {
        		     controule.setStope(3);
                     controule.Attack = true;
        	}
         }
        	return ;
    }
    
    
	public void tweenHitAnim() {
		Assets.soundHitEnimyMedium.play(getVolume(), 1, getPan());
		int flashAmount = 1;		
		tweenFlash(this, flashAmount, invincibleTimer, new Color(1, 0.6f, 0.6f, 0.4f))
		.end().start(creator.TWEEN_MANAGER);
	}
	
	
	public void tweenDeathAnim() {
		creator.TWEEN_MANAGER.killTarget(this);
		Timeline.createSequence().beginSequence()
		.push(Tween.call(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
			    destroy();
			}
		}))
		.push(Tween.to(this, SpriteAccessor.COLOR, deathDuration/2f).target(1, 0, 0))
		.push(Tween.to(this, SpriteAccessor.ALPHA, deathDuration/2f).target(0))
		.push(tweenFadeOut(this, deathDuration, new Color(1, 0, 0, 0)).end())
		.end().start(creator.TWEEN_MANAGER);
	}
   	public void TireFire() {
   		Assets.soundEnemyHit.play(getVolume(), 1, getPan());
   		instantaEntity(Shell.createShell(typeShell, b2body.getPosition(), b2body.getAngle(), force, Shell.MaskShell.enimyshell));
	}
    @Override
    public void deathEntity() {
    	Assets.movement.stop(soundMoved);
    	Assets.soundExplosionEnimy.play(getVolume(), 1, getPan());
		creator.addExper((int)MathUtils.map(1,16,5,20,codeEnimy));

    	destore = true;
	    setPosition(b2body.getPosition().x, b2body.getPosition().y);
	    setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
	    setSize(Box2DUtils.size(getBody()).x,Box2DUtils.size(getBody()).y);
        setOriginCenter();
    	tweenDeathAnim();
    }
    
	public void animationTrank(float dt) {
		timeTrak +=dt;
		
		float LinearVelocity = MathUtils.map(0, 40, 0, 1,getBody().getLinearVelocity().len());
		
		if(timeTrak*LinearVelocity>0.08f) {
			trank = (trank == trank1)? trank2 : trank1;
		    timeTrak=0;
		}
		setRegion(trank);

		Assets.movement.setPan(soundMoved, getPan(), getVolume(0.3f));
		Assets.movement.setPitch(soundMoved, LinearVelocity + 0.8f);
	}
    
    @Override
    public void draw(Batch batch, Body body) {
    	if(!destore) {
    	super.draw(batch, body);
    	batch.setColor(Color.WHITE);
    	}else {
    		batch.setColor(getColor());
    		batch.draw(this,  getX() -getWidth()/2,  getY()-getHeight()/2,  getOriginX() ,  getOriginY() ,  getWidth(),  getHeight(), getScaleX(), getScaleY(),  getRotation());
			TextureRegion AnimationF = destroyAnimation.getKeyFrame(dt_time);
			float WidthS = (AnimationF.getRegionWidth()*0.7f)/CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
			batch.setColor(Color.WHITE);
			batch.draw(AnimationF, getX()  -WidthS/2, getY() -HiegthS/2, WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
    	}
    }
	public void Hit(Explosion explosion) {
		if(!explosion.isExplose()) return;
	 life -= explosion.getDanger();
	 if(life <= 0)
	 		deathEntity();
 		else 
 			tweenHitAnim();
	 
	 if(!controule.isAttack())
		 controule.ReAttack();
	}
	public void Starting() {
		move =true;
	    Assets.movement.resume(soundMoved);
	}

	@Override
	public void getting(Entity otherEntity) {
		move = true;
	}
	@Override
	public void drifting(Entity otherEntity) {
	}

	@Override
	public float getAngularDamp() {
		return 2;
	}

	@Override
	public float getLinearDamp() {
		return 4;
	}

	@Override
	public float getDensity() {
		return 0.03f;
	}

	@Override
	public short getCategoryBits() {
		return CrachGame.ENIMY_BIT;
	}

	@Override
	public String toString() {
		return "Enimy{" +
				", destore=" + destore +
				", dt_time=" + dt_time +
				", force=" + force +
				", codeEnimy=" + codeEnimy +
				", timeTrak=" + timeTrak +
				", active=" + active +
				", move=" + move +
				'}';
	}
}
