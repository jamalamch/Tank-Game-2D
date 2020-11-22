package crach.stage.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import crach.stage.game.CrachGame;
import crach.stage.game.entites.Crach.CrachPlayer;
public class CameraHandler 
{
	private OrthographicCamera camera;
	private CrachPlayer target;
	private float lerp = 0.1f;
	private boolean followTarget = false;
	private boolean followX = true;
	private boolean followY = true;
	private boolean useBounds = false;
	private float x ;
	private float y ;
	private float xBound;
	private float yBound;
	private float WidthView,HeightView;
	
	public CameraHandler(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public void setTarget(CrachPlayer target, boolean followTarget) {
		this.target = target;
		this.followTarget = followTarget;
	}
	
	public void update() {
		if(followTarget) {
			
			x = target.getBody().getPosition().x;
			y = target.getBody().getPosition().y;
		}else return;
		if(followX) {
			camera.position.x = camera.position.x + (x - camera.position.x) * lerp;
			if(useBounds) 
				camera.position.x = MathUtils.clamp(camera.position.x, WidthView/2, xBound-(WidthView/2));		
		}
		if(followY) { 
			camera.position.y = camera.position.y + (y - camera.position.y) * lerp;
			if(useBounds) {
			camera.position.y = MathUtils.clamp(camera.position.y, HeightView/2, yBound-(HeightView/2));
			}
		}
		camera.update();
		}
	 
	public void setLerp(float lerp) {
		this.lerp = lerp;
	}
	
	public void setFollowX(boolean follow) {
		this.followX = follow;
	}

	public void setFollowY(boolean follow) {
		this.followY = follow;
	}

	public void setBounds(TiledMap map) {
		MapProperties prop = map.getProperties();
		int width = prop.get("width", Integer.class);
		int height = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);
		setBoundX((width * tilePixelWidth) / CrachGame.PPM);
		setBoundY((height * tilePixelHeight) / CrachGame.PPM);
		camera.position.x = xBound/2;
		camera.position.y= yBound/2;
	}
	
	public void setBoundY(float mapHeight) {
		useBounds = true;
		yBound = mapHeight;
	}

	public void setBoundX(float mapWidth) {
		useBounds = true;
		xBound = mapWidth;
	}
	
	public boolean isOffCamera(Vector2 pos) {
		return (pos.x >= (camera.position.x + camera.viewportWidth/2)
			 || pos.x <= (camera.position.x - camera.viewportWidth/2));
	}	
	
	public void setView(float WidthView,float HeightView) {
		this.WidthView = WidthView;
		this.HeightView = HeightView;
		if(this.WidthView >=  xBound) {
			followX = false;
			camera.position.x = xBound/2;
			camera.update();
		}
		else followX = true;
		if(this.HeightView >= yBound) {
			followY = false;
			camera.position.y = yBound/2;
			camera.update();
		}
		else followY = true;
	}
	public float getxBound() {
		return  xBound;
	}

	public float getyBound() {
		return  yBound;
	}
	public float getWidthView() {
		return WidthView;
	}
	public float getHeightView() {
		return HeightView;
	}
}
