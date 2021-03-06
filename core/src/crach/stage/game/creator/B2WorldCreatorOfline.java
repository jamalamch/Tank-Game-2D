package crach.stage.game.creator;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.PlayScreen;
import crach.stage.game.control.Pathfinding;
import crach.stage.game.control.Pathfinding.TestNode;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.door.Door;
import crach.stage.game.entity.enimy.Enimy;
import crach.stage.game.windows.dialog.Defeat;
import crach.stage.game.windows.dialog.DialogQuestion;
import crach.stage.game.windows.dialog.Pause;
import crach.stage.game.windows.dialog.Victory;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;


public class B2WorldCreatorOfline extends B2WorldCreator{
	public final float CellSize = 64 / CrachGame.PPM;

	protected Pathfinding PathFind;
	protected TestNode[][] mNodes;
	protected boolean isFindPathActive;

	protected Stage stage;
	public Vector2 pCellPlayer;
	
	private  int Enimerkill,PointScore,PointExper,Danger,nCoin,nHp;
	
	
	public B2WorldCreatorOfline(PlayScreen gdxPlayScreen) {
		super(gdxPlayScreen);

        if(tileMap.getLayers().get("Enimy").getObjects().getByType(MapObject.class).size > 0){
            this.PathFind = new Pathfinding();
            this.PathFind.createMaps(tileMap);
            this.mNodes = PathFind.getNodes();
            this.pCellPlayer = getEntityCell(player);
            this.isFindPathActive = true;
        }

		this.addDoorTitle();
		this.addToPath();

		this.addBoxTitle();
		this.addCircleTitle();
		this.addPickUpTitle();
		this.addEnimyTitle();

		this.addPlaceTitle();
		this.addBombTitle();
		this.addTextTitle();
	}
	@Override
	public void addPickUpTitle() {
		super.addPickUpTitle();
	}

	@Override
    public void update(float dt) {
		if(isFindPathActive) pCellPlayer = getEntityCell(player);
    	super.update(dt);
    }

	public Vector2 getEntityCell(Entity E) {
		Vector2 position = new Vector2(E.getBody().getPosition());
		position.scl(1 / CellSize);
		return position;
	}

	private void addToPath() {
	    if(isFindPathActive) {
            for (int i = 0; i < entitys.size; i++)
                if (entitys.get(i) instanceof Door)
                    addEntitToPath(entitys.get(i));
        }
	}

	private void addEntitToPath(Entity E) {
		Vector2 size = new Vector2(Box2DUtils.size(E.getBody()));
		size.rotateRad(E.getBody().getAngle());
		size.set(Math.abs(size.x), Math.abs(size.y));
		int xCell = (int)(size.x / CellSize) ;
		int yCell = (int)(size.y / CellSize) ;

		Vector2 Position = new Vector2(E.getBody().getPosition()) ;
		Position.sub(size.scl(0.5f));
		Position.scl(1 / CellSize);
					
		for(int i=0;i <=xCell;i++)
			for(int j=0;j <=yCell;j++) {
				PathFind.setCellClose((int)Position.x + i, (int)Position.y +j, true);
			}		
	}

//	private void addEntitToPath(Fixture fexFixture,float angle) {		
//		Vector2 size = new Vector2(Box2DUtils.size(fexFixture));
//		size.rotateRad(angle);
//		size.set(Math.abs(size.x), Math.abs(size.y));
//		int xCell = (int)(size.x / CellSize) ;
//		int yCell = (int)(size.y / CellSize) ;
//		Vector2 Position = new Vector2(fexFixture.getBody().getPosition()) ;
//		Position.sub(size.scl(0.5f));
//		Position.scl(1 / CellSize);
//		for(int i=0;i <=xCell;i++)
//			for(int j=0;j <=yCell;j++) {
//				PathFind.setCellClose((int)Position.x + i, (int)Position.y +j, true);
//			}
//	}

	public Pathfinding getPathfinding() {
		return  PathFind;
	}
	@Override
	public void deleteEnimy(Enimy E) {
		super.deleteEnimy(E);
		Enimerkill++;
		if(numberEnimy == 0)
			isFindPathActive = false;
	}
	@Override
	public void deletePlayer() {
		defeatGame();
	}

	@Override
	protected void activePlaceExit() {
		if(exite != null) exite.setActive(true);
	}

	@Override
	public void victorGame() {
		int score = getScore(),nstar = getStars();

		switch (modeGame){
			case simple:
				CrachGame.setStage(nStage, score);
				CrachGame.setStageNStar(nStage, nstar);
			break;
			case football:
				CrachGame.setMatch(nStage, score);
				CrachGame.setMatchNStar(nStage, nstar);
			break;
			case deathmatch:
			break;
		}

		CrachGame.submitScore(score);
		CrachGame.addMatchWinne();

		CrachGame.addHp(nHp);
		CrachGame.addCoin(nCoin);
		CrachGame.addEnimykill(numberEnimy);
		CrachGame.addExper(getExper());
		gdxPlayScreen.setPauser(true);
		gdxPlayScreen.addActor(new Victory(gdxPlayScreen.getnStage(),score,getRecord(),nCoin,nstar) {
			@Override
			public void Replay() {
				CrachGame.getGdxGame().getScreen().dispose();
				CrachGame.getGdxGame().setScreen(new PlayScreen(gdxPlayScreen.tileMap,nStage, gdxPlayScreen.modeGame));
			}
			
			@Override
			public void Continue() {
				if(isContinue()) {
					CrachGame.getGdxGame().getScreen().dispose();
					CrachGame.getGdxGame().setScreen(new PlayScreen(nStage+1, gdxPlayScreen.modeGame));
				}else{
					CrachGame.gotToMenu();
				}
			}
			@Override
			public void Back() {	
	        	  CrachGame.gotToMenu();
			}
		});
	}

