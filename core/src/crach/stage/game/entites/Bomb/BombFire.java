package crach.stage.game.entites.Bomb;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import box2dLight.Light;
import box2dLight.PointLight;
import crach.stage.game.Assest;
import crach.stage.game.CrachGame;

public class BombFire extends TrapBomb{
	
	Light lightFire ;
	
	public BombFire(MapObject object) {
		super(object, 20/CrachGame.PPM, 20/CrachGame.PPM, 100/CrachGame.PPM, 30/CrachGame.PPM);
		addLight();
		setRotation(angel);
	}
	
	public BombFire(Vector2 posiVector2,int derection) {
		super(posiVector2,derection,20/CrachGame.PPM, 20/CrachGame.PPM, 100/CrachGame.PPM, 30/CrachGame.PPM);
		addLight();
		setRotation(angel);
	}
	
	@Override
	public void setTexture() {
		setTexture(Assest.blueish_flame_up, Assest.Sprite_Fire_Shots_Flame);
	}
	
	
	@Override
	public void update(float dt) {
		dt_time +=dt;
		setRegion(animation.getKeyFrame(dt_time,true));
		
		if(attack) {
			if(pause) {
				timeAttack -=dt;
				if(timeAttack < 0) {
					starAttack();
				}
			}
			else
				if(animation.isAnimationFinished(dt_time))
					pauseAttack();
			
			lightFire.setDistance(((getWidth()>getHeight())?getWidth():getHeight())*2.5f);
		}
		
	}

	
	@Override
	protected void starAttack() {
		Assest.Effects_flam.get(MathUtils.random(3)).play(getVolume(),1,getPan());
		attack = true;
		pause =false;
		tweenToAttack();
		dt_time = 0;
		animation = animationExplosion;
	}
	@Override
	protected void pauseAttack() {
		tweenToPauser();
		dt_time = 0;
		timeAttack = 5*MathUtils.random(0.9f, 1.3f);
		pause =true;
		if(Destore)
			attack = pause =false;
		animation = animationIdle;
	}
	private void addLight() {
		lightFire = new PointLight(creator.rayHandler, 128, new Color(0.3f, 1, 0, 0.75f), 30,getX(),getY());
		lightFire.setStaticLight(true);
	}
}
