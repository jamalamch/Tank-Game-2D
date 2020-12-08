package crach.stage.game.entity.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import net.dermetfan.gdx.physics.box2d.Box2DUtils;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.entity.Explosion;
import crach.stage.game.entity.shell.Shell;
import crach.stage.game.utils.SpriteAccessor;

public class Ball  extends M_Circle{
    private int life = 10;
    private float deathDuration = 0.3f,dt_time;
    public Ball(MapObject object) {
        super(object);
    }

    @Override
    public void setTexture() {
        setTexture(Assets.animationBall.getKeyFrame(0));
    }

    @Override
    public void defineEntity(float x, float y, float angle) {
        defineEntity(x, y, angle, 40, 1.4f, 0.8f, 0.01f,0.6f,0.35f);
    }

    @Override
    public void Hit(Explosion explosion) {
        super.Hit(explosion);
        if(!(explosion instanceof Shell)) {
            life -= explosion.getDanger();
            if (life < 0) {
                deathEntity();
            }
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(destroy) {
            dt_time += dt;
            if(Assets.animationBall.isAnimationFinished(dt_time)) {
                creator.SetDestoryEntity(this);
                creator.defeatGame();
            }
        }
    }

    @Override
    public void deathEntity() {
        Assets.soundImpactCannon.play(getVolume(), 1, getPan());
        destroy = true;
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        setRotation(b2body.getAngle()*MathUtils.radiansToDegrees);
        setSize(Box2DUtils.size(getBody()).x,Box2DUtils.size(getBody()).y);
        setOriginCenter();
        tweenDeathAnim();
    }
    public void tweenDeathAnim() {
        creator.TWEEN_MANAGER.killTarget(this);
        Timeline.createSequence().beginSequence()
                .push(Tween.call(new TweenCallback() {
                    @Override
                    public void onEvent(int arg0, BaseTween<?> arg1) {
                        destroy();
                    }
                }))
                .push(Tween.to(this, SpriteAccessor.COLOR, deathDuration/2f).target(1, 0, 0))
                .push(Tween.to(this, SpriteAccessor.ALPHA, deathDuration/2f).target(0))
                .push(tweenFadeOut(this, deathDuration, new Color(1, 0, 0, 0)).end())
                .end().start(creator.TWEEN_MANAGER);
    }

    @Override
    public void draw(Batch batch, Body body) {
        if(!destroy)
            super.draw(batch, body);
        else{
            TextureRegion AnimationF = Assets.animationBall.getKeyFrame(dt_time);
            float WidthS = (AnimationF.getRegionWidth()*0.7f)/ CrachGame.PPM, HiegthS = (AnimationF.getRegionHeight()*0.7f)/CrachGame.PPM;
            batch.setColor(Color.WHITE);
            batch.draw(AnimationF, getX()  -WidthS/2, getY() -HiegthS/2, WidthS/2, HiegthS/2, WidthS, HiegthS, getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public String toString() {
        return "Ball{" +
                "life=" + life +
                ", deathDuration=" + deathDuration +
                ", dt_time=" + dt_time +
                '}';
    }
}
