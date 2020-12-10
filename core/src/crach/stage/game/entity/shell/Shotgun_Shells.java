package crach.stage.game.entity.shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assets;

public class Shotgun_Shells extends Shell{

	public Shotgun_Shells(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Shotgun_Shells, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureShotgunShells);
	}

}
