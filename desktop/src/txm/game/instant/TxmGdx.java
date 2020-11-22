package txm.game.instant;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import txm.game.instant.core.UivEditor;

public class TxmGdx {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 700;
		config.height =700;
		config.y=40;
		config.fullscreen=false;
		new LwjglApplication(new UivEditor(),config);
	}
}
