package crach.stage.game.Screen.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;

public class watchAds extends Stack{
	ProgressWatchAds progressWatchAds;
	private TextButton[] GanCiamond ;
	private Button PlayAds;
	private Button bCollect;
	public watchAds(Button bCollect) {
		super();
		this.bCollect=bCollect;
		progressWatchAds = new ProgressWatchAds();
		add(progressWatchAds);
		GanCiamond  = new TextButton[6];
		for(int i=0;i<6;i++) {
			int Diamound = 10+i*5;
			GanCiamond[i] = new TextButton(Diamound+"*",Assest.Style, "number");
			GanCiamond[i].setUserObject(Diamound);
			GanCiamond[i].setSize(50, 50);
			GanCiamond[i].setDisabled(true);
			GanCiamond[i].setTouchable(Touchable.disabled);
		}
		PlayAds = new Button(Assest.Style, "PlayAds");
		
		Table buttons = new Table();
		buttons.padRight(progressWatchAds.bgRightWidth-25);
		buttons.padLeft(progressWatchAds.bgLeftWidth);
		buttons.add(PlayAds).size(120).padLeft(-progressWatchAds.bgLeftWidth);
		for(int i=0;i<6;i++) 
		buttons.add(GanCiamond[i]).size(50).right().fillX().expand();
		add(buttons);
		pack();
		
		AddListener();
	}
	public boolean addViewAds() {
		bCollect.setDisabled(false);
		return progressWatchAds.addView();
	}
	public void CollectDiamond(){
		for(int i=0;i<6;i++) {
			if(!GanCiamond[i].isDisabled() && !GanCiamond[i].isChecked()) 
							GanCiamond[i].setChecked(true);
		}
		progressWatchAds.finir();
		bCollect.setDisabled(true);
	}
    private void AddListener() {
    	PlayAds.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
					Assest.buttonClick1.play();
					CrachGame.getiActivityRequestHandler().showVideosAds(new Runnable() {
						@Override
						public void run() {
							addViewAds();
							CrachGame.addAdsWatchd();
						}
					}, new Runnable() {
						@Override
						public void run() {
							if(CrachGame.isVibr()) Gdx.input.vibrate(60);
						}
					});
					System.out.println("videos ads watched");
    		}
    	});
    }
	public class ProgressWatchAds extends ProgressBar{
	    private float position;
		private Drawable bg,knobBefore;
		public float bgLeftWidth,bgRightWidth,getTopHeight,BottomHeight,positionHeight,positionWidth;
		public ProgressWatchAds() {
			super(0, 6, 0.001f, false, Assest.Style, "WatchAds");
	    	ProgressBarStyle style = this.getStyle();
			setAnimateDuration(1);
	 		 bg =  style.background;
			 knobBefore = style.knobBefore;
	 			if (bg != null) {
	 				positionHeight = bg.getTopHeight()+bg.getBottomHeight();
	 				bgLeftWidth = bg.getLeftWidth();
	 				bgRightWidth = bg.getRightWidth();
	 			}

		}
		@Override
		public void draw(Batch batch, float parentAlpha) {

	 		Color color = getColor();
	 		float x = getX();
	 		float y = getY();
	 		float width = getWidth();
	 		float height = getHeight();

	 		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
	 		
	 		
	 		            positionWidth = width;
	 	 			if (bg != null) {
		 				positionWidth -= bgLeftWidth + bgRightWidth;
		 			}
	 	 			float percent = getVisualPercent();
					position = (positionWidth ) * percent;
					position = Math.min(positionWidth, position);
					
		 			if (knobBefore != null)
		 				knobBefore.draw(batch, x + bgLeftWidth, y + (positionHeight) * 0.5f, position ,
		 						height-positionHeight);
		 	
		 			bg.draw(batch, x, y, width, height);
		 				
		}
		public boolean addView() {
			final int   value = (int)getValue();
			if(value<GanCiamond.length && (int)getVisualValue () == value) {
				setValue(value+1);
				GanCiamond[value].addAction(Actions.delay(1, Actions.run(new Runnable() {
					@Override
					public void run() {
						Assest.soundWatchAds.get(value).play();
       				    GanCiamond[value].setDisabled(false);
					}
				})));
			return true;
			}
			return false;
		}
		
		public Action ActionGanCiamond( final int value) {
			return Actions.run(new Runnable() {
				@Override
				public void run() {
					Assest.soundCrystal.play();
               	 GanCiamond[value].setDisabled(true);
               	 GanCiamond[value].setChecked(false);
               	 CrachGame.addDiamound((Integer)GanCiamond[value].getUserObject());
               	 if(value >0) 
               		 GanCiamond[value-1].addAction(Actions.delay(1f,ActionGanCiamond( value-1)));
               	 
      			 setValue(value);
				}});
		}
		
		public void finir() {
			final int  value = (int)getValue();
			if(value > 0) GanCiamond[value-1].addAction(Actions.delay(0.5f,ActionGanCiamond( value-1)));
		}
	}
}
