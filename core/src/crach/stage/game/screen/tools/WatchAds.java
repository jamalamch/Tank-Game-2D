package crach.stage.game.screen.tools;


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

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;

public class WatchAds extends Stack{
	ProgressWatchAds progressWatchAds;
	private TextButton[] buttonGanCiamonds;
	private Button buttonPlayAds;
	private Button buttonCollect;
	public WatchAds(Button bCollect) {
		super();
		this.buttonCollect =bCollect;
		progressWatchAds = new ProgressWatchAds();
		add(progressWatchAds);
		buttonGanCiamonds = new TextButton[6];
		for(int i=0;i<6;i++) {
			int Diamound = 10+i*5;
			buttonGanCiamonds[i] = new TextButton(Diamound+"*", Assets.skinStyle, "number");
			buttonGanCiamonds[i].setUserObject(Diamound);
			buttonGanCiamonds[i].setSize(50, 50);
			buttonGanCiamonds[i].setDisabled(true);
			buttonGanCiamonds[i].setTouchable(Touchable.disabled);
		}
		buttonPlayAds = new Button(Assets.skinStyle, "PlayAds");
		
		Table buttons = new Table();
		buttons.padRight(progressWatchAds.bgRightWidth-25);
		buttons.padLeft(progressWatchAds.bgLeftWidth);
		buttons.add(buttonPlayAds).size(120).padLeft(-progressWatchAds.bgLeftWidth);
		for(int i=0;i<6;i++) 
		buttons.add(buttonGanCiamonds[i]).size(50).right().fillX().expand();
		add(buttons);
		pack();
		
		AddListener();
	}
	public boolean addViewAds() {
		buttonCollect.setDisabled(false);
		return progressWatchAds.addView();
	}
	public void CollectDiamond(){
		for(int i=0;i<6;i++) {
			if(!buttonGanCiamonds[i].isDisabled() && !buttonGanCiamonds[i].isChecked())
							buttonGanCiamonds[i].setChecked(true);
		}
		progressWatchAds.finir();
		buttonCollect.setDisabled(true);
	}
    private void AddListener() {
    	buttonPlayAds.addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
					Assets.buttonClick1.play();
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
			super(0, 6, 0.001f, false, Assets.skinStyle, "WatchAds");
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
			if(value< buttonGanCiamonds.length && (int)getVisualValue () == value) {
				setValue(value+1);
				buttonGanCiamonds[value].addAction(Actions.delay(1, Actions.run(new Runnable() {
					@Override
					public void run() {
						Assets.soundWatchAds.get(value).play();
       				    buttonGanCiamonds[value].setDisabled(false);
					}
				})));
			return true;
			}
			return false;
		}
		
		private Action actionGanCiamond(final int value) {
			return Actions.run(new Runnable() {
				@Override
				public void run() {
					Assets.soundCrystal.play();
               	 buttonGanCiamonds[value].setDisabled(true);
               	 buttonGanCiamonds[value].setChecked(false);
               	 CrachGame.addDiamound((Integer) buttonGanCiamonds[value].getUserObject());
               	 if(value >0) 
               		 buttonGanCiamonds[value-1].addAction(Actions.delay(1f, actionGanCiamond( value-1)));
               	 
      			 setValue(value);
				}});
		}
		
		public void finir() {
			final int  value = (int)getValue();
			if(value > 0) buttonGanCiamonds[value-1].addAction(Actions.delay(0.5f, actionGanCiamond( value-1)));
		}
	}
}
