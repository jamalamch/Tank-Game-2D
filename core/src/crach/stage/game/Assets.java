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

public class Assets {

	public static AssetManager manager;
	public static MapsLoader mapsLoader;
	public static TextureAtlas textureAtlasSpriteGame;
	public static Skin skinStyle;

	public static JsonValue jsonDataGunA, jsonDataGunB, jsonDataHull, jsonDataTrack, jsonDataEnimy, jsonDataShell;

	public static JsonValue jsonStringMenu, jsonStringSetting, jsonStringModeGame, jsonStringshop, jsonStringHanger,
							jsonStringBonus, jsonStringSelectStage, jsonStringHelp, jsonStringGameOnline,
							jsonStringGameLocale, jsonStringDialog, jsonStringCredits;
	public static JsonValue valueBonus, valueNiveu;
	
	public static BitmapFont font;

	public static TextureRegion backgroundPlan1;
	public static TextureRegion backgroundPlan2;

	public static Animation<TextureRegion> animationBombExplosionA;
	public static Animation<TextureRegion> animationBombExplosionB;
	public static Animation<TextureRegion> animationBombExplosionC;
	public static Animation<TextureRegion> animationBombIdleA;
	public static Animation<TextureRegion> animationBombIdleB;
	public static Animation<TextureRegion> animationBombIdleC;
	public static Animation<TextureRegion> animationBombTriggeringA;
	public static Animation<TextureRegion> animationBombTriggeringB;
	public static Animation<TextureRegion> animationBombTriggeringC;
	public static Animation<TextureRegion> animationBombCannonFiring;

	public static TextureRegion Light1;
	public static TextureRegion Light2;
	public static TextureRegion Light3;

	public static Animation<TextureRegion> animationArrow;
	public static Animation<TextureRegion> animationArrowA;
	public static Animation<TextureRegion> animationAuraFinal;
	public static Animation<TextureRegion> animationAura;
	public static Animation<TextureRegion> animationBlueishFlame;
	public static Animation<TextureRegion> animationBlueishFlameUp;
	public static Animation<TextureRegion> animationBlueishSmoke;
	public static Animation<TextureRegion> animationBlueishSmokeUp;
	public static Animation<TextureRegion> animationCrystal2;
	public static Animation<TextureRegion> animationFireSmoke;
	public static Animation<TextureRegion> animationFireSmokeUp;
	public static Animation<TextureRegion> animationFire;
	public static Animation<TextureRegion> animationFireUp;
	public static Animation<TextureRegion> animationImpactAsteroidA;
	public static Animation<TextureRegion> animationImpactAsteroidB;
	public static Animation<TextureRegion> animationImpactAsteroidC;
	public static Animation<TextureRegion> animationMagic;
	public static Animation<TextureRegion> animationMagicUp;
	public static Animation<TextureRegion> animationMuzzle;
	public static Animation<TextureRegion> animationShipDeath;
	public static Animation<TextureRegion> animationSmoke;
	public static Animation<TextureRegion> animationStarA;
	public static Animation<TextureRegion> animationStarB;
	public static Animation<TextureRegion> animationStarC;

	public static TextureRegion textureKillIcon;
	public static Animation<TextureRegion> spriteEffectsExhaust00;
	public static Animation<TextureRegion> spriteEffectsExhaust01;
	public static Animation<TextureRegion> spriteEffectsExhaust02;
	public static Animation<TextureRegion> spriteEffectsExplosion;
	public static Animation<TextureRegion> spriteFireShotsFlame;
	public static Animation<TextureRegion> spriteFireShotsImpactA;
	public static Animation<TextureRegion> spriteFireShotsImpactB;
	public static Animation<TextureRegion> spriteFireShotsShotA;
	public static Animation<TextureRegion> spriteFireShotsShotB;

	public static Array<AtlasRegion> textureEnimyCrach, textureEnimyCrach2;

