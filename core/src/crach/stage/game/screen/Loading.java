package crach.stage.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;


public class Loading extends ScreenAdapter{
  	
	private SpriteBatch batch;
	
	private TextureAtlas textureLodingAtlas;
	private TextureRegion textureBackground;
	private TextureRegion textureHeadTitle;
	private TextureRegion textureBottomDecore;
	private TextureRegion textureBar_Loding;
	private TextureRegion textureLoadingKnob;
	private TextureRegion textureLoadingBarBG;
    
    private final float WidthHead = 274* CrachGame.RATA ,HeightHead = 77* CrachGame.RATA;
    private float widthBar = 0 , heightBar =  0;
    private final float WidthBottom= 198* CrachGame.RATA,HeightBottom = 30* CrachGame.RATA;
    private final float WidthKnob =4*CrachGame.RATA,HeightKnob = 14* CrachGame.RATA;
    
    private float xhead, yhead, xbar, ybar, xbottom, ybottom, xknob, yknob;
    private float valeur,valeurMax ;
    
    private float WorldWidth =Gdx.graphics.getWidth(), WorldHeight =Gdx.graphics.getHeight();
	
	private boolean SpritLoding, animation,animationfinir;
	
	public Loading() {
		textureLodingAtlas = new TextureAtlas("Sprite/Loading.atlas");
		batch = new SpriteBatch();
		textureBackground = textureLodingAtlas.findRegion("Background");
		textureHeadTitle = textureLodingAtlas.findRegion("Head_Loding");
		textureBottomDecore = textureLodingAtlas.findRegion("BottomLoding");
		textureBar_Loding = textureLodingAtlas.findRegion("Bar_Loding");
		textureLoadingKnob = textureLodingAtlas.findRegion("Loading_Knob");
		textureLoadingBarBG = textureLodingAtlas.findRegion("Loading_Bar_BG");
	}
	
	@Override
	public void show() {
		
		xbottom = (WorldWidth-WidthBottom)/2;
		ybottom = WorldHeight*CrachGame.RATA;
		xbar = (WorldWidth- widthBar)/2;
		ybar = ybottom + HeightBottom;
		xknob = xbar +25*2*CrachGame.RATA;
		yknob = ybar +4*CrachGame.RATA;
		xhead = (WorldWidth-WidthHead)/2;
		yhead = ybar + heightBar;
        if(!animation) {
        	 ybottom = 50/CrachGame.RATA;
			 ybar = ybottom + HeightBottom;
			 yknob = ybar +6*CrachGame.RATA;
			 widthBar = 1055*CrachGame.RATA;
	         heightBar = 26*CrachGame.RATA;
			 yhead = ybar + heightBar;
			 xbar = (WorldWidth- widthBar)/2;
			 xknob = xbar +25*CrachGame.RATA;
		     valeurMax = widthBar -50*CrachGame.RATA;
		     animationfinir =true;
        }
        
        Assets.load();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(textureBackground, 0, 0, WorldWidth, WorldHeight);
		batch.draw(textureHeadTitle, xhead, yhead, WidthHead, HeightHead);
		batch.draw(textureBar_Loding, xbar, ybar, widthBar, heightBar);
		batch.draw(textureLoadingBarBG, xknob, yknob, valeur, HeightKnob);
		batch.draw(textureLoadingKnob, xknob +valeur-WidthKnob/2, yknob, WidthKnob, HeightKnob);
		batch.draw(textureBottomDecore, xbottom, ybottom, WidthBottom, HeightBottom);
		batch.end();
		if(!SpritLoding)
        if(Assets.manager.isFinished()){
        	SpritLoding = true;
        	Assets.managerSprit();
        	Assets.finirLoding();
        	Assets.loadSound();
			CrachGame.load();
        	CrachGame.gotToMenu();
        }
		if(!animation() ){
			Assets.manager.update();
			valeur = valeurMax* Assets.manager.getProgress();
		}
	}
	public boolean animation(){
		if(!animationfinir) {
			if(ybottom > 50/CrachGame.RATA) {
				ybottom -= 20;
				ybar = ybottom + HeightBottom;
				yknob = ybar +6*CrachGame.RATA;
				yhead = ybar + heightBar;
			}
			else {
				if(widthBar < 1055*CrachGame.RATA)
					widthBar += 44;
				if(heightBar < 26*CrachGame.RATA) {
					heightBar += 0.9f;
					xbar = (WorldWidth- widthBar)/2;
					yhead = ybar + heightBar;
					xknob = xbar +25*2*CrachGame.RATA;
				}
				else {
					 widthBar = 1055*CrachGame.RATA;
			         heightBar = 26*CrachGame.RATA;
					 xbar = (WorldWidth- widthBar)/2;
					 xknob = xbar +25*CrachGame.RATA;
				     valeurMax = widthBar -50*CrachGame.RATA;
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
        textureLodingAtlas.dispose();
	}
}