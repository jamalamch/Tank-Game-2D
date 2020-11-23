package crach.stage.game.windows;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.windows.dialog.DialogQuestion;

public class Settings extends WindowsGames{
	
	
	private SelectBox<String> SelectLang;
	private Button replaceId,info;
	private TextField TextId;
	private CheckBox sfxBox,musicBox,vibrationBox,notificationBox;
	private Image sfxI,musicI,vibI,notI;
	private Slider Ssfx,Smusic;
	
    private Credits credits;

	public Settings() {
		super(Assets.jsonStringSetting.getString("title"), "Setting");
        this.getTitleTable().getCell(getTitleLabel()).maxWidth(600).padBottom(30);
		TextId = new TextField("", Assets.skinStyle);
		TextId.setAlignment(Align.center);
		SelectLang = new SelectBox<String>(Assets.skinStyle);
		Array<String> boxLa = new Array<String>(CrachGame.Langues);
		SelectLang.setItems(boxLa);
		SelectLang.setSelected(CrachGame.Langue);
		replaceId = new Button(Assets.skinStyle, "Ok_BTN");
		//ChangeLang = new Button(Assest.Style, "Change_BTN");
		//help = new Button(Assest.Style, "FAQ_BTN");
		info = new Button(Assets.skinStyle, "Info_BTN");
		sfxI = new Image(Assets.skinStyle, "BTNs/Sound_BTN");
		musicI = new Image(Assets.skinStyle, "BTNs/Music_BTN");
		vibI = new Image(Assets.skinStyle, "BTNs/Vibration_BTN");
		notI = new Image(Assets.skinStyle, "BTNs/Notifications_BTN");
		
		sfxBox = new CheckBox("", Assets.skinStyle);
		musicBox = new CheckBox("", Assets.skinStyle);
		vibrationBox = new CheckBox("", Assets.skinStyle);
		notificationBox = new CheckBox("", Assets.skinStyle);
		Smusic = new Slider(0, 1, 0.1f, false, Assets.skinStyle);
		Ssfx = new Slider(0, 1, 0.1f, false, Assets.skinStyle);
		
		addContent();
		AddListener();
	}

	@Override
	public void addContent() {
		    defaults().pad(10).center().minSize(50).fillX();
		    //setDebug(true);
		    row();
		    add().colspan(7);
			row();
			add(Assets.jsonStringSetting.getString("name"),"inSqure").padRight(-60).getActor().setAlignment(Align.center);
			add(TextId).colspan(2).padLeft(70);
			add(replaceId).maxSize(80);
			add(SelectLang).maxHeight(60).colspan(3);
			row();
			add(sfxI).maxSize(80);;
			add(Assets.jsonStringSetting.getString("sfx"),"inSqure").getActor().setAlignment(Align.center);
			add(Ssfx).prefWidth(230);
			add(sfxBox);
			add(vibI).maxSize(80);
			add(Assets.jsonStringSetting.getString("vibration"),"inSqure").getActor().setAlignment(Align.center);
			add(vibrationBox);
			row();
			add(musicI).maxSize(80);;
			add(Assets.jsonStringSetting.getString("music"),"inSqure").getActor().setAlignment(Align.center);
			add(Smusic).prefWidth(230);
			add(musicBox);
			add(notI).maxSize(80);;
			add(Assets.jsonStringSetting.getString("notification"),"inSqure").getActor().setAlignment(Align.center);
			add(notificationBox);
			row();
			add().colspan(5);
			add(Assets.jsonStringSetting.getString("credits"),"extra").maxSize(100,50).right();
			add(info).maxSize(60);
			pack();
	}
	@Override
	protected void AddListener() {
		replaceId.addListener(new ClickListener() {

		});
		SelectLang.getList().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
  				Assets.checkOnClick.play();
				if(!CrachGame.getLangue().equals(SelectLang.getSelected())){
					getStage().addActor(new DialogQuestion(Assets.jsonStringDialog.getString("changeLang")
							, Assets.jsonStringDialog.getString("msgChangLang")) {
						@Override
						public void Accepte() {
							CrachGame.setLangue(SelectLang.getSelected());
							Gdx.app.exit();
						}
						@Override
						public void Refuse() {
							SelectLang.setSelected(CrachGame.getLangue());
						}
					});
				}
			}
		});
		info.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
  				Assets.buttonClick1.play();
			if(credits == null) {
				credits=  new Credits();	    
			    getStage().addActor(credits);
				credits.Open();
			}else  
				credits.Open();
			}
		});
		TextId.addListener(new ClickListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	if(!TextId.getText().equals(CrachGame.getId())) CrachGame.setId(TextId.getText());
            	super.exit(event, x, y, pointer, toActor);
            }
		});
		sfxBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(sfxBox.isChecked()) { 
					CrachGame.setSfx(sfxBox.isChecked());
					Assets.checkOnClick.play();
				}
				else  {
					Assets.checkOfClick.play();
					CrachGame.setSfx(sfxBox.isChecked());	
				}
			}});
		musicBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(musicBox.isChecked())   Assets.checkOnClick.play();
				else  Assets.checkOfClick.play();
				CrachGame.setMusic(musicBox.isChecked());
			}
		});
		vibrationBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(vibrationBox.isChecked())   Assets.checkOnClick.play();
				else  Assets.checkOfClick.play();
				CrachGame.setVibr(vibrationBox.isChecked());
			}
		});
		notificationBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(notificationBox.isChecked())   Assets.checkOnClick.play();
				else  Assets.checkOfClick.play();
				CrachGame.setNotific(notificationBox.isChecked());
			}
		});
		Ssfx.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				CrachGame.setnVsfx(Ssfx.getValue());
			}
		});
		Smusic.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				CrachGame.setnVmusic(Smusic.getValue());
			}
		});
		super.AddListener();
	}
	public void Close() {
		super.Close();
		getStage().unfocus(TextId);
		Gdx.input.setOnscreenKeyboardVisible(false);
	}
	@Override
	public void Open() {
		TextId.setText(CrachGame.getId());
		musicBox.setChecked(CrachGame.isMusic());
		sfxBox.setChecked(CrachGame.isSfx());
		vibrationBox.setChecked(CrachGame.isVibr());
		notificationBox.setChecked(CrachGame.isNotific());
	    Smusic.setValue(CrachGame.getnVmusic());
	    Ssfx.setValue(CrachGame.getnVsfx());
		super.Open();
	}
	@Override
	public void resize() {
		super.resize();
		this.PoseY -= 30;
	}
}
