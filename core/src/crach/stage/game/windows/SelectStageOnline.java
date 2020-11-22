package crach.stage.game.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.screen.PlayScreen;
import crach.stage.game.windows.dialog.WaitDowload;

public class SelectStageOnline extends WindowsGames {
    private ButtonGroup<StageOnline> Stages;
    private Array<String> nameStage;
    private PlayScreen.ModeGame modeGame;
    private ScrollPane Scrll;

    public SelectStageOnline(){
        super(Assest.StringSelectStage.getString("title"));
        Stages = new ButtonGroup<StageOnline>();
        nameStage = CrachGame.getDownloadFiles().getListeNameFiles();
        if(nameStage.size>1)
            addContent();
        else
            add("erour in Connection");
    }
    @Override
    public void addContent() {
        padLeft(60);padRight(60);
        Table table = new Table();
        table.defaults().space(20).size(150, 170);
        StageOnline s;
        for(int i=0;i<nameStage.size;i++) {
            s = new StageOnline(nameStage.get(i));
            Stages.add(s);
            table.add(s);
            if ((i + 1) % 5 == 0)
                table.row().spaceTop(40);
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

    public class StageOnline extends TextButton {
        String Url ;
        String name;
        public StageOnline(String url){
            super("D", Assest.Style, "stage");
            this.Url = url;
            this.name = url.split("/")[1];
            this.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    getStage().addActor(new WaitDowload(Url){
                        @Override
                        public void onSuccess() {
                            Gdx.app.log("Firebase","Success");
                            CrachGame.getGdxGame().setScreen(new PlayScreen(Assest.loadMaps(this.getFile()),modeGame));
                        }
                        @Override
                        public void onFailure() {
                            Gdx.app.log("Firebase","Failure");
                        }
                    });
                }
            });
        }
    }
}
