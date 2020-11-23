package crach.stage.game.entity.Bomb;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assets;
import crach.stage.game.entity.Entity;

public class Bomb2  extends Bomb{

	public Bomb2(MapObject object) {
		super(object);
		this.lifeTime=0.07f;
		this.Domage = 100;
	}
	@Override
	public void setTexture() {
		setTexture(Assets.animationBombIdleB, Assets.animationBombTriggeringB, Assets.animationBombExplosionB);
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		if(destory) {
			if(animation.isAnimationFinished(dt_time)) {
		    	destroy();
			}
		}else {
			if(Explosion) {
				lifeTime -=dt;
				if(lifeTime < 0) {
					DeathEntity();
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
				AntyReaction(this, otherEntity, 2);
		}
	}

}
