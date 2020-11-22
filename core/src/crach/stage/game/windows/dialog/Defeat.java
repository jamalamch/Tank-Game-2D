package crach.stage.game.windows.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;

public abstract class Defeat extends DialogGame{

	public Button Bback,Breplay,Bplay;

	public Defeat( int nStage ,int score,int record,int coin,boolean isContinue) {
		super(Assest.StringDialog.getString("defeat"));
		addScore(score);
		addRecord(record);
		addCoin(coin);
		getButtonTable().defaults().pad(10).maxSize(115).expandX();
		button(Bback = new Button(getSkin(), "Back_BTN"), 0,Align.left);
		button(Breplay = new Button(getSkin(), "Replay_BTN"), 1,Align.center);
		button(Bplay = new Button(getSkin(), "Play_BTN"), 2,Align.right).pack();
		if(!isContinue){
			unActiveButton(Bplay);
		}
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
				Resume();
				break;
			default:
				break;
		}
	}
	@Override
	protected void Close() {
		Back();
	}
	public abstract void Resume();
	public abstract void Replay();
	public abstract void Back();

	

}
