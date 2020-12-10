package crach.stage.game.entity.shell;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.JsonValue;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.crach.Crach;
import crach.stage.game.entity.enimy.Enimy;

public abstract class Shell extends Entity implements Explosion{
	
	private short maskShell;
	private Animation<TextureRegion> animationFire  = Assets.spriteFireShotsShotB;

	private float heightShell, widthShell;
	private boolean destore;
	
	protected float speed;
	protected float timeLief ;
	protected float danger ;
	
	protected Vector2 Direction;
		
	protected final Vector2 LongOfCente = new Vector2(100/CrachGame.PPM,100/CrachGame.PPM);

	public Shell(Vector2 position,float r,float force,TypeShell type,short mask) {
	 this.updateParameter(type);
   	 this.Direction = new Vector2(speed+force, speed+force);
   	 this.Direction.setAngleRad(r);
   	 this.LongOfCente.setAngleRad(r);
   	 this.LongOfCente.add(position);
   	 this.maskShell = mask;
     setTexture();
     defineEntity(LongOfCente.x, LongOfCente.y,r);
     b2body.applyLinearImpulse(Direction, getBody().getPosition(), true);
   }
	private void updateParameter(TypeShell type){
		JsonValue value = Assets.jsonDataShell.get(type.ordinal());
		speed = value.getFloat("speed");
		danger = value.getFloat("Damage");
		timeLief = value.getFloat("life");
	}
	
    public void setTexture(TextureRegion region) {
    	super.setRegion(region);
    	widthShell = region.getRegionWidth()/(2*CrachGame.PPM);
    	heightShell = region.getRegionHeight()/(2*CrachGame.PPM);
    	setOriginCenter();
        setZIndex(Zindex.Zindexfire);
    }
    @Override
	public void defineEntity(float x, float y, float angle) {
        bodyDef(x, y, angle);
        createFixtureBox(widthShell, heightShell,false);
	}

	@Override
	public boolean isBullet() {
		print("bullet");
		return true;
	}

	@Override
	public short getCategoryBits() {
		return CrachGame.FIRE_BIT;
	}

	@Override
	public short getMaskBits() {
		return maskShell;
	}

	@Override
    public void update(float dt){
      if(!destore) {
    	      timeLief -= dt;
      if(timeLief<0) { 
    	  deathEntity();
	     } 
      }
      else {
    	  timeLief += dt;
  		if(animationFire.isAnimationFinished(timeLief)) {
  			creator.SetDestoryEntity(this);
		}
      }
    }
    
    public void deathEntity() {
    	 Assets.soundImpactShell.play(getVolume(),1,getPan());
		 timeLief = 0;
	     destore = true;
	     setPosition(b2body.getPosition().x, b2body.getPosition().y);
	     setRotation(b2body.getAngle()*MathUtils.radiansToDegrees+180);
	     destroy();
  	}

    @Override
    public void onContactStart(Entity otherEntity) {
    	if(!destore) {
    		if(otherEntity instanceof Crach || otherEntity instanceof Enimy) {
    			animationFire = Assets.spriteFireShotsShotA;
    		}
    		timeLief = 0;
    	}
    }

    @Override
    public void draw(Batch batch, Body body) {
    	if(!destore)
    	super.draw(batch, body);
    	else {
    		TextureRegion AnimationF = animationFire.getKeyFrame(timeLief);
			float widthFire = (AnimationF.getRegionWidth()*0.7f)/CrachGame.PPM, heightFire = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
			batch.draw(AnimationF, getX()-widthFire/2, getY()-heightFire/2 , widthFire/2, heightFire/2, widthFire, heightFire, 1, 1, getRotation());   	
    }
    }
	
	public float getDanger() {
		return danger;
	}
	public float setDanger(float dange) {
		float d = this.danger;
		this.danger= dange;
		return d;
	}

	@Override
	public boolean isExplose() {
		return true;
	}

	public enum TypeShell {
		Granade_Shell,Heavy_Shell,Laser,Light_Shell,Medium_Shell,Plasma,Shotgun_Shells,Sniper_Shell
	}
	
	
	public static class MaskShell {
		public static final short playershell = CrachGame.FIRE_BIT|CrachGame.BOMB_BIT |CrachGame.ENIMY_BIT|CrachGame.NOTHING_BIT |CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT;
		public static final short enimyshell = CrachGame.FIRE_BIT|CrachGame.BOMB_BIT |CrachGame.CRACH_BIT|CrachGame.NOTHING_BIT |CrachGame.OBJECT_BIT|CrachGame.DOOR_BIT;

		public static String toString(short maskshot) {
			if(maskshot == playershell)
				return "player Shot";
			if(maskshot == enimyshell)
				return "enimy shoet";
			return "";
		}
	}
	
	public static Shell createShell(TypeShell type,Vector2 position, float r, float force, short mask) {
		Shell shell = null;
			switch (type) {
			case Granade_Shell:
				shell = new Granade_Shell(position, r, force, mask);
				 break; 
			case Heavy_Shell:
				shell = new Heavy_Shell(position, r, force, mask);
				 break; 
			case Laser:
				shell = new Laser(position, r, force, mask);
				 break; 
			case Light_Shell:
				shell = new Light_Shell(position, r, force, mask);
				 break; 
			case Medium_Shell:
				shell = new Medium_Shell(position, r, force, mask);
				 break; 
			case Plasma:
				shell = new Plasma(position, r, force, mask);
				 break; 
			case Shotgun_Shells:
				shell = new Shotgun_Shells(position, r, force, mask);
				 break; 
			case Sniper_Shell:
				shell = new Sniper_Shell(position, r, force, mask);
					break;
			}
		return shell;
	}

	@Override
	public String toString() {
		return "Shell{" +
				", mask=" + MaskShell.toString(maskShell) +
				", speed=" + speed +
				", timeLief=" + timeLief +
				", danger=" + danger +
				'}';
	}
}
