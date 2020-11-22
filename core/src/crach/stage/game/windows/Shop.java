package crach.stage.game.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.Tools.WatchAds;

public class Shop extends Table{
	private final int prix100C=20,prix1000C=180,prixNoAds=1000,PrixNoAdsAndCoin=1100,prixXp2=100,prixXp3=120,prixXp6=200;
	
	
	private Button parent;
    private Button BXP_Boost,BMore_Diamond,BNo_ADS,BMore_Coin,Coin100,Coin1000,BConvert
                   ,BPaid,BCollect,BXp2,BXp3,BXp6,BXPaid;
    private ButtonGroup<Button>[] group;
    private ImageButton NoAds,NoAdsAndCoin;
    private Table TXP_Boost,TMore_Diamond,TNo_ADS,TMore_Coin;
    private Stack stackUi;
    private WatchAds watchAds;
    
	@SuppressWarnings("unchecked")
	public Shop(Button paButton) {
		super();
		this.parent = paButton;
		this.setVisible(false);
		BXP_Boost = new Button(Assest.Style, "XP_Boost_Icon");
		BMore_Diamond = new Button(Assest.Style, "More_Diamond_Icon");
		BNo_ADS = new Button(Assest.Style, "No_ADS_Icon");
		BMore_Coin = new Button(Assest.Style, "More_Coin_Icon");
		
	    Coin100 = new Button(Assest.Style, "toggle");
	    Coin1000 = new Button(Assest.Style, "toggle");
	    BConvert = new TextButton(Assest.Stringshop.getString("convert"), Assest.Style);
	    BConvert.setDisabled(true);
	    BCollect = new TextButton(Assest.Stringshop.getString("collect"), Assest.Style);
	    BCollect.setDisabled(true);
	    
	    NoAds = new ImageButton(Assest.Style, "NoAds");
	    NoAdsAndCoin = new ImageButton(Assest.Style, "NoAds");
	    BPaid = new TextButton(Assest.Stringshop.getString("paid"), Assest.Style);
	    BPaid.setDisabled(true);
	    BXp2 = new Button(Assest.Style, "toggle");
	    BXp3 = new Button(Assest.Style, "toggle");
	    BXp6 = new Button(Assest.Style, "toggle");
	    BXPaid = new TextButton(Assest.Stringshop.getString("paid"), Assest.Style);
	    BXPaid.setDisabled(true);
		TXP_Boost = new Table(Assest.Style);
		TXP_Boost.background("window");
		TMore_Diamond = new Table(Assest.Style);
		TMore_Diamond.background("window");
		TMore_Coin = new Table(Assest.Style);
		TMore_Coin.background("window");
		
		TNo_ADS = new Table(Assest.Style);
		TNo_ADS.background("window");
		
		watchAds = new WatchAds(BCollect);
		
		stackUi = new Stack();
		
		group = new ButtonGroup[4];
        addContent();
        AddListener();
	}
	public void addContent() {

	    //table More Coin   
		group[0] = new ButtonGroup<Button>();
		group[0].setMinCheckCount(0);
		group[0].add(Coin100,Coin1000);
	    
	    Coin100.row();
	    Coin100.add(new Image(Assest.Style, "Icon/Coin_A")).row();
	    Coin100.add(new Image(Assest.Style, "Icon/100_Coins")).row();
	    Coin100.add(""+prix100C,"Ldiamond").maxHeight(70).expandY().bottom().pad(20,0,-20,0);
	    Coin100.pack();
	    
	    Coin1000.row();
	    Coin1000.add(new Image(Assest.Style, "Icon/Coin_B")).row();
	    Coin1000.add(new Image(Assest.Style, "Icon/1000_Coins")).row();
	    Coin1000.add(""+prix1000C,"Ldiamond").maxHeight(70).expandY().bottom().pad(20,0,-20,0);
	    Coin1000.pack();
	    
	    TMore_Coin.defaults().pad(5).minSize(70);
	    TMore_Coin.add(Coin100).fillY();
	    TMore_Coin.add(Coin1000);
	    TMore_Coin.row();
	    TMore_Coin.add(BConvert).colspan(2).maxHeight(80).expandY().bottom();
	    TMore_Coin.pack();
        //table No add
	    group[1] = new ButtonGroup<Button>();
	    group[1].setMinCheckCount(0);
	    group[1].add(NoAds,NoAdsAndCoin);
	    
        NoAds.row();
        NoAds.add(""+prixNoAds,"Ldiamond").maxHeight(70).expandY().bottom().pad(20,0,-20,0);
	    NoAds.pack();
	    
	    NoAdsAndCoin.row();
	    NoAdsAndCoin.add(new Image(Assest.Style, "Icon/1000_Coins")).row();
	    NoAdsAndCoin.add(""+PrixNoAdsAndCoin,"Ldiamond").maxHeight(70).expandY().bottom().pad(20,0,-20,0);
	    NoAdsAndCoin.pack();
	    
	    TNo_ADS.defaults().pad(5).minSize(70);
	    TNo_ADS.add(NoAds).fillY();
	    TNo_ADS.add(NoAdsAndCoin);
	    TNo_ADS.row();
	    TNo_ADS.add(BPaid).colspan(2).maxHeight(80).expandY().bottom();
	    TNo_ADS.pack();
	    
	    //table add Diamond
	    BCollect.add(new Image(Assest.Style, "Icon/DIamoundS")).pad(-5);
	    BCollect.pack();
	    
	    TMore_Diamond.top().defaults().center();
	    TMore_Diamond.add(watchAds).padLeft(-10).padRight(-10).expandX().fillX().prefSize(700,150).row();
	    TMore_Diamond.add(Assest.Stringshop.getString("watch1"),"font",new Color(0x779dbb)).row();
	    TMore_Diamond.add(Assest.Stringshop.getString("watch2")+135+"£","font-small",new Color(0xababab)).row();
	    TMore_Diamond.add(BCollect).maxHeight(85).expandY().bottom();
	    TMore_Diamond.pack();
	    //table Xper
	    group[2] = new ButtonGroup<Button>();
	    group[2].setMinCheckCount(0);
	    group[2].add(BXp2,BXp3,BXp6);
	    LabelStyle SXp = new LabelStyle(Assest.Style.get("extra", LabelStyle.class));
	    SXp.fontColor = new Color(0x53abe6);
	    LabelStyle SCo = new LabelStyle(Assest.Style.get("extra", LabelStyle.class));
	    SCo.fontColor = new Color(Assest.Style.getColor("CoinColor"));
	    Color Carte = new Color(0.34f,0.714f,0.34f,1);
	    String Fill = Assest.Stringshop.getString("fill");
	    
	    BXp2.row().padBottom(5);
	    BXp2.add(new Label("% X2", SXp));
	    BXp2.add(new Label("$ X2", SCo)).row();
	    BXp2.add("( 2h","font-small",Color.SKY).colspan(2).row();
	    BXp2.add(new Image(Assest.Style, "Icon/Carte_Icon"));
	    BXp2.add(Fill,"font",Carte).row();
	    BXp2.add(""+prixXp2,"Ldiamond").colspan(2).maxHeight(70).expandY().bottom().pad(0,0,-20,0);
	    BXp2.pack();
	    
	    BXp3.row().padBottom(5);
	    BXp3.add(new Label("% X3", SXp));
	    BXp3.add(new Label("$ X3", SCo)).row();
	    BXp3.add("( 3h","font-small",Color.SKY).colspan(2).row();
	    BXp3.add(new Image(Assest.Style, "Icon/Carte_Icon"));
	    BXp3.add(Fill,"font",Carte).row();
	    BXp3.add(""+prixXp3,"Ldiamond").colspan(2).maxHeight(70).expandY().bottom().pad(0,0,-20,0);
	    BXp3.pack();
	    
	    BXp6.row().padBottom(5);
	    BXp6.add(new Label("% X6", SXp));
	    BXp6.add(new Label("$ X8", SCo)).row();
	    BXp6.add("( 4h","font-small",Color.SKY).colspan(2).row();
	    BXp6.add(new Image(Assest.Style, "Icon/Carte_Icon"));
	    BXp6.add(Fill,"font",Carte).row();
	    BXp6.add(""+prixXp6,"Ldiamond").colspan(2).maxHeight(70).expandY().bottom().pad(0,0,-20,0);
	    BXp6.pack();
	    
	    TXP_Boost.defaults().pad(5).minSize(70);
	    TXP_Boost.add(BXp2);
	    TXP_Boost.add(BXp3);
	    TXP_Boost.add(BXp6).row();
	    TXP_Boost.add(BXPaid).colspan(3).maxHeight(80).expandY().bottom();
	    TXP_Boost.pack();
	    //table Ui
	    group[3] = new ButtonGroup<Button>();
	    group[3].add(BXP_Boost,BMore_Diamond,BMore_Coin,BNo_ADS);
	    
	    BMore_Coin.setChecked(true);

	    defaults().pad(0).center().minSize(70).fillX().expandX().row();
	    add(BXP_Boost).prefSize(147,126);
	    add(BMore_Diamond).prefSize(147,126);
	    add(BMore_Coin).prefSize(147,126);
	    add(BNo_ADS).prefSize(147,126);
	    add().expand().row();
	    stackUi.add(TXP_Boost);
	    stackUi.add(TMore_Diamond);
	    stackUi.add(TMore_Coin);
	    stackUi.add(TNo_ADS);
	    add(stackUi).colspan(5);
		pack();		 
	}
	private void AddListener() {
		VisibleTable();
  		ClickListener C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  				Assest.checkOnClick.play();
  				VisibleTable();
  			}
  		};
  		BXP_Boost.addListener(C);
  		BMore_Diamond.addListener(C);
  		BNo_ADS.addListener(C);
  		BMore_Coin.addListener(C);
  		
  		C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  						if(group[0].getCheckedIndex() == -1) Assest.checkOfClick.play();
  							else Assest.checkOnClick.play();
                		BConvert.setDisabled(group[0].getCheckedIndex() == -1);
  			}
  		};
  		Coin100.addListener(C);
  		Coin1000.addListener(C);
  		C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
					if(group[1].getCheckedIndex() == -1) Assest.checkOfClick.play();
						else Assest.checkOnClick.play();
                	BPaid.setDisabled(group[1].getCheckedIndex() == -1);
  			}
  		};
  		NoAds.addListener(C);
  		NoAdsAndCoin.addListener(C);
  		C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  					if(group[2].getCheckedIndex() == -1) Assest.checkOfClick.play();
  						else Assest.checkOnClick.play();
                	BXPaid.setDisabled(group[2].getCheckedIndex() == -1);
  			}
  		};
        BXp2.addListener(C);
        BXp3.addListener(C);
        BXp6.addListener(C);
        

		BConvert.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				if(Coin100.isChecked() && CrachGame.getDiamound()>= prix100C) {
					Assest.soundBonus2.play();
					CrachGame.addCoin(100);
					CrachGame.addDiamound(-prix100C);
				}
				else if(Coin1000.isChecked() && CrachGame.getDiamound()>= prix1000C) {
					Assest.soundBonus3.play();

					CrachGame.addCoin(1000);
					CrachGame.addDiamound(-prix1000C);
				}else
					Assest.soundfialed.play();
			}
		});
		BPaid.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				if(NoAds.isChecked() && CrachGame.getDiamound()>= prixNoAds) {
					Assest.soundBonus2.play();
					CrachGame.addDiamound(-prixNoAds);
					removeAds();
				}
				else if(NoAdsAndCoin.isChecked() && CrachGame.getDiamound()>= PrixNoAdsAndCoin) {
					Assest.soundBonus3.play();
					CrachGame.addCoin(1000);
					CrachGame.addDiamound(-PrixNoAdsAndCoin);
					removeAds();
				}
				else
					Assest.soundfialed.play();
			}
		});
		BCollect.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				watchAds.CollectDiamond();
			}
		});
        BXPaid.addListener(new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				Assest.buttonClick1.play();
				if(BXp2.isChecked() && CrachGame.getDiamound()>= prixXp2 && CrachGame.getXP_Boost(0)+CrachGame.getXP_Boost(1)+CrachGame.getXP_Boost(2) == 0) {
					Assest.soundBonus1.play();
					CrachGame.setXP_Boost(0, 7200);
					CrachGame.addLife(10);
					CrachGame.addDiamound(-prixXp2);
					}
				else if(BXp3.isChecked() && CrachGame.getDiamound()>= prixXp3 && CrachGame.getXP_Boost(1)+CrachGame.getXP_Boost(2) == 0) {
					Assest.soundBonus1.play();
					CrachGame.setXP_Boost(0, 0);
					CrachGame.setXP_Boost(1, 10800);
					CrachGame.addLife(10);
					CrachGame.addDiamound(-prixXp3);
				}
				else if(BXp6.isChecked() && CrachGame.getDiamound()>= prixXp6 && CrachGame.getXP_Boost(2) == 0) {
					Assest.soundBonus1.play();
					CrachGame.setXP_Boost(0, 0);
					CrachGame.setXP_Boost(1, 0);
					CrachGame.setXP_Boost(2, 14400);
					CrachGame.addLife(10);
					CrachGame.addDiamound(-prixXp6);
				}
				else
					Assest.soundfialed.play();
			}
		});		
    }
	public void adshasWatched() {
	}
	private  void removeAds() {
		CrachGame.setNoAds(true);
		NoAds.setTouchable(Touchable.disabled);
		NoAdsAndCoin.setTouchable(Touchable.disabled);
	}
	public void VisibleTable() {
		TMore_Diamond.setVisible(false);	
		TXP_Boost.setVisible(false);
        TMore_Coin.setVisible(false);
        TNo_ADS.setVisible(false);
        
  		if(BXP_Boost.isChecked())
  			TXP_Boost.setVisible(true);
  		else if(BNo_ADS.isChecked())
  			TNo_ADS.setVisible(true);
  		else if(BMore_Coin.isChecked())
  			TMore_Coin.setVisible(true);
  		else if(BMore_Diamond.isChecked())
  			TMore_Diamond.setVisible(true);
	}
	public void Open_TMore_Coin(){
		BMore_Coin.setChecked(true);
		VisibleTable();
		Open();
	}
	public void Open_BXP_Boost(){
		BXP_Boost.setChecked(true);
		VisibleTable();
		Open();
	}
	public void Open_TMore_Diamond(){
		BMore_Diamond.setChecked(true);
		VisibleTable();
		Open();
	}
    public void Open(){
        if(!this.isVisible()){
      		this.addAction(Actions.sequence(Actions.moveTo(getX()+100,getY()),Actions.visible(true),Actions.parallel(Actions.alpha(1, 0.2f),Actions.moveTo(this.getX(), this.getY(),0.2f))));
      	}
	}
    public void Close() {
      	if(this.isVisible()) {
     		this.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0, 0.2f),Actions.moveTo(getX()+100,getY() ,0.25f)),Actions.visible(false),Actions.moveTo(this.getX(), this.getY())));
     	}
	}
    @Override
    public void setVisible(boolean visible) {
        this.parent.setChecked(visible);
    	super.setVisible(visible);
    }
}
