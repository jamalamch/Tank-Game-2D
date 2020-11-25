package crach.stage.game.entity.shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assets;

public class Light_Shell extends Shell{

	public Light_Shell(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Light_Shell, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.textureLightShell);

	}

}
