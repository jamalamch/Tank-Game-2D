package crach.stage.game.Screen.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.Screen.MenuUI;
import crach.stage.game.Screen.Tools.ProgressUi.ProgressText;
import crach.stage.game.windows.dialog.BonusCuler;
import crach.stage.game.windows.dialog.DialogQuestion;

public class HubUi extends Table{
	private Label LCoin,LDiamound;
	private ProgressText LifeGame;
	private ProgressText PExper;
	private Image ICoin,IDiamound,ILife,Inive;
	private MenuUI menuUI;
      public HubUi(MenuUI menuUI) {
    	  super();
    	  this.menuUI = menuUI;
          this.setVisible(false);
    	  LCoin = new Label("", Assest.Style,"inSqure");
    	  LCoin.setColor(Assest.Style.getColor("CoinColor"));
    	  LDiamound = new Label("", Assest.Style,"inSqure");
    	  LDiamound.setColor(Assest.Style.getColor("diamondColor"));
    	  LCoin.setAlignment(Align.center);
    	  LDiamound.setAlignment(Align.center);
    	  LifeGame = new ProgressText(10, Assest.Style, "ProgresUI",new Color(0xA07676FF),false);
          LifeGame2 = new ProgressUi(10, Assest.Style, "ProgresUI",false);
    	  PExper = new ProgressText(Assest.ValueNive.get(CrachGame.getNive()-1).getInt("max"), Assest.Style, "B-horizontal", new Color(0xfd9d9d9));
    	  ICoin = new Image(Assest.Style, "Icon/Coin_A");
    	  IDiamound =new  Image(Assest.Style, "Icon/DIamond");
    	  ILife =new Image(Assest.Style, "Icon/Carte_Icon");
    	  Inive = new Image(Assest.Style, Assest.ValueNive.get(CrachGame.getNive()-1).getString("drawable"));
          addContent();
          updatePExper(CrachGame.getExper());
          updateLCoin(CrachGame.getCoin());
          updateLDiamound(CrachGame.getDiamound());
          updateLife(CrachGame.getLife());
          AddListener();
	  }
      public void addContent() {
          top();
          defaults().pad(5,10,5,10).fillX().minSize(30);
          add(LCoin).prefSize(250, 60);
          add(ICoin).prefSize(70).pad(-5);
          add(LifeGame).prefSize(160, 60);
          add(ILife).prefSize(70).pad(-5);
          add(LDiamound).prefSize(140, 60);
          add(IDiamound).prefSize(60);
          add().expandX();
          add(PExper).prefSize(350, 60);
          add(Inive).prefSize(90).pad(-10);
          setBackground(Assest.Style.getDrawable("Hub"));
          
      }
     public void updateLCoin(int  coin) {
    	 LCoin.getText().clear();
    	 LCoin.getText().append(coin, 8);
    	 LCoin.invalidate();
     }
     public void updateLDiamound(int diamound) {
    	 LDiamound.getText().clear();
    	 LDiamound.getText().append(diamound,4);
    	 LDiamound.invalidate();
     }
     public void updateLife(int life) {
   	  	LifeGame.setValue(life);
   	  	LifeGame2.setValue(life);
   	  	if(life >= 10) {
   	  	    LifeGame.getText().clear();
            LifeGame.getText().append("");
            LifeGame.invalidate();
        }
     }
     public void timerLife(int TimerS){
         LifeGame.getText().clear();
         LifeGame.getText().append(TimerS/60,2).append(':').append(TimerS%60,2).append('(');
         LifeGame.invalidate();
     }
     public void updatePExper(int exper){
    	 PExper.setValue(exper);
     }
     public void updateNive(int nive, int exper) {
    	 Inive.setDrawable(Assest.Style, Assest.ValueNive.get(nive-1).getString("drawable"));
         ProgressText pExper = new ProgressText(Assest.ValueNive.get(nive-1).getInt("max"), Assest.Style, "B-horizontal", new Color(0xfd9d9d9));
         this.getCell(this.PExper).setActor(pExper);
         this.PExper = pExper;
         updatePExper(exper);
         getStage().addActor(new BonusCuler(Assest.StringDialog.getString("finirStage"),
                 Assest.ValueNive.get(nive-2).getInt("prize"),
                 Assest.ValueNive.get(nive-2).getString("drawable")));
     }
     private void AddListener() {
         ClickListener  C = new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 Assest.buttonClick1.play();
                 menuUI.getShope().Open_TMore_Coin();
             }
         };
         LCoin.addListener(C);
         ICoin.addListener(C);
         C = new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 Assest.buttonClick1.play();
                 menuUI.getShope().Open_TMore_Diamond();
             }
         };
         LDiamound.addListener(C);
         IDiamound.addListener(C);

         C = new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 Assest.buttonClick1.play();
                 menuUI.getShope().Open_BXP_Boost();
             }
         };
         Inive.addListener(C);
         PExper.addListener(C);
         C = new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 Assest.buttonClick1.play();
                 if(CrachGame.getLife() > 3)
                     return;
                 getStage().addActor(new DialogQuestion(Assest.StringDialog.getString("noLife")
                         , Assest.StringDialog.getString("msgNoLife") ){
                     @Override
                     public void Accepte() {
                         CrachGame.getiActivityRequestHandler().showVideosAds(new Runnable() {
                             @Override
                             public void run() {
                                 CrachGame.addLife(10);
                             }
                         }, new Runnable() {
                             @Override
                             public void run() {
                            	 if(CrachGame.isVibr()) Gdx.input.vibrate(60);
                             }
                         });
                     }
                     @Override
                     public void Refuse() {}
                 });
             }
         };
         LifeGame.addListener(C);
         ILife.addListener(C);


      		
     }
      public void Open() {
          if(!this.isVisible()){
      		this.addAction(Actions.sequence(Actions.delay(1.5f),Actions.moveTo(0,getY()+getHeight()),Actions.visible(true),Actions.moveTo(this.getX(), this.getY(),0.3f)));
      	}
      }
      public void Close() {
      	if(this.isVisible()) {
    		this.addAction(Actions.sequence(Actions.moveTo(0,getY()+getHeight() ,0.4f),Actions.visible(false),Actions.moveTo(this.getX(), this.getY())));
    	}
      }
    private  ProgressUi LifeGame2;
    public ProgressUi getLifeGame() {
        return LifeGame2;
    }
}
