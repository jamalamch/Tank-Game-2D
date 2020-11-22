package crach.stage.game.entites.Pickups;

import com.badlogic.gdx.maps.MapObject;
import crach.stage.game.Assest;
import crach.stage.game.entites.Entity;

public class Upgarde extends Pickup{

	public Upgarde(MapObject object) {
		super(object);
		this.Score = 30;
	}
	@Override
	public void setTexture() {
		setTexture(Assest.upgrade);
	}
	@Override
	public void DeathEntity() {
		super.DeathEntity();
		creator.getPlayer().UpgradCrach();
		creator.addExper(15);
	}
	@Override
	void deletePickup(Entity E) {
		creator.SetDestoryEntity(E);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 32);
	}


}
