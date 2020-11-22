package crach.stage.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import crach.stage.game.entites.Crach.CrachPlayer.State;
import crach.stage.game.entites.Object.Box;
import crach.stage.game.Creator.B2WorldCreator;

public class controulPc extends controuleClass {

	public controulPc(final B2WorldCreator creator) {
		super(creator);
		informationUpdate();
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
			if (player.state == State.CONTROL && Dt_time > player.fire) {
				player.TireFire();
				Dt_time = 0;
			}
		}
		if (Interaction) {
			final Box box = (Box) player.interactEntity;
			if (Gdx.input.isKeyPressed(Input.Keys.F)) {
				if (player.state == State.MOVE_BOX && player.boxBumber == null) {
					player.addPower(2);
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				if (box.isEnabled() && player.state == State.CONTROL) {
					player.startInteractionWithBox(box);
					box.setState(Box.State.INTER);
				} else if (player.state == State.MOVE_BOX) {
					box.setState(Box.State.NONE);
					player.endInteractionWithBox(box);
				}
			}
			if (box.isINTER_PLAYERS()) {
				player.AntyReaction(box);
			}
		} else if (player.boxBumber != null)
			player.boxBumber(player.boxBumber);
        
		super.update(delta);
	}

	public void informationUpdate() {
		//getHub().updateVitass("vitass : " + vitass.len()/(float)Math.sqrt(2) + " force : " + player.Force + " vitassfaire : " + fairevitass);
	}
}
