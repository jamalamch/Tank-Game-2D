package crach.stage.game.entites.Object;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assest;

public class BoxInte extends Box{

	public BoxInte(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.boxNoActive);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
	      if(getBody().isAwake())
	    	  setRegion(Assest.boxActive);
	      else 
	    	  setRegion(Assest.boxNoActive);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 50, 50, 2, 2, 0.02f);
	}
}
