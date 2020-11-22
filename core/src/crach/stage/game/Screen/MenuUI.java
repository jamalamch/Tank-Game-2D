package crach.stage.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.GdxRequest;
import crach.stage.game.Screen.Tools.*;
import crach.stage.game.windows.Bonus;
import crach.stage.game.windows.Hanger;
import crach.stage.game.windows.SelectStage;
import crach.stage.game.windows.Settings;
import crach.stage.game.windows.Shop;

public class MenuUI extends ScreenGame {
	
	private Table UiMenu;
    private HubUi UiHub; 
    private HorizontalGroup SettingAndInfo;
    private VerticalGroup HangarShop;
    private ModeGame modegame;

    private Shop shope;   
    private Hanger hanger;
	private Settings Setting;
	private Bonus BonusW;
	private SelectStage selectStage;
	private SelectStage selectMatch;

	
	private Image HeadTitle;
    private Image backgroundI;
	private Button B_Star,B_Exit,Rating_BTN,More_Games_BTN, Game_Center_BTN,Facebook_BTN
			//,Twitter_BTN
			,Share_BTN,Settings_BTN,Settings_BTN2
	               ,Hangar_BTN,Shop_BTN,Info_BTN;
		
	public MenuUI() {
		super();
		HeadTitle = new Image(Assest.Style, "Head_title");
		backgroundI = new Image(Assest.Style, "BackgroundMenu");
		backgroundI.setFillParent(true);
		backgroundI.setScaling(Scaling.fill);
		HeadTitle.setOrigin(Align.center);		
		B_Star = new TextButton(Assest.StringMenu.getString("Star"), Assest.Style,"tileButton");
		B_Exit = new TextButton(Assest.StringMenu.getString("Exit"), Assest.Style,"tileButton");
		B_Exit.setVisible(false);
		Rating_BTN = new Button(Assest.Style, "Rating_BTN");
		More_Games_BTN = new Button(Assest.Style, "More_Games_BTN");
		Game_Center_BTN = new Button(Assest.Style, "Game_Center_BTN");
		Facebook_BTN = new Button(Assest.Style, "Facebook_BTN");
		//Twitter_BTN = new Button(Assest.Style, "Twitter_BTN");
		Share_BTN = new Button(Assest.Style, "Share_BTN");
		Settings_BTN = new Button(Assest.Style, "Settings_BTN");
		Settings_BTN2 = new Button(Assest.Style, "Settings_BTN");
		Hangar_BTN = new Button(Assest.Style, "Hangar_BTN");
		Shop_BTN = new Button(Assest.Style, "Shop_BTN");
		Info_BTN = new Button(Assest.Style, "Info_BTN");
       addContent();
       AddListener();
       if(MathUtils.random(1) == 1) {
		   Assest.musicMenu2.play();
		   Assest.musicMenu2.setLooping(true);
	   }else{
		   Assest.musicMenu1.play();
		   Assest.musicMenu1.setLooping(true);
	   }
	}
	@Override
	public void show() {
		super.show();
		selectStage.update();
		selectMatch.update();
	}

