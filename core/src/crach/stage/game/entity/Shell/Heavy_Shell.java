package crach.stage.game.entity.Shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assets;

public class Heavy_Shell extends Shell{

	public Heavy_Shell(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Heavy_Shell, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureHeavyShell);
	}
}
