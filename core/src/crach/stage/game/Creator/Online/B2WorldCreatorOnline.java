package crach.stage.game.Creator.Online;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import crach.stage.game.Creator.B2WorldCreator;
import crach.stage.game.Creator.Online.connection.Connection;
import crach.stage.game.Creator.Online.connection.connectionLocal;
import crach.stage.game.Screen.PlayScreen;
import crach.stage.game.entites.*;
import crach.stage.game.entites.Enimy.Enimy;



public class B2WorldCreatorOnline extends B2WorldCreator {

	protected int nomberplayer;
	protected Connection connection;
	protected HashMap<String, Entity> EntityMap;
	protected HashMap<String, StateEntity> EntityActive;

	private boolean onLine;

	public enum StateEntity {
		New_Object, Destor_Object, Move_Object,
		New_Player, Destor_Player, Move_Player,
		New_Pickup, Destor_Pickup,
		New_fire,Destor_fire,
		New_Enimy, Destor_Enimy, Move_Enimy,
	}

	public B2WorldCreatorOnline(PlayScreen playScreen) {
		super(playScreen);
		EntityMap = new HashMap<String, Entity>();
		EntityActive = new HashMap<String, StateEntity>();
		connection = new connectionLocal(this,getPlayer(),0.6f);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		for(Entity E : EntityMap.values())
			E.update(dt);
	}

	public void putEntity(StateEntity State, String id, float x, float y, float r, int code) {
		if (!EntityMap.containsKey(id)) {
			Entity E = creatEntity(State, id, x, y, r);
			EntityMap.put(id, E);
			addEntity(E);
		}
	}

	public Entity getEntiy(String id) {
		return EntityMap.get(id);
	}

	public void updateEntityPosition(String id, float x, float y, float r) {
		if (EntityMap.containsKey(id)) {
			Entity E = getEntiy(id);
			E.UpdateToDeplace(new Vector2(x, y), r);
		}
	}

	public void destoryEntity(StateEntity State, String id) {
		if (EntityMap.containsKey(id)) {
			EntityMap.get(id).destroy();
			SetDestoryEntity(EntityMap.remove(id));
		}
	}



	public Entity creatEntity(StateEntity State, String id, float x, float y, float r) {
		Entity E = null;
		switch (State) {
		case New_Object:
			break;
		case New_Player:
			//E = new Crach( x, y, r);
			nomberplayer ++;
			break;
		case New_Pickup:
			break;
		case New_Enimy:
			E = new Enimy(id, x, y, r);
		case New_fire:
			//E = new fire(new Vector2(x, y), r);
			break;
		default:
			return null;
		}
		E.SetForceDeplace(x, y, r);
		
		return E;
	}

	public Entity creatEntity(StateEntity State, String id, float x, float y) {
		Entity E = null;
		switch (State) {
		case New_Object:
			break;
		case New_Pickup:
			break;
		case New_Enimy:
			E = new Enimy(id, x, y);
		default:
			return null;
		}
		return E;
	}

	public HashMap<String, StateEntity> getEntityActive() {
		return EntityActive;
	}
    
	public HashMap<String, Entity> getEntityMap() {
		return EntityMap;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}
    public boolean isEntityExist(String id) {
    	return EntityMap.containsKey(id);
    }

	@Override
	public void victorGame() {
		
	}

	@Override
	public void defeatGame() {
		
	}

	@Override
	public void pauser() {
		
	}

	@Override
	public void QuitGame() {

	}

	@Override
	public void rePlay() {

	}

	@Override
	public boolean isContinue() {
		return false;
	}

	@Override
	protected void activePlaceExit() {

	}

	@Override
	public void addExper(int expertion) {

	}

	@Override
	public void addScore(int score) {

	}

	@Override
	public void addDanger(int danger) {

	}

	@Override
	public void addCoin(int coin) {

	}

	@Override
	public void addHp(int hp) {

	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public int getExper() {
		return 0;
	}

	@Override
	public int getStars() {
		return 0;
	}

	@Override
	public int getRecord() {
		return 0;
	}

	@Override
	public int getCoin() {
		return 0;
	}

	@Override
	public int getHp() {
		return 0;
	}
}
