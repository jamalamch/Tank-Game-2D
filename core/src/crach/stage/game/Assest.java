package crach.stage.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import crach.stage.game.screen.PlayScreen;
import crach.stage.game.utils.ArFont;

public class Assest {

	public static AssetManager manager;
	public static MapsLoader mapsLoader;
	public static TextureAtlas SpriteGame;
	public static Skin Style;

	public static JsonValue DataGunA, DataGunB, DataHull, DataTrack, DataEnimy, DataShell;
	public static JsonValue StringMenu, StringSetting, StringModeGame, Stringshop, StringHanger, StringBonus,
			StringSelectStage, StringHelp, StringGameOnline, StringGameLocale, StringDialog, StringCredits;
	public static JsonValue ValueBonus,ValueNive;
	
	public static BitmapFont font;

	public static TextureRegion backgroundPlan1;
	public static TextureRegion backgroundPlan2;

	public static Animation<TextureRegion> Bomb_Explosion_A;
	public static Animation<TextureRegion> Bomb_Explosion_B;
	public static Animation<TextureRegion> Bomb_Explosion_C;
	public static Animation<TextureRegion> Bomb_Idle_A;
	public static Animation<TextureRegion> Bomb_Idle_B;
	public static Animation<TextureRegion> Bomb_Idle_C;
	public static Animation<TextureRegion> Bomb_Triggering_A;
	public static Animation<TextureRegion> Bomb_Triggering_B;
	public static Animation<TextureRegion> Bomb_Triggering_C;
	public static Animation<TextureRegion> Bomb_cannon_firing;

	public static TextureRegion Light1;
	public static TextureRegion Light2;
	public static TextureRegion Light3;

	public static Animation<TextureRegion> arrow;
	public static Animation<TextureRegion> arrow_a;
	public static Animation<TextureRegion> aura_final;
	public static Animation<TextureRegion> aura;
	public static Animation<TextureRegion> blueish_flame;
	public static Animation<TextureRegion> blueish_flame_up;
	public static Animation<TextureRegion> blueish_smoke;
	public static Animation<TextureRegion> blueish_smoke_up;
	public static Animation<TextureRegion> crystal2;
	public static Animation<TextureRegion> fire_smoke;
	public static Animation<TextureRegion> fire_smoke_up;
	public static Animation<TextureRegion> fire;
	public static Animation<TextureRegion> fire_up;
	public static Animation<TextureRegion> impact_asteroid_a;
	public static Animation<TextureRegion> impact_asteroid_b;
	public static Animation<TextureRegion> impact_asteroid_c;
	public static Animation<TextureRegion> magic;
	public static Animation<TextureRegion> magic_up;
	public static Animation<TextureRegion> muzzle;
	public static Animation<TextureRegion> ship_death;
	public static Animation<TextureRegion> smoke;
	public static Animation<TextureRegion> stara;
	public static Animation<TextureRegion> starb;
	public static Animation<TextureRegion> starc;

	public static TextureRegion Kill_Icon;
	public static Animation<TextureRegion> Sprite_Effects_Exhaust_00;
	public static Animation<TextureRegion> Sprite_Effects_Exhaust_01;
	public static Animation<TextureRegion> Sprite_Effects_Exhaust_02;
	public static Animation<TextureRegion> Sprite_Effects_Explosion;
	public static Animation<TextureRegion> Sprite_Fire_Shots_Flame;
	public static Animation<TextureRegion> Sprite_Fire_Shots_Impact_A;
	public static Animation<TextureRegion> Sprite_Fire_Shots_Impact_B;
	public static Animation<TextureRegion> Sprite_Fire_Shots_Shot_A;
	public static Animation<TextureRegion> Sprite_Fire_Shots_Shot_B;

	public static Array<AtlasRegion> Enimycrach, Enimycrach2;

	public static TextureRegion comet_a;
	public static Animation<TextureRegion> comet_animation_a;
	public static TextureRegion comet_b;
	public static Animation<TextureRegion> comet_animation_b;

