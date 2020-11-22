package crach.stage.game.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;

public class Credits extends WindowsGames{
	
	private final String GameNameVersion = "Crach Game 1.0.0-beta";
	private ScrollPane Scrll;
	private VerticalGroup Grouplist;
	private Table logiccelOpen;
	private Image Libgdx,Gimp,Tiled,Audacity;
	
	public Credits() {
		super();
		setFillParent(true);
		Grouplist = new VerticalGroup();
		addContent();
		AddListener();
		setMovable(false);
	}

	@Override
	public void addContent() {
		addString(GameNameVersion);
		addTitleString("Credits");
		addString("developed by");
		//add logo stodio
		addTitleString("Programming & Level Design");
		addString("jamal amchart");
		
//		addTitleString("Programming");
//		addString("jamal amchart");
		
		addTitleString("Graphics");
		addString("CraftPix.net").addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("https://www.CraftPix.net");
			}
		});;
		
//		addTitleString("Product Managment");
//		addString("jamal amchart");
		
		addTitleString("Sound Design / Music");
		addString("by Eric Matyas \n www.soundimage.org").addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("https://www.soundimage.org");
			}
		});
		
		addTitleString("Testers");
		addString("said saich , zakaria bou ,mohamed lachgher");
		
		addTitleString("Contact");
		addString("jamalamchart@gmail.com").addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("mailto:jamalamchart@gmail.com?subject=Game+Crach");
			}
		});
		
		addTitleString("Thanks");
		addString("To all the Users for playing ,great levls and revims "
				  +"\n"+ "to the following fantastic open source projects we developed Tank Game with");
		



		Libgdx = new Image(getSkin(),"Icon/libgdx-logo");
		Tiled = new Image(getSkin(),"Icon/Tiled-logo");
		Gimp = new Image(getSkin(),"Icon/gimp-logo.jpeg");
		Audacity =new Image(getSkin(),"Icon/AudacityLogo");

		logiccelOpen = new Table(Assest.Style);
		logiccelOpen.defaults().pad(1).space(40).expandX();
		logiccelOpen.add(Libgdx);
		logiccelOpen.add(Tiled).row();
		logiccelOpen.add(Gimp);
		logiccelOpen.add(Audacity).row();
		logiccelOpen.pack();

		Grouplist.addActor(logiccelOpen);
		addString("please recommend Tank game");

		addString("Thank You");
		
        Grouplist.pad(10,100,10,100);
        Grouplist.space(10);
		Scrll = new ScrollPane(Grouplist, Assest.Style);
		Scrll.setScrollingDisabled(true, false);
		Scrll.setScrollBarPositions(true,false);
		Scrll.setVariableSizeKnobs(false);
		Scrll.setFadeScrollBars(false);
		pad(60,20,40,20);
		add().height(20);
		row();
        add(Scrll).expand();
		pack();
	}
	private Label addTitleString(String title) {
		Label L = new Label(title, getSkin(), "font", new Color(0x5fdf00ff));
		L.setAlignment(Align.top,Align.center);
		Grouplist.addActor(L);
		return L;
	}
	private Label addString(String conString) {
		Label L = new Label(conString, getSkin(), "font-small", new Color(0x5f8f12ff));
		L.setAlignment(Align.top,Align.center);
		Grouplist.addActor(L);
		return L;
	}
	@Override
	public void Open() {
		super.Open();
		Scrll.setScrollY(0);
		Scrll.fling(15, 0, -10);
	}
	@Override
	protected void AddListener() {
		super.AddListener();
		Libgdx.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("https://libgdx.badlogicgames.com");
			}
		});
		Tiled.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("http://www.mapeditor.org");
			}
		});
		Gimp.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("https://www.gimp.org");
			}
		});
		Audacity.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				Gdx.net.openURI("https://audacityteam.org");
			}
		});
	}
	@Override
	public void resize() {
	}
}
