package crach.stage.game.entites.Door;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;



public class DoorSimple extends Door{
	private final float HieghtPx = 64 / CrachGame.PPM;
	private final float WidthPSx = 2 * HieghtPx;	
	
	private TextureRegion Lax,StateDoor;	
	private float PLx, PLy, PSx, PSy;


    
	public DoorSimple(MapObject object, int DerType) {
		super(object, DerType);
		this.defineEntity(X, Y, 0);
		switch (this.DerType) {
		case Bottom:
			PLx = X - HieghtPx / 2;
			PSx = X - WidthPSx / 2;
			PLy = Y - Width - HieghtPx;
			PSy = Y + Width;
			break;
		case Top:
			PLx = X - HieghtPx / 2;
			PSx = X - WidthPSx / 2;
			PLy = Y + Width;
			PSy = Y - Width - HieghtPx;
			break;
		case Left:
			PLx = X - Width - HieghtPx ;
			PSx = X + Width -HieghtPx/2;
			PLy = PSy = Y - HieghtPx / 2;
			break;
		case Right:
			PLx = X + Width ;
			PSx = X - Width - WidthPSx*0.75f;
			PLy = PSy = Y - HieghtPx / 2;			
			break;
		}
		addtoDourder(PLx + HieghtPx / 2, PLy + HieghtPx / 2, HieghtPx / 2, HieghtPx / 2);
		addtoDourder(PSx + WidthPSx / 2, PSy + HieghtPx / 2, WidthPSx / 2, HieghtPx / 2);
	}
	@Override
	public void setTexture() {
		Lax = Assest.Lax;
		StateDoor = Assest.DoorClose;
		setRegion(Assest.DoorSimple);
		setOriginCenter();
		}
	@Override
	protected void openDoor() {
          super.openDoor();
  		  StateDoor = Assest.DoorOpen;
	}
	@Override
	protected void StopMove() {
		super.StopMove();
		StateDoor = Assest.DoorClose;
	}
	@Override
	public void draw(Batch batch, Body body) {
		super.draw(batch, b2body);
		batch.draw(StateDoor, PSx, PSy, WidthPSx / 2, HieghtPx / 2, WidthPSx, HieghtPx, 1, 1, rotat90);
		batch.draw(Lax, PLx, PLy, HieghtPx / 2, HieghtPx / 2, HieghtPx, HieghtPx, 1, 1, rotat90);
	}
}
