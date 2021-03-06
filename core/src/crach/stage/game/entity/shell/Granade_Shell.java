package crach.stage.game.entity.shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assets;

public class Granade_Shell extends Shell{

	public Granade_Shell(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Granade_Shell, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureGranadeShell);
	}
}
