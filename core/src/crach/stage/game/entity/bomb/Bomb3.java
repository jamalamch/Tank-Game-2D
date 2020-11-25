package crach.stage.game.entity.bomb;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assets;
import crach.stage.game.entity.Entity;

public class Bomb3  extends Bomb{

	public Bomb3(MapObject object) {
		super(object);
		this.lifeTime=0.05f;
	}
	@Override
	public void setTexture() {
		setTexture(Assets.animationBombIdleC, Assets.animationBombTriggeringC, Assets.animationBombExplosionC);
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		if(destroy) {
			if(animation.isAnimationFinished(dt_time)) {
		    	destroy();
			}
		}else {
			if(Explosion) {
				lifeTime -=dt;
				if(lifeTime < 0) {
					deathEntity();
				}
			}
	}
		
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 40, 120);
	}
	@Override
	public void onContactStart(Entity otherEntity) {
		if(!Explosion) {
			Explosion = true;
			animation =animationTriggering;
		}else{
			if(otherEntity != null)
				antyReaction(this, otherEntity, 2);
		}
	}

}
