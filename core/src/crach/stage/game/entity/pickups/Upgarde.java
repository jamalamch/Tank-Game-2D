package crach.stage.game.entity.pickups;

import com.badlogic.gdx.maps.MapObject;
import crach.stage.game.Assets;
import crach.stage.game.entity.Entity;

public class Upgarde extends Pickup{

	public Upgarde(MapObject object) {
		super(object);
		this.Score = 30;
	}
	@Override
	public void setTexture() {
		setTexture(Assets.animationUpgrade);
	}
	@Override
	public void deathEntity() {
		super.deathEntity();
		creator.getPlayer().upgradCrach();
		creator.addExper(15);
	}
	@Override
	void deletePickup(Entity E) {
		creator.SetDestoryEntity(E);
	}
	@Override
	public void defineEntity(float x, float y, float angle) {
		defineEntity(x, y, angle, 32);
	}

	@Override
	public String toString() {
		return "Upgrade{" + super.toString() +"}";
	}
}
