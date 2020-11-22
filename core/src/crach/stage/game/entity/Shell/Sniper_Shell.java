package crach.stage.game.entity.Shell;

import com.badlogic.gdx.math.Vector2;
import crach.stage.game.Assest;

public class Sniper_Shell extends Shell{

	public Sniper_Shell(Vector2 position, float r, float force, short mask) {
		super(position, r, force, TypeShell.Sniper_Shell, mask);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.Sniper_Shell);
	}

}
