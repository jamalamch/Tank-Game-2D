package crach.stage.game.entity.pickups;

import com.badlogic.gdx.maps.MapObject;
import crach.stage.game.Assets;
import crach.stage.game.entity.Entity;


public abstract class Crystal extends Pickup{
	public Crystal(String id,float x, float y) {
        this(x, y);
        this.Id=id;
	}
	public Crystal(float x, float y) {
		super(x, y);
	}

	public Crystal(MapObject object) {
		super(object);
	}
	
	public static class A extends Crystal{

		public A(MapObject object) {
			super(object);
			this.Score = 20;
		}

		@Override
		public void setTexture() {
			setTexture(Assets.animationCrystal);
		}
		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 20);
		}
		@Override
		public void deathEntity() {
			super.deathEntity();
			Assets.soundCrystal.play();
		}
		@Override
		void deletePickup(Entity E) {
			creator.deleteCry((Crystal) E);
		}
	}
	public static class B extends Crystal{
		public B(float x, float y) {
			super(x, y);
			this.Score = 50;
		}

		@Override
		public void setTexture() {
			setTexture(Assets.animationCrystal2);
		}

		@Override
		public void defineEntity(float X, float Y, float R) {
			defineEntity(X, Y, R, 25);
		}	
		@Override
		public void deathEntity() {
			super.deathEntity();
			Assets.soundBonus1.play();
		}
		@Override
		void deletePickup(Entity E) {
			creator.SetDestoryEntity(E);
		}

	}
	
}
