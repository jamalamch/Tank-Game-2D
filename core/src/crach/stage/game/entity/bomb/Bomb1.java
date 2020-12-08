package crach.stage.game.entity.bomb;

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
	public void defineEntity(float x, float y, float angle) {
		super.defineEntity(x, y, angle, 40, 140);
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
