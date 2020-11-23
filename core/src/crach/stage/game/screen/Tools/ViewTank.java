package crach.stage.game.screen.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;

public class ViewTank extends Actor {
	
    private float dstBetTr;
    private float heightTr, widthTr;
    private float heightGun, widthGun;
    private float heightPos, widthPos;
    private float heightBody ,widthBody;
	
    private float rotation = 90;
    private float actTire,Dt_time,Dt_smoke,Dt_tire;
    private boolean SmokeFire;
    
	int ncolor = 0,nGunA=5 ,nGun_B=3 ,nHull = 4 ,nTranck = 1;

    
	float scaleT = 1;
	
    private final  Animation<TextureRegion> animation = Assets.spriteEffectsExhaust00;
    
	private TextureRegion textureTrank;
    private TextureRegion textureTrack_1;
    private TextureRegion textureTrack_2;
    private TextureRegion textureGun;
    private TextureRegion textureGunPoss;
    private TextureRegion textureTrack;
    
    private Drawable backDrawable ;
    public ViewTank() {
    	
		updateColor(CrachGame.getColorSelect());
        updateTrack(CrachGame.getTrackSelect());
        updateTrank(CrachGame.getHullSelect());
        updateGun(CrachGame.getGunBSelect());
        updateGunPoss(CrachGame.getGunASelect());
		backDrawable = Assets.skinStyle.getDrawable("Main_Tank_Table");
    }  
    public void updateColor(int ncolor) {
    	this.ncolor = ncolor;
    	if(textureGun != null) updateGun(nGun_B);
    	if(textureTrank != null) updateTrank(nHull);
    }
    public void updateGunPoss(int nGunA) {
    	this.nGunA=nGunA;
		textureGunPoss = Assets.textureGunAs.get(nGunA);
	    heightPos = textureGunPoss.getRegionHeight()*scaleT ;
		widthPos = textureGunPoss.getRegionWidth()*scaleT;
    }
    public void updateGun(int nGun_B) {
    	this.nGun_B=nGun_B;
		textureGun = Assets.textureGunBs.get(ncolor).get(nGun_B);
	    heightGun = textureGun.getRegionHeight()*scaleT ;
		widthGun = textureGun.getRegionWidth()*scaleT;
    }    
    public void updateTrank(int nHull) {
    	this.nHull = nHull;
    	textureTrank = Assets.textureHulls.get(ncolor).get(nHull);
	    heightBody = textureTrank.getRegionHeight()*scaleT ;widthBody = textureTrank.getRegionWidth()*scaleT;
	    if(heightBody < (90*scaleT*2- heightTr)) {
	    	widthBody = widthBody*((90*scaleT*2- heightTr)/heightBody);
	    	heightBody=(90*scaleT*2- heightTr);
	    	dstBetTr =85*scaleT;
	    }
	    else {
	    	dstBetTr =90*scaleT;
	    }
    }
    public void updateTrack(int nTranck) {
    	this.nTranck = nTranck;
		textureTrack_1 = Assets.textureTrackAs.get(nTranck);
		textureTrack_2 =  Assets.textureTrackBs.get(nTranck);
		textureTrack = textureTrack_1;
	    heightTr = textureTrack_1.getRegionHeight()*scaleT ;
		widthTr = textureTrack_1.getRegionWidth()*scaleT;
    }
    @Override
    public void act(float delta) {
    	if(isVisible()) {
	     actTire = (actTire <= 0) ? 0 : actTire - 10*delta;

			Dt_time +=delta;
			if(Dt_time>0.2f) {
			    textureTrack = (textureTrack == textureTrack_1)? textureTrack_2 : textureTrack_1;
			    Dt_time=0;
			}
			if(SmokeFire) {
				Dt_smoke += delta;
			}
			Dt_tire += delta;
			if(Dt_tire > 5) {
				actTire = 30;
				SmokeFire =true;
				Dt_tire=0;
			}
				
    	}
    	super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float Px = getX()+getWidth()/2;
		float Py = getY()+getHeight()/2-10;
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		
		backDrawable.draw(batch, getX(), getY(), getWidth(), getHeight());
		
		batch.draw(textureTrack
				, Px  - widthTr /2
				, Py  - dstBetTr - heightTr /2
				, widthTr /2
				, heightTr /2 + dstBetTr
				, widthTr, heightTr, scaleX, scaleY, rotation);
		batch.draw(textureTrack
				, Px  - widthTr /2
				, Py  + dstBetTr - heightTr /2
				, widthTr /2, heightTr /2 - dstBetTr
				, widthTr, heightTr, scaleX, scaleY, rotation);
		batch.draw(textureTrank
				, Px - widthBody/2
				, Py- heightBody/2
				, widthBody/2
				, heightBody/2
				, widthBody, heightBody, scaleX, 1, rotation);
		batch.draw(textureGun, Px - widthGun /2 - actTire, Py - heightGun /2 , widthGun /2 + actTire , heightGun /2, widthGun, heightGun, scaleX, scaleY, rotation);
		batch.draw(textureGunPoss, Px + widthGun /2 - actTire, Py - heightPos /2 , -widthGun /2 + actTire , heightPos /2, widthPos, heightPos, scaleX, scaleY, rotation);
		if(SmokeFire) {
			TextureRegion AnimationF = animation.getKeyFrame(Dt_smoke);
			int WidthS = AnimationF.getRegionWidth(), HiegthS = AnimationF.getRegionHeight();
			batch.draw(AnimationF, Px + widthGun *2 - actTire -WidthS/2, Py -HiegthS/2, -widthGun *2 + actTire +WidthS/2, HiegthS/2, WidthS, HiegthS, 1, 1, rotation);
			if(animation.isAnimationFinished(Dt_smoke)) {
				SmokeFire = false;
				Dt_smoke=0;
			}
		}
    }

}
