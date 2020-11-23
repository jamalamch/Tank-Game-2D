package crach.stage.game.entity.Bomb;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.Light;
import box2dLight.PointLight;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;

public abstract class BombGun extends TrapBomb{
	
	protected Body target;
	protected Light lightFire ;
	
	public BombGun(MapObject object) {
		super(object, 50/CrachGame.PPM, 50/CrachGame.PPM, 0, 0);
		this.pause = true;
		addLight();
		setRotation(angel);
		this.timeAttack = MathUtils.random(0f, 5f);
	}

	@Override
	public void setTexture() {
		setTexture(Assets.animationMagic, Assets.animationMagicUp);
	}

	@Override
	public void update(float dt) {
		dt_time +=dt;
		setRegion(animation.getKeyFrame(dt_time,true));
		if(!pause) {
			timeAttack -=dt;
			if( timeAttack < 0) {
				starAttack();
			}
			lightFire.setDistance(((getWidth()>getHeight())?getWidth():getHeight())*3.5f);
		}
	}


	@Override
	public void Getting(Entity otherEntity) {
		this.setStop(false);
		this.target = otherEntity.getBody();
		dt_time = 0;
		animation = animationExplosion;
	}
	@Override
	public void Drifting(Entity otherEntity) {
		this.setStop(true);
		dt_time = 0;
		animation = animationIdle;
	}
	@Override
	public void setStop(boolean stop) {
		pause = stop;
	}
	@Override
	protected void pauseAttack() {
	}
	public abstract void addLight();
//	private void addLight() {
//		lightFire = new PointLight(creator.rayHandler, 128, new Color(0.3f, 0.3f, 1, 0.75f), 30,getX(),getY());
//		lightFire.setStaticLight(true);
//	}
	public static class A extends BombGun{

	public A(MapObject object) {
		super(object);
	}
	@Override
	public void starAttack() {
		Assets.soundBlueAttack.play(getVolume());
			creator.addEntity(new BombGunShell.A(b2body.getPosition(), target));
			timeAttack = 9;
	}
	@Override
	public void addLight() {
		lightFire = new PointLight(creator.rayHandler, 128, new Color(0.9f, 0.86f, 0.43f, 0.75f), 30,getX(),getY());
		lightFire.setStaticLight(true);
	}
	}
	
	public static class B extends BombGun{

	public B(MapObject object) {
		super(object);
	}
	@Override
	public void starAttack() {
			Assets.soundRedAttack.play(getVolume());
			creator.addEntity(new BombGunShell.B(b2body.getPosition(), target));
			timeAttack = 10;
	}
	@Override
	public void addLight() {
		lightFire = new PointLight(creator.rayHandler, 128, new Color(0.89f, 0.66f, 0.63f, 0.75f), 30,getX(),getY());
		lightFire.setStaticLight(true);
	}
	}
	public static BombGun CreateBombGun(MapObject object){
		BombGun B = null;
		int codetype = object.getProperties().get("code", MathUtils.random(1), Integer.class);
		switch(codetype) {
			case 0:
				B = new A(object);
				break;
			case 1:
				B = new B(object);
				break;
		}
		return B;
	}
}
