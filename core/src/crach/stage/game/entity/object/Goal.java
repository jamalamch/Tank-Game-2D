package crach.stage.game.entity.object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import net.dermetfan.gdx.math.MathUtils;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;

public class Goal extends Object {
    public Goal(MapObject object) {
        defineEntity(object,getAngle(object.getProperties().get("derect",0,Integer.class))* com.badlogic.gdx.math.MathUtils.degreesToRadians);
        setTexture();
    }

    @Override
    public void setTexture() {
        setRegion(Assets.textureFootballGoals.get(com.badlogic.gdx.math.MathUtils.random(1)));
        setOriginCenter();
        setZIndex(Zindex.ZindexDoor);
    }

    public void defineEntity(float X, float Y, float R) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        Shape shape;

        bdef.type = BodyDef.BodyType.StaticBody;
     //   bdef.linearDamping=2;
      //  bdef.angularDamping=1.4f;
        bdef.position.set(X,Y-15);
        bdef.angle = R;
        b2body = world.createBody(bdef);
        shape = new ChainShape();

        float[] shapGoor = MathUtils.div(new float[]{-164f,-188.5f,-163f,-101.5f,-143f,-90.5f,-133f,-55.5f,0f,0f,34.5f,-22.5f,65.5f,-14.5f,81.5f,13f,
                74.5f,46.5f,38f,64f,20f,59.5f,1.5f,45.5f,-4f,19f,-144f,-42f,-172.5f,-33.5f,-188.5f,-42f,-199f,-74.5f,-180f,-97.5f,-181f,-284.5f,-198f,
                -306.5f,-190f,-337.5f,-160f,-346f,-145.5f,-339f,-5f,-400.5f,17f,-439f,57.5f,-440f,80.5f,-412f,75.5f,-371f,50f,-357.5f,14.5f,-365f,-0.5f,
                -381f,-133.5f,-324.5f,-137.5f,-292.5f,-162.5f,-279.5f
        }, CrachGame.PPM+2);

        ((ChainShape)shape).createLoop(shapGoor);
        fdef.shape = shape;
      //  fdef.density = 0.1f;
        fdef.filter.categoryBits = CrachGame.OBJECT_BIT;
        b2body.createFixture(fdef);
        shape.dispose();

        shape = new PolygonShape();
        fdef = new FixtureDef();
        ((PolygonShape)shape).setAsBox(4,13,new Vector2(-7,-16),0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = CrachGame.ZONE_BIT;
        fdef.filter.maskBits = CrachGame.OBJECT_BIT;
        b2body.createFixture(fdef);
        shape.dispose();
        b2body.setUserData(this);
    }
    @Override
    public void onContactStart(Entity otherEntity) {
        if (otherEntity instanceof Ball) {
            creator.addScore(com.badlogic.gdx.math.MathUtils.random(20 +10*creator.getnStage(),50 +10*creator.getnStage()));
            creator.victorGame();
        }
    }
}
