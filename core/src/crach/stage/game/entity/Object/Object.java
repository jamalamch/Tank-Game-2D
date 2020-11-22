package crach.stage.game.entity.Object;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

import crach.stage.game.Assest;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Explosion;

import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public abstract class Object extends Entity{
	private final static int force = 90;
    private final static int derivative = 20;
    
    public PositionController.PD toPosition ;
    public RotationController.PD toRoration ; 
    protected Color Colorflash = new Color(0.9f, 0.9f, 0.8f,0.75f);

    public static float getHieght() {
    	return 5;
    }

    public enum State {
        NONE, PLACE,  INTER ,INTER_PLAYERS
    }
    
    private  State state = State.NONE;
	public Object(){
	}
	public Object(MapObject object) {
		setTexture();
		defineEntity(object);
	}
	public Object(float x,float y) {
		setTexture();
		defineEntity(x,y);
	}
	public Object(float x,float y,float r) {
		setTexture();
		defineEntity(x,y,r);
	}
	public void defineEntity(float X,float Y) {
        this.defineEntity(X, Y, (float)(Math.random()*1.7));
	}
	public void setTexture(TextureRegion texture) {
 	    setRegion(texture);
        setOriginCenter();
        setZIndex(Zindex.ZindexBox);
	}
    @Override
   public void update(float dt) {
//  	  if(creator.isOnLine()) {
//     if(isINTER_PLAYERS()){
//  	   boolean NONE =true;
//         if(!MathUtils.isEqual(getBody().getPosition().x,toPosition.getDestination().x,5 ) ||
//            !MathUtils.isEqual(getBody().getPosition().y,toPosition.getDestination().y,5 )) 
//              {
//      			toPosition.applyForceToCenter(this.getBody(),true);
//      			Gdx.app.log("test", " box moving "+id);
//      			NONE = false;
//              }
//         if( !MathUtils.isEqual(b2body.getAngle()%2*MathUtils.E , toRoration.getAngle().floatValue()%2*MathUtils.E, 0.1f)) {
//      			toRoration.applyTorque(getBody(), true);
//      			Gdx.app.log("test", " box rotation "+id);
//      			NONE = false;
//            }
//         if(NONE) {
//      	   state = State.NONE;
//      	   getBody().setAwake(false);
//         }
//     }
//     else  if(!b2body.getLinearVelocity().isZero(100) || (int)(b2body.getAngularVelocity()) != 0) {
//    		creator.BoxActive.put(id,StateBox.Move_Object);
//      }
//    } 
    }
    @Override
    public void onContactStart(Entity otherEntity) {
    	if(otherEntity instanceof Explosion) {
    		Explosion E = (Explosion) otherEntity;
    		if(E.isExplose())
    			Hit(E);
    }
    }
    
    @Override
    public void SetForceDeplace(float x,float y,float r){
			toPosition = new PositionController.PD(force,derivative,new Vector2(x, y));
			toRoration = new RotationController.PD(force, derivative, r);
    }
    @Override
    public void UpdateToDeplace(Vector2 toPosion, float toAngle) {
			toPosition.setDestination(toPosion);
			toRoration.setAngle(toAngle);
			setState(State.INTER_PLAYERS);
    }
    
	public void Hit(Explosion explosion) {
		Assest.impact_Object_wall.play(getVolume(),1,getPan());
    	tweenFlash(this,1,1,Colorflash);
	}
	public boolean isEnabled() {
        switch( state){
       	 case  NONE :   		
       	 case PLACE :
       		 return true;
	   	default:
			return false;
        }            
	}
	public boolean isEnplace() {
       switch( state){
  	 case PLACE :
  		 return true;
 	default:
		return false;
   }
	}
	public boolean isINTER_PLAYERS() {
		if(state == State.INTER_PLAYERS) {
			return true;
		}
		return false;
	}
	
	public void setState(State S) {
		state = S;
	}
}
