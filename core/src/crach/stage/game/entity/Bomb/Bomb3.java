package crach.stage.game.entity.Bomb;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assest;
import crach.stage.game.entity.Entity;

public class Bomb3  extends Bomb{

	public Bomb3(MapObject object) {
		super(object);
		this.lifeTime=0.05f;
	}
	@Override
	public void setTexture() {
		setTexture(Assest.Bomb_Idle_C, Assest.Bomb_Triggering_C, Assest.Bomb_Explosion_C);
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
