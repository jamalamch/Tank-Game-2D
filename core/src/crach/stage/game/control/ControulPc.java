package crach.stage.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import crach.stage.game.creator.B2WorldCreator;

public class ControulPc extends ControuleClass {

	public ControulPc(final B2WorldCreator creator) {
		super(creator);
		this.stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
		        if(keycode == Input.Keys.ESCAPE) {
		        	creator.QuitGame();
		        }
				return super.keyDown(event, keycode);
			}
		});
	}

	@Override
	public void update(float delta) {
		Center = b2body.getPosition();
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			vitaseUp.setAngleRad(b2body.getAngle());
			b2body.applyForceToCenter(vitaseUp, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			vitaseDown.setAngleRad(b2body.getAngle());
			b2body.applyForceToCenter(vitaseDown.scl(-1), true);
		}	
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				b2body.setAngularVelocity(-player.rotation);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				b2body.setAngularVelocity(player.rotation);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			if (Dt_time > player.fire) {
				player.tireFire();
				Dt_time = 0;
			}
		}
		super.update(delta);
	}
}
