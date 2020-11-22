package crach.stage.game.entites.PLace;

import com.badlogic.gdx.maps.MapObject;
import crach.stage.game.entites.Entity;

public abstract class Place extends Entity{
	
	protected int hieght = 70;

	
    public enum Interaction {
        NONE, BOX, CrachPlyer
    }

	protected Interaction interaction = Interaction.NONE;
	public Entity interactEntity;
	
	public Place() {
		setTexture();
	}
	public Place(int x,int y,float r) {
		defineEntity(x, y,r);
		setTexture();
	}
	public Place(MapObject object){
		defineEntity(object);
		setTexture();
	}	    
}
