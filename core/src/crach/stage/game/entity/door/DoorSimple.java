package crach.stage.game.entity.door;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;



public class DoorSimple extends Door{
	private final float hieghtPx = 64 / CrachGame.PPM;
	private final float widthPSx = 2 * hieghtPx;
	
	private TextureRegion Lax,StateDoor;	
	private float pLx, pLy, pSx, pSy;


    
	public DoorSimple(MapObject object, int DerType) {
		super(object, DerType);
		this.defineEntity(X, Y, 0);
		switch (this.DerType) {
		case Bottom:
			pLx = X - hieghtPx / 2;
			pSx = X - widthPSx / 2;
			pLy = Y - width - hieghtPx;
			pSy = Y + width;
			break;
		case Top:
			pLx = X - hieghtPx / 2;
			pSx = X - widthPSx / 2;
			pLy = Y + width;
			pSy = Y - width - hieghtPx;
			break;
		case Left:
			pLx = X - width - hieghtPx;
			pSx = X + width - hieghtPx /2;
			pLy = pSy = Y - hieghtPx / 2;
			break;
		case Right:
			pLx = X + width;
			pSx = X - width - widthPSx *0.75f;
			pLy = pSy = Y - hieghtPx / 2;
			break;
		}
		addtoDourder(pLx + hieghtPx / 2, pLy + hieghtPx / 2, hieghtPx / 2, hieghtPx / 2);
		addtoDourder(pSx + widthPSx / 2, pSy + hieghtPx / 2, widthPSx / 2, hieghtPx / 2);
	}
	@Override
	public void setTexture() {
		Lax = Assets.textureLax;
		StateDoor = Assets.textureDoorClose;
		setRegion(Assets.textureDoorSimple);
		setOriginCenter();
		}
	@Override
	protected void openDoor() {
          super.openDoor();
  		  StateDoor = Assets.textureDoorOpen;
	}
	@Override
	protected void StopMove() {
		super.StopMove();
		StateDoor = Assets.textureDoorClose;
	}
	@Override
	public void draw(Batch batch, Body body) {
		super.draw(batch, b2body);
		batch.draw(StateDoor, pSx, pSy, widthPSx / 2, hieghtPx / 2, widthPSx, hieghtPx, 1, 1, rotat90);
		batch.draw(Lax, pLx, pLy, hieghtPx / 2, hieghtPx / 2, hieghtPx, hieghtPx, 1, 1, rotat90);
	}
}
