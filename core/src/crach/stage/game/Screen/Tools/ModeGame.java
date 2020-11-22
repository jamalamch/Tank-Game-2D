package crach.stage.game.Screen.Tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import crach.stage.game.Assest;
import crach.stage.game.Screen.MenuUI;

public class ModeGame extends Table{
	    private Button btn1vs1,btnmission, btdeathmatch,btnFoot;
	    private Label L1vs1,LMission,Ldeathmatch,LFoot;
	    private MenuUI Menu;
       public ModeGame(MenuUI Menu) {
    	   this.setVisible(false);
    	   this.Menu = Menu;
    	   btn1vs1 = new Button(Assest.Style, "PVP_BTN");
    	   btnmission = new Button(Assest.Style, "PTP_BTN");
		   btdeathmatch = new Button(Assest.Style, "Deathmatch_BTN");
    	   btnFoot = new Button(Assest.Style, "Football_BTN");
    	   L1vs1 = new Label(Assest.StringModeGame.getString("1vs1"), Assest.Style, "inSqure");
    	   LMission = new Label(Assest.StringModeGame.getString("mission"), Assest.Style, "inSqure");
		   Ldeathmatch = new Label(Assest.StringModeGame.getString("Deathmatch"), Assest.Style, "inSqure");
    	   LFoot = new Label(Assest.StringModeGame.getString("Football"), Assest.Style, "inSqure");
    	   addContent();
		   AddListener();
		   
		   btn1vs1.setTouchable(Touchable.disabled);
		   btdeathmatch.setTouchable(Touchable.disabled);
		   
		   btn1vs1.addAction(Actions.alpha(0.2f));
		   btdeathmatch.addAction(Actions.alpha(0.2f));
		   
		   L1vs1.addAction(Actions.alpha(0.2f));
		   Ldeathmatch.addAction(Actions.alpha(0.2f));
	   }
       public void addContent() {
//    	   ButtonGroup<Button> Group1 = new ButtonGroup<Button>();
//    	   Group1.setMinCheckCount(0);
//    	   Group1.add(btn1vs1);
//    	   Group1.add(btnmission);
//    	   Group1.add(btdeathmatch);
//    	   Group1.add(btnFoot);

    	   center();
           defaults().pad(5,30,5,30).minSize(130,60);
            row();
            add(btn1vs1).prefSize(160);
            add(btnmission).prefSize(160);
            add(btdeathmatch).prefSize(160);
            add(btnFoot).prefSize(160);
            row();
            add(L1vs1);
            add(LMission);
            add(Ldeathmatch);
            add(LFoot);
            setBackground(Assest.Style.getDrawable("Decor_Part_Side_Table"));
            pack();
       }
       private void AddListener() {
		   btn1vs1.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assest.checkOnClick.play();
				   btn1vs1.setChecked(false);
			   }});
		   btnmission.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assest.checkOnClick.play();
				   Menu.getSelectStage().Open();
				   btnmission.setChecked(false);
			   }});
		   btdeathmatch.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assest.checkOnClick.play();
				   btdeathmatch.setChecked(false);
			   }});
		   btnFoot.addListener(new ClickListener() {
			   @Override
			   public void clicked(InputEvent event, float x, float y) {
				   Assest.checkOnClick.play();
				   Menu.getSelectMatch().Open();
				   btnFoot.setChecked(false);
			   }});
       }
       public void Open(){
           if(!this.isVisible()){
         		this.addAction(Actions.sequence(Actions.delay(1.7f),Actions.moveTo(getX()+getHeight(),getY()),Actions.visible(true),Actions.moveTo(this.getX(), this.getY(),0.35f)));
         	}
       }
       public void Close() {
         	if(this.isVisible()) {
        		this.addAction(Actions.sequence(Actions.moveTo(getX()+getWidth(),getY() ,0.5f),Actions.visible(false),Actions.moveTo(this.getX(), this.getY())));
        	}
       }
}
