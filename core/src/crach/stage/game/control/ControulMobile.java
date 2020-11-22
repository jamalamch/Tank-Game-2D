package crach.stage.game.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import crach.stage.game.Assest;
import crach.stage.game.entity.Crach.CrachPlayer.State;
import crach.stage.game.entity.Object.Box;
import crach.stage.game.creator.B2WorldCreator;



public class ControulMobile extends ControuleClass {


	private Button buttonUp;
	private Button buttonDown;
//	private Button buttonRIGHT;
//	private Button buttonLEFT;
	private Button buttonForce;
	private Button buttonIteraction;
	private Touchpad touchpad;
	private Vector2 toRotation = new Vector2();

	public ControulMobile(final B2WorldCreator creator) {
        super(creator);
		buttonUp = new Button(Assest.Style,"ButtonUp");
		buttonDown = new Button(Assest.Style,"ButtonDown");
//		buttonRIGHT = new Button(Assest.Style,"ButtonRight");
//		buttonLEFT = new Button(Assest.Style,"ButtonLeft");
		buttonForce = new Button(Assest.Style,"Buttonfire");
		buttonIteraction = new Button(Assest.Style,"ButtonInteraction");
		touchpad = new Touchpad(50,Assest.Style);;
		buttonIteraction.setVisible(false);
		buttonIteraction.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Interaction) {
				  Box box = (Box) player.interactEntity; 
		            if (box.isEnabled() && player.state == State.CONTROL) {
		               player.startInteractionWithBox(box);
		               box.setState(crach.stage.game.entity.Object.Box.State.INTER);
		           }else  if ( player.state == State.MOVE_BOX) {
		               box.setState(crach.stage.game.entity.Object.Box.State.NONE);
		               player.endInteractionWithBox(box);           
		            }
			      }
			  }
		});
		buttonForce.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
    			if ( player.state == State.CONTROL && Dt_time > player.fire){
                    player.TireFire();
					Dt_time = 0;
				}
		}});
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.BACK)
					creator.QuitGame();
				if(keycode == Input.Keys.UP){
					vitaseUp.setAngleRad(b2body.getAngle());
					b2body.applyForceToCenter(vitaseUp, true);
				}
				return super.keyDown(event, keycode);
			}
		});
        Table table = new Table();
		table.setFillParent(true);
        table.pad(2).bottom().defaults().pad(10).size(120).center();
        table.add(touchpad).pad(15).size(300).padBottom(-120);
		table.add().expandX();
		table.add(buttonIteraction);
		table.add(buttonForce).colspan(2).row();
		table.add().colspan(3);
		table.add(buttonUp).size(140);
		table.add(buttonDown);
        table.pack();
        stage.addActor(table);
	}
	@Override
	public void update(float delta) {
    	Center= b2body.getPosition();

            if (buttonUp.isPressed()) {
            	vitaseUp.setAngleRad(b2body.getAngle());
            	b2body.applyForceToCenter(vitaseUp, true);
            }
            if (buttonDown.isPressed()) {
            	vitaseDown.setAngleRad(b2body.getAngle());
				b2body.applyForceToCenter(vitaseDown.scl(-1), true);
            }
            if (touchpad.isTouched()) {
				toRotation.set(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
				player.updateToAngel(toRotation);
            }
            if(Interaction) {
                final Box box = (Box) player.interactEntity;
                if(buttonForce.isPressed()){
        			if ( player.state == State.MOVE_BOX && player.boxBumber == null){
        				 player.addPower(2);
        			}
        		} 
                if(box.isINTER_PLAYERS()) {
                	player.AntyReaction(box);
                }
            }else if(player.boxBumber != null)
          	   player.boxBumber(player.boxBumber);
            
    super.update(delta);
	}
	@Override
	public void setInteraction(boolean Inter) {
        super.setInteraction(Inter);
		buttonIteraction.setVisible(Inter);
	}
}