	public static TextureRegion textureCometA;
	public static Animation<TextureRegion> animationCometA;
	public static TextureRegion textureCometB;
	public static Animation<TextureRegion> animationCometB;

	public static Array<AtlasRegion> textureGunAs;
	public static Array<Array<AtlasRegion>> textureGunBs;
	public static Array<Array<AtlasRegion>> textureHulls;

	public static TextureRegion textureTireTrack1;
	public static TextureRegion textureTireTrack2;
	public static Array<AtlasRegion> textureTrackAs;
	public static Array<AtlasRegion> textureTrackBs;

	public static Animation<TextureRegion> animationCrystal;
	public static Animation<TextureRegion> animationGold;
	public static Animation<TextureRegion> animationPowerMax;
	public static Animation<TextureRegion> animationPowerUp;
	public static Animation<TextureRegion> animationUpgrade;
	public static Animation<TextureRegion> animationRepair;


	public static TextureRegion textureGranadeShell;
	public static TextureRegion textureHeavyShell;
	public static TextureRegion textureLaser;
	public static TextureRegion textureLightShell;
	public static TextureRegion textureMediumShell;
	public static TextureRegion texturePlasma;
	public static TextureRegion textureShotgunShells;
	public static TextureRegion textureSniperShell;

	public static Array<AtlasRegion> textureBlastTrails;
	public static TextureRegion textureBorderA;
	public static TextureRegion textureBorderB;
	public static TextureRegion textureBorderC;
	public static TextureRegion textureContainerA;
	public static TextureRegion textureContainerB;
	public static TextureRegion textureContainerC;
	public static TextureRegion textureContainerD;

	public static Array<AtlasRegion> textureCactus;
	public static Array<AtlasRegion> textureCrates;
	public static Array<AtlasRegion> texturePalms;
	public static Array<AtlasRegion> textureTires;
	public static Array<AtlasRegion> textureTrees;

	public static Array<AtlasRegion> textureBox;

	public static TextureRegion textureArtifact;
	public static Animation<TextureRegion> animationBall;
	public static Array<AtlasRegion> textureFootballGoals;
	public static Array<AtlasRegion> textureBarells;

	public static TextureRegion textureBoxActive;
	public static TextureRegion textureBoxNoActive;
	public static TextureRegion textureDoorClose;
	public static TextureRegion textureDoorExit;
	public static TextureRegion textureDoorOpen;
	public static TextureRegion textureDoorSimple;
	public static TextureRegion textureDotA;
	public static TextureRegion textureDotB;
	public static TextureRegion textureDotC;
	public static TextureRegion ExitNo;
	public static TextureRegion ExitYes;
	public static TextureRegion textureFlagA;
	public static TextureRegion textureFlagB;
	public static TextureRegion textureLax;
	public static TextureRegion texturePlatform;
	public static TextureRegion texturePlatformActive0;
	public static TextureRegion texturePlatformActive1;
	
	
	
	
	public static void load() {
		manager = new AssetManager();
		manager.load("Sprite/SpriteGame.atlas", TextureAtlas.class);
		manager.load("skins/GameStyle.json", Skin.class);
		manager.load("skins/jf_flat.fnt",BitmapFont.class);
		addSound();
	}

