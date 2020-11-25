package crach.stage.game.entity.object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;

import crach.stage.game.Assets;

public  class Container extends Box{

	public Container(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		int random = MathUtils.random(3);
		switch (random) {
		case 0:
			setTexture(Assets.textureContainerA);
			break;
		case 1:
			setTexture(Assets.textureContainerB);
			break;
		case 2:
			setTexture(Assets.textureContainerC);
			break;
		case 3:
			setTexture(Assets.textureContainerD);
			break;
		}
	}

	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 80, 160, 4.5f, 4.7f, 0.07f);
	}
	
}
