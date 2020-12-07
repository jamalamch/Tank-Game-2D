package crach.stage.game.control;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import crach.stage.game.control.Pathfinding.ArrayGraphFind;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.crach.CrachPlayer;
import crach.stage.game.entity.enimy.Enimy;
import crach.stage.game.creator.B2WorldCreatorOfline;

public class ControuleEnimy implements Controule {
	protected B2WorldCreatorOfline creator;
	protected Pathfinding FindPath;
	protected ArrayGraphFind mPath;
	private Enimy Enimy;
	private CrachPlayer Player;
	
	
	private float Dt_Attack;
	private float Dt_Stop;

	private int PathAdd;
	private float CellSize;

	public boolean Attack;
	private boolean Move;
	private boolean DoorClose;

	public ControuleEnimy(Enimy Enimy, CrachPlayer Player) {
		this.Enimy = Enimy;
		this.Player = Player;
		this.creator = (B2WorldCreatorOfline) Entity.creator;
		this.FindPath = creator.getPathfinding();
		this.CellSize = (float) Math.sqrt(2 * creator.CellSize * creator.CellSize);
		setInteraction(true);
	}

	@Override
	public void update(float delta) {
			if (!DoorClose) {
				walks(delta);
				follow();

				if (Attack) {
					attackOn(delta);
				}
				
			} else stop(delta);
			
			Enimy.toRoration.applyTorque(Enimy.getBody(), true);
	}
	
	public void setInteraction(boolean Inter) {
		Vector2 position = creator.getEntityCell(Enimy);
		mPath = FindPath.calculatePath((int) position.x, (int) position.y, (int) creator.pCellPlayer.x,
				(int) creator.pCellPlayer.y);
			setCloseDoor(mPath.hasDoor());
	}
	
	public void setCloseDoor(boolean close) {
		DoorClose = close;
		if (!DoorClose) {
			Enimy.Starting();
			Dt_Stop = -1;
		}
	}

	public void setStope(float dt) {
		Dt_Stop = dt;
	}

	private void rotationToEntity(Entity E) {
		Vector2 V = Enimy.getBody().getPosition();
		Vector2 chemain = new Vector2(E.getBody().getPosition());
		chemain.sub(V);
		Enimy.toRoration.setAngle(chemain.angleRad());
	}

	private void follow() {
		if (PathAdd < 12) {
			if (!Move )
				if (mPath.getCount() > 4) {
					float x = mPath.first().mX + creator.CellSize / 2;
					float y = mPath.first().mY + creator.CellSize / 2;
					Enimy.updateToDeplace(new Vector2(x, y), !Attack);
					Move = true;
					mPath.getNode().remove(0).select(false);
				} 
			     if(mPath.getCount() < 6)
	                    Attack = true;		
		} else {
			FindPath.generePath(mPath);
			PathAdd = 0;
		}
		if (!Player.getBody().getLinearVelocity().isZero(10)) {
			if (mPath.peek() != FindPath.mNodes[(int) creator.pCellPlayer.x][(int) creator.pCellPlayer.y]) {
				PathAdd += mPath.fastAdd((int) creator.pCellPlayer.x,(int) creator.pCellPlayer.y);
				if (PathAdd < 0)
					PathAdd = 0;
				System.out.println("Active " + PathAdd + " cont " + mPath.getCount());
			} 
			if(Attack) {
				if(Player.getBody().getPosition().dst(Enimy.getBody().getPosition()) > 6*CellSize)
					Attack = false;
			}
		}
	}
	private void walks(float delta) {
		if (Dt_Stop < 0) {
			if (!MathUtils.isEqual(Enimy.getBody().getPosition().x, Enimy.toPosition.getDestination().x, 5)
					|| !MathUtils.isEqual(Enimy.getBody().getPosition().y, Enimy.toPosition.getDestination().y,
							5)) {
				Enimy.toPosition.applyForceToCenter(Enimy.getBody(), true);
			} else
				Move = false;
		} else
			Dt_Stop -= delta;

	}
	private void attackOn(float delta) {
		rotationToEntity(Player);
		if (Dt_Attack <= 0) {
			fireAttack(Player);
			Dt_Attack=Enimy.fire;
		} else
			Dt_Attack -= delta;
	}

	private void stop(float delta) {
		if (Dt_Stop < 0) {
			rotationToEntity(Player);
			Dt_Stop = 6;
		} else {
			Dt_Stop -= delta;
			if(DoorClose)
			if (!Player.getBody().getLinearVelocity().isZero(10))
				if (mPath.isDoor((int) creator.pCellPlayer.x,(int) creator.pCellPlayer.y)) {
						setCloseDoor(false);
				}
		}
	}
	private  void fireAttack(Entity E) {
		Enimy.TireFire();
	}
	public boolean isAttack() {
		return Attack;
	}
	public void ReAttack() {
		 Attack = true;	
		 setCloseDoor(false);
	}
}
