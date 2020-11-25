package crach.stage.game.entity.shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assets;

public class Laser extends Shell{

	public Laser(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Laser, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureLaser);

	}

}