	public static Array<AtlasRegion> Gun_A;
	public static Array<Array<AtlasRegion>> Gun_B;
	public static Array<Array<AtlasRegion>> Hull;

	public static TextureRegion Tire_Track1;
	public static TextureRegion Tire_Track2;
	public static Array<AtlasRegion> Track_A;
	public static Array<AtlasRegion> Track_B;

	public static Animation<TextureRegion> crystal;
	public static Animation<TextureRegion> gold;
	public static Animation<TextureRegion> pwr_max;
	public static Animation<TextureRegion> pwr_up;
	public static Animation<TextureRegion> upgrade;
	public static Animation<TextureRegion> repair;


	public static TextureRegion Granade_Shell;
	public static TextureRegion Heavy_Shell;
	public static TextureRegion Laser;
	public static TextureRegion Light_Shell;
	public static TextureRegion Medium_Shell;
	public static TextureRegion Plasma;
	public static TextureRegion Shotgun_Shells;
	public static TextureRegion Sniper_Shell;

	public static Array<AtlasRegion> Blast_Trail;
	public static TextureRegion Border_A;
	public static TextureRegion Border_B;
	public static TextureRegion Border_C;
	public static TextureRegion Container_A;
	public static TextureRegion Container_B;
	public static TextureRegion Container_C;
	public static TextureRegion Container_D;

	public static Array<AtlasRegion> Cactus;
	public static Array<AtlasRegion> Crate;
	public static Array<AtlasRegion> Palm;
	public static Array<AtlasRegion> Tire;
	public static Array<AtlasRegion> Tree;

	public static Array<AtlasRegion> box;

	public static TextureRegion Artifact;
	public static Animation<TextureRegion> Ball;
	public static Array<AtlasRegion> Football_Goals;
	public static Array<AtlasRegion> Barell;

	public static TextureRegion boxActive;
	public static TextureRegion boxNoActive;
	public static TextureRegion DoorClose;
	public static TextureRegion DoorExit;
	public static TextureRegion DoorOpen;
	public static TextureRegion DoorSimple;
	public static TextureRegion Dot_A;
	public static TextureRegion Dot_B;
	public static TextureRegion Dot_C;
	public static TextureRegion ExitNo;
	public static TextureRegion ExitYes;
	public static TextureRegion Flag_A;
	public static TextureRegion Flag_B;
	public static TextureRegion Lax;
	public static TextureRegion Platform;
	public static TextureRegion PlatformActive0;
	public static TextureRegion PlatformActive1;
	
	
	
	
	public static void load() {
		manager = new AssetManager();
		manager.load("Sprite/SpriteGame.atlas", TextureAtlas.class);
		manager.load("skins/GameStyle.json", Skin.class);
		manager.load("skins/jf_flat.fnt",BitmapFont.class);
		addSound();
	}

	public static void managerSprit() {
		SpriteGame =  manager.get("Sprite/SpriteGame.atlas", TextureAtlas.class);
		loadbackground();
		loadBombs();
		loadEffects();
		loadEnimy();
		loadHulls();
		loadShell();
		loadSpriteTitle();
		loadTracks();
		loadpickup();
	}

