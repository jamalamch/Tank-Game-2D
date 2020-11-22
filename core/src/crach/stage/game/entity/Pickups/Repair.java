package crach.stage.game.entity.Pickups;

import com.badlogic.gdx.maps.MapObject;

import crach.stage.game.Assest;
import crach.stage.game.entity.Entity;

public class Repair extends Pickup{

	public Repair(MapObject object) {
		super(object);
	}

	@Override
	public void setTexture() {
		setTexture(Assest.repair);
	}
	@Override
	public void DeathEntity() {
		super.DeathEntity();
		creator.getPlayer().reLifeUpdate();
		creator.addExper(10);
		creator.addHp(1);
	}
	@Override
	void deletePickup(Entity E) {
		creator.SetDestoryEntity(E);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 40);
	
}
}
