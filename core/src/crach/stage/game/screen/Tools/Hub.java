package crach.stage.game.screen.Tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.PlayScreen;
import crach.stage.game.creator.B2WorldCreator;


public class Hub extends Table {
	
	private B2WorldCreator creator;
	private PlayScreen gdxPlayScreen;
	
	private float timer ;
	private int secs ;
	private int mins ;
	
	private Image health, weapon2;
	private ProgressUi pHealth, pWeapon2;
	private Label labelScore, timerLabel;

	private SonAndMusic sonAndMusic;
	private Button menu;
	private boolean disableTimer;
	
	
	
	public Hub(B2WorldCreator creator) {
		      this.creator = creator;
		      this.gdxPlayScreen = creator.getGdxPlayScreen();
              labelScore = new Label("000000", Assest.Style);
              timerLabel = new Label("00:00", Assest.Style,"extra");
			  timerLabel.setAlignment(Align.center);

              health = new Image(Assest.Style, "Icon/Health");
              weapon2 = new Image(Assest.Style, "Icon/Weapon2");
              pHealth = new ProgressUi(creator.getPlayer().getLife(), Assest.Style, "A_Upgrade_h", true);
              pHealth.setValue(creator.getPlayer().getLife());
              pWeapon2 = new ProgressUi(creator.getPlayer().fire,0.1f, Assest.Style, "C_Upgrade_h", true);
              
              menu = new Button(Assest.Style, "Lalister");
              
              sonAndMusic = new SonAndMusic(menu);

              addContent();
              addListener();
	}
	public void addContent() {
		      top();
              setFillParent(true);
              defaults().minSize(40).pad(4);
              add(health);add(pHealth).width(300);add(labelScore).expandX();
              add(timerLabel).size(100,40);
              add(CrachGame.getMenuUi().getUiHub().getLifeGame()).maxHeight(40);
              add(sonAndMusic).padBottom(-sonAndMusic.getHeight());add(menu).row();
              add(weapon2);add(pWeapon2).left().row();
              pack();
	}
	
	protected void addListener() {
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				while(sonAndMusic.hasActions())
					sonAndMusic.removeAction(sonAndMusic.getActions().get(0));

				if(!sonAndMusic.isVisible()) {
					sonAndMusic.setVisible(true);		
					sonAndMusic.addAction(Actions.delay(10, Actions.visible(false)));
				}
				else {
					sonAndMusic.setVisible(false);
				}
			}
		});
	}
	
	public void update( float delta) {
		timer += delta;
		if(!disableTimer ) {
			if(mins >= 99 && secs >= 99 && timer >= 99) {
				disableTimer  = true;
			} else {
				if(timer >= 1) {
					secs++;
					timer = 0;
				}
				if(secs >= 60) {
					mins++;
					secs = 0;
				}
			}
		}
			updateTimerLabel();
		}

	private void updateTimerLabel() {
			timerLabel.getText().clear();
			timerLabel.getText().append(mins, 2).append(" : ").append(secs,2);
			timerLabel.invalidate ();
	}

	public void updatePointScore(int point) {
		labelScore.getText().clear();
		labelScore.getText().append(point,6);
		labelScore.invalidate();
	}
	public void updatePHealth(float health) {
		pHealth.setValue(health);
	}
	public void updatePWeapon(float weapon) {
		pWeapon2.setValue(weapon);
	}
	public int getMins() {
		return mins;
	}
	private class SonAndMusic extends Table{
		
		private Button music,son,annuler,pause,zoom,menu;
		
		public SonAndMusic(Button menu) {
			super(Assest.Style);
			this.menu = menu;
			music = new Button(Assest.Style, "music");
			son = new Button(Assest.Style, "son");
			annuler = new Button(Assest.Style, "annuler");
            pause = new Button(Assest.Style, "pauser");
            zoom = new Button(Assest.Style, "Zoom");
            music.setChecked(!CrachGame.isMusic());
            son.setChecked(!CrachGame.isSfx());
			defaults().maxSize(60).pad(2);
			row();
			add(music).row();
			add(son).row();
			add(pause).row();
			add(annuler).row();
			add(zoom).row();
			pack();
			setBackground("controle/lister_2");
			setVisible(false);
			addListener();
		}
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			this.menu.setChecked(visible);
			if(visible)
				zoom.setChecked(gdxPlayScreen.isZoom());
		}
		protected void addListener() {
			music.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					CrachGame.setMusic(!music.isChecked());
				}
			});
			son.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					CrachGame.setSfx(!son.isChecked());
				}
			});
			pause.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					creator.pauser();
				}
			});
			annuler.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					creator.QuitGame();
				}
			});
			zoom.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(zoom.isChecked())
						gdxPlayScreen.upToModeZoom();
					else 
						gdxPlayScreen.quitModeZoom();
				}
			});
			
		}
	}
}
