package crach.stage.game.windows.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assets;

public abstract class SelectLevel extends DialogGame{

	public SelectLevel( int nStage ,int score,int record,int star) {
		super(Assets.jsonStringDialog.getString("level")+(nStage+1));
		addScore(score);
		addRecord(record);
		addStar(star);
		getButtonTable().defaults().pad(10).maxSize(115).expandX();
		button(new Button(getSkin(), "Hangar_BTN"), 0,Align.left);
		getButtonTable().add();
		button(new Button(getSkin(), "Play_BTN"), 1,Align.right).pack();
	}
	public Dialog button(Button button, int x,int align) {
		getButtonTable().add(button).align(align);
		setObject(button, x);
		return this;
	}
	protected void result (Object object) {
		Assets.buttonClick1.play();
		switch((Integer)object) {
			case 0:
				Hangar();
				break;
			case 1:
				Play();
				break;
			default:
				break;
		}
	}
	@Override
	protected void Close() {
		hide();
	}
	protected abstract void Play();
	protected abstract void Hangar();
	
	@Override
	protected Stars addStars(int nstar) {
		Stars stars =  super.addStars(nstar);
		stars.addStarsAction(0, false);
		return stars;
	}
}
