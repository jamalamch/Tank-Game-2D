package crach.stage.game.windows;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.Tools.ProgressUi;

public class Bonus extends WindowsGames{

	private VerticalGroup Grouplist;
	private ScrollPane Scrll;
	private final Json reader = new Json();
	public Bonus() {
		super(Assest.StringBonus.getString("title"), "default");
        this.getTitleTable().getCell(getTitleLabel()).maxWidth(500).padBottom(30);
		Grouplist = new VerticalGroup();
		addContent();
		AddListener();
	}

	@Override
	public void addContent() {
       for(int i=1;i<13;i++) {
    	   Medal medal =new Medal(i);
    	   Grouplist.addActor(medal);
       }
        Grouplist.pad(10,100,10,100);
        Grouplist.space(80);
		Scrll = new ScrollPane(Grouplist, Assest.Style);
		Scrll.setScrollingDisabled(true, false);
		Scrll.setScrollBarPositions(true,false);
		Scrll.setVariableSizeKnobs(false);
		Scrll.setFadeScrollBars(false);
		add().height(60);
		row();
        add(Scrll).expandX().height(450);
		pack();
		Actor A = Grouplist.getChild(1);
		Scrll.setScrollY(Grouplist.getHeight()-A.getY()-A.getHeight());
		
	}
	@Override
	protected void AddListener() {
		super.AddListener();
	}
	private enum typeMedal {
		Coin,Exper,bombs,Hp,Nive,adsWatchd,enimykill,matchWinne,nPlayMulti,nStar,hoursPlayed
	}

	public class Medal extends Table{
		private MedalData data;
		private ProgressBar BarVal;
		private TextButton BuCuler;
		private int codeMedal;
		public Medal(int codeMedal) {
			super(Assest.Style);
			background("Cloud_01");
			data = reader.readValue("Medal"+codeMedal,MedalData.class,MedalData.class , Assest.ValueBonus);
			this.codeMedal = codeMedal;
			addContent();
		}
		public void addContent() {
		    	   BarVal = new  ProgressUi(data.quantity, Assest.Style, "C_Upgrade_h",true);
		    	   BarVal.setRound(false);
		    	   Image badgeIm = new Image(Assest.Style,data.medalIcon);
		    	   BuCuler = new TextButton(Assest.StringBonus.getString("Culer"), Assest.Style);
		 
		    	   defaults().minSize(50).pad(5);
		    	   row();
		    	   Label infoMission = new Label(Assest.StringBonus.getString(data.mission)+"\n"+data.quantity, getSkin(),"extra" );
		    	   infoMission.setColor(Color.SKY);
		    	   infoMission.setAlignment(Align.center);
		    	   add();
		    	   add(infoMission).minWidth(300).padRight(-50).left().fillY().colspan(2);
		    	   add(""+data.prize,"Ldiamond").right().prefWidth(150);
		    	   row();
		    	   add(badgeIm).prefSize(150).padTop(-100).center();
		    	   add(BarVal).prefSize(300,30).left();
		    	   add(BuCuler).colspan(2).prefSize(150,70).padLeft(90).right();
		    	   row();
		    	   pack();
		    	   
    	   		   BuCuler.addListener(new ClickListener() {
		    		  @Override
		    		public void clicked(InputEvent event, float x, float y) {
		    			  Assest.soundBonus2.play();
		    			  CrachGame.addDiamound(data.prize);
		    			  CrachGame.setBonusColled(codeMedal);
		    			  BuCuler.setTouchable(Touchable.disabled);
		    			  BuCuler.setDisabled(true);
		    		  } 
		    	   });
    	   		   update();
	}
	public void update() {
		BarVal.setValue(getValeur(data.type));
		if(getValeur(data.type) >= data.quantity) {
			if(!CrachGame.isBonusColled(codeMedal)) {
			BuCuler.setTouchable(Touchable.enabled);
			BuCuler.setDisabled(false);
		}else{
				BuCuler.setVisible(false);
			}
	}else{
			BuCuler.setTouchable(Touchable.disabled);
			BuCuler.setDisabled(true);
		}
		}
	}
	public int getValeur(typeMedal type) {
		switch(type) {
		case Coin:
		return CrachGame.getCoin();
		case Exper:
		return CrachGame.getExper();
		case bombs:
		return CrachGame.getBombs();
		case Nive:
		return CrachGame.getNive();
		case adsWatchd:
		return CrachGame.getAdsWatchd();
		case enimykill:
		return CrachGame.getEnimykill();
		case matchWinne:
		return CrachGame.getMatchWinne();
		case nPlayMulti:
		return CrachGame.getnPlayMulti();
		case nStar:
		return CrachGame.getnStar();
		case Hp:
		return CrachGame.getHp();
		case hoursPlayed:
		return (int)CrachGame.getHoursPlayed();
		}
		return 0;
	}
	public static class MedalData {
		public String medalIcon;
		public String mission;
		public int prize;
		
		public typeMedal type;
		public int quantity;
		public MedalData(){		
		}
	}
	@Override
	public void resize() {
		super.resize();
		this.PoseY -= 30;
	}
}
