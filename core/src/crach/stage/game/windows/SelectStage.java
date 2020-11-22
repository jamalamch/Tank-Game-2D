package crach.stage.game.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.Screen.MenuUI;
import crach.stage.game.Screen.PlayScreen;
import crach.stage.game.windows.dialog.SelectLevel;
import crach.stage.game.windows.dialog.WaitDowload;

public class SelectStage extends WindowsGames{
	private ButtonGroup<stage> Stages ;
	private int [][] stage;
	private PlayScreen.ModeGame modeGame;
	private ScrollPane Scrll;
	public SelectStage() {
		this(Assest.StringSelectStage.getString("title"),CrachGame.getStage(),PlayScreen.ModeGame.simple);
	}
	public SelectStage(String title,int[][] stage,PlayScreen.ModeGame modeGame) {
		super(title);
		this.stage = stage;
		this.modeGame = modeGame;
		this.getTitleTable().getCell(getTitleLabel()).maxSize(650,100).padBottom(30);
		Stages =new ButtonGroup<SelectStage.stage>();
		addContent();
		AddListener();
	}
	@Override
	public void addContent() {
		padLeft(60);padRight(60);

        Table table = new Table();
		table.defaults().space(20).size(150, 170);
        int i;
        boolean stageActive = true;
        for(i=0;i<stage.length;i++) {
        	stage s = new stage(i,stageActive);
        	Stages.add(s);
			table.add(s);
        	if((i+1) % 5 == 0)
        		table.row().spaceTop(40);
        	if(stageActive && stage[i][1] == 0) stageActive = false;
        }
		Scrll = new ScrollPane(table, new ScrollPane.ScrollPaneStyle());
		Scrll.setScrollingDisabled(true, false);
		Scrll.setFadeScrollBars(false);
		add().height(40).row();
		add(Scrll).expandX().height(450);
        row();
        add().height(35);
        pack();
	}
	
	@Override
	protected void AddListener() {
		super.AddListener();
		ClickListener C = new ClickListener() {
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  				Assest.buttonClick1.play();
  				final stage stage = ((stage) (event.getListenerActor()));
  				getStage().addActor(new SelectLevel(stage.nStage ,stage.Score,stage.Record,stage.nstar) {
					
					@Override
					protected void Play() {
		  				CrachGame.getGdxGame().setScreen(new PlayScreen(stage.nStage,modeGame));
					}
					
					@Override
					protected void Hangar() {
						((MenuUI)CrachGame.getGdxGame().getScreen()).getHanger().Open();
					}
				});
			};
		};
		for(stage s : Stages.getButtons())
			s.addListener(C);
	}
	public void update() {
		for(stage s : Stages.getButtons())
			s.update();
	}
	public class stage extends TextButton{
		private int nstar;
		private int nStage;
		private int Score;
		private int Record;
		Image Istar[] = new Image[3];

		public stage(int number,boolean active) {
			super((active)?number+1+"":"?", Assest.Style, "stage");
			this.getLabelCell().top();
			this.nStage = number;			
			this.Istar[0] = new Image(getDrawableStar(0));
			this.Istar[1] = new Image(getDrawableStar(0));
			this.Istar[2] = new Image(getDrawableStar(0));
			addContent();
			if(!active) {
				this.setDisabled(true);
				this.setTouchable(Touchable.disabled);
			}
		}
		private void addContent() {
			Table Stars = new Table();
			Stars.setBackground(getSkin().getDrawable("Upgrade_Level_Table"));
			Stars.add(Istar[0],Istar[1],Istar[2]).pack();
			defaults().top();
			row();
			add(Stars).pad(0,-20,-60,-20);
			pack();
		}
		private Drawable getDrawableStar(int valueStar) {
			if(valueStar <= 0)
				return getSkin().getDrawable("Icon/StarSmall03");
			if(valueStar <= 1)
				return getSkin().getDrawable("Icon/StarSmall02");
						return getSkin().getDrawable("Icon/StarSmall01");
		}
		private Action updateStar(final int indexStar,final int nStar) {
			final stage table = this;
			return  Actions.run(new Runnable() {
				@Override
				public void run() {
					if(nStar>0) {
					Istar[indexStar].setDrawable(getDrawableStar(nStar));
					if(indexStar<2) {
						table.addAction(updateStar( indexStar+1, nStar-2));
						}
					}	
				}
			});
		}
		private boolean update() {
			this.Score= stage[nStage][0];
			this.nstar = stage[nStage][1];
			if(this.isDisabled() && stage[nStage-1][1] > 0) {
				this.setDisabled(false);
				this.setTouchable(Touchable.enabled);
				this.setText(this.nStage+1+"");
			}
			this.addAction(updateStar(0,nstar));
			return nstar > 0;
		}
	}
	@Override
	public void resize() {
		super.resize();
		this.PoseY -= 20;
	}
}
