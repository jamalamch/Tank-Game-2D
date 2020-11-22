package crach.stage.game.Screen.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;

public class ViewTank extends Actor {
	
    private  float DstBetTr ;
    private  float HieghtTr ,WidthTr;
    private  float HieghtGun ,WidthGun;
    private  float HieghtPos ,WidthPos;
    private  float heightBody ,widthBody; 
	
    private float rotation = 90;
    private float actTire,Dt_time,Dt_smoke,Dt_tire;
    private boolean SmokeFire;
    
	int ncolor = 0,nGunA=5 ,nGun_B=3 ,nHull = 4 ,nTranck = 1;

    
	float scaleT = 1;
	
    private final  Animation<TextureRegion> animation = Assest.Sprite_Effects_Exhaust_00;
    
	private TextureRegion Trank;
    private TextureRegion Track_1;
    private TextureRegion Track_2;
    private TextureRegion Gun;
    private TextureRegion GunPoss;
    private TextureRegion Track;
    
    private Drawable backDrawable ;
    public ViewTank() {
    	
		updateColor(CrachGame.getColorSelect());
        updateTrack(CrachGame.getTrackSelect());
        updateTrank(CrachGame.getHullSelect());
        updateGun(CrachGame.getGunBSelect());
        updateGunPoss(CrachGame.getGunASelect());
		backDrawable = Assest.Style.getDrawable("Main_Tank_Table");
    }  
    public void updateColor(int ncolor) {
    	this.ncolor = ncolor;
    	if(Gun != null) updateGun(nGun_B);
    	if(Trank != null) updateTrank(nHull);
    }
    public void updateGunPoss(int nGunA) {
    	this.nGunA=nGunA;
		GunPoss = Assest.Gun_A.get(nGunA);
	    HieghtPos = GunPoss.getRegionHeight()*scaleT ;WidthPos=GunPoss.getRegionWidth()*scaleT;
    }
    public void updateGun(int nGun_B) {
    	this.nGun_B=nGun_B;
		Gun = Assest.Gun_B.get(ncolor).get(nGun_B);
	    HieghtGun = Gun.getRegionHeight()*scaleT ;WidthGun=Gun.getRegionWidth()*scaleT;
    }    
    public void updateTrank(int nHull) {
    	this.nHull = nHull;
    	Trank = Assest.Hull.get(ncolor).get(nHull);
	    heightBody = Trank.getRegionHeight()*scaleT ;widthBody =Trank.getRegionWidth()*scaleT;
	    if(heightBody < (90*scaleT*2-HieghtTr)) {
	    	widthBody = widthBody*((90*scaleT*2-HieghtTr)/heightBody);
	    	heightBody=(90*scaleT*2-HieghtTr);
	    	DstBetTr=85*scaleT;
	    }
	    else {
	    	DstBetTr=90*scaleT;
	    }
    }
    public void updateTrack(int nTranck) {
    	this.nTranck = nTranck;
		Track_1 = Assest.Track_A.get(nTranck);
		Track_2 =  Assest.Track_B.get(nTranck);
		Track = Track_1;
	    HieghtTr = Track_1.getRegionHeight()*scaleT ;WidthTr=Track_1.getRegionWidth()*scaleT;
    }
    @Override
    public void act(float delta) {
    	if(isVisible()) {
	     actTire = (actTire <= 0) ? 0 : actTire - 10*delta;

			Dt_time +=delta;
			if(Dt_time>0.2f) {
			    Track = (Track == Track_1)?Track_2 : Track_1;
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
		
		batch.draw(Track
				, Px  - WidthTr/2
				, Py  -DstBetTr - HieghtTr/2
				, WidthTr/2
				, HieghtTr/2 +DstBetTr  
				,WidthTr, HieghtTr, scaleX, scaleY, rotation);
		batch.draw(Track
				, Px  - WidthTr/2
				, Py  +DstBetTr - HieghtTr/2
				, WidthTr/2, HieghtTr/2 -DstBetTr
				,WidthTr, HieghtTr, scaleX, scaleY, rotation);
		batch.draw(Trank
				, Px - widthBody/2
				, Py- heightBody/2
				, widthBody/2
				, heightBody/2
				, widthBody, heightBody, scaleX, 1, rotation);
		batch.draw(Gun    , Px - WidthGun/2 - actTire, Py -HieghtGun/2 , WidthGun/2 + actTire , HieghtGun/2, WidthGun, HieghtGun, scaleX, scaleY, rotation);
		batch.draw(GunPoss, Px + WidthGun/2 - actTire, Py -HieghtPos/2 , -WidthGun/2 + actTire , HieghtPos/2, WidthPos, HieghtPos, scaleX, scaleY, rotation);
		if(SmokeFire) {
			TextureRegion AnimationF = animation.getKeyFrame(Dt_smoke);
			int WidthS = AnimationF.getRegionWidth(), HiegthS = AnimationF.getRegionHeight();
			batch.draw(AnimationF, Px + WidthGun*2 - actTire -WidthS/2, Py -HiegthS/2, -WidthGun*2 + actTire +WidthS/2, HiegthS/2, WidthS, HiegthS, 1, 1, rotation);
			if(animation.isAnimationFinished(Dt_smoke)) {
				SmokeFire = false;
				Dt_smoke=0;
			}
		}
    }

}
