package crach.stage.game.entites.Crach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

import net.dermetfan.gdx.physics.box2d.RotationController;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.control.controuleClass;
import crach.stage.game.entites.Entity;
import crach.stage.game.entites.Explosion;
import crach.stage.game.entites.Object.Box;
import crach.stage.game.entites.Object.Crate;
import crach.stage.game.entites.Pickups.Gold;
import crach.stage.game.entites.Pickups.Pickup;
import crach.stage.game.entites.Shell.Shell;
import crach.stage.game.windows.Hanger;

public class CrachPlayer extends Crach {
	
    private Hanger hanger;
    
    private controuleClass controule;
    
	public Entity interactEntity;
    private Joint currentJoint;
    private int life ,power;

	public enum State {
	        CONTROL, MOVE_BOX
	    }
    public enum Interaction {
        NONE, BOX
    }   
    public State state = State.CONTROL;
    public Interaction interaction = Interaction.NONE;
    
	public Box boxBumber;
	Vector2 Vectorforce;

	public RotationController.PD toRoration ;
	
	public CrachPlayer(float x,float y,float r) {
		super(x, y, r);
        updateParameter();
	}
	public CrachPlayer(MapObject object) {
		super(object);
		updateParameter();
	}
	@Override
	public void setTexture() {
		this.hanger = CrachGame.getMenuUi().getHanger();
		super.setTexture();
	}
	public void defineEntity(float X,float Y,float R) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(X , Y);
        bdef.linearDamping=2;
        bdef.angularDamping=5f;
        bdef.angle=R;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthBody/2, DstBetTr+HieghtTr/2);
        fdef.shape = shape;
        fdef.density = 0.03f;
        fdef.filter.categoryBits=CrachGame.CRACH_BIT;
        b2body.createFixture(fdef);  
		shape.dispose();

        fdef = new FixtureDef(); 

		shape = new PolygonShape();
		float pLamassY = WidthPos+WidthGun/2;
		if( pLamassY < widthBody/2) pLamassY = widthBody/2;
			
		shape.setAsBox(WidthPos/2, HieghtPos/2,new Vector2(pLamassY - WidthPos/2,0),0);
		fdef.shape = shape;

        fdef.filter.categoryBits = CrachGame.LAMAS_BIT;
        
		b2body.createFixture(fdef);		
		
		shape.dispose();
		b2body.setUserData(this);				
	}
	


	public void updateParameter() {
		getAllParameter();
		this.life = 150 + hanger.getEnergy();
		this.rotation = (float)(hanger.getRotationGun());
		toRoration = new RotationController.PD(rotation*10,15,0);
		this.rotation /=40;
		tweenFadeInt();
		creator.updatehubHealth();
	}
	private void getAllParameter() {
		this.Vitass = hanger.getSpeed()*3;
		this.Force= hanger.getProtection()/10;
		this.fire = 10/((float)hanger.getRefire());
	}
	public void reLifeUpdate() {
		Assest.upgradSound.play();
		this.life = 150 + hanger.getEnergy();
		tweenFadeInt();
		creator.updatehubHealth();
	}
	public void UpgradCrach() {
			Assest.upgradSound.play();
			this.Vitass = hanger.getSpeed()*6;
			this.Force= (hanger.getProtection()/10) + 10;
			this.fire = 10f/((float)hanger.getRefire()) - 0.3f;
			this.life = +150;			
			tweenFlash(this, 40, 30, new Color(0.85f, 0.7f, 1, 0.6f))
			.push(Tween.call(new TweenCallback() {
				@Override
				public void onEvent(int var1, BaseTween<?> var2) {
					getAllParameter();
				}
			})).end().start(creator.TWEEN_MANAGER);
	}
		
    public void onContactStart(Entity otherEntity) {
    	if(otherEntity instanceof Box && !(otherEntity instanceof Crate)){
            if (interaction == Interaction.NONE) {
            	setInteraction(Interaction.BOX, otherEntity);
            }
    	}
    	else if(otherEntity instanceof Gold)
    		creator.addCoin(((Pickup)otherEntity).getScore());
    	else if(otherEntity instanceof Pickup) {
    		creator.addScore(((Pickup)otherEntity).getScore());
    	}else if(otherEntity instanceof Crach) {
    		AntyReaction(otherEntity);
    	}else
    		super.onContactStart(otherEntity);
    }
    
  
	public void Hit(Explosion explosion) {
    	life -=explosion.getDanger();
		if(life <= 0) {
			Destore =true;
			DeathEntity();
			if(CrachGame.isVibr())
				if(CrachGame.isVibr()) Gdx.input.vibrate(150);
		}
		else {
			tweenHitAnim();
			if(CrachGame.isVibr())
				if(CrachGame.isVibr()) Gdx.input.vibrate(50);
		}
		creator.addDanger((int)MathUtils.map(20, 200, 1, 10, explosion.getDanger()));
	}
    
    public void onContactEnd(Entity otherEntity) {
        if ( otherEntity instanceof Box  ) {
            if (interaction == Interaction.BOX && state == State.CONTROL) {
            	setInteraction(Interaction.NONE, null);
            }
        }
    }
    
    public void setInteraction( Interaction interaction,Entity interactEntity) {
        controule.setInteraction((interactEntity == null) ? false : true);
        this.interactEntity = interactEntity;
        this.interaction = interaction;

    }
    
    public void endInteractionWithBox(Box box) {
    	if(power != 0) {
    		boxBumber =box;
    		Vectorforce = new Vector2(power,power);
    		Vectorforce.setAngleRad(b2body.getAngle());
    	}
        if (currentJoint == null) return;
        setState(State.CONTROL);
        setInteraction(Interaction.NONE ,null);
    	world.destroyJoint(currentJoint);
        currentJoint = null;
    }

    public void startInteractionWithBox(Box box) {
		if (currentJoint != null) return;
		addPower(0);
		boxBumber = null;
        setState(State.MOVE_BOX);
        Vector2 V =new  Vector2(40/CrachGame.PPM,40/CrachGame.PPM);
        V.setAngleRad(b2body.getAngle());
        V.add(getBody().getWorldCenter());
        		
		 
			PrismaticJointDef jointDef = new PrismaticJointDef();
			
			  jointDef.initialize(getBody(),box.getBody(),V, new Vector2(1,1));	
			  jointDef.motorSpeed = 1.0f; jointDef.enableMotor = true;
			  jointDef.lowerTranslation =0f;
			  jointDef.upperTranslation = 0f;
			  jointDef.enableLimit = true;


        currentJoint = world.createJoint(jointDef);
    }
    public void BoxHasDestory() {
    	
    }
    
    public void AntyReaction(Entity E) {
    	AntyReaction(E,this, Force);
    	
    }
    public void TireFire() {
    	Assest.playerattack.play();
    	creator.addEntity(Shell.createShell(typeShell, getBody().getPosition(), b2body.getAngle(), Force, Shell.maskShell.playershot));
        actGunPoss(20,30);
        addSmokeFire();
    }
    public void setState(State state) {
        this.state = state;
    }
    

    public void addPower(int P) {
    	power += P; 
    	if(power > 200 || P == 0)  power = 0 ;
    	//controule.getHub().upPowerCrach(power);
    }
    public void boxBumber(Box box) {
    	if(power > 0) {    	
    	box.getBody().applyForceToCenter(Vectorforce, true);
    	addPower(-1);
    	}
    	else {
    		boxBumber = null;
    	}
    }
       
	public boolean isActive() {
		if(b2body.isAwake()) {
        if( b2body.getLinearVelocity().isZero(100) && (int)(b2body.getAngularVelocity()) == 0)
		return false;
        return true;
		}
        return false;
	}
	public controuleClass getControule() {
		return controule;
	}
	public void setControule(controuleClass controule) {
		this.controule = controule;
	}
	public int getLife() {
		return life;
	}


	public void updateToAngel(Vector2 ToAngle){
		toRoration.setAngle(ToAngle.angleRad());
		toRoration.applyTorque(b2body,true);
	}
}
