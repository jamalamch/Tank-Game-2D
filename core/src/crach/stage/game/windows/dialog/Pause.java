package crach.stage.game.windows.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;

public abstract class Pause extends DialogGame{

	public Pause( int score) {
		super(Assest.StringDialog.getString("pause"));
		addScore(score);
		getButtonTable().defaults().pad(10).maxSize(115).expandX();
		button(new Button(getSkin(), "Replay_BTN"), 0,Align.left);
		button(new Button(getSkin(), "Play_BTN"), 1,Align.center);
		button(new Button(getSkin(), "Settings_BTN"), 2,Align.right).pack();
	}
	
	public Dialog button(Button button, int x,int align) {
		getButtonTable().add(button).align(align);
		setObject(button, x);
		return this;
	}
	
	protected void result (Object object) {
		Assest.buttonClick1.play();
		switch((Integer)object) {
			case 0:
				Replay();
				break;
			case 1:
				Resume();
				break;
			case 2:
				Settings();
				break;
			default:
				break;
		}
	}
	@Override
	protected void Close() {
		Resume();
	}
	public abstract void Resume();
	public abstract void Replay();
	public abstract void Settings();
}
