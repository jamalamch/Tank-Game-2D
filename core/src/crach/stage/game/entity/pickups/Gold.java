package crach.stage.game.entity.pickups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;

public class Gold extends Pickup{

	public Gold(MapObject object) {
		super(object);
		this.Score = MathUtils.random(5, 20);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.animationGold);
		setScale(1.7f);
		setPosition(b2body.getPosition().x, b2body.getPosition().y);
	}
	@Override
	public void deathEntity() {
    	destroy = true;
    	tweenDeathAnim(new Color(1, 0.5f, 0.01f, 0.6f),2.2f);
		Assets.soundCoins.get(MathUtils.random(1)).play();
	}
	@Override
	void deletePickup(Entity E) {
		creator.SetDestoryEntity(E);
	}
	@Override
	public void defineEntity(float X, float Y, float R) {
		defineEntity(X, Y, R, 20);
	}
	@Override
	public void draw(Batch batch, Body body) {
		setWidth(this.getRegionWidth()/CrachGame.PPM);
		setHeight(this.getRegionHeight()/CrachGame.PPM);
		setOriginCenter();
		batch.setColor(getColor());
		batch.draw(this
				, getX() - getWidth()/2
				, getY() - getHeight()/2
				, getWidth()/2
				, getHeight()/2
				, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.setColor(Color.WHITE);
	}
}
