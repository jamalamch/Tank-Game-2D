package crach.stage.game.screen.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import crach.stage.game.Assets;
import crach.stage.game.screen.MenuUI;

public class ModeGame extends Table{
	    private Button btn1vs1,btnmission, btdeathmatch,btnFoot;
	    private Label l1Vs1, lMission, ldeathmatch, lFoot;
	    private MenuUI menu;
       public ModeGame(MenuUI Menu) {
    	   this.setVisible(false);
    	   this.menu = Menu;
    	   btn1vs1 = new Button(Assets.skinStyle, "PVP_BTN");
    	   btnmission = new Button(Assets.skinStyle, "PTP_BTN");
		   btdeathmatch = new Button(Assets.skinStyle, "Deathmatch_BTN");
    	   btnFoot = new Button(Assets.skinStyle, "Football_BTN");
    	   l1Vs1 = new Label(Assets.jsonStringModeGame.getString("1vs1"), Assets.skinStyle, "inSqure");
    	   lMission = new Label(Assets.jsonStringModeGame.getString("mission"), Assets.skinStyle, "inSqure");
		   ldeathmatch = new Label(Assets.jsonStringModeGame.getString("Deathmatch"), Assets.skinStyle, "inSqure");
    	   lFoot = new Label(Assets.jsonStringModeGame.getString("Football"), Assets.skinStyle, "inSqure");
    	   addContent();
		   addListener();
		   
		   btn1vs1.setTouchable(Touchable.disabled);
		   btdeathmatch.setTouchable(Touchable.disabled);
		   
		   btn1vs1.addAction(Actions.alpha(0.2f));
		   btdeathmatch.addAction(Actions.alpha(0.2f));
		   
		   l1Vs1.addAction(Actions.alpha(0.2f));
		   ldeathmatch.addAction(Actions.alpha(0.2f));
	   }
       public void addContent() {
    	   center();
           defaults().pad(5,30,5,30).minSize(130,60);
            row();
            add(btn1vs1).prefSize(160);
            add(btnmission).prefSize(160);
            add(btdeathmatch).prefSize(160);
            add(btnFoot).prefSize(160);
            row();
            add(l1Vs1);
            add(lMission);
            add(ldeathmatch);
            add(lFoot);
            setBackground(Assets.skinStyle.getDrawable("Decor_Part_Side_Table"));
            pack();
       }
       private void addListener() {
		   btn1vs1.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assets.checkOnClick.play();
				   btn1vs1.setChecked(false);
			   }});
		   btnmission.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assets.checkOnClick.play();
				   menu.getSelectStage().Open();
				   btnmission.setChecked(false);
			   }});
		   btdeathmatch.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assets.checkOnClick.play();
				   btdeathmatch.setChecked(false);
			   }});
		   btnFoot.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assets.checkOnClick.play();
				   menu.getSelectMatch().Open();
				   btnFoot.setChecked(false);
			   }});
       }
       public void open(){
           if(!this.isVisible()){
         		this.addAction(Actions.sequence(Actions.delay(1.7f),Actions.moveTo(getX()+getHeight(),getY()),Actions.visible(true),Actions.moveTo(this.getX(), this.getY(),0.35f)));
         	}
       }
       public void close() {
         	if(this.isVisible()) {
        		this.addAction(Actions.sequence(Actions.moveTo(getX()+getWidth(),getY() ,0.5f),Actions.visible(false),Actions.moveTo(this.getX(), this.getY())));
        	}
       }
}
