package crach.stage.game.entity.Bomb;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assets;
import crach.stage.game.entity.Entity;

public class Bomb1 extends Bomb{

	public Bomb1(MapObject object) {
		super(object);
		this.lifeTime=1;
		this.Domage = 120;
	}
	@Override
	public void setTexture() {
		setTexture(Assets.animationBombIdleA, Assets.animationBombTriggeringA, Assets.animationBombExplosionA);
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
		super.defineEntity(X, Y, R, 40, 140);
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
