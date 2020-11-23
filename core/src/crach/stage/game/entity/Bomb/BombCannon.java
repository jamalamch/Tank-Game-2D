package crach.stage.game.entity.Bomb;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;

public class BombCannon extends TrapBomb{
	public BombCannon(MapObject object) {
		super(object, 20/CrachGame.PPM, 20/CrachGame.PPM, 70/CrachGame.PPM, 30/CrachGame.PPM);
		setRotation(angel);
	}
	
	public BombCannon(Vector2 posiVector2,int derection) {
		super(posiVector2,derection,20/CrachGame.PPM, 20/CrachGame.PPM, 70/CrachGame.PPM, 30/CrachGame.PPM);
		setRotation(angel);
	}
	
	@Override
	public void setTexture() {
		setTexture(Assets.animationBombCannonFiring,null);
	}
	
	@Override
	public void update(float dt) {		
		if(attack) {
			if(pause) {
				timeAttack -=dt;
				if(timeAttack < 0) {
					starAttack();
				}
			}
			else {
			dt_time +=dt;
			setRegion(animation.getKeyFrame(dt_time,true));
				if(animation.isAnimationFinished(dt_time))
					pauseAttack();
		}
	}
}
	@Override
	protected void starAttack() {
		attack = true;
		pause = false;
		tweenToAttack();
		dt_time = 0;
		Assets.soundImpactCannon.play(getVolume(), 1, getPan());
	}
	@Override
	protected void pauseAttack() {
		tweenToPauser();
		dt_time = 0;
		timeAttack = 8*MathUtils.random(0.9f, 1.3f);
		pause =true;
		if(destory)
			attack = pause =false;
		setRegion(animation.getKeyFrame(dt_time,true));
	}
}
