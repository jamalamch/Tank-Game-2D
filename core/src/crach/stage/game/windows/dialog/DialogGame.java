package crach.stage.game.windows.dialog;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;

public  class DialogGame extends Dialog{
	protected Button Close;
	public DialogGame(String title) {
		this(title, "dialog", "head", true);
        this.getTitleTable().getCell(getTitleLabel()).size(500,115).padTop(-120);
		getContentTable().defaults().top().pad(4);
		setMovable(false);
	}
	
	public void addScore(int score) {
		 getContentTable().add(Assest.StringDialog.getString("score"),"inSqure").width(200).getTable().add(""+score, "inSqure").prefWidth(150).expandX().right().row();
	}
	public void addRecord(int record) {
		getContentTable().add(Assest.StringDialog.getString("record"),"inSqure").width(200).getTable().add(""+record, "inSqure").prefWidth(150).expandX().right().row();
	}
	public void addCoin(int coin) {
		getContentTable().add(String.format("%04d", coin),"Lcoin").colspan(2).center().size(200,70).row();
	}
	public void addStar(int star) {
		getContentTable().add(addStars(star)).colspan(2).expandX();
	}
	
	public DialogGame(String title,String Style,String Titlebackground,boolean Bclose) {
		super(title, Assest.Style, Style);
        this.getTitleLabel().setAlignment(Align.center );
        if(Titlebackground != null) 
        	super.getTitleLabel().getStyle().background =  Assest.Style.getDrawable(Titlebackground);
        	      
        if(Bclose) {
         Close = new Button(Assest.Style, "Close_BTN");
		 this.getTitleTable().add(Close).right().pad(5,-20,0,-70).maxSize(70);
			Close.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Assest.buttonClick2.play();
					Close();
					hide();
				}
			});
        }
		setOrigin(Align.center);
	}
	
	@Override
	protected void setStage(Stage stage) {
		super.setStage(stage);
		scaleBy(-0.2f);
		pack();
		if(stage != null) setPosition((stage.getWidth()-getWidth()*getScaleX())/2, (stage.getHeight()-getHeight()*getScaleY())/2);
	}
	protected void Close() {
	}
	protected Stars addStars(int nstar) {	
		return new Stars(nstar);
	}

	public void unActiveButton(Button button){
		button.setTouchable(Touchable.disabled);
		button.addAction(Actions.alpha(0.4f));
	}
	class Stars extends Table{
		int nStar;
		Image star[] = new Image[3];
		public Stars(int nstar) {	
			super(Assest.Style);
			this.nStar =nstar;
			this.star[0] = new Image(getDrawableStar(0));
			this.star[1] = new Image(getDrawableStar(0));
			this.star[2] = new Image(getDrawableStar(0));
			this.add(star[0],star[1],star[2]).pack();
			this.setBackground("Decor_Table04");
		}
		private Drawable getDrawableStar(int valueStar) {
			if(valueStar <= 0)
				return getSkin().getDrawable("Icon/Star03");
			if(valueStar <= 1)
				return getSkin().getDrawable("Icon/Star02");
						return getSkin().getDrawable("Icon/Star01");
		}
		protected void addStarsAction( float delayT , boolean sound) {	
			this.addAction(addStar(0, nStar, delayT, sound));
		}
		private Action addStar(final int indexStar,final int nStar,final float delayT ,final boolean sound) {
			final Table table = this;
			return Actions.delay(delayT, Actions.run(new Runnable() {
				@Override
				public void run() {
					if(nStar>0) {
					if(sound) Assest.soundCrystal.play();
					star[indexStar].setDrawable(getDrawableStar(nStar));
					if(indexStar<2)
						table.addAction(addStar( indexStar+1, nStar-2,delayT,sound));
					}
				}
			}));
		}
	}
}
