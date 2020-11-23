package crach.stage.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class Text {
    private String text;
    private float textWidth;
    private float textHeight;
    private float x;
    private float y;
    private boolean visible;
    
    GlyphLayout layout;

    public Text() {
    }
    public Text(String text,float x,float y) {
    	this(text);
    	this.x=x;
    	this.y=y;
    	this.visible =true;
    }
    public Text(String text) {
        if (this.text == null || !this.text.equals(text)) {
            setText(text);
        }
    }

    public Text setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Text setText(String text) {
        this.text = text;

        if (text == null) {
            textWidth = 0;
            textHeight = 0;
        } else {
            layout = new GlyphLayout(Assets.font, text,Color.BLACK,12/CrachGame.PPM, Align.center, false);
            textWidth = layout.width;
            textHeight = layout.height;

        }

        return this;
    }

    public void draw(Batch batch, float x, float y) {
        if (visible && text != null) {
            Assets.font.draw(batch, text, x - (textWidth/2), y+textHeight/2,textWidth/CrachGame.PPM, Align.center, false);
        }
    }
    public void draw(Batch batch) {
        if (visible && text != null) {
        	
        	Assets.font.draw(batch,layout, x ,y +textHeight/2);
        }
    }
}
