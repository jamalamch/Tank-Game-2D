package crach.stage.game.entity.object;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assets;

public class BoxInte extends Box{

	public BoxInte(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureBoxNoActive);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
	      if(getBody().isAwake())
	    	  setRegion(Assets.textureBoxActive);
	      else 
	    	  setRegion(Assets.textureBoxNoActive);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 50, 50, 2, 2, 0.02f);
	}

	@Override
	public String toString() {
		return "Box Inte";
	}
}
