package crach.stage.game.creator.Online;

import com.badlogic.gdx.math.MathUtils;

import crach.stage.game.screen.PlayScreen;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Enimy.Enimy;
import crach.stage.game.entity.Object.Object;


public class B2WorldCreatorOnlineAdmin extends B2WorldCreatorOnline{
	private  int MaxNumberBox ;
	private  int MaxNumberCry ;
	private  int MaxNumberEnimy ;
	private float BoondsX, BoondsY;

	public B2WorldCreatorOnlineAdmin(PlayScreen playScreen, int Code, float Bonndsx, float Bonndsy,int numberBox,int numberCry,int numberEnimy) {
		super(playScreen);
		this.MaxNumberBox = numberBox;
		this.MaxNumberCry = numberCry;
		this.MaxNumberEnimy=numberEnimy;
		this.BoondsX = Bonndsx;
		this.BoondsY = Bonndsy;
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		updateRoom();
	}
	@Override
	public void destoryEntity(StateEntity State, String id) {
		super.destoryEntity(State, id);
		switch (State) {
			case Destor_Object:
			numberBox--;
			break;
			case Destor_Pickup:
			numberCry--;
		case Destor_Enimy:
			numberEnimy--;
		case Destor_Player:
			nomberplayer --;
		default:
			break;
		}
	}
	public void creatEntity(StateEntity State, String id) {
		float x = MathUtils.random(Object.getHieght()/2,BoondsX-Object.getHieght()/2);
		float y = MathUtils.random(Object.getHieght()/2,BoondsY-Object.getHieght()/2);
		Entity E = creatEntity(State, id, x, y);
		EntityMap.put(id, E);
		EntityActive.put(id, State);
		E.SetForceDeplace(x, y, E.getBody().getAngle());
	}
	public void updateRoom() {
		if (numberBox < MaxNumberBox) {
			creatEntity(StateEntity.New_Object, String.valueOf(EntityMap.size() + 1));
			numberBox++;
		}
		if (numberCry < MaxNumberCry) {
			creatEntity(StateEntity.New_Pickup, String.valueOf(EntityMap.size() + 1));
			numberCry++;
		}
		if (numberEnimy < MaxNumberEnimy) {
			creatEntity(StateEntity.New_Enimy, String.valueOf(EntityMap.size() + 1));
			numberEnimy++;
		}
	}
	public void addAlltoActive() {
		for (Entity b : EntityMap.values())
			if (b instanceof Object)
				EntityActive.put(b.getId(), StateEntity.New_Object);
			else if (b instanceof Enimy)
				EntityActive.put(b.getId(), StateEntity.New_Enimy);
    }
}
