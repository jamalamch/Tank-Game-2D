package crach.stage.game.entites.Bomb;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assest;
import crach.stage.game.entites.Entity;

public class Bomb1 extends Bomb{

	public Bomb1(MapObject object) {
		super(object);
		this.lifeTime=1;
		this.Domage = 120;
	}
	@Override
	public void setTexture() {
		setTexture(Assest.Bomb_Idle_A, Assest.Bomb_Triggering_A, Assest.Bomb_Explosion_A);
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		if(Destore) {
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
