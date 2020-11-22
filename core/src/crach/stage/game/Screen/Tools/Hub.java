package crach.stage.game.Screen.Tools;

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
import crach.stage.game.Screen.PlayScreen;
import crach.stage.game.Creator.B2WorldCreator;


public class Hub extends Table {
	
	private B2WorldCreator creator;
	private PlayScreen GdxPlayScreen;
	
	private float timer ;
	private int secs ;
	private int mins ;
	
	private Image Health,Weapon2;
	private ProgressUi PHealth,PWeapon2; 
	private Label LabelScore, timerLabel;

	private SonAndMusic sonAndMusic;
	private Button Menu;
	private boolean disableTimer;
	
	
	
	public Hub(B2WorldCreator creator) {
		      this.creator = creator;
		      this.GdxPlayScreen = creator.getGdxPlayScreen();
              LabelScore = new Label("000000", Assest.Style);
              timerLabel = new Label("00:00", Assest.Style,"extra");
			  timerLabel.setAlignment(Align.center);

              Health = new Image(Assest.Style, "Icon/Health");
              Weapon2 = new Image(Assest.Style, "Icon/Weapon2");
              PHealth = new ProgressUi(creator.getPlayer().getLife(), Assest.Style, "A_Upgrade_h", true);
              PHealth.setValue(creator.getPlayer().getLife());
              PWeapon2 = new ProgressUi(creator.getPlayer().fire,0.1f, Assest.Style, "C_Upgrade_h", true);
              
              Menu = new Button(Assest.Style, "Lalister");
              
              sonAndMusic = new SonAndMusic(Menu);

              addContent();
              AddListener();
	}
	public void addContent() {
		      top();
              setFillParent(true);
              defaults().minSize(40).pad(4);
              add(Health);add(PHealth).width(300);add(LabelScore).expandX();
              add(timerLabel).size(100,40);
              add(CrachGame.getMenuUi().getUiHub().getLifeGame()).maxHeight(40);
              add(sonAndMusic).padBottom(-sonAndMusic.getHeight());add(Menu).row();
              add(Weapon2);add(PWeapon2).left().row();
              pack();
	}
	
	protected void AddListener() {
		Menu.addListener(new ClickListener() {
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
		LabelScore.getText().clear();
		LabelScore.getText().append(point,6);
		LabelScore.invalidate();
	}
	public void updatePHealth(float health) {
		PHealth.setValue(health);
	}
	public void updatePWeapon(float weapon) {
		PWeapon2.setValue(weapon);
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
			AddListener();
		}
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			this.menu.setChecked(visible);
			if(visible)
				zoom.setChecked(GdxPlayScreen.isZoom());
		}
		protected void AddListener() {
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
						GdxPlayScreen.upToModeZoom();
					else 
						GdxPlayScreen.quitModeZoom();
				}
			});
			
		}
	}
}
