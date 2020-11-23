package crach.stage.game.screen;

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

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.GdxRequest;
import crach.stage.game.screen.Tools.*;
import crach.stage.game.windows.Bonus;
import crach.stage.game.windows.Hanger;
import crach.stage.game.windows.SelectStage;
import crach.stage.game.windows.Settings;
import crach.stage.game.windows.Shop;

public class MenuUI extends ScreenGame {
	
	private Table uiMenu;
    private HubUi uiHub;
    private HorizontalGroup settingAndInfo;
    private VerticalGroup hangarShop;
    private ModeGame modegame;

    private Shop shope;   
    private Hanger hanger;
	private Settings setting;
	private Bonus bonusW;
	private SelectStage selectStage;
	private SelectStage selectMatch;

	
	private Image headTitle;
    private Image backgroundI;
	private Button bStar, bExit,Rating_BTN, moreGamesBTN, gameCenterBTN, facebookBTN
			, shareBTN, settingsBTN, settingsBTN2
			, hangarBTN, shopBTN, infoBTN;
		
	public MenuUI() {
		super();
		headTitle = new Image(Assets.skinStyle, "Head_title");
		headTitle.setOrigin(Align.center);
		backgroundI = new Image(Assets.skinStyle, "BackgroundMenu");
		backgroundI.setFillParent(true);
		backgroundI.setScaling(Scaling.fill);
		bStar = new TextButton(Assets.jsonStringMenu.getString("Star"), Assets.skinStyle,"tileButton");
		bExit = new TextButton(Assets.jsonStringMenu.getString("Exit"), Assets.skinStyle,"tileButton");
		bExit.setVisible(false);
		Rating_BTN = new Button(Assets.skinStyle, "Rating_BTN");
		moreGamesBTN = new Button(Assets.skinStyle, "More_Games_BTN");
		gameCenterBTN = new Button(Assets.skinStyle, "Game_Center_BTN");
		facebookBTN = new Button(Assets.skinStyle, "Facebook_BTN");
		//Twitter_BTN = new Button(Assest.Style, "Twitter_BTN");
		shareBTN = new Button(Assets.skinStyle, "Share_BTN");
		settingsBTN = new Button(Assets.skinStyle, "Settings_BTN");
		settingsBTN2 = new Button(Assets.skinStyle, "Settings_BTN");
		hangarBTN = new Button(Assets.skinStyle, "Hangar_BTN");
		shopBTN = new Button(Assets.skinStyle, "Shop_BTN");
		infoBTN = new Button(Assets.skinStyle, "Info_BTN");
       addContent();
       addlistener();
       if(MathUtils.random(1) == 1) {
		   Assets.musicMenu2.play();
		   Assets.musicMenu2.setLooping(true);
	   }else{
		   Assets.musicMenu1.play();
		   Assets.musicMenu1.setLooping(true);
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
		uiHub = new HubUi(this);
		addActor(uiHub);

		uiMenu = new Table(Assets.skinStyle);
		//table.setDebug(true);
		uiMenu.pad(2);
		uiMenu.defaults().pad(8).center().minSize(3);
		uiMenu.setFillParent(true);
		uiMenu.row();
		uiMenu.add(Rating_BTN).prefSize(120);
		uiMenu.add();
		uiMenu.add().expandX().prefSize(120);
		uiMenu.add();
		uiMenu.add(settingsBTN).prefSize(120);
		uiMenu.row();
		uiMenu.add(moreGamesBTN).prefSize(120);
		uiMenu.add().colspan(4);
		uiMenu.row();
		uiMenu.add(gameCenterBTN).prefSize(120);
		uiMenu.add().colspan(4);
		uiMenu.row();
		uiMenu.add(headTitle).colspan(5).padBottom(-50).padTop(-300).expandY()
		      .prefSize(830, 280).center();
		uiMenu.row();
		uiMenu.add(bStar).colspan(5).minWidth(400).align(Align.bottom).padBottom(-70).padTop(50).bottom();
		uiMenu.row();
		uiMenu.add(bExit).colspan(5).expandY().top().padBottom(60).minWidth(300);
		uiMenu.row().padTop(-200).bottom();
		uiMenu.add(facebookBTN).prefSize(120);
		//UiMenu.add(Twitter_BTN).prefSize(120);
		uiMenu.add(shareBTN).prefSize(120);
		uiMenu.add().colspan(3);
		uiMenu.row();
		uiMenu.pack();
		addActor(uiMenu);
		settingAndInfo = new HorizontalGroup();
		settingAndInfo.addActor(settingsBTN2);
		settingAndInfo.addActor(infoBTN);
        settingAndInfo.setScale(0.5f);
        settingAndInfo.space(10);
        settingAndInfo.setVisible(false);
        settingAndInfo.pack();
        addActor(settingAndInfo);
        hangarShop = new VerticalGroup();
        hangarShop.addActor(hangarBTN);
        hangarShop.addActor(shopBTN);
        hangarShop.space(5);
        hangarShop.setVisible(false);
        hangarShop.pack();
        modegame = new ModeGame(this);
        addActor(modegame);
		addActor(hangarShop);
        hanger = new Hanger(hangarBTN);
        addActor(hanger);
		bonusW = new Bonus();
		addActor(bonusW);
		setting = new Settings();
		stage.addActor(setting);
        shope = new Shop(shopBTN);
        addActor(shope); 
        selectStage = new SelectStage();
        addActor(selectStage);
        selectMatch = new SelectStage(Assets.jsonStringSelectStage.getString("title_match"),CrachGame.getMatchfoot(), PlayScreen.ModeGame.football);
		addActor(selectMatch);
	}
 
	public HubUi getUiHub() {
		return uiHub;
	}

	private void addlistener() {
		bStar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				   Assets.buttonClick1.play();
				   openUiPlay();
			}});
		
        bExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				  // CrachGame.getGdxGame().setScreen(new PlayScreen(10));
               // CloseUiPlay(); 
			}});
        
        Rating_BTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				bonusW.Open();
			}});     
        moreGamesBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				CrachGame.showLeaderboard();
			}});
        gameCenterBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
                Gdx.net.openURI("https://play.google.com/store/apps/dev?id=5154066778505369303");
            }
		});
        facebookBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
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
        shareBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				//Gdx.net.openURI("http://play.google.com/store/apps/details?id=crach.stage.game");
                GdxRequest.partage();
			}
		}); 
        InputListener C = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				if(setting == null) {
				}
				setting.Open();
			}
        };
        settingsBTN.addListener(C);
        settingsBTN2.addListener(C);
        hangarBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
				if(!hanger.hasActions() && !hanger.isVisible())
					hanger.Open();
			}
		});
        shopBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(shopBTN.isChecked()) Assets.checkOnClick.play();
				else Assets.checkOfClick.play();
				if(!shope.hasActions() && !shope.isVisible())
					shope.Open();
			}
		});
        infoBTN.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick1.play();
			}
		});
         C = new InputListener() {
        	@Override
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(closeAllWindows()) return false;
        		return super.touchDown(event, x, y, pointer, button);
        	}
        };
        uiMenu.addListener(C);
        settingAndInfo.addListener(C);
        hangarShop.addListener(C);
        modegame.addListener(C);
        stage.addListener(new InputListener() {        	
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
					Assets.buttonClick2.play();
					if(!closeAllWindows())
	 			        closeUiPlay();
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
	
	public void setUiMenuTochable(Touchable tochable) {
		if(uiMenu.isVisible()) uiMenu.setTouchable(tochable);
		if(settingAndInfo.isVisible())  settingAndInfo.setTouchable(tochable);
		if(hangarShop.isVisible()) hangarShop.setTouchable(tochable);
		if(modegame.isVisible()) modegame.setTouchable(tochable);
	}
	public void openUiPlay() {
		if(!uiMenu.hasActions() && !modegame.hasActions()) {
			uiMenu.addAction(Actions.sequence(Actions.alpha(0, 1.5f),Actions.visible(false)));
			headTitle.addAction(Actions.scaleTo(2.5f, 2.5f, 1.5f));
			uiHub.Open();
			modegame.open();
			hangarShop.addAction(Actions.sequence(Actions.delay(1.5f),Actions.moveTo(-hangarShop.getWidth(), hangarShop.getY()),Actions.visible(true),Actions.moveTo(hangarShop.getX(), hangarShop.getY(),0.4f)));
			settingAndInfo.addAction(Actions.sequence(Actions.delay(1.5f),Actions.moveTo(settingAndInfo.getX(),-settingAndInfo.getHeight()),Actions.visible(true),Actions.moveTo(settingAndInfo.getX(), settingAndInfo.getY(),0.4f)));
			}
	}
	public void closeUiPlay() {
		if(!uiMenu.hasActions() && !uiHub.hasActions() && !uiMenu.isVisible() && hangarShop.isVisible() ) {
			uiHub.Close();
			modegame.close();
			uiMenu.addAction(Actions.sequence(Actions.delay(0.55f),Actions.visible(true),Actions.alpha(1, 0.6f)));
			headTitle.addAction(Actions.sequence(Actions.delay(0.6f),Actions.scaleTo(1, 1, 1)));
			hangarShop.addAction(Actions.sequence(Actions.moveTo(-hangarShop.getWidth(), hangarShop.getY(),0.3f),Actions.visible(false),Actions.moveTo(hangarShop.getX(), hangarShop.getY())));
			settingAndInfo.addAction(Actions.sequence(Actions.moveTo(settingAndInfo.getX(),-settingAndInfo.getHeight(),0.4f),Actions.visible(false),Actions.moveTo(settingAndInfo.getX(), settingAndInfo.getY())));
		}
	}
	public boolean closeAllWindows() {
		boolean WindowOpen = false;
		
		 if(setting.isVisible() && !setting.hasActions()) {
			WindowOpen =true;
			setting.Close();
		}
		if(shope.isVisible() && !shope.hasActions()) {
			WindowOpen =true;
			shope.Close();  
	    }
		if(hanger.isVisible() && !hanger.hasActions()) {
			WindowOpen =true;
			hanger.Close();
		}
	    if(bonusW.isVisible() && !bonusW.hasActions()) {
			WindowOpen =true;
			bonusW.Close();
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
			setting.resize();
		
			bonusW.resize();
		
			uiHub.setSize(stage.getWidth(), 85);
			uiHub.setPosition(0, stage.getHeight()-85);
		
			hangarShop.setPosition(10, stage.getHeight()-390);
		
			settingAndInfo.setPosition(stage.getWidth()-150, 10);
		
			modegame.setSize(1000, 400);
			modegame.setPosition(stage.getWidth()-1010,stage.getHeight()/4);
		
			float Y = hangarShop.getY()+ hangarShop.getHeight()-shope.getHeight();
			if(Y < 0) Y=0;
			shope.setPosition(hangarShop.getX()+ shopBTN.getWidth(),Y);
		
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