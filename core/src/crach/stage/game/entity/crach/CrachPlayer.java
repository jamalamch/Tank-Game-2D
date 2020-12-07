package crach.stage.game.entity.crach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.dermetfan.gdx.physics.box2d.RotationController;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.control.ControuleClass;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.object.Box;
import crach.stage.game.entity.pickups.Gold;
import crach.stage.game.entity.pickups.Pickup;
import crach.stage.game.entity.shell.Shell;
import crach.stage.game.windows.Hanger;

public class CrachPlayer extends Crach {
	
    private Hanger hanger;
    
    private ControuleClass controule;
    
    private int life ,power;

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
	public void defineEntity(float x,float y,float angle) {
        bodyDef(x,y,angle,2,5);
        createFixtureBox(widthBody/2, DstBetTr+HieghtTr/2,false);

		float pLamassY = WidthPos+WidthGun/2;
		if( pLamassY < widthBody/2)
			pLamassY = widthBody/2;

		createFixtureBoxAt(new Vector2(pLamassY - WidthPos/2,0),0,WidthPos/2, HieghtPos/2,false);
	}

	public void updateParameter() {
		getAllParameter();
		this.life = 150 + hanger.getEnergy();
		this.rotation = (float)(hanger.getRotationGun());
		toRoration = new RotationController.PD(rotation*10,15,0);
		this.rotation /=40;
		tweenFadeInt();
		creator.updateHubHealth();
	}
	private void getAllParameter() {
		this.Vitass = hanger.getSpeed()*3;
		this.force = hanger.getProtection()/10;
		this.fire = 10/((float)hanger.getRefire());
	}
	public void reLifeUpdate() {
		Assets.soundUpgrade.play();
		this.life = 150 + hanger.getEnergy();
		tweenFadeInt();
		creator.updateHubHealth();
	}
	public void upgradCrach() {
			Assets.soundUpgrade.play();
			this.Vitass = hanger.getSpeed()*6;
			this.force = (hanger.getProtection()/10) + 10;
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
		if(otherEntity instanceof Gold)
    		creator.addCoin(((Pickup)otherEntity).getScore());
    	else if(otherEntity instanceof Pickup) {
    		creator.addScore(((Pickup)otherEntity).getScore());
    	}else if(otherEntity instanceof Crach) {
    		antyReaction(otherEntity);
    	}else
    		super.onContactStart(otherEntity);
    }
    
  
	public void hit(Explosion explosion) {
    	life -=explosion.getDanger();
		if(life <= 0) {
			destroy =true;
			deathEntity();
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

    public void antyReaction(Entity E) {
    	antyReaction(E,this, force);
    }
    public void tireFire() {
    	Assets.soundPlayerAttack.play();
    	creator.addEntity(Shell.createShell(typeShell, b2body.getPosition(), b2body.getAngle(), force, Shell.maskShell.playershot));
        actGunPoss(5,10);
        addSmokeFire();
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
	public ControuleClass getControule() {
		return controule;
	}

	public void setControule(ControuleClass controule) {
		this.controule = controule;
	}

	public int getLife() {
		return life;
	}


	public void updateToAngel(Vector2 ToAngle){
		toRoration.setAngle(ToAngle.angleRad());
		toRoration.applyTorque(b2body,true);
	}


	@Override
	public float getLinearDamp() {
		return 2;
	}

	@Override
	public float getAngularDamp() {
		return 5;
	}

	@Override
	public short getCategoryBits() {
		return CrachGame.CRACH_BIT;
	}

	@Override
	public String toString() {
		return "CrachPlayer{" +
				", life=" + life +
				", power=" + power +
				'}';
	}
}