	@Override
	public void defeatGame() {
		gdxPlayScreen.setPauser(true);
		gdxPlayScreen.addActor(new Defeat(gdxPlayScreen.getnStage(),getScore(),getRecord(),nCoin,modeGame == PlayScreen.ModeGame.simple) {
			@Override
			public void Resume() {
				rePlay();
				Assets.soundUpgrade.play();
			}
			
			@Override
			public void Replay() {
				CrachGame.addHp(nHp);
				CrachGame.addCoin(nCoin);
				CrachGame.addEnimykill(numberEnimy);
				CrachGame.addExper(getExper());

				CrachGame.getGdxGame().getScreen().dispose();
				CrachGame.getGdxGame().setScreen(new PlayScreen(gdxPlayScreen.tileMap,nStage, gdxPlayScreen.modeGame));
			}
			
			@Override
			public void Back() {
				CrachGame.addHp(nHp);
				CrachGame.addCoin(nCoin);
				CrachGame.addEnimykill(numberEnimy);
				CrachGame.addExper(getExper());

				CrachGame.gotToMenu();
			}
		});
        gdxPlayScreen.showAds();
    }

	@Override
	public void pauser() {
		gdxPlayScreen.setPauser(true);
		gdxPlayScreen.addActor(new Pause(getScore()) {
			@Override
			public void Settings() {
			}
			@Override
			public void Resume() {
				gdxPlayScreen.setPauser(false);
			}
			@Override
			public void Replay() {
				CrachGame.addHp(nHp);
				CrachGame.addCoin(nCoin);
				CrachGame.addEnimykill(numberEnimy);
				CrachGame.addExper(getExper());

				CrachGame.getGdxGame().getScreen().dispose();
				CrachGame.getGdxGame().setScreen(new PlayScreen(gdxPlayScreen.tileMap,nStage, gdxPlayScreen.modeGame));
			}
		});
        gdxPlayScreen.showAds();
    }
	
	@Override
	public void QuitGame() {
		gdxPlayScreen.setPauser(true);
		gdxPlayScreen.addActor(new DialogQuestion(Assets.jsonStringDialog.getString("exit"), Assets.jsonStringDialog.getString("msgExit")) {
			@Override
			public void Refuse() {
				gdxPlayScreen.setPauser(false);
			}
			
			@Override
			public void Accepte() {
				CrachGame.addHp(nHp);
				CrachGame.addCoin(nCoin);
				CrachGame.addEnimykill(numberEnimy);
				CrachGame.addExper(getExper());

				CrachGame.gotToMenu();
			}
		});
        gdxPlayScreen.showAds();
    }

	@Override
	public void rePlay() {
		if(!CrachGame.addLife(-1)){
			gdxPlayScreen.setPauser(true);
			gdxPlayScreen.addActor(new DialogQuestion(Assets.jsonStringDialog.getString("noLife")
					, Assets.jsonStringDialog.getString("msgNoLife")) {
				@Override
				public void Accepte() {
					CrachGame.getiActivityRequestHandler().showVideosAds(new Runnable() {
						@Override
						public void run() {
							CrachGame.addLife(10);
							gdxPlayScreen.setPauser(false);
						}
					}, new Runnable() {
						@Override
						public void run() {
							CrachGame.gotToMenu();
						}
					});
				}
				@Override
				public void Refuse() {
					CrachGame.gotToMenu();
				}
			});
		}else{
			gdxPlayScreen.setPauser(false);
		}
		player.reLifeUpdate();
	}

	@Override
	public void drawEntity(Batch batch,int minindexZ,int maxIndexZ) {
		super.drawEntity(batch, minindexZ, maxIndexZ);
	//	PathFind.drawPath(batch);
	}
	
	@Override
	public int getScore() {
		int scoore = 10*nStage+ PointScore + nCoin + Enimerkill*20 - Danger -(10 - hub.getMins());
		return (scoore > 0)?scoore:0;
	}
	@Override
	public int getExper() {
		return (((getScore()>0)?getScore()*2:0) + PointExper)*CrachGame.getXPBoost();
	}

	@Override
	public int getStars() {
		return (int)MathUtils.map(0, getRecord(), 1, 6, getScore());
	}

	@Override
	public int getRecord() {
		return tileMap.getProperties().get("score", 100, Integer.class);
	}

	@Override
	public int getCoin() {
		return nCoin;
	}

	@Override
	public void addScore(int score) {
		PointScore += score;
		hub.updatePointScore(PointScore);
	}

	@Override
	public void addExper(int expertion) {
		 PointExper += expertion;
	}

	@Override
	public void addDanger(int danger) {
		Danger +=danger;
		hub.updatePHealth(player.getLife());
	}
	@Override
	public void addCoin(int coin) {
		nCoin += coin; 
	}

	@Override
	public void addHp(int hp) {
		nHp +=hp;
	}

	@Override
	public int getHp() {
		return nHp;
	}

	@Override
	public boolean isContinue() {
		switch (modeGame){
			case deathmatch:
				return  false;
			case football:
				return (CrachGame.getLengthMatch() > nStage+1);
			case simple:
				return (CrachGame.getLengthStage() > nStage+1);
		}
		return false;
	}
}
