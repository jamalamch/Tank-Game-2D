package crach.stage.game.windows.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import crach.stage.game.Assets;

public abstract class DialogQuestion extends DialogGame{
    private Button Yes,No;
	public DialogQuestion(String title,String message) {
		this(title, message, "special-dialog");
	}
	public DialogQuestion(String title,String message,String Style) {
		super(title, Style,null,false);
		getTitleTable().padBottom(-60).setWidth(120);
		Yes = new Button( getSkin(), "Ok_BTN");
		No = new Button( getSkin(), "Refuse_BTN");
		text(new Label(message, getSkin(), "font-small",Color.SKY)).bottom();
		getButtonTable().defaults().pad(5,40,5,40).maxSize(80).fillX();
		button(Yes,true).button(No,false);
		pack();
	}
	public void addContent(Actor actor,int colspan) {
		this.getContentTable().row();
		this.getContentTable().add(actor).colspan(colspan).fillX().row();
		this.getContentTable().pack();
	}
	@Override
	protected void result(Object object) {
		Assets.buttonClick1.play();
		if((Boolean)object)
			Accepte();
		else
			Refuse();
	}
	
	 public abstract void Accepte();
	 public abstract void Refuse();

	
}