	public static void finirLoding() {
		Style = manager.get("skins/GameStyle.json", Skin.class);
		JsonReader jsonReader = new JsonReader();
		JsonValue ValueData = jsonReader.parse(Gdx.files.internal("CrachType.json"));

		DataGunA = ValueData.get("GunA");
		DataGunB = ValueData.get("GunB");
		DataHull = ValueData.get("Hull");
		DataTrack = ValueData.get("Track");

		DataEnimy = ValueData.get("crach");
		DataShell = ValueData.get("Shell");

		JsonValue ValueLangue = jsonReader.parse(Gdx.files.internal("traduction.json")).get(CrachGame.Langue);
		StringMenu = ValueLangue.get("Menu");
		StringSetting = ValueLangue.get("Setting");
		StringModeGame = ValueLangue.get("ModeGame");
		Stringshop = ValueLangue.get("shop");
		StringHanger = ValueLangue.get("Hanger");
		StringBonus = ValueLangue.get("Bonus");
		StringSelectStage = ValueLangue.get("Select_stage");
		StringHelp = ValueLangue.get("Help");
		StringGameOnline = ValueLangue.get("GameOnLine");
		StringGameLocale = ValueLangue.get("GameLocale");
		StringDialog = ValueLangue.get("dialoge");
		StringCredits = ValueLangue.get("credits");
		if(CrachGame.Langue.equals("Arabic")){
			for(int i = 0;i<ValueLangue.size;i++){
				JsonValue values = ValueLangue.get(i);
				for(int j=0;j<values.size;j++)
					values.get(j).set(ArFont.getText(values.getString(j)));
			}


		}

		ValueBonus = jsonReader.parse(Gdx.files.internal("BonusValue.json"));
		ValueNive = jsonReader.parse(Gdx.files.internal("ValueNive.json"));
		
		font = manager.get("skins/jf_flat.fnt",BitmapFont.class);
		font.getData().setScale(0.2f);
		
		mapsLoader = new MapsLoader();
	}

	private static void loadbackground() {
		String Folder = "background/";
		backgroundPlan1 = SpriteGame.findRegion(Folder + "planet-1");
		backgroundPlan2 = SpriteGame.findRegion(Folder + "planet-2");
	}

