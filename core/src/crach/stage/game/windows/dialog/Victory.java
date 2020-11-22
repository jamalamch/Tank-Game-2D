package crach.stage.game.windows.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;

public abstract class Victory extends DialogGame{
	public Victory( int nStage ,int score,int record,int coin,int star) {
		super(Assest.StringDialog.getString("victory"));
		addScore(score);
		addRecord(record);
		addCoin(coin);
		addStar(star);
		getButtonTable().defaults().pad(10).maxSize(115).expandX();
		button(new Button(getSkin(), "Back_BTN"), 0,Align.left);
		button(new Button(getSkin(), "Replay_BTN"), 1,Align.center);
		button(new Button(getSkin(), "Play_BTN"), 2,Align.right).pack();
		Assest.musicVector.play();
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
				Back();
				break;
			case 1:
				Replay();
				break;
			case 2:
				Continue();
				break;
			default:
				break;
		}
	}
	@Override
	protected void Close() {
		Back();
	}
	public abstract void Continue();
	public abstract void Replay();
	public abstract void Back();
	
	@Override
	protected Stars addStars(int nstar) {
		Stars stars =  super.addStars(nstar);
		stars.addStarsAction(1, true);
		return stars;
	}
}
