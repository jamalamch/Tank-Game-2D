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

import crach.stage.game.Assest;
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
		super(Assest.StringSetting.getString("title"), "Setting");
        this.getTitleTable().getCell(getTitleLabel()).maxWidth(600).padBottom(30);
		TextId = new TextField("", Assest.Style);
		TextId.setAlignment(Align.center);
		SelectLang = new SelectBox<String>(Assest.Style);
		Array<String> boxLa = new Array<String>(CrachGame.Langues);
		SelectLang.setItems(boxLa);
		SelectLang.setSelected(CrachGame.Langue);
		replaceId = new Button(Assest.Style, "Ok_BTN");
		//ChangeLang = new Button(Assest.Style, "Change_BTN");
		//help = new Button(Assest.Style, "FAQ_BTN");
		info = new Button(Assest.Style, "Info_BTN");
		sfxI = new Image(Assest.Style, "BTNs/Sound_BTN");
		musicI = new Image(Assest.Style, "BTNs/Music_BTN");
		vibI = new Image(Assest.Style, "BTNs/Vibration_BTN");
		notI = new Image(Assest.Style, "BTNs/Notifications_BTN");
		
		sfxBox = new CheckBox("", Assest.Style);
		musicBox = new CheckBox("", Assest.Style);
		vibrationBox = new CheckBox("", Assest.Style);
		notificationBox = new CheckBox("", Assest.Style);
		Smusic = new Slider(0, 1, 0.1f, false, Assest.Style);
		Ssfx = new Slider(0, 1, 0.1f, false, Assest.Style);
		
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
			add(Assest.StringSetting.getString("name"),"inSqure").padRight(-60).getActor().setAlignment(Align.center);
			add(TextId).colspan(2).padLeft(70);
			add(replaceId).maxSize(80);
			add(SelectLang).maxHeight(60).colspan(3);
			row();
			add(sfxI).maxSize(80);;
			add(Assest.StringSetting.getString("sfx"),"inSqure").getActor().setAlignment(Align.center);
			add(Ssfx).prefWidth(230);
			add(sfxBox);
			add(vibI).maxSize(80);
			add(Assest.StringSetting.getString("vibration"),"inSqure").getActor().setAlignment(Align.center);
			add(vibrationBox);
			row();
			add(musicI).maxSize(80);;
			add(Assest.StringSetting.getString("music"),"inSqure").getActor().setAlignment(Align.center);
			add(Smusic).prefWidth(230);
			add(musicBox);
			add(notI).maxSize(80);;
			add(Assest.StringSetting.getString("notification"),"inSqure").getActor().setAlignment(Align.center);
			add(notificationBox);
			row();
			add().colspan(5);
			add(Assest.StringSetting.getString("credits"),"extra").maxSize(100,50).right();
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
  				Assest.checkOnClick.play();
				if(!CrachGame.getLangue().equals(SelectLang.getSelected())){
					getStage().addActor(new DialogQuestion(Assest.StringDialog.getString("changeLang")
							,Assest.StringDialog.getString("msgChangLang")) {
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
  				Assest.buttonClick1.play();
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
					Assest.checkOnClick.play();
				}
				else  {
					Assest.checkOfClick.play();
					CrachGame.setSfx(sfxBox.isChecked());	
				}
			}});
		musicBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(musicBox.isChecked())   Assest.checkOnClick.play();
				else  Assest.checkOfClick.play();
				CrachGame.setMusic(musicBox.isChecked());
			}
		});
		vibrationBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(vibrationBox.isChecked())   Assest.checkOnClick.play();
				else  Assest.checkOfClick.play();
				CrachGame.setVibr(vibrationBox.isChecked());
			}
		});
		notificationBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(notificationBox.isChecked())   Assest.checkOnClick.play();
				else  Assest.checkOfClick.play();
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