	private void  addContent() {
		addActor(backgroundI);
		UiHub = new HubUi(this);
		addActor(UiHub);

		UiMenu = new Table(Assest.Style);
		//table.setDebug(true);
		UiMenu.pad(2);
		UiMenu.defaults().pad(8).center().minSize(3);
		UiMenu.setFillParent(true);
		UiMenu.row();
		UiMenu.add(Rating_BTN).prefSize(120);
		UiMenu.add();
		UiMenu.add().expandX().prefSize(120);
		UiMenu.add();
		UiMenu.add(Settings_BTN).prefSize(120);
		UiMenu.row();
		UiMenu.add(More_Games_BTN).prefSize(120);
		UiMenu.add().colspan(4);
		UiMenu.row();
		UiMenu.add(Game_Center_BTN).prefSize(120);
		UiMenu.add().colspan(4);
		UiMenu.row();
		UiMenu.add(HeadTitle).colspan(5).padBottom(-50).padTop(-300).expandY()
		      .prefSize(830, 280).center();
		UiMenu.row();
		UiMenu.add(B_Star).colspan(5).minWidth(400).align(Align.bottom).padBottom(-70).padTop(50).bottom();
		UiMenu.row();
		UiMenu.add(B_Exit).colspan(5).expandY().top().padBottom(60).minWidth(300);
		UiMenu.row().padTop(-200).bottom();
		UiMenu.add(Facebook_BTN).prefSize(120);
		//UiMenu.add(Twitter_BTN).prefSize(120);
		UiMenu.add(Share_BTN).prefSize(120);
		UiMenu.add().colspan(3);
		UiMenu.row();
		UiMenu.pack();
		addActor(UiMenu);
		SettingAndInfo = new HorizontalGroup();
		SettingAndInfo.addActor(Settings_BTN2);
		SettingAndInfo.addActor(Info_BTN);
        SettingAndInfo.setScale(0.5f);
        SettingAndInfo.space(10);
        SettingAndInfo.setVisible(false);
        SettingAndInfo.pack();
        addActor(SettingAndInfo);
        HangarShop = new VerticalGroup();
        HangarShop.addActor(Hangar_BTN);
        HangarShop.addActor(Shop_BTN);
        HangarShop.space(5);
        HangarShop.setVisible(false);
        HangarShop.pack();
        modegame = new ModeGame(this);
        addActor(modegame);
		addActor(HangarShop);
        hanger = new Hanger(Hangar_BTN);
        addActor(hanger);
		BonusW = new Bonus();
		addActor(BonusW);
		Setting = new Settings();
		stage.addActor(Setting);
        shope = new Shop(Shop_BTN);
        addActor(shope); 
        selectStage = new SelectStage();
        addActor(selectStage);
        selectMatch = new SelectStage(Assest.StringSelectStage.getString("title_match"),CrachGame.getMatchfoot(), PlayScreen.ModeGame.football);
		addActor(selectMatch);
	}
 
	public HubUi getUiHub() {
		return UiHub;
	}

	private void AddListener() {
		B_Star.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				   Assest.buttonClick1.play();
				   OpenUiPlay();
			}});
		
        B_Exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				  // CrachGame.getGdxGame().setScreen(new PlayScreen(10));
               // CloseUiPlay(); 
			}});
        
        Rating_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				BonusW.Open();
			}});     
        More_Games_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				CrachGame.showLeaderboard();
			}});
        Game_Center_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
                Gdx.net.openURI("https://play.google.com/store/apps/dev?id=5154066778505369303");
            }
		});
        Facebook_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
                Gdx.net.openURI("https://www.facebook.com/Gamer-Key-106490947508953");
            }
		});  
