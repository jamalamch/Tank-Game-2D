package crach.stage.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import crach.stage.game.Assest;
import crach.stage.game.CrachGame;


public class Loading extends ScreenAdapter{
  	
	private SpriteBatch batch;
	
	private TextureAtlas lodingAtlas ;
	private TextureRegion background;
	private TextureRegion HeadTitle;
	private TextureRegion BottomDecore;
	private TextureRegion Bar_Loding;
	private TextureRegion Loading_Knob;
	private TextureRegion Loading_Bar_BG;
    
    private final float WidthHead = 274* CrachGame.RATA ,HeightHead = 77* CrachGame.RATA;
    private float WidthBar= 0 ,HeightBar =  0; 
    private final float WidthBottom= 198* CrachGame.RATA,HeightBottom = 30* CrachGame.RATA;
    private final float WidthKnob =4*CrachGame.RATA,HeightKnob = 14* CrachGame.RATA;
    
    private float Xhead,Yhead,Xbar,Ybar,Xbottom,Ybottom,Xknob,Yknob;  
    private float valeur,valeurMax ;
    
    private float WorldWidth =Gdx.graphics.getWidth(), WorldHeight =Gdx.graphics.getHeight();
	
	private boolean SpritLoding, animation,animationfinir;
	
	public Loading() {
		lodingAtlas = new TextureAtlas("Sprite/Loading.atlas");
		batch = new SpriteBatch();
		background = lodingAtlas.findRegion("Background");
		HeadTitle = lodingAtlas.findRegion("Head_Loding");
		BottomDecore = lodingAtlas.findRegion("BottomLoding");
		Bar_Loding = lodingAtlas.findRegion("Bar_Loding");
		Loading_Knob = lodingAtlas.findRegion("Loading_Knob");
		Loading_Bar_BG = lodingAtlas.findRegion("Loading_Bar_BG");	
	}
	
	@Override
	public void show() {
		
		Xbottom = (WorldWidth-WidthBottom)/2;
		Ybottom = WorldHeight*CrachGame.RATA;
		Xbar = (WorldWidth-WidthBar)/2;
		Ybar = Ybottom  + HeightBottom;
		Xknob = Xbar+25*2*CrachGame.RATA;
		Yknob = Ybar+4*CrachGame.RATA;
		Xhead = (WorldWidth-WidthHead)/2;
		Yhead = Ybar + HeightBar;
        if(!animation) {
        	 Ybottom = 50/CrachGame.RATA;
			 Ybar = Ybottom  + HeightBottom;
			 Yknob = Ybar+6*CrachGame.RATA;
			 WidthBar = 1055*CrachGame.RATA;
	         HeightBar = 26*CrachGame.RATA;
			 Yhead = Ybar + HeightBar;
			 Xbar = (WorldWidth-WidthBar)/2;
			 Xknob = Xbar+25*CrachGame.RATA;
		     valeurMax = WidthBar-50*CrachGame.RATA;
		     animationfinir =true;
        }
        
        Assest.load();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, WorldWidth, WorldHeight);
		batch.draw(HeadTitle , Xhead, Yhead, WidthHead, HeightHead);
		batch.draw(Bar_Loding, Xbar, Ybar, WidthBar, HeightBar);
		batch.draw(Loading_Bar_BG, Xknob , Yknob, valeur, HeightKnob);
		batch.draw(Loading_Knob, Xknob+valeur-WidthKnob/2, Yknob, WidthKnob, HeightKnob);
		batch.draw(BottomDecore, Xbottom, Ybottom, WidthBottom, HeightBottom);
		batch.end();
		if(!SpritLoding)
        if(Assest.manager.isFinished()){
        	SpritLoding = true;
        	Assest.managerSprit();
        	Assest.finirLoding();
        	Assest.loadSound();
			CrachGame.load();
        	CrachGame.GotToMenu();
        }
		if(!Animation() ){
			Assest.manager.update();
			valeur = valeurMax*Assest.manager.getProgress();
		}
	}
	public boolean Animation(){
		if(!animationfinir) {
			if(Ybottom > 50/CrachGame.RATA) {
				Ybottom -= 20;
				Ybar = Ybottom  + HeightBottom;
				Yknob = Ybar+6*CrachGame.RATA;
				Yhead = Ybar + HeightBar;
			}
			else {
				if(WidthBar < 1055*CrachGame.RATA)
					WidthBar += 44;
				if(HeightBar < 26*CrachGame.RATA) {
					HeightBar += 0.9f;
					Xbar = (WorldWidth-WidthBar)/2;
					Yhead = Ybar + HeightBar;
					Xknob = Xbar+25*2*CrachGame.RATA;
				}
				else {
					 WidthBar = 1055*CrachGame.RATA;
			         HeightBar = 26*CrachGame.RATA;
					 Xbar = (WorldWidth-WidthBar)/2;
					 Xknob = Xbar+25*CrachGame.RATA;
				     valeurMax = WidthBar-50*CrachGame.RATA;
				     animationfinir =true;
				}
			}
		return true;
		}
		return false;
	}
	@Override
	public void dispose() {
        batch.dispose();
        lodingAtlas.dispose();
	}
}