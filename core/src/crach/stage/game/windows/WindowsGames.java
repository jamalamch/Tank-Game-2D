package crach.stage.game.windows;


import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;


public abstract class WindowsGames extends Window{
 
	protected float PoseX,PoseY;
	private static Random R = new Random();
	private Button Close;
	public WindowsGames(String title) {
		this(title, "default", "Table-special01");  
	} 
	public WindowsGames(String title,String Style) {
		this(title, Style, "head");  
	}   
	public WindowsGames(String title,String Style,String DrawTitle) {
        super(title, Assets.skinStyle, Style);
        super.getTitleLabel().setAlignment(Align.center);
        super.getTitleLabel().getStyle().background =  Assets.skinStyle.getDrawable(DrawTitle);
		Close = new Button(Assets.skinStyle, "Close_BTN");
		this.getTitleTable().add(Close).right().pad(5,-40,-10,-30).maxSize(70);
        this.setVisible(false);
		this.setOrigin(Align.center);
        this.setSkin(Assets.skinStyle);
        setKeepWithinStage(false);
	}
	public WindowsGames() {
		super("", Assets.skinStyle,"default");
		Close = new Button(Assets.skinStyle, "Close_BTN");
		this.getTitleTable().add(Close).right().bottom().pad(5,-40,0,-10).maxSize(70);
        this.setVisible(false);
		this.setOrigin(Align.center);
        this.setSkin(Assets.skinStyle);
        setKeepWithinStage(false); 
	}
	
	public abstract void addContent() ;
	
    public void Open(){
    	toFront();
        int i;
        i = R.nextInt((int)CrachGame.Width);
        if(!this.isVisible()){
    		this.addAction(Actions.sequence(Actions.moveTo(getStage().getWidth(),i),Actions.visible(true),Actions.parallel(Actions.alpha(1, 0.3f),Actions.moveTo(PoseX, PoseY,0.3f))));
    	}
    }
    public void Close() {
        int i;
        i = R.nextInt((int)CrachGame.Width);
    	if(this.isVisible()) {
    		this.addAction(Actions.sequence(Actions.parallel(Actions.alpha(0, 1),Actions.moveTo(i,getStage().getHeight() ,0.9f)),Actions.visible(false)));
    	}
	}
	protected void AddListener() {
		Close.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.buttonClick2.play();
				Close();
			}
		});
	}
	public void resize() {
		if(getStage() != null) {
			setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center);
			PoseX = getX();
			PoseY = getY();
		}
	}

}
