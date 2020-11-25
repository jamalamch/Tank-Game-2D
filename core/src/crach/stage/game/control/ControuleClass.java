package crach.stage.game.control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;

import crach.stage.game.entity.crach.CrachPlayer;
import crach.stage.game.creator.B2WorldCreator;



public class ControuleClass implements Controule {


    protected Stage stage;
	
	protected CrachPlayer player;
	protected Body b2body;
	protected Vector2 vitaseUp,vitaseDown ;
	protected Vector2 Center;
	protected float Dt_time;

    public ControuleClass(final B2WorldCreator creator){
        this.stage = creator.getGdxPlayScreen().getStage();
        this.player = creator.getPlayer();
        this.b2body = player.getBody();
        this.vitaseUp = new Vector2(player.Vitass,player.Vitass);
        this.vitaseDown = new Vector2(vitaseUp).scl(0.3f);
        player.setControule(this);
    }
	@Override
	public void update(float delta) {
		Dt_time += (Dt_time < 10) ?delta:0;
	}
	
	public void setPlayer(CrachPlayer player) {
		this.player = player;
        this.b2body = player.getBody();
        player.setControule(this);
	}
	
    public float getDt_time() {
		return Dt_time;
	}
}