	private static void loadBombs() {
		String Folder = "Bombs/";
		float frameDuration = 0.16f;
		Bomb_Explosion_A = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Explosion_A"));
		Bomb_Explosion_B = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Explosion_B"));
		Bomb_Explosion_C = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Explosion_C"));
		Bomb_Idle_A = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "Bomb_Idle_A"));
		Bomb_Idle_B = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "Bomb_Idle_B"));
		Bomb_Idle_C = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "Bomb_Idle_C"));
		Bomb_Triggering_A = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Triggering_A"));
		Bomb_Triggering_B = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Triggering_B"));
		Bomb_Triggering_C = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Bomb_Triggering_C"));
		
		Bomb_cannon_firing = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "cannon-firing")); 

		Light1 = SpriteGame.findRegion(Folder + "Light", 1);
		Light2 = SpriteGame.findRegion(Folder + "Light", 2);
		Light3 = SpriteGame.findRegion(Folder + "Light", 3);
	}

	private static void loadEffects() {
		String Folder = "Effects/";
		float frameDuration = 0.17f;

		arrow = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "arrow"));
		arrow_a = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "arrow_a"));
		aura_final = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "aura_final"));
		aura = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "aura"));
		blueish_flame = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "blueish_flame"));
		blueish_flame_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "blueish_flame_up"));
		blueish_smoke = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "blueish_smoke"));
		blueish_smoke_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "blueish_smoke_up"));
		fire_smoke = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "fire+smoke"));
		fire_smoke_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "fire_smoke_up"));
		fire = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "fire"));
		fire_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "fire_up"));
		impact_asteroid_a = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "ImpactA/impact-asteroid-a"));
		impact_asteroid_b = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "ImpactB/impact-asteroid-b"));
		impact_asteroid_c = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "ImpactB/impact-asteroid-b"));
		magic = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "magic"));
		magic_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "magic_up"));
		muzzle = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "muzzle"));
		ship_death = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "ship-death"));
		smoke = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "smoke"));
		stara = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "stara"));
		starb = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "starb"));
		starc = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "starc"));
		
		
		Kill_Icon = SpriteGame.findRegion(Folder + "Kill_Icon");
		Sprite_Effects_Exhaust_00 = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_00"));
		Sprite_Effects_Exhaust_01 = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_01"));
		Sprite_Effects_Exhaust_02 = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_02"));
		Sprite_Effects_Explosion = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Effects_Explosion"));
		Sprite_Fire_Shots_Flame = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Flame"));
		Sprite_Fire_Shots_Impact_A = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Impact_A"));
		Sprite_Fire_Shots_Impact_B = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Impact_B"));
		Sprite_Fire_Shots_Shot_A = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Shot_A"));
		Sprite_Fire_Shots_Shot_B = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Shot_B"));

	}
	private static void loadpickup() {
		String Folder = "pickups/";
		float frameDuration = 0.18f;
		crystal = new Animation<TextureRegion>(frameDuration,
				SpriteGame.findRegion(Folder + "crystal0").split(16, 16)[0]);
		gold = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "gold"));
		crystal2 = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "crystal"));
		pwr_max = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "pwr_max"));
		upgrade = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "upgrade"));
		repair = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "repair"));
		pwr_up = new Animation<TextureRegion>(frameDuration, SpriteGame.findRegions(Folder + "pwr_up"));
	}
	
	private static void loadEnimy() {
		String Folder = "Enimy/";
		Enimycrach = SpriteGame.findRegions(Folder + "crach");
		Enimycrach2 = SpriteGame.findRegions(Folder + "crach2");

		comet_a = SpriteGame.findRegion(Folder + "comet-full-a");
		comet_animation_a = new Animation<TextureRegion>(1f, SpriteGame.findRegions(Folder + "comet-a"));

		comet_b = SpriteGame.findRegion(Folder + "comet-full-b");
		comet_animation_b = new Animation<TextureRegion>(1f, SpriteGame.findRegions(Folder + "comet-b"));
	}

	private static void loadHulls() {
		Gun_A = SpriteGame.findRegions("Hulls_Color_A/Gun_A");
		Gun_B = new Array<Array<AtlasRegion>>();
		Gun_B.add(SpriteGame.findRegions("Hulls_Color_A/Gun_B"), SpriteGame.findRegions("Hulls_Color_B/Gun_B"),
				SpriteGame.findRegions("Hulls_Color_C/Gun_B"), SpriteGame.findRegions("Hulls_Color_D/Gun_B"));
		Hull = new Array<Array<AtlasRegion>>();
		Hull.add(SpriteGame.findRegions("Hulls_Color_A/Hull"), SpriteGame.findRegions("Hulls_Color_B/Hull"),
				SpriteGame.findRegions("Hulls_Color_C/Hull"), SpriteGame.findRegions("Hulls_Color_D/Hull"));
	}

	private static void loadTracks() {
		String Folder = "Tracks/";
		Tire_Track1 = SpriteGame.findRegion(Folder + "Tire_Track", 1);
		Tire_Track2 = SpriteGame.findRegion(Folder + "Tire_Track", 2);

		Track_A = SpriteGame.findRegions(Folder + "Track_A");
		Track_B = SpriteGame.findRegions(Folder + "Track_B");
	}

	private static void loadShell() {
		String Folder = "Shell/";

		Granade_Shell = SpriteGame.findRegion(Folder + "Granade_Shell");
		Heavy_Shell = SpriteGame.findRegion(Folder + "Heavy_Shell");
		Laser = SpriteGame.findRegion(Folder + "Laser");
		Light_Shell = SpriteGame.findRegion(Folder + "Light_Shell");
		Medium_Shell = SpriteGame.findRegion(Folder + "Medium_Shell");
		Plasma = SpriteGame.findRegion(Folder + "Plasma");
		Shotgun_Shells = SpriteGame.findRegion(Folder + "Shotgun_Shells");
		Sniper_Shell = SpriteGame.findRegion(Folder + "Sniper_Shell");
	}

	private static void loadSpriteTitle() {
		String Folder = "SpriteTitle/";
		
		
		Blast_Trail = SpriteGame.findRegions(Folder + "Blast_Trail");
		Border_A = SpriteGame.findRegion(Folder + "Border_A");
		Border_B = SpriteGame.findRegion(Folder + "Border_B");
		Border_C = SpriteGame.findRegion(Folder + "Border_C");
		Container_A = SpriteGame.findRegion(Folder + "Container_A");
		Container_B = SpriteGame.findRegion(Folder + "Container_B");
		Container_C = SpriteGame.findRegion(Folder + "Container_C");
		Container_D = SpriteGame.findRegion(Folder + "Container_D");

		
		Cactus = SpriteGame.findRegions(Folder + "Cactus");
		Crate = SpriteGame.findRegions(Folder + "Crate");
		Palm = SpriteGame.findRegions(Folder + "Palm");
		Tire = SpriteGame.findRegions(Folder + "Tire");
		Tree= SpriteGame.findRegions(Folder + "Tree"); 
		
		box = SpriteGame.findRegions(Folder + "Box"); 
		
		Artifact = SpriteGame.findRegion(Folder + "Artifact");
		Ball = new Animation<TextureRegion>(0.05f,SpriteGame.findRegions(Folder + "Ball"));
		Football_Goals = new Array<AtlasRegion>();
		Football_Goals.add(SpriteGame.findRegion(Folder + "Football_Goals_A"),SpriteGame.findRegion(Folder + "Football_Goals_B"));
		Barell = SpriteGame.findRegions(Folder + "Barell");
		boxActive = SpriteGame.findRegion(Folder + "boxActive");
		boxNoActive = SpriteGame.findRegion(Folder + "boxNoActive");
		DoorClose = SpriteGame.findRegion(Folder + "DoorClose");
		DoorExit = SpriteGame.findRegion(Folder + "DoorExit");
		DoorOpen = SpriteGame.findRegion(Folder + "DoorOpen");
		DoorSimple = SpriteGame.findRegion(Folder + "DoorSimple");
		Dot_A = SpriteGame.findRegion(Folder + "Dot_A");
		Dot_B = SpriteGame.findRegion(Folder + "Dot_B");
		Dot_C = SpriteGame.findRegion(Folder + "Dot_C");
		ExitNo = SpriteGame.findRegion(Folder + "ExitNo");
		ExitYes = SpriteGame.findRegion(Folder + "ExitYes");
		Flag_A = SpriteGame.findRegion(Folder + "Flag_A");
		Flag_B = SpriteGame.findRegion(Folder + "Flag_B");
		Lax = SpriteGame.findRegion(Folder + "Lax");
		Platform = SpriteGame.findRegion(Folder + "Platform");
		PlatformActive0 = SpriteGame.findRegion(Folder + "PlatformActive0");
		PlatformActive1 = SpriteGame.findRegion(Folder + "PlatformActive1");
	}
	public static String getPathStage(PlayScreen.ModeGame modegame){
		String path = null;
		switch (modegame){
			case simple:
				path = "stage";
				break;
			case football:
				path = "stageFoot";
				break;
			case deathmatch:
				path = "stageDeath";
				break;
		}
		return path;
	}
	public static TiledMap loadMaps(int i, PlayScreen.ModeGame modegame) {
			return mapsLoader.load(getPathStage(modegame)+(i + 1) + ".tmx");
	}
	public static TiledMap loadMaps(FileHandle tmxFile,PlayScreen.ModeGame modeGame){
		return mapsLoader.load(getPathStage(modeGame),tmxFile);
	}
	public static TiledMap loadMaps(FileHandle tmxFile){
		return mapsLoader.load(tmxFile);
	}

	// Sound and music
	public static UiMusic musicMenu1, musicMenu2;
	public static UiSound musicVector ;
	// sound menu ui
	public static UiSound buttonClick1, buttonClick2
						, checkOnClick, checkOfClick
						, soundfialed, soundErrour
							,soundCrystal, soundPurchase
							, soundBonus1, soundBonus2, soundBonus3;
	
	public static Array<UiSound> soundCoins = new Array<UiSound>(2);
	public static Array<UiSound> soundWatchAds = new Array<UiSound>(7);
	//sound of Game mode
	public static UiSound blue_attack, red_attack, Gun_Motor
			, enemyhit, explode_Bomb, explos_Barell_secret
			, explosion_Enimy, explosion_Player
			, impact_cannon
			, impact_Object_wall
			, destroy_circular_blade
			, movement
		//	Tank_reload,
			,impact_Shell
			,soundDoor, upgradSound
			, playerattack, hit_crate_heavy
			, hit_Enimy_medium, playerhit;
	public static Array<UiSound> Effects_flam = new Array<UiSound>(4);
	
	public static void addSound() {
		boolean SoundMenu = false, SoundGame = false;
		if(Gdx.app.getType() == ApplicationType.Android )
			SoundMenu =SoundGame= true;
		musicMenu1 = new UiMusic("music/music_Techno-Gameplay.mp3",SoundMenu);
		musicMenu2 = new UiMusic("music/music_Self-Destruct-Sequence.mp3",SoundMenu);
		musicVector = new UiSound("sfx/fanfare.mp3",SoundMenu);
	
		buttonClick1 = new UiSound("sfx/button_click0.ogg",SoundMenu);
		buttonClick2 = new UiSound("sfx/button_click1.ogg",SoundMenu);
		checkOfClick = new UiSound("sfx/Button_check_of.wav",SoundMenu);
		checkOnClick = new UiSound("sfx/Button_check_on.wav",SoundMenu);
		soundfialed = new UiSound("sfx/click_failed.wav",SoundMenu);
		soundErrour = new UiSound("sfx/soundTooHigh.mp3",SoundMenu);
		soundBonus1 = new UiSound("sfx/bonus_finir.mp3",SoundMenu);
		soundBonus2 = new UiSound("sfx/coins_bonus0.wav",SoundMenu);
		soundBonus3 = new UiSound("sfx/height_coin_bonus0.wav",SoundMenu);
		soundCrystal = new UiSound("sfx/crystal_pickup.ogg",SoundMenu);
		soundPurchase = new UiSound("sfx/purchase.wav",SoundMenu);
		for (int i = 1; i < 8; i++)
			soundWatchAds.add(new UiSound("sfx/coin_tone" + i + ".wav",SoundMenu));
		soundCoins.add(new UiSound("sfx/coin_click0.wav",SoundMenu));
		soundCoins.add(new UiSound("sfx/coin_click1.wav",SoundMenu));

		
		blue_attack = new UiSound("sound/blue_attack.ogg",SoundGame);
		red_attack = new UiSound("sound/red_attack.ogg",SoundGame);	
		for (int i = 1; i < 5; i++)
			Effects_flam.add(new UiSound("sound/Effects_flam" + i + ".mp3",SoundGame));
		Gun_Motor =  new UiSound("sound/motor-loop.ogg",SoundGame);
		enemyhit = new UiSound("sound/enemyhit.mp3",SoundGame);
		explode_Bomb = new UiSound("sound/explode.ogg",SoundGame);
		explos_Barell_secret = new UiSound("sound/explos_secret.wav",SoundGame);
		explosion_Enimy = new UiSound("sound/explosion0.wav",SoundGame);
		explosion_Player = new UiSound("sound/explosion0_revival.wav",SoundGame);
		impact_cannon = new UiSound("sound/Impact_cannon.wav",SoundGame);
		impact_Object_wall = new UiSound("sound/impact_wall.wav",SoundGame);
		destroy_circular_blade = new UiSound("sound/destroy_circular_blade.wav",SoundGame);
		movement = new UiSound("sound/movement.ogg",SoundGame);
		playerattack = new UiSound("sound/playerattack.mp3",SoundGame);
		hit_crate_heavy = new UiSound("sound/player-hit-heavy.mp3",SoundGame);
		hit_Enimy_medium = new UiSound("sound/player-hit-medium.mp3",SoundGame);
		playerhit = new UiSound("sound/playerhit.mp3",SoundGame);
		impact_Shell = new  UiSound("sound/player-hit-medium.mp3",SoundGame);
		soundDoor = new UiSound("sound/OpenDoor.wav",SoundGame);
		upgradSound = new UiSound("sound/upgrade.mp3",SoundGame);
	}

	public static void loadSound() {
			musicMenu1.get(); 
			musicMenu2.get();
			musicVector.get();
			
			buttonClick1.get(); 
			buttonClick2.get(); 
			checkOnClick.get(); 
			checkOfClick.get();
			soundfialed.get(); 
			soundErrour.get();
			soundCrystal.get(); 
			soundPurchase.get(); 
			soundBonus1.get(); 
			soundBonus2.get(); 
			soundBonus3.get();
			
			for (UiSound sound : soundWatchAds)
				sound.get();
			for(UiSound sound : soundCoins)
				sound.get();
			
			blue_attack.get();
			 red_attack.get(); 
			 for (UiSound sound : Effects_flam)
				 sound.get();
			 
			 Gun_Motor.get();
			 enemyhit.get();
			 explode_Bomb.get();
			 explos_Barell_secret.get();
			 explosion_Enimy.get();
			 explosion_Player .get();
			 impact_cannon .get();
			 impact_Object_wall .get();
			 destroy_circular_blade .get();
			 movement.get();
			 //Tank_reload.get();
			 impact_Shell.get();
			 soundDoor.get();
			 upgradSound .get();
			 playerattack.get();
			 hit_crate_heavy .get();
			 hit_Enimy_medium.get();
			 playerhit.get();
	}
	public static void updateMusic(boolean isplay) {
		Music music  = UiMusic.InPlayerStream;
		if(isplay && !music.isPlaying())
			music.play();
		else if(!isplay && music.isPlaying())
			music.pause();	
	}
	public static void updateMusic(float volume) {
		Music music  = UiMusic.InPlayerStream;
		music.setVolume(volume);
	}
	public static void pauseAllSound() {
		Array<Sound> AllSound = manager.getAll(Sound.class, new Array<Sound>());
		for(Sound S : AllSound)
			S.pause();
	}
	public static void resumeAllSound() {
		Array<Sound> AllSound = manager.getAll(Sound.class, new Array<Sound>());
		for(Sound S : AllSound)
			S.resume();
	}
	public static void stopAllSound() {
		Array<Sound> AllSound = manager.getAll(Sound.class, new Array<Sound>());
		for(Sound S : AllSound)
			S.stop();
	}
	
	public static  void dispose() {
		manager.clear();
		manager.dispose();
		mapsLoader.dispose();
	}
	
	public static class UiSound implements Sound{
		Sound sound ;
		String Urlsource;
		public UiSound(String Urlsource){
			this.Urlsource = Urlsource;
			load();
		}
		public UiSound(String Urlsource,boolean isGet){
			this.Urlsource = Urlsource;
			if(isGet) load();
		}
		private void load() {
			manager.load("audio/"+Urlsource, Sound.class);
		}
		private void get() {
			if(manager.contains("audio/"+Urlsource, Sound.class))
				this.sound = manager.get("audio/"+Urlsource, Sound.class);
			else this.sound = sound0;
			Urlsource = null;
		}
		@Override
		public long play() {
			if(CrachGame.isSfx())
				return sound.play(CrachGame.getnVsfx());
			return -1;
		}
		@Override
		public long play(float volume) {
			if(CrachGame.isSfx())
				return sound.play(volume*CrachGame.getnVsfx());
			return -1;
		}
		@Override
		public long play(float volume, float pitch, float pan) {
			if(CrachGame.isSfx())
				return sound.play(volume *CrachGame.getnVsfx(), pitch, pan);
			return -1;
		}
		@Override
		public long loop() {
			if(CrachGame.isSfx())
				return sound.loop(CrachGame.getnVsfx());
			return -1;
		}
		@Override
		public long loop(float volume) {
			if(CrachGame.isSfx())
				return sound.loop(volume*CrachGame.getnVsfx());
			return -1;
		}
		@Override
		public long loop(float volume, float pitch, float pan) {
			if(CrachGame.isSfx())
				return sound.loop(volume*CrachGame.getnVsfx(), pitch, pan);
			return -1;
		}
		@Override
		public void stop() {
			sound.stop();
		}
		@Override
		public void pause() {
			sound.pause();
		}
		@Override
		public void resume() {
			sound.resume();
		}
		@Override
		public void dispose() {
			sound.dispose();			
		}
		@Override
		public void stop(long soundId) {
			sound.stop(soundId);
		}
		@Override
		public void pause(long soundId) {
			sound.pause(soundId);
		}
		@Override
		public void resume(long soundId) {
			if(CrachGame.isSfx())
				sound.resume(soundId);
		}
		@Override
		public void setLooping(long soundId, boolean looping) {
			sound.setLooping(soundId, looping);
		}
		@Override
		public void setPitch(long soundId, float pitch) {
			sound.setPitch(soundId, pitch);
		}
		@Override
		public void setVolume(long soundId, float volume) {
			sound.setVolume(soundId, volume*CrachGame.getnVsfx());
		}
		@Override
		public void setPan(long soundId, float pan, float volume) {
			sound.setPan(soundId, pan, volume*CrachGame.getnVsfx());
		}
	}
	public static class UiMusic implements Music{
		static Music InPlayerStream;	
		Music music;
		String Urlsource ;
		public UiMusic(String Urlsource) {
			this.Urlsource=Urlsource;
			load();
		}
		public UiMusic(String Urlsource,boolean isLoad) {
			this.Urlsource=Urlsource;
			if(isLoad) load();
		}
		private void load() {
			manager.load("audio/"+Urlsource, Music.class);
		}
		private void get() {
			if(manager.contains("audio/"+Urlsource, Music.class))
				this.music = manager.get("audio/"+Urlsource, Music.class);
			else this.music = music0;
			Urlsource = null;
		}
		@Override
		public void play() {
			if(CrachGame.isMusic()) {
				music.play();
				music.setVolume(CrachGame.getnVmusic());
				if(InPlayerStream != null)
					if(InPlayerStream.isPlaying())
						InPlayerStream.stop();
			}
			InPlayerStream = music;
		}
		@Override
		public void pause() {	
			music.pause();
		}
		@Override
		public void stop() {
			music.stop();
		}
		@Override
		public boolean isPlaying() {
			return music.isPlaying();
		}
		@Override
		public void setLooping(boolean isLooping) {
			music.setLooping(isLooping);
		}
		@Override
		public boolean isLooping() {
			return false;
		}
		@Override
		public void setVolume(float volume) {
			music.setVolume(volume*CrachGame.getnVmusic());
		}
		@Override
		public float getVolume() {
			return music.getVolume();
		}
		@Override
		public void setPan(float pan, float volume) {
			music.setPan(pan, volume);
		}
		@Override
		public void setPosition(float position) {
			music.setPosition(position);
		}
		@Override
		public float getPosition() {
			return music.getPosition();
		}
		@Override
		public void dispose() {
			music.dispose();
		}
		@Override
		public void setOnCompletionListener(OnCompletionListener listener) {
			music.setOnCompletionListener(listener);
		}
	}
	private static Sound sound0 = new Sound() {
		public void stop(long soundId) {}
		public void stop() {}
		public void setVolume(long soundId, float volume) {}
		public void setPitch(long soundId, float pitch) {}
		public void setPan(long soundId, float pan, float volume) {}
		public void setLooping(long soundId, boolean looping) {}
		public void resume(long soundId) {}
		public void resume() {}
		public long play(float volume, float pitch, float pan) {return 0;}
		public long play(float volume) {return 0;}
		public long play() {return 0;}
		public void pause(long soundId) {}
		public void pause() {}
		public long loop(float volume, float pitch, float pan) {return 0;}
		public long loop(float volume) {return 0;}
		public long loop() {return 0;}
		public void dispose() {}
	};
	private static Music music0 = new Music() {
		public void stop() {}
		public void setVolume(float volume) {}
		public void setPosition(float position) {}
		public void setPan(float pan, float volume) {}
		public void setOnCompletionListener(OnCompletionListener listener) {}
		public void setLooping(boolean isLooping) {}
		public void play() {}
		public void pause() {}
		public boolean isPlaying() {return false;}
		public boolean isLooping() {return false;}
		public float getVolume() {return 0;}
		public float getPosition() {return 0;}
		public void dispose() {}
	};
}