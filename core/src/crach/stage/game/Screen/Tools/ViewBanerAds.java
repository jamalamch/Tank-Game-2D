package crach.stage.game.Screen.Tools;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;

public class ViewBanerAds extends Image {
	private static boolean isAdsLoad = true;
	private Timer.Task taskVisible;

    public ViewBanerAds(){
    	super(Assest.Style, "Table04");
    	setVisible(false);
    	setOrigin(Align.bottom);
        taskVisible = new Timer.Task() {
            @Override
            public void run() {
                CrachGame.getiActivityRequestHandler().showAds(false);
                setVisible(false);
            }
        };
    }
    @Override
    protected void setStage(Stage stage) {
    	super.setStage(stage);
    }
	public void open(){
        if(taskVisible.isScheduled())
            taskVisible.cancel();
        if(isAdsLoad && !isVisible()) {
            CrachGame.getiActivityRequestHandler().showAds(true);
            setVisible(true);
        }
    }
	public void close(){
        if(taskVisible.isScheduled())
            taskVisible.cancel();
        if(isAdsLoad && isVisible()) {
            Timer.schedule(taskVisible,4);
        }
    }
    public void resize(Viewport viewport,int width,int height){
        setSize(306,56);
        setPosition(viewport.getWorldWidth()/2,0,Align.bottom);
    }

    @Override
    public void setSize(float width, float height) {
        if(getStage() == null ) return;
        Viewport viewport = getStage().getViewport();
        float ratax = viewport.getWorldWidth()/viewport.getScreenWidth();
        float ratay = viewport.getWorldHeight()/viewport.getScreenHeight();
        super.setSize(width*ratax, height*ratay);
    }

    public boolean isload() {
    	return isAdsLoad;
    }
    public static void setIsAdsLoad(boolean isload){
        isAdsLoad = isload;
    }
}
