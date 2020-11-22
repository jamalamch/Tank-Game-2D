package crach.stage.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import crach.stage.game.screen.Tools.ViewBanerAds;
import crach.stage.game.utils.CameraHandler;
import crach.stage.game.utils.WorldContactListener;
import crach.stage.game.creator.B2WorldCreator;
import crach.stage.game.creator.B2WorldCreatorOfline;
import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.Tools.WordMapRender;

import crach.stage.game.entity.Entity;
import crach.stage.game.windows.dialog.DialogQuestion;

public class PlayScreen extends ScreenGame{
	
	public enum ModeGame{
		simple,football,deathmatch,P2P
	}
	public ModeGame modeGame;
	public TiledMap tileMap;
	private int nStage;

	private boolean Pause,Zoom;
	
    private SpriteBatch batch;
    private World box2dWorld;
	private Box2DDebugRenderer box2DDebugRenderer;
    private WordMapRender mapRender;
    private WorldContactListener worldContactListener;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
	private OrthographicCamera box2DCamera;
    private CameraHandler cameraHandler;
    private Viewport gamePort;
    private B2WorldCreator creator;
	private DragListener dragListener;
	private final float TIME_STEP = 1/60f;
	private float accumulator = TIME_STEP;
	private ViewBanerAds banerAds;
    public PlayScreen(int nstage,ModeGame modegame) {
		this(Assest.loadMaps(nstage,modegame),nstage,modegame);
    }
	public PlayScreen(TiledMap TiledMap ,ModeGame modegame) {
    	this(TiledMap,TiledMap.getProperties().get("code",99,Integer.class),modegame);
	}
	public PlayScreen(TiledMap TiledMap,int nStage ,ModeGame modegame) {
		super();
		this.nStage = nStage;
		this.modeGame = modegame;
		box2DCamera = new OrthographicCamera();

		batch = new SpriteBatch();

		gamePort = new ExtendViewport(CrachGame.Width/CrachGame.PPM,CrachGame.Height/CrachGame.PPM,box2DCamera);
		cameraHandler = new CameraHandler(box2DCamera);

		tileMap = TiledMap;
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tileMap,1/CrachGame.PPM,batch) ;

		cameraHandler.setBounds(tileMap);

		box2dWorld = new World(new Vector2(0, 0), true);

		mapRender = new WordMapRender(cameraHandler.getxBound(),cameraHandler.getyBound());
		box2DDebugRenderer = new Box2DDebugRenderer();
		addActor(mapRender);
		worldContactListener = new WorldContactListener();
		box2dWorld.setContactListener(worldContactListener);
		creator = new B2WorldCreatorOfline(this);
		cameraHandler.setTarget(creator.getPlayer(), true);

		dragListener = new DragListener() {
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				updatecamera(-getDeltaX (), -getDeltaY());
			}
		};
		banerAds = new ViewBanerAds();
		addActor(banerAds);
	}
		@Override
	public void show() {
		super.show();
		if(!CrachGame.addLife(-1)){
			Pause = true;
			getStage().addActor(new DialogQuestion(Assest.StringDialog.getString("noLife")
							, Assest.StringDialog.getString("msgNoLife")) {
				@Override
				public void Accepte() {
					CrachGame.getiActivityRequestHandler().showVideosAds(new Runnable() {
						@Override
						public void run() {
							CrachGame.addLife(10);
							Pause = false;
						}
					}, new Runnable() {
						@Override
						public void run() {
							if(CrachGame.isVibr()) Gdx.input.vibrate(60);
							CrachGame.GotToMenu();
						}
					});
				}
				@Override
				public void Refuse() {
					CrachGame.GotToMenu();
				}
			});
		}
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.19f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!Pause) {
			accumulator += delta;
			while (accumulator >= TIME_STEP) {
				box2dWorld.step(TIME_STEP, 6, 2);
				accumulator -= TIME_STEP;
			}
        creator.update(delta);
		cameraHandler.update();
        }
    	creator.TWEEN_MANAGER.update(delta);
        tiledMapRenderer.setView(box2DCamera);	
        batch.setColor(Color.WHITE);
        tiledMapRenderer.render(new int[] {0});
        creator.drawEntity(batch,0,Entity.Zindex.ZindexDoor-1);
        tiledMapRenderer.render(new int[] {1,2});
        creator.drawEntity(batch,Entity.Zindex.ZindexDoor,20);
        creator.updateAndRenderLight(box2DCamera);
        drawStage(delta);
        mapRender.render(delta,box2DCamera.position.x,box2DCamera.position.y,stage.getBatch(), box2dWorld);

		box2DDebugRenderer.render(box2dWorld,box2DCamera.combined);
	}
	public void resize() {
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	public void resize(float zoom){
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), zoom);
	}
	@Override
	public void resize(int width, int height) {
		final float zoom = 1.2f;
		resize(width, height, zoom);
	}
	public void resize(int width, int height,float zoom) {
		box2DCamera.zoom=zoom;
		gamePort.update(width, height);
		cameraHandler.setView(gamePort.getWorldWidth()*zoom, gamePort.getWorldHeight()*zoom);
		ViewGamePort.update(width, height,true);
		banerAds.resize(ViewGamePort,width, height);
		mapRender.translate(ViewGamePort.getCamera().combined, ViewGamePort.getWorldWidth(), ViewGamePort.getWorldHeight());
		mapRender.setMapVeiw(cameraHandler.getWidthView(), cameraHandler.getHeightView());
	}
	public void updatecamera(float dragX,float dragY) {
			float loopX = box2DCamera.position.x + (dragX/CrachGame.PPM)*box2DCamera.zoom;
			float loopY = box2DCamera.position.y + (dragY/CrachGame.PPM)*box2DCamera.zoom;
			if(loopX < cameraHandler.getxBound() && loopX > 0) 
		                    box2DCamera.position.x=loopX;
			if(loopY < cameraHandler.getyBound() && loopY > 0) 
		                   box2DCamera.position.y=loopY;			
		box2DCamera.update();
        tiledMapRenderer.setView(box2DCamera);			
	}
	public void upToModeZoom() {
		Zoom=Pause=true;
		resize(2);
		stage.addListener(dragListener);
	}
	public void quitModeZoom() {
		Zoom=Pause=false;
		resize();
		stage.removeListener(dragListener);
	}
    public boolean isPause() {
		return Pause;
	}
    public boolean isZoom() {
		return Zoom;
	}
	@Override
	public void pause() {
		if(!Pause || Zoom)
			creator.pauser();
	}
	public void setPauser(boolean pause) {
		if(pause && Zoom)
			quitModeZoom();
		this.Pause = pause; 
		if(pause)
			Assest.pauseAllSound();
		else{
			Assest.resumeAllSound();
			banerAds.close();
		}
	}
	@Override
	public void hide() {
	}
	@Override
	public void dispose() {
		 if(stage != null) stage.dispose();
		 if(creator != null) creator.dispose();
		 if(box2dWorld != null) box2dWorld.dispose();
		 if(mapRender != null) mapRender.dispose();    
		 if(tiledMapRenderer != null) tiledMapRenderer.dispose();
		 if(batch != null) batch.dispose();
		 Assest.stopAllSound();
		banerAds.close();
	}
	public World getBox2dWorld() {
			return box2dWorld;
	}
	public TiledMap getTileMap() {
			return tileMap;
	}
	public int getnStage() {
		return nStage;
	}
	public void showAds() {
    	banerAds.open();
	}
}
