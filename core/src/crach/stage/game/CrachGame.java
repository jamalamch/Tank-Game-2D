package crach.stage.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;

import crach.stage.game.screen.Loading;
import crach.stage.game.screen.MenuUI;
import crach.stage.game.screen.ScreenGame;
import crach.stage.game.windows.Hanger;
import crach.stage.game.windows.Hanger.TypeItem;
import de.golfgl.gdxgamesvcs.GameServiceException;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;

public class CrachGame extends Game implements IGameServiceListener {


	private static CrachGame GdxGame ;
	
	private static  Preferences prefs;

	private static IActivityRequestHandler iActivityRequestHandler;
	private static IGameServiceClient gsClient;
	private static DownloadFiles downloadFiles;

	public final static float Width = 1200;
	public final static float Height= 700;
	
	public static final float PPM = 10;
	public static float RATA ;
	
	public final static String Langues[]= {"English","Arabic"} ;
	//,"France" comain soon
	public static String Langue;
	public static String Font ;
    
	public static final short FRENDS_BIT = 1<<0;
	public static final short ENIMY_BIT = 1<<1;
	public static final short LAMAS_BIT = 1<<2;
	public static final short NOTHING_BIT = 1<<3;
	public static final short OBJECT_BIT = 1<<4;
	public static final short PLACE_BOX = 1<<5;
	public static final short ZONE_BIT = 1<<6;
	public static final short CRYSTAL_BIT = 1<<7;
	public static final short FIRE_BIT = 1<<8;
	public static final short DOOR_BIT = 1<<9;
	public static final short PLACE_BIT = 1<<10;
	public static final short BOMB_BIT = 1<<11;
	public static final short EXPLOSION_BIT = 1<<12;
	public static final short LIGHT_BIT = 1<<13;
	public static final short CRACH_BIT = 1<<14;


	
	private static MenuUI menuUi;
		
	private static String Id;
	private static boolean music ,sfx ,notific,vibr,noAds;
	private static float nVmusic,nVsfx; 
	private static float hoursPlayed;
	private static int coin, diamound, exper, life, hp,bombs, nive,adsWatchd,enimykill,matchWinne,nPlayMulti,nStar;
    private static Double deltaTimeSeconds ;
    private static final long timeStar = System.currentTimeMillis();

    private static boolean isload;

	private static boolean[] bonus = new boolean[20];
	private static int[][] stage ;
	private static int[][] matchfoot;
	private static float[] XPBoost = new float[3];
	private static boolean[] paidGun_A,paidGun_B, paidHull,paidTrack;
	private static int selectGunA;
	private static int selectGunB;
	private static int selectHull;
	private static int selectTrack;
	private static int selectColor;

