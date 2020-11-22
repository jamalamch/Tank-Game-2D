package crach.stage.game.entity.Crach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crach.stage.game.CrachGame;
import net.dermetfan.gdx.physics.box2d.PositionController;
import net.dermetfan.gdx.physics.box2d.RotationController;

public class CrachEnimy extends Crach{

	
    public CrachEnimy(float x, float y, float r) {
		super(x, y, r);
		SetForceDeplace(x, y, r);
	}
	public PositionController.PD toPosition ;
    public RotationController.PD toRoration ;
	
    
    
	public void defineEntity(float X,float Y,float R) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(X , Y);
        bdef.linearDamping=2;
        bdef.angularDamping=3;
        bdef.angle=R;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthBody/2, DstBetTr+HieghtTr/2);
        fdef.shape = shape;
        	    fdef.isSensor=true;
        		fdef.filter.categoryBits=CrachGame.FRENDS_BIT;
    	        fdef.filter.maskBits=CrachGame.CRACH_BIT|CrachGame.LAMAS_BIT;
        b2body.createFixture(fdef);  
		shape.dispose();

        fdef = new FixtureDef(); 

		shape = new PolygonShape();
		shape.setAsBox(WidthPos/2, HieghtPos/2,new Vector2(WidthPos/2+WidthGun/2,0),0);
		fdef.shape = shape;
	        fdef.isSensor=true;
	        fdef.filter.categoryBits=CrachGame.FRENDS_BIT;
	        fdef.filter.maskBits=CrachGame.CRACH_BIT|CrachGame.LAMAS_BIT;
		b2body.createFixture(fdef);		
		shape.dispose();
		MassData data = new MassData();
        data.I=5;
        data.mass=5;
	    b2body.setMassData(data); 		
		b2body.setUserData(this);		
	}
    
	public void update(float dt) {
		super.update(dt);
      if(!MathUtils.isEqual(getBody().getPosition().x,toPosition.getDestination().x,4 ) ||
         !MathUtils.isEqual(getBody().getPosition().y,toPosition.getDestination().y,4 )) 
      {
			toPosition.applyForceToCenter(this.getBody(),true);
			Gdx.app.log("test", " moving");
     }
    if( !MathUtils.isEqual((b2body.getAngle()) %2*MathUtils.E ,toRoration.getAngle().floatValue()%2*MathUtils.E, 0.005f)) {
			toRoration.applyTorque(getBody(), true);
			Gdx.app.log("test", " rotation ");
    }	
    }
	
	
	@Override
	public void SetForceDeplace(float x,float y, float r) {
		toPosition = new PositionController.PD(Vitass,derivative,new Vector2(x, y));
		toRoration = new RotationController.PD(Vitass, derivative, r);
	}
	@Override
	public void UpdateToDeplace(Vector2 toPosion, float toAngle) {
		toPosition.setDestination(toPosion);
		toRoration.setAngle(toAngle);   
	}
}