//        Twitter_BTN.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Assest.buttonClick1.play();
//
//			}
//		});
        Share_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				//Gdx.net.openURI("http://play.google.com/store/apps/details?id=crach.stage.game");
                GdxRequest.partage();
			}
		}); 
        InputListener C = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				if(Setting == null) {
				}
				Setting.Open();
			}
        };
        Settings_BTN.addListener(C);  
        Settings_BTN2.addListener(C);             
        Hangar_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				if(!hanger.hasActions() && !hanger.isVisible())
					hanger.Open();
			}
		});
        Shop_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Shop_BTN.isChecked()) Assest.checkOnClick.play();
				else Assest.checkOfClick.play();
				if(!shope.hasActions() && !shope.isVisible())
					shope.Open();
			}
		});
        Info_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
			}
		});
         C = new InputListener() {
        	@Override
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(CloseAllWindows()) return false;
        		return super.touchDown(event, x, y, pointer, button);
        	}
        };
        UiMenu.addListener(C);
        SettingAndInfo.addListener(C);
        HangarShop.addListener(C);
        modegame.addListener(C);
        stage.addListener(new InputListener() {        	
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
					Assest.buttonClick2.play();
					if(!CloseAllWindows())
	 			        CloseUiPlay();
					}
				return (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK);
			}});
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawStage(delta);
	}
	
	public void SetUiMenuTochable(Touchable tochable) {
		if(UiMenu.isVisible()) UiMenu.setTouchable(tochable);
		if(SettingAndInfo.isVisible())  SettingAndInfo.setTouchable(tochable);
		if(HangarShop.isVisible()) HangarShop.setTouchable(tochable);
		if(modegame.isVisible()) modegame.setTouchable(tochable);
	}
	public void OpenUiPlay() {
		if(!UiMenu.hasActions() && !modegame.hasActions()) {
			UiMenu.addAction(Actions.sequence(Actions.alpha(0, 1.5f),Actions.visible(false)));
			HeadTitle.addAction(Actions.scaleTo(2.5f, 2.5f, 1.5f));
			UiHub.Open();
			modegame.Open();
			HangarShop.addAction(Actions.sequence(Actions.delay(1.5f),Actions.moveTo(-HangarShop.getWidth(),HangarShop.getY()),Actions.visible(true),Actions.moveTo(HangarShop.getX(), HangarShop.getY(),0.4f)));
			SettingAndInfo.addAction(Actions.sequence(Actions.delay(1.5f),Actions.moveTo(SettingAndInfo.getX(),-SettingAndInfo.getHeight()),Actions.visible(true),Actions.moveTo(SettingAndInfo.getX(), SettingAndInfo.getY(),0.4f)));
			}
	}
	public void CloseUiPlay() {
		if(!UiMenu.hasActions() && !UiHub.hasActions() && !UiMenu.isVisible() &&HangarShop.isVisible() ) {
			UiHub.Close();
			modegame.Close();
			UiMenu.addAction(Actions.sequence(Actions.delay(0.55f),Actions.visible(true),Actions.alpha(1, 0.6f)));
			HeadTitle.addAction(Actions.sequence(Actions.delay(0.6f),Actions.scaleTo(1, 1, 1)));
			HangarShop.addAction(Actions.sequence(Actions.moveTo(-HangarShop.getWidth(),HangarShop.getY(),0.3f),Actions.visible(false),Actions.moveTo(HangarShop.getX(), HangarShop.getY())));
			SettingAndInfo.addAction(Actions.sequence(Actions.moveTo(SettingAndInfo.getX(),-SettingAndInfo.getHeight(),0.4f),Actions.visible(false),Actions.moveTo(SettingAndInfo.getX(), SettingAndInfo.getY())));
		}
	}
	public boolean CloseAllWindows() {
		boolean WindowOpen = false;
		
		 if(Setting.isVisible() && !Setting.hasActions()) {
			WindowOpen =true;
			Setting.Close();
		}
		if(shope.isVisible() && !shope.hasActions()) {
			WindowOpen =true;
			shope.Close();  
	    }
		if(hanger.isVisible() && !hanger.hasActions()) {
			WindowOpen =true;
			hanger.Close();
		}
	    if(BonusW.isVisible() && !BonusW.hasActions()) {
			WindowOpen =true;
			BonusW.Close();
		}
	    if(selectStage.isVisible() && !selectStage.hasActions()) {
			WindowOpen =true;
			selectStage.Close();
	    }
		if(selectMatch.isVisible() && !selectMatch.hasActions()) {
			WindowOpen =true;
			selectMatch.Close();
		}
		return WindowOpen;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
			Setting.resize();
		
			BonusW.resize();
		
			UiHub.setSize(stage.getWidth(), 85);
			UiHub.setPosition(0, stage.getHeight()-85);
		
			HangarShop.setPosition(10, stage.getHeight()-390);
		
			SettingAndInfo.setPosition(stage.getWidth()-150, 10);
		
			modegame.setSize(1000, 400);
			modegame.setPosition(stage.getWidth()-1010,stage.getHeight()/4);
		
			float Y = HangarShop.getY()+HangarShop.getHeight()-shope.getHeight();
			if(Y < 0) Y=0;
			shope.setPosition(HangarShop.getX()+Shop_BTN.getWidth(),Y);
		
			hanger.resize();
			selectStage.resize();
			selectMatch.resize();
	}
	public Hanger getHanger() {
		return hanger;
	}
	public SelectStage getSelectStage() {
		return selectStage;
	}
	public SelectStage getSelectMatch() {
		return selectMatch;
	}
	public Shop getShope() {
		return shope;
	}
}