	private final static  int TIME_LIFE = 90;
	private static int  dt_timeLife = TIME_LIFE;
	public CrachGame(IActivityRequestHandler RequestHandler){
		iActivityRequestHandler = RequestHandler;
		RequestHandler.showAds(false);
	}
	public static MenuUI getMenuUi() {
		return menuUi;
	}
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("Data.prefs");
		Langue = prefs.getString("Langue",Langues[0]);
	  	RATA= getRata();
	  	isload = false;
        setScreen(new Loading());
        GdxGame = this;
        menuUi = null;
		//connect to Game servise
		try{
			gsClient.setListener(this);
			gsClient.resumeSession();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	private static void updateLife(){
		if(life < 10){
			dt_timeLife -= 1;
			if(dt_timeLife < 0) {
				addLife(+1);
				dt_timeLife = TIME_LIFE;
			}
			else
				menuUi.getUiHub().timerLife(dt_timeLife);
		}
	}
	private static void updateXP_Boost(){
		if(XPBoost[2] >0)
			XPBoost[2] -=1;
		else if(XPBoost[1] >0)
			XPBoost[1] -=1;
		else if(XPBoost[0] >0)
			XPBoost[0] -=1;
	}
	private static void timerInOneSecounds(){
		Timer.instance().scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				updateLife();
				updateXP_Boost();
			}
		},0,1);
	}
	public static void gotToMenu() {
		 Screen toDispose = GdxGame.getScreen();
		 if(menuUi == null) {
			 menuUi = new MenuUI();
			 timerInOneSecounds();
			 addLife((int)(deltaTimeSeconds/ TIME_LIFE));
		 }
		 GdxGame.setScreen(menuUi);
		 toDispose.dispose();
	}
	public float getRata(){
		float Ratawidth = (float)Gdx.graphics.getWidth()/Width;
		float Rataheight = (float)Gdx.graphics.getHeight()/Height;
			return  (Ratawidth < Rataheight)?Ratawidth : Rataheight;
	}
	public static  CrachGame getGdxGame() {
		return GdxGame;
	}
	public static ScreenGame getGdxScreen() {
		return (ScreenGame)GdxGame.getScreen();
	}

	private static int getNombeOfStageGame() {
		int strlen = 1;
		while(Gdx.files.internal("maps/stage/stage"+strlen+".tmx").exists()) {
			strlen++;
		}
		return strlen-1;
	}
	private static int getNombeOfStageFoot() {
		int strlen = 1;
		while(Gdx.files.internal("maps/stage/stageFoot"+strlen+".tmx").exists()) {
			strlen++;
		}
		return strlen-1;
	}
	
	public static void load(){
		        Id = prefs.getString("Id","Player01");
		       	exper = prefs.getInteger("Exper",0);
		     	nive = prefs.getInteger("Niveau",1);
		    	diamound = prefs.getInteger("Diamound",5);
		        coin = prefs.getInteger("Coin",100);
		        life = prefs.getInteger("Life",8);
		        bombs = prefs.getInteger("bombs",2);
		        hp = prefs.getInteger("Hp",2);
		      	music = prefs.getBoolean("music",true);  
		        sfx = prefs.getBoolean("sfx",true);  
		     	nVmusic = prefs.getFloat("nVmusic",1); 
				nVsfx = prefs.getFloat("nVsfx",1); 
		 		notific = prefs.getBoolean("notific",true);  
		 		vibr = prefs.getBoolean("vibr",true);
		 		adsWatchd= prefs.getInteger("ads",0);
		 		noAds = prefs.getBoolean("noAds",false);
		 		hoursPlayed = prefs.getFloat("hoursP",0);
		 		enimykill = prefs.getInteger("enimyk",0);
		 		matchWinne = prefs.getInteger("matchW",0);
		 		nPlayMulti = prefs.getInteger("nPlayMu",0);
		 		nStar = prefs.getInteger("nStar",0);
		 		
		 		selectColor = prefs.getInteger("sColor",0);
		 	    selectGunA = prefs.getInteger("sGunA",0);
		 	    selectGunB = prefs.getInteger("sGunB",0);
		 	    selectHull = prefs.getInteger("sHull",0);
		 	    selectTrack = prefs.getInteger("sTrack",0);
		 		
		 		
		 		deltaTimeSeconds = (timeStar - prefs.getLong("Time", timeStar))*0.001 ;

		        System.out.println("delta time "+deltaTimeSeconds);
		int i;     
        for(i=0; i< XPBoost.length; i++)
        	XPBoost[i] = prefs.getFloat("Xp"+i,0);
        
        for( i=1;i<=bonus.length;i++)
        	 bonus[i-1]=prefs.getBoolean("bonus"+i,false);
        
        stage = new int[getNombeOfStageGame()][2];
        matchfoot = new int[getNombeOfStageFoot()][2];
        for( i=0;i<stage.length;i++) {
    		 stage[i][0]=prefs.getInteger("stage"+i,0); 
    		 stage[i][1]=prefs.getInteger("nstar"+i,0); 
        }
		for(i=0; i< matchfoot.length; i++) {
			matchfoot[i][0]=prefs.getInteger("matchfoot"+i,0);
			matchfoot[i][1]=prefs.getInteger("nstarfoot"+i,0);
		}
        paidGun_A=new boolean[Hanger.sizeByItems(TypeItem.Gun_A)];
        paidGun_B =new boolean[Hanger.sizeByItems(TypeItem.Gun_B)];
        paidHull =new boolean[Hanger.sizeByItems(TypeItem.Hull)];
        paidTrack = new boolean[Hanger.sizeByItems(TypeItem.Track)];
        
    	paidGun_A[0]=true;
    	paidGun_B[0]=true;
    	paidHull[0]=true;
    	paidTrack[0]=true;

        for( i=1;i<paidGun_A.length;i++)
        	paidGun_A[i]=prefs.getBoolean("Gun_A"+i,false);
        for( i=1;i<paidGun_B.length;i++)
        	paidGun_B[i]=prefs.getBoolean("Gun_B"+i,false);
        for( i=1;i<paidHull.length;i++)
        	paidHull[i]=prefs.getBoolean("Hull"+i,false);
        for( i=1;i<paidTrack.length;i++)
        	paidTrack[i]=prefs.getBoolean("Track"+i,false);
        isload = true;
	}
	public static void update(){
	    if(!isload)
	        return;
		prefs.clear();
		prefs.putString("Id",Id);
		prefs.putString("Langue",Langue);
		prefs.putInteger("Exper", exper);
		prefs.putInteger("Niveau", nive);
		prefs.putInteger("Diamound", diamound);
		prefs.putInteger("Coin", coin);
		prefs.putInteger("Life", life);
		prefs.putInteger("bombs",bombs);
        prefs.putInteger("Hp", hp);
		prefs.putBoolean("music", music);
		prefs.putBoolean("sfx", sfx);
		prefs.putBoolean("notific",notific);
		prefs.putBoolean("vibr",vibr);
		prefs.putFloat("nVmusic", nVmusic);
		prefs.putFloat("nVsfx", nVsfx);
 		prefs.putInteger("ads",adsWatchd);
 		prefs.putBoolean("noAds",noAds);
 		
 		prefs.putInteger("sColor",selectColor);
 	    prefs.putInteger("sGunA",selectGunA);
 	    prefs.putInteger("sGunB",selectGunB);
 	    prefs.putInteger("sHull",selectHull);
 	    prefs.putInteger("sTrack",selectTrack);
 		
 		hoursPlayed =(float) (hoursPlayed+(( System.currentTimeMillis() - timeStar)*0.001)/3600);
 		prefs.putFloat("hoursP",hoursPlayed);
 		prefs.putInteger("enimyk",enimykill);
 		prefs.putInteger("matchW",matchWinne);
 		prefs.putInteger("nPlayMu",nPlayMulti);
 		prefs.putInteger("nStar",nStar);
 		prefs.putLong("Time", System.currentTimeMillis());
 		
 		int i;
        for(i=0; i< XPBoost.length; i++)
        	if(XPBoost[i] >0) prefs.putFloat("Xp"+i,(XPBoost[i]>0)? XPBoost[i]:0);
        for(i=1;i<=bonus.length;i++)
       	 	if(bonus[i-1]) prefs.putBoolean("bonus"+i,bonus[i-1]);
        for(i=0;i<stage.length;i++)
        	if(stage[i][0] != 0) { prefs.putInteger("stage"+i,stage[i][0]);
        						   prefs.putInteger("nstar"+i,stage[i][1]);
        	}
		for(i=0; i< matchfoot.length; i++)
			if(matchfoot[i][0] != 0) { prefs.putInteger("matchfoot"+i, matchfoot[i][0]);
										prefs.putInteger("nstarfoot"+i, matchfoot[i][1]);
			}
        for(i=1;i<paidGun_A.length;i++)
        	if(paidGun_A[i]) prefs.putBoolean("Gun_A"+i,paidGun_A[i]);
        for(i=1;i<paidGun_B.length;i++)
        	if(paidGun_B[i]) prefs.putBoolean("Gun_B"+i,paidGun_B[i]);
        for(i=1;i<paidHull.length;i++)
        	if(paidHull[i]) prefs.putBoolean("Hull"+i,paidHull[i]);
        for(i=1;i<paidTrack.length;i++)
        	if(paidTrack[i]) prefs.putBoolean("Track"+i,paidTrack[i]);


		prefs.flush();
	}
	//.................... Setting...........................//
	public static String getLangue() {
		return Langue;
	}
	public static void setLangue(String langue) {
		Langue = langue;
	}
	public static String getId() {
		return Id;
	}
	public static void setId(String id) {
		Id = id;
	}
	public static boolean isMusic() {
		return music;
	}
	public static void setMusic(boolean music) {
		CrachGame.music = music;
		Assets.updateMusic(music);
	}
	public static boolean isSfx() {
		return sfx;
	}
	public static void setSfx(boolean sfx) {
		CrachGame.sfx = sfx;
	}
	public static boolean isNotific() {
		return notific;
	}
	public static void setNotific(boolean notific) {
		CrachGame.notific = notific;
	}
	public static boolean isVibr() {
		return vibr;
	}
	public static void setVibr(boolean vibr) {
		CrachGame.vibr = vibr;
	}
	public static float getnVmusic() {
		return nVmusic;
	}
	public static void setnVmusic(float nVmusic) {
		CrachGame.nVmusic = nVmusic;
		Assets.updateMusic(nVmusic);
	}
	public static float getnVsfx() {
		return nVsfx;
	}
	public static void setnVsfx(float nVsfx) {
		CrachGame.nVsfx = nVsfx;
	}
	
	// ...........................Profile Coin...........................//
	public static int getCoin() {
		return coin;
	}
	public static void addCoin(int coin) {
		CrachGame.coin += coin ; menuUi.getUiHub().updateLCoin(CrachGame.coin);
	}
	public static int getDiamound() {
		return diamound;
	}
	public static void addDiamound(int diamound) {
		CrachGame.diamound += diamound ; menuUi.getUiHub().updateLDiamound(CrachGame.diamound);
	}
	public static int getExper() {
		return exper;
	}
	public static void addExper(int exper) {
		CrachGame.exper += exper;
		if(CrachGame.exper < Assets.valueNiveu.get(nive -1).getInt("max"))
			menuUi.getUiHub().updatePExper(CrachGame.exper);
		else{
			CrachGame.exper = CrachGame.exper - Assets.valueNiveu.get(nive -1).getInt("max");
			nive +=1;
			menuUi.getUiHub().updateNive(nive, CrachGame.exper);
		}
	}
	public static int getLife() {
		return life;
	}
	public static boolean addLife(int life) {
		CrachGame.life += life;
		CrachGame.life = (CrachGame.life >10)?10: CrachGame.life;
		menuUi.getUiHub().updateLife(CrachGame.life);
		if(CrachGame.life >= 0)
				return true;
		else{
			CrachGame.life = 0;
			return false;
		}
	}
	public static int getNive() {
		return nive;
	}
	public static void addNive() {
		nive += 1;
	}
	public static int getBombs() {
		return bombs;
	}
	public static void setBombs(int bombs) {
		CrachGame.bombs = bombs;
	}
	public static int getHp() {
		return hp;
	}
	public static void addHp(int hp) {
		CrachGame.hp += hp;
	}
	//.............................BonusColled...........................//
	public static boolean isBonusColled(int nbonus) {
		return bonus[nbonus-1];
	}
	public static void setBonusColled(int bonus) {
		CrachGame.bonus[bonus-1] = true;
	}
	//.............................Stage...........................//
	public static int getLengthStage() {
		return  stage.length;
	}
	public static int getStage(int nstage) {
		return stage[nstage][0];
	}
	public static void setStage(int nstage,int Score) {
		if(nstage < CrachGame.stage.length)
		if(Score > CrachGame.stage[nstage][0])
			CrachGame.stage[nstage][0] = Score;
	}
	public static int getStageNStar(int nstage) {
		return stage[nstage][1];
	}
	public static void setStageNStar(int nstage,int NStar) {
		if(nstage < CrachGame.stage.length)
			CrachGame.stage[nstage][1] = NStar;
	}

	public static int[][] getStage() {
		return stage;
	}
	public static int getLengthMatch() {
		return  matchfoot.length;
	}
	public static int getMatch(int nstage) {
		return matchfoot[nstage][0];
	}
	public static void setMatch(int nstage,int Score) {
		if(nstage < CrachGame.matchfoot.length)
			if(Score > CrachGame.matchfoot[nstage][0])
				CrachGame.matchfoot[nstage][0] = Score;
	}
	public static int getMatchNStar(int nstage) {
		return matchfoot[nstage][1];
	}
	public static void setMatchNStar(int nstage,int NStar) {
		if(nstage < CrachGame.matchfoot.length)
			CrachGame.matchfoot[nstage][1] = NStar;
	}
	public static int[][] getMatchfoot() {
		return matchfoot;
	}
	//.................................Shoping...........................// 
	public static boolean isPaidGun_A(int code) {
		return paidGun_A[code];
	}
	public static void setPaidGun_A(int code) {
		CrachGame.paidGun_A[code] = true;
	}
	public static boolean isPaidGun_B(int code) {
		return paidGun_B[code];
	}
	public static void setPaidGun_B(int code) {
		CrachGame.paidGun_B[code]=true;
	}
	public static boolean isPaidHull(int code) {
		return paidHull[code];
	}
	public static void setPaidHull(int code) {
		CrachGame.paidHull[code] = true;
	}
	public static boolean isPaidTrack(int code) {
		return paidTrack[code];
	}
	public static void setPaidTrack(int code) {
		CrachGame.paidTrack[code] = true;
	}
	public static int getXPBoost(){
		if(XPBoost[2] > 0)
			return 6;
		if(XPBoost[1] > 0)
			return 3;
		if(XPBoost[0] > 0)
			return 2;
		else
			return 1;
	}
	public static float getXP_Boost(int XpCode) {
		return XPBoost[XpCode];
	}
	public static void setXP_Boost(int xXCode,int Period) {
		XPBoost[xXCode] = Period;
	}
	//.................................add info..................................//
	public static int getAdsWatchd() {
		return adsWatchd;
	}
	public static void addAdsWatchd() {
		CrachGame.adsWatchd += 1;
	}
	public static boolean isNoAds() {
		return noAds;
	}
	public static void setNoAds(boolean noAds) {
		CrachGame.noAds = noAds;
	}
	public static float getHoursPlayed() {
		return hoursPlayed;
	}
	public static int getEnimykill() {
		return enimykill;
	}
	public static void addEnimykill(int enimykill) {
		CrachGame.enimykill += enimykill;
	}
	public static int getMatchWinne() {
		return matchWinne;
	}
	public static void addMatchWinne() {
		CrachGame.matchWinne += 1;
	}
	public static int getnPlayMulti() {
		return nPlayMulti;
	}
	public static void addnPlayMulti() {
		CrachGame.nPlayMulti += 1;
	}
	public static int getnStar() {
		return nStar;
	}
	public static void setnStar(int nStar) {
		CrachGame.nStar = nStar;
	}
	public static Double getDeltaTimeSeconds() {
		return deltaTimeSeconds;
	}	
	public static int getGunASelect() {
		return selectGunA;
	}
	public static int getGunBSelect() {
		return selectGunB;
	}
	public static int getHullSelect() {
		return selectHull;
	}
	public static int getTrackSelect() {
		return selectTrack;
	}
	public static int getColorSelect() {
		return selectColor;
	}
	public static void setGunASelect(int code) {
		 selectGunA  =  code;
	}
	public static void setGunBSelect(int code) {
		selectGunB  =  code;
	}
	public static void setHullSelect(int code) {
		selectHull =  code;
	}
	public static void setTrackSelect(int code) {
		selectTrack =  code;
	}
	public static void setColorSelect(int code) {
		 selectColor =  code;
	}

	public static void gsSignInOrOut() {
		if (gsClient.isSessionActive())
			gsClient.logOff();
		else {
			if (!gsClient.logIn())
				Gdx.app.error("Gdx game", "Cannot sign in: No credentials or session id given.");

			refreshStatus();
		}
	}
	private static void refreshStatus() {
		String newStatusText;
		String newUserText;
		if (gsClient.isSessionActive())
			newStatusText = "SESSION ACTIVE";
		else if (gsClient.isConnectionPending())
			newStatusText = "CONNECTING SESSION...";
		else
			newStatusText = "NO SESSION";
		Gdx.app.log("Gdx game",newStatusText);

		newUserText = gsClient.getPlayerDisplayName();
		if(newUserText != null) Id = newUserText;
		Gdx.app.log("Gdx game",newUserText != null ? newUserText : "(none)");
	}

	@Override
	public void gsOnSessionActive() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				refreshStatus();
			}
		});
	}

	@Override
	public void gsOnSessionInactive() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				refreshStatus();
			}
		});
	}

	@Override
	public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {
		Gdx.app.log("Gdx game",""+et.toString());
	}
	public static void submitScore(int Score){
        	gsClient.submitToLeaderboard("CgkIqNCw3o8IEAIQAQ",Score,null);
    }
    public static void showLeaderboard(){
		try {
			if(gsClient.isSessionActive())
				gsClient.showLeaderboards("CgkIqNCw3o8IEAIQAQ");
			else if (gsClient.logIn()) {
				prefs.putBoolean("gsConnect",true);
			}else {
				Gdx.app.error("Gdx game", "Cannot sign in: No credentials or session id given.");
				prefs.putBoolean("gsConnect",false);
			}
		} catch (GameServiceException e) {
			e.printStackTrace();
		}
	}

	public static IActivityRequestHandler getiActivityRequestHandler() {
		return iActivityRequestHandler;
	}
	public static IGameServiceClient getGsClient() {
		return gsClient;
	}

	public static DownloadFiles getDownloadFiles() {
		return downloadFiles;
	}

	public static void setGsClient(IGameServiceClient gsClient) {
		CrachGame.gsClient = gsClient;
	}
	public static void setDownloadFiles(DownloadFiles downloadFiles) {
		CrachGame.downloadFiles = downloadFiles;
	}

	@Override
	public void dispose() {
		Assets.dispose();
		super.dispose();
	}
}
