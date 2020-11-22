package crach.stage.game.entites.Zone;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import crach.stage.game.entites.Entity;
import crach.stage.game.entites.InZone;

public class ZonObject extends Zone{

	protected final static Array<ZonObject> Zones = new Array<ZonObject>();
	protected Array<InZone> ObjectInZon  = new Array<InZone>();
	
	public ZonObject(MapObject object,short maskbit) {
		super(object, maskbit);
		Id = object.getProperties().get("Id", String.class);
		Zones.add(this);
	}

	@Override
	public void update(float dt) {
		
	}
	@Override
	public void onContactStart(Entity otherEntity) {
		for(InZone inZone : ObjectInZon)
			inZone.Getting(otherEntity);
	}
	@Override
	public void onContactEnd(Entity otherEntity) {
		for(InZone inZone : ObjectInZon)
			inZone.Drifting(otherEntity);
	}
	public void addObjectZon(InZone inzone) {
		ObjectInZon.add(inzone);
	}
	public static boolean addToZone(InZone inZone,String Id) {
		for(int i=0;i<Zones.size;i++)
			if(Zones.get(i).getId().equals(Id)) {
				Zones.get(i).addObjectZon(inZone);
				return true;
			}
				
		return false;
	}
	public static void dispose() {
		Zones.clear();
	}
}