	public static void managerSprit() {
		textureAtlasSpriteGame =  manager.get("Sprite/SpriteGame.atlas", TextureAtlas.class);
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
		skinStyle = manager.get("skins/GameStyle.json", Skin.class);
		JsonReader jsonReader = new JsonReader();
		JsonValue ValueData = jsonReader.parse(Gdx.files.internal("CrachType.json"));

		jsonDataGunA = ValueData.get("GunA");
		jsonDataGunB = ValueData.get("GunB");
		jsonDataHull = ValueData.get("Hull");
		jsonDataTrack = ValueData.get("Track");

		jsonDataEnimy = ValueData.get("crach");
		jsonDataShell = ValueData.get("Shell");

		JsonValue ValueLangue = jsonReader.parse(Gdx.files.internal("traduction.json")).get(CrachGame.Langue);
		jsonStringMenu = ValueLangue.get("Menu");
		jsonStringSetting = ValueLangue.get("Setting");
		jsonStringModeGame = ValueLangue.get("ModeGame");
		jsonStringshop = ValueLangue.get("shop");
		jsonStringHanger = ValueLangue.get("Hanger");
		jsonStringBonus = ValueLangue.get("Bonus");
		jsonStringSelectStage = ValueLangue.get("Select_stage");
		jsonStringHelp = ValueLangue.get("Help");
		jsonStringGameOnline = ValueLangue.get("GameOnLine");
		jsonStringGameLocale = ValueLangue.get("GameLocale");
		jsonStringDialog = ValueLangue.get("dialoge");
		jsonStringCredits = ValueLangue.get("credits");
		if(CrachGame.Langue.equals("Arabic")){
			for(int i = 0;i<ValueLangue.size;i++){
				JsonValue values = ValueLangue.get(i);
				for(int j=0;j<values.size;j++)
					values.get(j).set(ArFont.getText(values.getString(j)));
			}


		}

		valueBonus = jsonReader.parse(Gdx.files.internal("BonusValue.json"));
		valueNiveu = jsonReader.parse(Gdx.files.internal("ValueNive.json"));
		
		font = manager.get("skins/jf_flat.fnt",BitmapFont.class);
		font.getData().setScale(0.2f);
		
		mapsLoader = new MapsLoader();
	}

	private static void loadbackground() {
		String Folder = "background/";
		backgroundPlan1 = textureAtlasSpriteGame.findRegion(Folder + "planet-1");
		backgroundPlan2 = textureAtlasSpriteGame.findRegion(Folder + "planet-2");
	}

