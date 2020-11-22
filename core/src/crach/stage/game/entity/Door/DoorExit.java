package crach.stage.game.entity.Door;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;

public class DoorExit extends Door{
	private final float HieghtPx = 48 / CrachGame.PPM;
	private final float WidthPSx = 70 /CrachGame.PPM;
	
	private TextureRegion StateDoor;
	private float PSx, PSy;

	
	public DoorExit(MapObject object, int DerType) {
		super(object, DerType);
		this.height =20 / CrachGame.PPM;
		this.defineEntity(X, Y, 0);
		PSx=X-WidthPSx / 2;
		PSy=Y-HieghtPx / 2;
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		if(toOpen || toClose) {
			PSx=getBody().getPosition().x-WidthPSx / 2;
			PSy=getBody().getPosition().y-HieghtPx / 2;
		}			
	}
	@Override
	public void setTexture() {
		StateDoor =Assest.ExitNo;
		setRegion(Assest.DoorExit);
		setOriginCenter();
	}
	@Override
	protected void openDoor() {
          super.openDoor();
  		  StateDoor = Assest.ExitYes;
	}
	@Override
	protected void StopMove() {
		super.StopMove();
		StateDoor = Assest.ExitNo;
	}
	@Override
	public void draw(Batch batch, Body body) {
		super.draw(batch, b2body);
		batch.draw(StateDoor, PSx, PSy, WidthPSx / 2, HieghtPx / 2, WidthPSx, HieghtPx, 1, 1, -rotat90);
	}


}
