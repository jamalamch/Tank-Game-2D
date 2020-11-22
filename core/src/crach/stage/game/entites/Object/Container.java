package crach.stage.game.entites.Object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;

import crach.stage.game.Assest;

public  class Container extends Box{

	public Container(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		int random = MathUtils.random(3);
		switch (random) {
		case 0:
			setTexture(Assest.Container_A);
			break;
		case 1:
			setTexture(Assest.Container_B);
			break;
		case 2:
			setTexture(Assest.Container_C);
			break;
		case 3:
			setTexture(Assest.Container_D);
			break;
		}
	}

	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 80, 160, 4.5f, 4.7f, 0.07f);
	}
	
}