	private static void loadBombs() {
		String Folder = "Bombs/";
		float frameDuration = 0.16f;
		animationBombExplosionA = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Explosion_A"));
		animationBombExplosionB = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Explosion_B"));
		animationBombExplosionC = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Explosion_C"));
		animationBombIdleA = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "Bomb_Idle_A"));
		animationBombIdleB = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "Bomb_Idle_B"));
		animationBombIdleC = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "Bomb_Idle_C"));
		animationBombTriggeringA = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Triggering_A"));
		animationBombTriggeringB = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Triggering_B"));
		animationBombTriggeringC = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Bomb_Triggering_C"));
		
		animationBombCannonFiring = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "cannon-firing"));

		Light1 = textureAtlasSpriteGame.findRegion(Folder + "Light", 1);
		Light2 = textureAtlasSpriteGame.findRegion(Folder + "Light", 2);
		Light3 = textureAtlasSpriteGame.findRegion(Folder + "Light", 3);
	}

	private static void loadEffects() {
		String Folder = "Effects/";
		float frameDuration = 0.17f;

		animationArrow = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "arrow"));
		animationArrowA = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "arrow_a"));
		animationAuraFinal = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "aura_final"));
		animationAura = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "aura"));
		animationBlueishFlame = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "blueish_flame"));
		animationBlueishFlameUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "blueish_flame_up"));
		animationBlueishSmoke = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "blueish_smoke"));
		animationBlueishSmokeUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "blueish_smoke_up"));
		animationFireSmoke = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "fire+smoke"));
		animationFireSmokeUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "fire_smoke_up"));
		animationFire = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "fire"));
		animationFireUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "fire_up"));
		animationImpactAsteroidA = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "ImpactA/impact-asteroid-a"));
		animationImpactAsteroidB = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "ImpactB/impact-asteroid-b"));
		animationImpactAsteroidC = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "ImpactB/impact-asteroid-b"));
		animationMagic = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "magic"));
		animationMagicUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "magic_up"));
		animationMuzzle = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "muzzle"));
		animationShipDeath = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "ship-death"));
		animationSmoke = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "smoke"));
		animationStarA = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "stara"));
		animationStarB = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "starb"));
		animationStarC = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "starc"));
		
		
		textureKillIcon = textureAtlasSpriteGame.findRegion(Folder + "Kill_Icon");
		spriteEffectsExhaust00 = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_00"));
		spriteEffectsExhaust01 = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_01"));
		spriteEffectsExhaust02 = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Effects_Exhaust_02"));
		spriteEffectsExplosion = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Effects_Explosion"));
		spriteFireShotsFlame = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Flame"));
		spriteFireShotsImpactA = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Impact_A"));
		spriteFireShotsImpactB = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Impact_B"));
		spriteFireShotsShotA = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Shot_A"));
		spriteFireShotsShotB = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegions(Folder + "Sprite_Fire_Shots_Shot_B"));

	}
	private static void loadpickup() {
		String Folder = "pickups/";
		float frameDuration = 0.18f;
		animationCrystal = new Animation<TextureRegion>(frameDuration,
				textureAtlasSpriteGame.findRegion(Folder + "crystal0").split(16, 16)[0]);
		animationGold = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "gold"));
		animationCrystal2 = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "crystal"));
		animationPowerMax = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "pwr_max"));
		animationUpgrade = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "upgrade"));
		animationRepair = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "repair"));
		animationPowerUp = new Animation<TextureRegion>(frameDuration, textureAtlasSpriteGame.findRegions(Folder + "pwr_up"));
	}
	
	private static void loadEnimy() {
		String Folder = "Enimy/";
		textureEnimyCrach = textureAtlasSpriteGame.findRegions(Folder + "crach");
		textureEnimyCrach2 = textureAtlasSpriteGame.findRegions(Folder + "crach2");

		textureCometA = textureAtlasSpriteGame.findRegion(Folder + "comet-full-a");
		animationCometA = new Animation<TextureRegion>(1f, textureAtlasSpriteGame.findRegions(Folder + "comet-a"));

		textureCometB = textureAtlasSpriteGame.findRegion(Folder + "comet-full-b");
		animationCometB = new Animation<TextureRegion>(1f, textureAtlasSpriteGame.findRegions(Folder + "comet-b"));
	}

	private static void loadHulls() {
		textureGunAs = textureAtlasSpriteGame.findRegions("Hulls_Color_A/Gun_A");
		textureGunBs = new Array<Array<AtlasRegion>>();
		textureGunBs.add(textureAtlasSpriteGame.findRegions("Hulls_Color_A/Gun_B"), textureAtlasSpriteGame.findRegions("Hulls_Color_B/Gun_B"),
				textureAtlasSpriteGame.findRegions("Hulls_Color_C/Gun_B"), textureAtlasSpriteGame.findRegions("Hulls_Color_D/Gun_B"));
		textureHulls = new Array<Array<AtlasRegion>>();
		textureHulls.add(textureAtlasSpriteGame.findRegions("Hulls_Color_A/Hull"), textureAtlasSpriteGame.findRegions("Hulls_Color_B/Hull"),
				textureAtlasSpriteGame.findRegions("Hulls_Color_C/Hull"), textureAtlasSpriteGame.findRegions("Hulls_Color_D/Hull"));
	}

	private static void loadTracks() {
		String Folder = "Tracks/";
		textureTireTrack1 = textureAtlasSpriteGame.findRegion(Folder + "Tire_Track", 1);
		textureTireTrack2 = textureAtlasSpriteGame.findRegion(Folder + "Tire_Track", 2);

		textureTrackAs = textureAtlasSpriteGame.findRegions(Folder + "Track_A");
		textureTrackBs = textureAtlasSpriteGame.findRegions(Folder + "Track_B");
	}

	private static void loadShell() {
		String Folder = "Shell/";

		textureGranadeShell = textureAtlasSpriteGame.findRegion(Folder + "Granade_Shell");
		textureHeavyShell = textureAtlasSpriteGame.findRegion(Folder + "Heavy_Shell");
		textureLaser = textureAtlasSpriteGame.findRegion(Folder + "Laser");
		textureLightShell = textureAtlasSpriteGame.findRegion(Folder + "Light_Shell");
		textureMediumShell = textureAtlasSpriteGame.findRegion(Folder + "Medium_Shell");
		texturePlasma = textureAtlasSpriteGame.findRegion(Folder + "Plasma");
		textureShotgunShells = textureAtlasSpriteGame.findRegion(Folder + "Shotgun_Shells");
		textureSniperShell = textureAtlasSpriteGame.findRegion(Folder + "Sniper_Shell");
	}

	private static void loadSpriteTitle() {
		String Folder = "SpriteTitle/";
		
		
		textureBlastTrails = textureAtlasSpriteGame.findRegions(Folder + "Blast_Trail");
		textureBorderA = textureAtlasSpriteGame.findRegion(Folder + "Border_A");
		textureBorderB = textureAtlasSpriteGame.findRegion(Folder + "Border_B");
		textureBorderC = textureAtlasSpriteGame.findRegion(Folder + "Border_C");
		textureContainerA = textureAtlasSpriteGame.findRegion(Folder + "Container_A");
		textureContainerB = textureAtlasSpriteGame.findRegion(Folder + "Container_B");
		textureContainerC = textureAtlasSpriteGame.findRegion(Folder + "Container_C");
		textureContainerD = textureAtlasSpriteGame.findRegion(Folder + "Container_D");

		
		textureCactus = textureAtlasSpriteGame.findRegions(Folder + "Cactus");
		textureCrates = textureAtlasSpriteGame.findRegions(Folder + "Crate");
		texturePalms = textureAtlasSpriteGame.findRegions(Folder + "Palm");
		textureTires = textureAtlasSpriteGame.findRegions(Folder + "Tire");
		textureTrees = textureAtlasSpriteGame.findRegions(Folder + "Tree");
		
		textureBox = textureAtlasSpriteGame.findRegions(Folder + "Box");
		
		textureArtifact = textureAtlasSpriteGame.findRegion(Folder + "Artifact");
		animationBall = new Animation<TextureRegion>(0.05f, textureAtlasSpriteGame.findRegions(Folder + "Ball"));
		textureFootballGoals = new Array<AtlasRegion>();
		textureFootballGoals.add(textureAtlasSpriteGame.findRegion(Folder + "Football_Goals_A"), textureAtlasSpriteGame.findRegion(Folder + "Football_Goals_B"));
		textureBarells = textureAtlasSpriteGame.findRegions(Folder + "Barell");
		textureBoxActive = textureAtlasSpriteGame.findRegion(Folder + "boxActive");
		textureBoxNoActive = textureAtlasSpriteGame.findRegion(Folder + "boxNoActive");
		textureDoorClose = textureAtlasSpriteGame.findRegion(Folder + "DoorClose");
		textureDoorExit = textureAtlasSpriteGame.findRegion(Folder + "DoorExit");
		textureDoorOpen = textureAtlasSpriteGame.findRegion(Folder + "DoorOpen");
		textureDoorSimple = textureAtlasSpriteGame.findRegion(Folder + "DoorSimple");
		textureDotA = textureAtlasSpriteGame.findRegion(Folder + "Dot_A");
		textureDotB = textureAtlasSpriteGame.findRegion(Folder + "Dot_B");
		textureDotC = textureAtlasSpriteGame.findRegion(Folder + "Dot_C");
		ExitNo = textureAtlasSpriteGame.findRegion(Folder + "ExitNo");
		ExitYes = textureAtlasSpriteGame.findRegion(Folder + "ExitYes");
		textureFlagA = textureAtlasSpriteGame.findRegion(Folder + "Flag_A");
		textureFlagB = textureAtlasSpriteGame.findRegion(Folder + "Flag_B");
		textureLax = textureAtlasSpriteGame.findRegion(Folder + "Lax");
		texturePlatform = textureAtlasSpriteGame.findRegion(Folder + "Platform");
		texturePlatformActive0 = textureAtlasSpriteGame.findRegion(Folder + "PlatformActive0");
		texturePlatformActive1 = textureAtlasSpriteGame.findRegion(Folder + "PlatformActive1");
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
	public static UiSound soundBlueAttack, soundRedAttack, soundGunMotor
			, soundEnemyHit, soundExplodeBomb, soundExplosBarellSecret
			, soundExplosionEnimy, soundExplosionPlayer
			, soundImpactCannon
			, soundImpactObjectWall
			, soundDestroyCircularBlade
			, movement
		//	Tank_reload,
			, soundImpactShell
			,soundDoor, soundUpgrade
			, soundPlayerAttack, soundHitCrateHeavy
			, soundHitEnimyMedium, soundPlayerHit;
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

		
		soundBlueAttack = new UiSound("sound/blue_attack.ogg",SoundGame);
		soundRedAttack = new UiSound("sound/red_attack.ogg",SoundGame);
		for (int i = 1; i < 5; i++)
			Effects_flam.add(new UiSound("sound/Effects_flam" + i + ".mp3",SoundGame));
		soundGunMotor =  new UiSound("sound/motor-loop.ogg",SoundGame);
		soundEnemyHit = new UiSound("sound/enemyhit.mp3",SoundGame);
		soundExplodeBomb = new UiSound("sound/explode.ogg",SoundGame);
		soundExplosBarellSecret = new UiSound("sound/explos_secret.wav",SoundGame);
		soundExplosionEnimy = new UiSound("sound/explosion0.wav",SoundGame);
		soundExplosionPlayer = new UiSound("sound/explosion0_revival.wav",SoundGame);
		soundImpactCannon = new UiSound("sound/Impact_cannon.wav",SoundGame);
		soundImpactObjectWall = new UiSound("sound/impact_wall.wav",SoundGame);
		soundDestroyCircularBlade = new UiSound("sound/destroy_circular_blade.wav",SoundGame);
		movement = new UiSound("sound/movement.ogg",SoundGame);
		soundPlayerAttack = new UiSound("sound/playerattack.mp3",SoundGame);
		soundHitCrateHeavy = new UiSound("sound/player-hit-heavy.mp3",SoundGame);
		soundHitEnimyMedium = new UiSound("sound/player-hit-medium.mp3",SoundGame);
		soundPlayerHit = new UiSound("sound/playerhit.mp3",SoundGame);
		soundImpactShell = new  UiSound("sound/player-hit-medium.mp3",SoundGame);
		soundDoor = new UiSound("sound/OpenDoor.wav",SoundGame);
		soundUpgrade = new UiSound("sound/upgrade.mp3",SoundGame);
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
			
			soundBlueAttack.get();
			 soundRedAttack.get();
			 for (UiSound sound : Effects_flam)
				 sound.get();
			 
			 soundGunMotor.get();
			 soundEnemyHit.get();
			 soundExplodeBomb.get();
			 soundExplosBarellSecret.get();
			 soundExplosionEnimy.get();
			 soundExplosionPlayer.get();
			 soundImpactCannon.get();
			 soundImpactObjectWall.get();
			 soundDestroyCircularBlade.get();
			 movement.get();
			 //Tank_reload.get();
			 soundImpactShell.get();
			 soundDoor.get();
			 soundUpgrade.get();
			 soundPlayerAttack.get();
			 soundHitCrateHeavy.get();
			 soundHitEnimyMedium.get();
			 soundPlayerHit.get();
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
		String urlSource;
		public UiMusic(String url) {
			this.urlSource =url;
			load();
		}
		public UiMusic(String url,boolean isLoad) {
			this.urlSource =url;
			if(isLoad) load();
		}
		private void load() {
			manager.load("audio/"+ urlSource, Music.class);
		}
		private void get() {
			if(manager.contains("audio/"+ urlSource, Music.class))
				this.music = manager.get("audio/"+ urlSource, Music.class);
			else this.music = music0;
			urlSource = null;
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