package crach.stage.game.Creator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import aurelienribon.tweenengine.TweenManager;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import crach.stage.game.CrachGame;
import crach.stage.game.Text;
import crach.stage.game.Screen.PlayScreen;
import crach.stage.game.Screen.Tools.Hub;
import crach.stage.game.control.controulMobile;
import crach.stage.game.control.controulPc;
import crach.stage.game.control.controuleClass;
import crach.stage.game.entites.Bomb.Bomb;
import crach.stage.game.entites.Entity;
import crach.stage.game.entites.Bomb.BombCannon;
import crach.stage.game.entites.Bomb.BombFire;
import crach.stage.game.entites.Bomb.BombGun;
import crach.stage.game.entites.Crach.CrachPlayer;
import crach.stage.game.entites.Door.Door;
import crach.stage.game.entites.Door.DoorExit;
import crach.stage.game.entites.Door.DoorSimple;
import crach.stage.game.entites.Enimy.Enimy;
import crach.stage.game.entites.Object.Ball;
import crach.stage.game.entites.Object.Box;
import crach.stage.game.entites.Object.Container;
import crach.stage.game.entites.Object.Crate;
import crach.stage.game.entites.Object.Goal;
import crach.stage.game.entites.Object.M_Circle;
import crach.stage.game.entites.Object.Barell;
import crach.stage.game.entites.Object.Object;
import crach.stage.game.entites.PLace.Place;
import crach.stage.game.entites.PLace.PlaceBox;
import crach.stage.game.entites.PLace.PlaceExit;
import crach.stage.game.entites.Pickups.Crystal;
import crach.stage.game.entites.Pickups.Gold;
import crach.stage.game.entites.Pickups.Pickup;
import crach.stage.game.entites.Pickups.Repair;
import crach.stage.game.entites.Pickups.Upgarde;
import crach.stage.game.entites.Zone.ZonObject;
import crach.stage.game.entites.Zone.Zone;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public abstract class B2WorldCreator implements Disposable{

	public TweenManager TWEEN_MANAGER ;
    public RayHandler rayHandler;
    public Filter Fglobal;
    public Filter Fstatic;


	protected controuleClass controule;
	protected Hub hub;
    
	protected PlayScreen GdxPlayScreen;
	protected PlayScreen.ModeGame modeGame;
	protected World box2dWorld;
	protected TiledMap tileMap; 
	protected CrachPlayer player;
	protected PlaceExit Exite;
    protected Array<Entity> ENTITYS;
    protected Array<Entity> ToDestroy;
    
    protected Array<Text> MapText;
    
    protected int numberBox;
    protected int numberCry;
    protected int numberEnimy;
    
    protected int nStage;
    
	public enum TypeEntity {
        Box, Bomb ,Crach,Crystal,Place,Enimy;
    }

	public B2WorldCreator(PlayScreen gdxPlayScreen) {
		this.GdxPlayScreen = gdxPlayScreen;
		this.modeGame = gdxPlayScreen.modeGame;
        this.box2dWorld = gdxPlayScreen.getBox2dWorld();
        this.tileMap = gdxPlayScreen.getTileMap();
        this.nStage = gdxPlayScreen.getnStage();
        
        Entity.setCreator(this);
        Entity.setWorld(box2dWorld);
        
        TWEEN_MANAGER = new TweenManager();
        
        ToDestroy = new Array<Entity>();
        ENTITYS = new Array<Entity>() {
        	@Override
        	public void add(Entity arg0) {
        		 super.add(arg0);
        	     this.sort(Box2DSprite.getZComparator());
        	}
        };
        MapText = new Array<Text>();
        addBorderTitle();
		addLightTitle();
        addPlayerTitle();
        
        gdxPlayScreen.addActor(hub = new Hub(this));
        switch (Gdx.app.getType()) {
		case Android:
		case iOS:
				controule = new controulMobile(this);
			break;
		case WebGL:
        case Desktop:
                controule = new controulPc(this);
		default:
			break;
		}
	}
	
	protected void addBorderTitle() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        Body body =null;
        bdef.type = BodyDef.BodyType.StaticBody;
        body = box2dWorld.createBody(bdef);
		for(MapObject object : tileMap.getLayers().get("BourderObjet").getObjects()){

	           Shape shape;
	           if (object instanceof RectangleMapObject) {
	               shape = getRectangle((RectangleMapObject)object);
	           }
	           else if (object instanceof PolygonMapObject) {
	               shape = getPolygon((PolygonMapObject)object);
	           }
	           else if (object instanceof PolylineMapObject) {
	               shape = getPolyline((PolylineMapObject)object);
	           }
	           else if (object instanceof CircleMapObject) {
	               shape = getCircle((CircleMapObject)object);
	           }
	           else if (object instanceof EllipseMapObject) {
	               shape = getCircle((EllipseMapObject)object);
	           }
	           else {
	               continue;
	           }
	               fdef.filter.categoryBits = CrachGame.NOTHING_BIT;
	               fdef.shape = shape;
	               body.createFixture(fdef);
	           }
	}
	
	
	protected void addBoxTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Box");
		if( maplayer == null) return;
		String type;
		Object B = null;
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	type = object.getProperties().get("type",String.class);
        	  if(type.equals("box")) {
				  B = Box.CreateBox(object);
			  } else if(type.equals("crate")) {
        	  	B = Crate.CreateCrate(object);
			  }else if(type.equals("container")) {
        	  	B = new Container(object);
			  }else if(type.equals("goal")){
				  B = new Goal(object);
			  }

			if(B != null)
				ENTITYS.add(B);
        }


	}
	protected void addCircleTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Circle");
		if( maplayer == null) return;
		String type;
		M_Circle B = null;
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
			type = object.getProperties().get("type","barell",String.class);
			if(type.equals("barell"))
				B = Barell.CreateBarell(object);
			else if(type.equals("ball"))
				B = new Ball(object);

              if(B != null) ENTITYS.add(B);
        }
	}
	protected void addPickUpTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Pickup");
		if( maplayer == null) return;
		String type ;
		Pickup pick;
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	type = object.getProperties().get("type","gold", String.class);
        	 if(type.equals("gold")) {
         		pick = new Gold(object);
         		ENTITYS.add(pick);
         	}
        	else if(type.equals("crystal")) {
        		pick = new Crystal.A(object);
        		ENTITYS.add(pick);
        		numberCry++;
            }
        	else if(type.equals("upgarde")) {
        		pick = new Upgarde(object);
        		ENTITYS.add(pick);
        	}
        	else if(type.equals("repair")) {
        		pick = new Repair(object);
        		ENTITYS.add(pick);
        	}else
        		Gdx.app.log("errour"," pick no type");
        }
	}
	protected void addEnimyTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Enimy");
		if( maplayer == null) return;
		
		int type;
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	  type = object.getProperties().get("code",MathUtils.random(1,5), Integer.class);
              Enimy E = new Enimy(object, (float)(Math.random()*1.4),type-1);
              ENTITYS.add(E);
              numberEnimy++;
        }
	}
	protected void addPlayerTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Player");
		if( maplayer == null) return;
		
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
            player = new CrachPlayer(object);
            ENTITYS.add(player);
      }
	}
	protected void addDoorTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Door");
		if( maplayer == null) return;
		
		Door D;
		int type;
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	type = object.getProperties().get("derect",0, Integer.class);
        	if(object.getProperties().get("exit", false,Boolean.class)) {
                 D = new DoorExit(object, type);
        	}else {
        		 D = new DoorSimple(object, type);
        	}
    		ENTITYS.add(D);
        }
	}
	protected void addPlaceTitle() {
		MapLayer maplayer = tileMap.getLayers().get("Place");
		int type;
		Place B;
		if( maplayer == null) return;
		
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	type = object.getProperties().get("code", 0,Integer.class);
        	switch(type) {
        		case 0 :
        			B= new PlaceExit(object);
        			Exite = (PlaceExit)B;
        			ENTITYS.add(B);
        		break;
        		case 1 :
        			B= new PlaceBox(object);
        			ENTITYS.add(B);
        		break;
        	    default : 
        	    break;
        	}    
         }
        if(numberCry > 0 && Exite != null)
        	Exite.setActive(false);
	}
	protected void addBombTitle() {
		MapLayer maplayerBomb = tileMap.getLayers().get("Bomb");
		MapLayer maplayerZon = tileMap.getLayers().get("Zone");
		Array<Zone> LesZones = new Array<Zone>();
		if( maplayerZon != null)
		 for(MapObject object : maplayerZon.getObjects().getByType(MapObject.class)) {
			 LesZones.add( new ZonObject(object, CrachGame.CRACH_BIT));
		 }
			 
		if( maplayerBomb == null) return;
		
		String type;
		Entity B =null;

        for(MapObject object : maplayerBomb.getObjects().getByType(MapObject.class)){
			type = object.getProperties().get("type","bomb",String.class);
			if(type.equals("bomb"))
				B = Bomb.CreateBomb(object);
			else if(type.equals("bombgun")) {
				B = BombGun.CreateBombGun(object);
			}else if(type.equals("bombfire")) {
				B = new BombFire(object);
			}
			else if(type.equals("Bombcan")) {
				B = new BombCannon(object);
			}
           ENTITYS.add(B);
         }
	}
	
	protected void addLightTitle() {
		rayHandler = new RayHandler(box2dWorld);
		rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 1f);
		rayHandler.setBlurNum(3);
		
		Fglobal = new Filter();
		Fstatic = new Filter();
		
		Fglobal.categoryBits = CrachGame.LIGHT_BIT;
		Fstatic.categoryBits = CrachGame.LIGHT_BIT;
		
		Fglobal.maskBits = CrachGame.LIGHT_BIT|CrachGame.NOTHING_BIT|CrachGame.CRACH_BIT|CrachGame.OBJECT_BIT |CrachGame.FRENDS_BIT |CrachGame.DOOR_BIT|CrachGame.ENIMY_BIT;
		Fstatic.maskBits = CrachGame.NOTHING_BIT;
		
		Light.setGlobalContactFilter(Fglobal);
   	    

		MapLayer maplayer = tileMap.getLayers().get("Light");
		Color C;
		float distance;
		if( maplayer == null) return;
		
        for(MapObject object : maplayer.getObjects().getByType(MapObject.class)){
        	 C = object.getProperties().get("color",Color.WHITE, Color.class);
        	 distance = object.getProperties().get("distance",60,Integer.class);
        	 Rectangle rect = ((RectangleMapObject) object).getRectangle();        	
        	 PointLight pl= new PointLight(rayHandler, 128, C, distance,(rect.getX() + rect.getWidth() / 2) /CrachGame.PPM, (rect.getY() + rect.getHeight() / 2) /CrachGame.PPM);
        	 
        		 if(object.getProperties().get("static",true, Boolean.class)) {
        			 pl.setStaticLight(true);
        			 pl.setContactFilter(Fstatic);
        		 }
        	}
	}
	protected void addTextTitle() {
		MapLayer maplayerText = tileMap.getLayers().get("Text");
		for(RectangleMapObject object : maplayerText.getObjects().getByType(RectangleMapObject.class)) {
			String mText = object.getName();
       	 	Rectangle rect = object.getRectangle();        	
			MapText.add(new Text(mText,rect.getX()  /CrachGame.PPM, rect.getY()  /CrachGame.PPM));
		}
	}
    public void update(float dt) { 
    	    for(int i = 0;i<ENTITYS.size;i++)
    	    	ENTITYS.get(i).update(dt);
    	    destoryEntity();
    	    controule.update(dt);
            hub.update(dt);
            updatehubWeapon(controule.getDt_time());
    }
    public void drawEntity(Batch batch,int minindexZ,int maxIndexZ) {
        batch.begin();
        for(int i = 0;i<ENTITYS.size;i++) {
        	if(ENTITYS.get(i).getZIndex() > maxIndexZ)
        		break;
        	if(ENTITYS.get(i).getZIndex() < minindexZ)
        		continue;
        	ENTITYS.get(i).draw(batch, ENTITYS.get(i).getBody());
        }
        for(int i=0;i<MapText.size;i++)
        	MapText.get(i).draw(batch);
        batch.end();
        batch.setColor(Color.WHITE);
    }
    protected void destoryEntity() {
   	 while(!ToDestroy.isEmpty()){
		 System.out.println("Destor");
		 Entity destor = ToDestroy.get(ToDestroy.size-1);
		 destoryEntity(destor);
	  }
    }
    public void addEntity(Entity E) {
    	ENTITYS.add(E);
    }
    private void destoryEntity(Entity E) {
    	ENTITYS.removeValue(E,false);
		ToDestroy.removeValue(E,false);
    }
    public void SetDestoryEntity(Entity E) {
    	if(!ToDestroy.contains(E,false))
    	          ToDestroy.add(E);
    }
    
	public CrachPlayer getPlayer() {
		return player;
	}
	
	public void deletePlayer() {
	}
	
	public void deleteCry(Crystal E) {
		SetDestoryEntity(E);
		numberCry--;
	}
	public void deleteEnimy(Enimy E) {
		SetDestoryEntity(E);
		numberEnimy--;
		if(numberEnimy == 0)
			activePlaceExit();
	}
	private  PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / CrachGame.PPM,
                (rectangle.y + rectangle.height * 0.5f ) / CrachGame.PPM);
        polygon.setAsBox(rectangle.width * 0.5f /CrachGame.PPM,
                rectangle.height * 0.5f / CrachGame.PPM,
                size,
                0.0f);
        return polygon;
    }
    private  CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / CrachGame.PPM);
        circleShape.setPosition(new Vector2(circle.x / CrachGame.PPM, circle.y / CrachGame.PPM));
        return circleShape;
    }
    private  CircleShape getCircle(EllipseMapObject EllipseObject) {
        Ellipse ellipse = EllipseObject.getEllipse();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(ellipse.height/2 / CrachGame.PPM);
        circleShape.setPosition(new Vector2(ellipse.x / CrachGame.PPM+circleShape.getRadius(), ellipse.y / CrachGame.PPM+circleShape.getRadius()));
        return circleShape;
    }
    
    
    private  PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / CrachGame.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private  ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / CrachGame.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / CrachGame.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
	

	
    public abstract void victorGame();
    public abstract void defeatGame();
    public abstract void pauser();
	public abstract void QuitGame();
	public abstract  void rePlay();
	public abstract boolean isContinue();
	protected abstract void activePlaceExit();

	public abstract void  addExper(int expertion);
	public abstract void addScore(int score);
	public abstract void addDanger(int danger);
	public abstract void addCoin(int coin);
	public abstract void addHp(int hp);
	
    public abstract int getScore();
	public abstract int getExper();
    public abstract int getStars();
    public abstract int getRecord();
    public abstract int getCoin();
	public abstract int getHp();



	@Override
	public void dispose() {
        ToDestroy.clear();
        ENTITYS.clear();
        MapText.clear();
        ZonObject.dispose();
	}

	public void updateAndRenderLight(OrthographicCamera box2dCamera) {
        if(rayHandler == null)
        	return;
        rayHandler.setCombinedMatrix(box2dCamera);
        rayHandler.updateAndRender();
	}
	public controuleClass getControule() {
		return controule;
	}
	public Hub getHub() {
		return hub;
	}
	public void updatehubHealth() {
		if(hub != null)
			hub.updatePHealth(player.getLife());
	}
	public void updatehubWeapon(float Vweapon) {
		hub.updatePWeapon(Vweapon);
	}
	public PlayScreen getGdxPlayScreen() {
		return GdxPlayScreen;
	}

	public int getnStage() {
		return nStage;
	}
}
