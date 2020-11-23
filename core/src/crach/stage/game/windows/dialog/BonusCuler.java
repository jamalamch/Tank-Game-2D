package crach.stage.game.windows.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;

public class BonusCuler extends DialogGame {
    private Button Culer;
    private int gift;
    public BonusCuler(String message, int gift, String drawable) {
        super(Assets.jsonStringDialog.getString("BonusNive"), "special-dialog2", null, false);
        this.gift=gift;
        getTitleTable().padBottom(-60).setWidth(120);
        if(drawable != null)
            getContentTable().add(new Image(getSkin(),drawable)).padTop(30).row();
        getContentTable().add(new Label(message, getSkin(), "font", Color.SKY)).row();
        getContentTable().add(new Label(gift+"*", getSkin(), "numberFont","diamondColor")).padTop(-55).row();
        Culer = new TextButton(Assets.jsonStringBonus.getString("Culer"),getSkin());
        getButtonTable().defaults().pad(5,40,5,40).maxHeight(100).fillX();
        button(Culer,true);
        pack();
    }
    public BonusCuler(String message,int gift) {
        this(message,gift,null);
    }

    @Override
    protected void result(Object object) {
        Assets.soundBonus2.play();
        CrachGame.addDiamound(gift);
    }
}